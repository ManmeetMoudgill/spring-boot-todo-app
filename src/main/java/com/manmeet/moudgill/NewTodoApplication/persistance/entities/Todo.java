package com.manmeet.moudgill.NewTodoApplication.persistance.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Table(name = "todos")
@NoArgsConstructor
@Getter
@Setter
public class Todo {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long todoId;


    @Column(name = "todo_title", nullable = false, length = 100)
    private String todoTitle;


    @Column(name = "todo_description", nullable = false, length = 255)
    private String todoDescription;

    @Column(name = "todo_created_at", nullable = false)
    private LocalDateTime todoCreatedDate;

    @Column(name = "todo_completed_at")
    private LocalDateTime todoCompletedDate;

    @PrePersist
    private void initCreatedDate() {
        this.todoCreatedDate = LocalDateTime.now();
    }


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User todoUser;


}
