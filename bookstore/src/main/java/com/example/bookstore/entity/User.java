package com.example.bookstore.entity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;

    @Column(nullable = false,length =50,unique = true)
    private String username ;

    @Column(nullable = false,length = 100,unique=true)
    private String email ;

    @Column(nullable = false)
    private String password ;

    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private Set<Roles> roles ;

    @Column(name = "updated_at")
    private LocalDateTime updatedat;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdat;

    @PrePersist
    protected void onCreate() {
        updatedat = LocalDateTime.now();
        createdat = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedat = LocalDateTime.now();
    }

    public void addRole(Roles role) {
        roles.add(role);
        role.getUsers().add(this);
    }

    public void removeRole(Roles role) {
        roles.remove(role);
        role.getUsers().remove(this);
    }
}
