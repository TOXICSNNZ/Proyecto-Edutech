package com.edutech.cl.edutech.assemblers;

import com.edutech.cl.edutech.controller.GerenteControllerV2;
import com.edutech.cl.edutech.model.Gerente;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class GerenteModelAssembler implements RepresentationModelAssembler<Gerente, EntityModel<Gerente>> {

    @Override
    public EntityModel<Gerente> toModel(Gerente gerente) {
        return EntityModel.of(gerente,
                linkTo(methodOn(GerenteControllerV2.class).getGerenteById(gerente.getId())).withSelfRel(),
                linkTo(methodOn(GerenteControllerV2.class).getAllGerentes()).withRel("gerentes"));
    }
}
