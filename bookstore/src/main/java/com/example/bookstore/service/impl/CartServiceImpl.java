package com.example.bookstore.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bookstore.DTO.request.AddToCartRequest;
import com.example.bookstore.DTO.request.UpdateCartItemRequest;
import com.example.bookstore.DTO.respones.CartResponse;
import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.Cart;
import com.example.bookstore.entity.CartItem;
import com.example.bookstore.entity.User;
import com.example.bookstore.exception.InsufficientStockException;
import com.example.bookstore.exception.InvalidOperationException;
import com.example.bookstore.exception.ResourceNotFoundException;
import com.example.bookstore.mapper.CartMapper;
import com.example.bookstore.repository.CartItemRepository;
import com.example.bookstore.repository.CartRepository;
import com.example.bookstore.service.BookService;
import com.example.bookstore.service.CartService;
import com.example.bookstore.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service 
@RequiredArgsConstructor 
@Slf4j 
public class CartServiceImpl implements CartService {

    private  final  CartRepository cartRepository;
    private  final  CartItemRepository cartItemRepository;
    private  final UserService userService;
    private  final  BookService bookService;
    private  final  CartMapper cartMapper;


    @Override 
    @Transactional 
    public  CartResponse getCartForUser(Long userId){
        Cart cart = getOrCreateCart(userId);
        return cartMapper.toResponse(cart);

    }
    
    @Override 
    @Transactional 
    public CartResponse addItem(Long userId , AddToCartRequest request){
        Cart cart = getOrCreateCart(userId);
        Book book = bookService.getEntityById(request.bookId());

        if (book.getStockQuantity()<request.quantity()) {
            throw new InsufficientStockException(book.getTitle(),book.getStockQuantity(), request.quantity());

            
        }
        Optional<CartItem> existing =  cartItemRepository.findByCartIdAndBookId(cart.getId(), book.getId());

        CartItem item;
        if(existing.isPresent()){
            item = existing.get();

            int newQty = item.getQuantity() + request.quantity();
            if (book.getStockQuantity() < newQty) {
                throw new InsufficientStockException(book.getTitle(), book.getStockQuantity(), newQty);

                
            }
            item.setQuantity(newQty);
        }
        else{
            item = CartItem.builder().cart(cart)
                    .book(book)
                    .quantity(request.quantity())
                    .build();
            cart.getCartItems().add(item);

        }


        cartItemRepository.save(item);
        log.info("User {} added book id = {} qty ={} to cart " , userId , book.getId() , request.quantity());
        return cartMapper.toResponse(cart);
    }

    @Override 
    @Transactional 
     public  CartResponse updateItemQuantity(Long userId , Long cartItemId, 
                                                UpdateCartItemRequest request){

                                                    Cart cart = getOrCreateCart(userId);
                                                    CartItem item = cartItemRepository.findById(cartItemId)
                                                                    .orElseThrow(()-> new ResourceNotFoundException("CartItem", "id", cartItemId));


                                                    verifyItemBelongsToUser(item, cart);

                                                    Book book = item.getBook();
                                                    if (book.getStockQuantity() < request.quantity()) {
                                                        throw new InsufficientStockException(book.getTitle(), book.getStockQuantity(), request.quantity());
                                                        
                                                    }

                                                    item.setQuantity(request.quantity());
                                                    cartItemRepository.save(item);
                                                    return cartMapper.toResponse(cart);
                                                }

    @Override 
    @Transactional 
    public CartResponse removeItem(Long userId , Long cartItemId){
        Cart cart = getOrCreateCart(userId);
        CartItem item = cartItemRepository.findById(cartItemId)
                        .orElseThrow(()-> new ResourceNotFoundException("CartItem", "id", cartItemId));

        verifyItemBelongsToUser(item, cart);

        cart.getCartItems().remove(item);
        cartItemRepository.delete(item);
        return cartMapper.toResponse(cart);
    }

    @Override 
    @Transactional 
    public void  clearCart(Long userId){
        cartRepository.findByUserId(userId).ifPresent(cart -> {
            cartItemRepository.deleteByCartId(cart.getId());
            cart.getCartItems().clear();
        });
    }

    @Override 
    @Transactional 
    public Cart getEntityByUserId(Long userId){
        return cartRepository.findByUserId(userId)
                .orElseThrow(()-> new ResourceNotFoundException("Cart", "userId", userId));

    }

    private Cart getOrCreateCart(Long userId){
        return cartRepository.findByUserId(userId).orElseGet(()-> {
            User user = userService.getEntityById(userId);
            Cart newCart = Cart.builder().user(user).build();
            return cartRepository.save(newCart);
        });
    }

    private  void verifyItemBelongsToUser(CartItem item , Cart cart){
        if (!item.getCart().getId().equals(cart.getId())) {
            throw new InvalidOperationException("Cart item does not belong to this user ");
            
        }
    }
     
}
