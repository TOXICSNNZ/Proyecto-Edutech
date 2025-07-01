package com.edutech.cl.edutech.service;

import com.edutech.cl.edutech.model.Instructor;
import com.edutech.cl.edutech.repository.InstructorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class InstructorService {

    @Autowired
    private InstructorRepository instructorRepository;

    public List<Instructor> findAll() {
        return instructorRepository.findAll();
    }

    public Instructor findById(int id) {
        return instructorRepository.findById(id).get();
    }

    public Instructor save(Instructor instructor) {
        return instructorRepository.save(instructor);
    }

    public void delete(Integer id) {
        instructorRepository.deleteById(id);
    }
}
