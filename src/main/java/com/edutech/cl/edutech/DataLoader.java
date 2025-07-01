package com.edutech.cl.edutech;

import com.edutech.cl.edutech.model.*;
import com.edutech.cl.edutech.repository.*;

import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Random;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private GerenteRepository gerenteRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();

        // Crear Usuarios
        for (int i = 0; i < 20; i++) {
            Usuario usuario = new Usuario();
            usuario.setNombre(faker.name().fullName());
            usuario.setEmail(faker.internet().emailAddress());
            usuario.setContrasena(faker.internet().password());
            usuarioRepository.save(usuario);
        }

        // Crear Administradores
        for (int i = 0; i < 5; i++) {
            Administrador admin = new Administrador();
            admin.setEmail(faker.internet().emailAddress());
            admin.setContrasena(faker.internet().password());
            administradorRepository.save(admin);
        }

        // Crear Gerentes
        for (int i = 0; i < 5; i++) {
            Gerente gerente = new Gerente();
            gerente.setNombre(faker.name().fullName());
            gerente.setEmail(faker.internet().emailAddress());
            gerente.setContrasena(faker.internet().password());
            gerenteRepository.save(gerente);
        }

        // Crear Instructores
        for (int i = 0; i < 10; i++) {
            Instructor instructor = new Instructor();
            instructor.setNombre(faker.name().fullName());
            instructor.setEmail(faker.internet().emailAddress());
            instructor.setContrasena(faker.internet().password());
            instructor.setCurso(faker.educator().course());
            instructor.setGerente(random.nextInt(5) + 1);
            instructorRepository.save(instructor);
        }
    }
}
