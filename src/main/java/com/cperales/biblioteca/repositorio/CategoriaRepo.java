package com.cperales.biblioteca.repositorio;

import com.cperales.biblioteca.modelo.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Repositorio para manejar operaciones sobre categorías
public interface CategoriaRepo extends JpaRepository<Categoria, Integer> {

    // Buscar categoría por su nombre
    Optional<Categoria> findByNombre(String nombre);
}
