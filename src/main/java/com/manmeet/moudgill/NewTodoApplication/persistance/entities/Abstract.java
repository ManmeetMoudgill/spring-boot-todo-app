package com.manmeet.moudgill.NewTodoApplication.persistance.entities;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
public class Abstract implements Serializable {

    @Column(nullable = false)
    protected LocalDateTime createdAt;

    protected LocalDateTime updatedAt;


}
