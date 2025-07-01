package com.edutech.cl.edutech.service;

import com.edutech.cl.edutech.model.Administrador;
import com.edutech.cl.edutech.repository.AdministradorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class AdministradorService {

    @Autowired
    private AdministradorRepository administradorRepository;

    public List<Administrador> findAll() {
        return administradorRepository.findAll();
    }

    public Administrador findById(int id) {
        return administradorRepository.findById(id).get();
    }

    public Administrador save(Administrador administrador) {
        return administradorRepository.save(administrador);
    }

    public void delete(Integer id) {
        administradorRepository.deleteById(id);
    }
}
