package com.edutech.cl.edutech.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "administradores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Administrador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;
    private String contrasena;
}