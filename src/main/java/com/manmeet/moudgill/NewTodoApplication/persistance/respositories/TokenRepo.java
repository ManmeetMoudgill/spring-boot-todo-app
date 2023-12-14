package com.manmeet.moudgill.NewTodoApplication.persistance.respositories;

import com.manmeet.moudgill.NewTodoApplication.persistance.entities.Token;
import com.manmeet.moudgill.NewTodoApplication.persistance.queries.TokenQueries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface TokenRepo extends JpaRepository<Token, Long> {


    @Query(TokenQueries.FIND_TOKEN_BY_USER_ID_AND_BY_TOKEN)
    Optional<Token> findByUserIdAndByTokenContent(@Param("userId") Long userId, @Param("tokenContent") String tokenContent);

}
