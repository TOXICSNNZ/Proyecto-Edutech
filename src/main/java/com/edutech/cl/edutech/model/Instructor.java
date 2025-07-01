package com.edutech.cl.edutech.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "instructores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    private String email;
    private String contrasena;
    private String curso;
    private int gerente;
}

