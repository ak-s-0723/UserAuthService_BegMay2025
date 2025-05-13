package org.example.userauthservice_begmay2025.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class UserSession extends BaseModel {

    @ManyToOne
    private User user;

    private String token;
}
