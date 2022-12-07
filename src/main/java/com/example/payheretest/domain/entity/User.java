package com.example.payheretest.domain.entity;

import com.example.payheretest.domain.response.UserResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Setter(AccessLevel.NONE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity {
    private String email;
    private String hashedPassword;
    private String name;


    public UserResponse toResponse() {
        return UserResponse.builder()
                .id(super.getId())
                .email(email)
                .name(name)
                .build();
    }

    public void updateName(String name) {
        this.name = name;
    }
}
