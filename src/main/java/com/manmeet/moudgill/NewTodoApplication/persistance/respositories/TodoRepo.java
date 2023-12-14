package com.manmeet.moudgill.NewTodoApplication.persistance.respositories;

import com.manmeet.moudgill.NewTodoApplication.persistance.entities.Todo;
import com.manmeet.moudgill.NewTodoApplication.persistance.queries.TodoQueries;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface TodoRepo extends JpaRepository<Todo, Long> {

    @Query(TodoQueries.GET_TODOS_OF_USER_BY_USERID)
    Page<Todo> getTodosOfUserByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query(TodoQueries.GET_SINGLE_TODO_OF_USER_BY_USERID)
    Optional<Todo> getSingleTodoOfUserByUserId(@Param("todoId") Long todoId, @Param("userId") Long userId);
}
