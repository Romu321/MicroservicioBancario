package com.Service;

import com.Model.EntidadBancaria;

import com.Repository.EntidadBancariaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EntidadBancariaServiceTest {

    @Mock
    private EntidadBancariaRepository repository;

    @InjectMocks
    private EntidadBancariaService service;

    @Test
    void obtenerTodas_conEntidades_retornaListaNoVacia() {

        EntidadBancaria entidad = new EntidadBancaria();
        entidad.setNombre("Banco Ejemplo");
        List<EntidadBancaria> entidades = Collections.singletonList(entidad);

        when(repository.findAll()).thenReturn(entidades);


        List<EntidadBancaria> resultado = service.obtenerTodas();


        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("Banco Ejemplo", resultado.get(0).getNombre());
    }

    @Test
    void obtenerTodas_sinEntidades_retornaListaVacia() {

        when(repository.findAll()).thenReturn(Collections.emptyList());
        List<EntidadBancaria> resultado = service.obtenerTodas();
        assertTrue(resultado.isEmpty());
    }

    @Test
    void obtenerPorId_conIdExistente_retornaEntidad() {

        EntidadBancaria entidad = new EntidadBancaria();
        entidad.setId(1L);
        entidad.setNombre("Banco Ejemplo");
        when(repository.findById(1L)).thenReturn(Optional.of(entidad));
        EntidadBancaria resultado = service.obtenerPorId(1L);
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Banco Ejemplo", resultado.getNombre());
    }

    @Test
    void obtenerPorId_conIdInexistente_lanzaExcepcion() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> service.obtenerPorId(1L));
    }

    @Test
    void crear_conIdNoExistente_retornaEntidadCreada() {

        EntidadBancaria entidad = new EntidadBancaria();
        entidad.setNombre("Banco Ejemplo");

        EntidadBancaria entidadGuardada = new EntidadBancaria();
        entidadGuardada.setId(1L);
        entidadGuardada.setNombre("Banco Ejemplo");

        when(repository.save(any(EntidadBancaria.class))).thenReturn(entidadGuardada);

        EntidadBancaria resultado = service.crear(entidad);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Banco Ejemplo", resultado.getNombre());
    }

    @Test
    void crear_conIdExistente_lanzaExcepcion() {

        EntidadBancaria entidad = new EntidadBancaria();
        entidad.setId(1L);
        entidad.setNombre("Banco Ejemplo");

        when(repository.existsById(1L)).thenReturn(true);

        assertThrows(ResponseStatusException.class, () -> service.crear(entidad));
    }

    @Test
    void actualizar_conIdExistente_actualizaEntidad() {

        EntidadBancaria entidadExistente = new EntidadBancaria();
        entidadExistente.setId(1L);
        entidadExistente.setNombre("Banco Ejemplo");
        entidadExistente.setDireccion("Dirección Antigua");
        entidadExistente.setTelefono("123456789");

        EntidadBancaria entidadActualizada = new EntidadBancaria();
        entidadActualizada.setDireccion("Dirección Nueva");
        entidadActualizada.setTelefono("987654321");

        when(repository.findById(1L)).thenReturn(Optional.of(entidadExistente));
        when(repository.save(any(EntidadBancaria.class))).thenReturn(entidadExistente); //Simula la actualización

        EntidadBancaria resultado = service.actualizar(1L, entidadActualizada);

        assertNotNull(resultado);
        assertEquals("Dirección Nueva", resultado.getDireccion());
        assertEquals("987654321", resultado.getTelefono());
    }

    @Test
    void actualizar_conIdInexistente_lanzaExcepcion() {

        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.actualizar(1L, new EntidadBancaria()));
    }


    @Test
    void eliminar_conIdExistente_eliminaEntidad() {

        EntidadBancaria entidad = new EntidadBancaria();
        entidad.setId(1L);
        entidad.setNombre("Banco Ejemplo");

        when(repository.findById(1L)).thenReturn(Optional.of(entidad));

        service.eliminar(1L);

    }

    @Test
    void eliminar_conIdInexistente_lanzaExcepcion() {

        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.eliminar(1L));
    }
}
