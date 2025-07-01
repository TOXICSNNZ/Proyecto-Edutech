package com.edutech.cl.edutech.service;

import com.edutech.cl.edutech.model.Gerente;
import com.edutech.cl.edutech.repository.GerenteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class GerenteService {

    @Autowired
    private GerenteRepository gerenteRepository;

    public List<Gerente> findAll() {
        return gerenteRepository.findAll();
    }

    public Gerente findById(int id) {
        return gerenteRepository.findById(id).get();
    }

    public Gerente save(Gerente gerente) {
        return gerenteRepository.save(gerente);
    }

    public void delete(Integer id) {
        gerenteRepository.deleteById(id);
    }
}

