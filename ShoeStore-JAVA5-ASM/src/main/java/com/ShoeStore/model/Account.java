package com.ShoeStore.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userCode;

    @Column(unique = true)
    private String email;

    private String password;

    private String fullName;

    private String phone;

    private String role;

    private Integer status;
}