package com.JFSEI.Storage_Management_System.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "login")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Login {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean status;

}
