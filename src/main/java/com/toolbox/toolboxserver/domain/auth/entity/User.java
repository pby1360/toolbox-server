package com.toolbox.toolboxserver.domain.auth.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class User implements Persistable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String name;
    private String joinPath;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime modifiedAt;
    @Transient
    private boolean isNew = false;

    public User() {};

    public static User createUser (String email, String name, String joinPath) {
        User newUser = new User();
        newUser.email = email;
        newUser.name = name;
        newUser.joinPath = joinPath;
        newUser.isNew = true;
        return newUser;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getJoinPath() {
        return joinPath;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }
}
