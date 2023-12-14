package com.manmeet.moudgill.NewTodoApplication.persistance.entityListeners;

import com.manmeet.moudgill.NewTodoApplication.persistance.entities.Abstract;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

public class AbstractListener {


    @PrePersist
    public void setCreatedAt(Abstract abstractEntity) {
        abstractEntity.setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void setUpdatedAt(Abstract abstractEntity) {
        abstractEntity.setUpdatedAt(LocalDateTime.now());
    }

}
