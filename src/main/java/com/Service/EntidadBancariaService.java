package com.Service;

import com.Model.EntidadBancaria;
import com.Repository.EntidadBancariaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class EntidadBancariaService {

    @Autowired
    private EntidadBancariaRepository repository;

    public List<EntidadBancaria> obtenerTodas() {
        List<EntidadBancaria> entidades = repository.findAll();

        if (entidades == null || entidades.isEmpty()) {
            return Collections.emptyList();
        }

        return entidades;
    }

    public EntidadBancaria obtenerPorId(Long id) {
        Optional<EntidadBancaria> entidadOptional = repository.findById(id);

        if (entidadOptional.isPresent()) {
            return entidadOptional.get();
        } else {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró la entidad bancaria con el ID especificado.");

        }
    }
    public EntidadBancaria crear(EntidadBancaria entidad) {
        if (entidad.getId() != null && repository.existsById(entidad.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ya existe una entidad con ese ID");
        }
        return repository.save(entidad);
    }

    public EntidadBancaria actualizar(Long id, EntidadBancaria entidad) {
        EntidadBancaria entidadExistente = repository.findById(id).orElse(null);
        if (entidadExistente != null) {
            entidadExistente.setDireccion(entidad.getDireccion());
            entidadExistente.setTelefono(entidad.getTelefono());
            return repository.save(entidadExistente);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entidad bancaria no encontrada");
        }
    }

    public void eliminar(Long id) {
        EntidadBancaria entidad = repository.findById(id).orElse(null);
        if (entidad == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entidad bancaria no encontrada");
        }
        else {
        repository.deleteById(id);
        }
        
    }
}
