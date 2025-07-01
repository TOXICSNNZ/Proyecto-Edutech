package com.edutech.cl.edutech.assemblers;

import com.edutech.cl.edutech.controller.UsuarioControllerV2;
import com.edutech.cl.edutech.model.Usuario;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UsuarioModelAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {

    @Override
    public EntityModel<Usuario> toModel(Usuario usuario) {
        return EntityModel.of(usuario,
                linkTo(methodOn(UsuarioControllerV2.class).getUsuarioById(usuario.getId())).withSelfRel(),
                linkTo(methodOn(UsuarioControllerV2.class).getAllUsuarios()).withRel("usuarios"));
    }
}

