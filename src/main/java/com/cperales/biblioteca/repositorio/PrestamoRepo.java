package com.cperales.biblioteca.repositorio;

import com.cperales.biblioteca.modelo.Prestamo;
import com.cperales.biblioteca.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrestamoRepo extends JpaRepository<Prestamo, Integer> {
    List<Prestamo> findByUsuario(Usuario usuario);
}
