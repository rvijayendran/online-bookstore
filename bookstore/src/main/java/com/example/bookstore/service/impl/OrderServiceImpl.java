package com.example.bookstore.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bookstore.DTO.request.OrderItemRequest;
import com.example.bookstore.DTO.request.OrderRequest;
import com.example.bookstore.DTO.respones.OrderResponse;
import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.Order;
import com.example.bookstore.entity.OrderItem;
import com.example.bookstore.entity.OrderStatus;
import com.example.bookstore.entity.User;
import com.example.bookstore.exception.InsufficientStockException;
import com.example.bookstore.exception.InvalidOperationException;
import com.example.bookstore.exception.ResourceNotFoundException;
import com.example.bookstore.mapper.OrderMapper;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.OrderRepository;
import com.example.bookstore.service.BookService;
import com.example.bookstore.service.CartService;
import com.example.bookstore.service.NotificationService;
import com.example.bookstore.service.OrderService;
import com.example.bookstore.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service 
@RequiredArgsConstructor 
@Slf4j 
public class OrderServiceImpl implements OrderService {


        private final OrderRepository orderRepository;
        private  final BookRepository bookRepository;
        private  final UserService userService;
        private  final BookService bookService;
        private  final CartService cartService;
        private  final NotificationService notificationService;
        private  final OrderMapper orderMapper;

        @Override 
        @Transactional 
        public OrderResponse placeOrder(Long userId , OrderRequest request ){
                User user = userService.getEntityById(userId);

                Order order = Order.builder()
                                .user(user)
                                .status(OrderStatus.PENDING)
                                .totalAmount(BigDecimal.ZERO)
                                .build();
                    
                List<OrderItem> items = new ArrayList<>();
                BigDecimal total = BigDecimal.ZERO;


                for(OrderItemRequest itemReq :  request.items()){
                    Book book = bookRepository.findById(itemReq.bookId())
                                    .orElseThrow(()-> new ResourceNotFoundException("book","id", itemReq.bookId()));

                    if (book.getStockQuantity() < itemReq.quantity()) {
                        throw new InsufficientStockException(book.getTitle(), book.getStockQuantity(), itemReq.quantity());

                        
                    }

                    book.setStockQuantity(book.getStockQuantity() - itemReq.quantity());

                    bookRepository.save(book);

                    OrderItem item = OrderItem.builder()
                                    .order(order)
                                    .book(book)
                                    .quantity(itemReq.quantity())
                                    .priceAtPurchase(book.getPrice())
                                    .build();
                    items.add(item);

                    total = total.add(
                                    book.getPrice().multiply(BigDecimal.valueOf(itemReq.quantity()))
                                    );
                }

                order.setOrderItems(items);
                order.setTotalAmount(total);

                Order saved = orderRepository.save(order);

                log.info("Order placed id={} , user={} , total={}", saved.getId(), user.getUsername() , total);

                cartService.clearCart(userId);

                notificationService.sendOrderConfirmation(user.getEmail(), saved.getId(), total);

                return orderMapper.toResponse(saved);

            
        }


    @Override 
    @Transactional 
    public Page<OrderResponse> getOrderHistory(Long userId , Pageable pageable){
        return orderRepository.findByUserId(userId, pageable)
                .map(orderMapper::toResponse);

    }

    @Override 
    @Transactional(readOnly = true)
    public OrderResponse getOrderById(Long userId , Long orderId){
        Order order = getEntityById(orderId);
        if (!Long.valueOf(order.getUser().getId()).equals(userId)) {

            throw new  InvalidOperationException("Order does not belong to this  user ");

            
        }
        return orderMapper.toResponse(order);
    }

    @Override 
    @Transactional(readOnly = true)
    public Order getEntityById(Long orderId){
        return  orderRepository.findById(orderId)
                    .orElseThrow(() -> new ResourceNotFoundException("Order", "id",orderId));
                    
    }

    
}
