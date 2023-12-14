package com.manmeet.moudgill.NewTodoApplication.persistance.respositories;

import com.manmeet.moudgill.NewTodoApplication.persistance.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}
