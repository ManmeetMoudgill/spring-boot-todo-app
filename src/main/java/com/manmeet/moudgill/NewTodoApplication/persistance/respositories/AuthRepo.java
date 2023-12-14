package com.manmeet.moudgill.NewTodoApplication.persistance.respositories;

import com.manmeet.moudgill.NewTodoApplication.persistance.entities.User;
import com.manmeet.moudgill.NewTodoApplication.persistance.queries.AuthQueries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


public interface AuthRepo extends JpaRepository<User, Long> {


    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    @Transactional
    @Modifying
    @Query(AuthQueries.UPDATE_THE_USER_PASSWORD)
    void updatePassword(@Param("username") String username, @Param("newPassword") String newPassword, @Param("newConfirmPassword")
    String newConfirmPassword);


}
