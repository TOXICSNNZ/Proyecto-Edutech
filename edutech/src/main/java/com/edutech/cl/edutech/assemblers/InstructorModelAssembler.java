package com.edutech.cl.edutech.assemblers;

import com.edutech.cl.edutech.controller.InstructorControllerV2;
import com.edutech.cl.edutech.model.Instructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class InstructorModelAssembler implements RepresentationModelAssembler<Instructor, EntityModel<Instructor>> {

    @Override
    public EntityModel<Instructor> toModel(Instructor instructor) {
        return EntityModel.of(instructor,
                linkTo(methodOn(InstructorControllerV2.class).getInstructorById(instructor.getId())).withSelfRel(),
                linkTo(methodOn(InstructorControllerV2.class).getAllInstructores()).withRel("instructores"));
    }
}
