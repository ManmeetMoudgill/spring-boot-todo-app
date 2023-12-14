package com.manmeet.moudgill.NewTodoApplication.persistance.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "tokens")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;


    @Column(nullable = false, length = 300)
    private String tokenContent;


    @Column(nullable = false)

    private LocalDateTime tokenCreatedAt;


    private LocalDateTime tokenExpiredAt;

    @PrePersist
    public void initToken() {
        this.tokenCreatedAt = LocalDateTime.now();
        this.isTokenActive = true;
    }


    @Column(name = "is_token_active")
    public boolean isTokenActive;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User tokenUser;
}
