package com.manmeet.moudgill.NewTodoApplication.persistance.entities;

import com.manmeet.moudgill.NewTodoApplication.enums.Role;
import com.manmeet.moudgill.NewTodoApplication.persistance.entityListeners.AbstractListener;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EntityListeners({AbstractListener.class})
@Table(name = "users")
public class User extends Abstract implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, length = 50, unique = true)
    private String username;

    @Column(nullable = false, unique = true, length = 60)
    private String email;


    @Column(nullable = false, length = 300)
    private String password;

    @Column(nullable = false, length = 300)
    private String confirmPassword;


    @Enumerated(EnumType.STRING)
    private Role userRole;


    @OneToMany(mappedBy = "todoUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Todo> userTodos = new ArrayList<>();


    @OneToMany(mappedBy = "tokenUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Token> userTokens = new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.userRole.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
