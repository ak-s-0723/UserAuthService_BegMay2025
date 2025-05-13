package org.example.userauthservice_begmay2025.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Entity
public class User extends BaseModel {
    private String email;

    private String password;

    public User() {
        this.setState(State.ACTIVE);
        this.setCreatedAt(new Date());
        this.setLastUpdatedAt(new Date());
    }
}
