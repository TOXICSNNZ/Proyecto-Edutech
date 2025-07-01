package com.edutech.cl.edutech.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "gerentes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Gerente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    private String email;
    private String contrasena;
}
