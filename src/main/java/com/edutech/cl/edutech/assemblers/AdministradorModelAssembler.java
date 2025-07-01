package com.edutech.cl.edutech.assemblers;

import com.edutech.cl.edutech.controller.AdministradorControllerV2;
import com.edutech.cl.edutech.model.Administrador;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class AdministradorModelAssembler implements RepresentationModelAssembler<Administrador, EntityModel<Administrador>> {

    @Override
    public EntityModel<Administrador> toModel(Administrador admin) {
        return EntityModel.of(admin,
                linkTo(methodOn(AdministradorControllerV2.class).getAdministradorById(admin.getId())).withSelfRel(),
                linkTo(methodOn(AdministradorControllerV2.class).getAllAdministradores()).withRel("administradores"));
    }
}

