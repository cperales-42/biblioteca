package com.cperales.biblioteca.repositorio;

import com.cperales.biblioteca.modelo.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// Repositorio para gestionar los libros en la base de datos
public interface LibroRepo extends JpaRepository<Libro, Integer> {

    // Buscar libros cuyo título contenga una cadena, sin importar mayúsculas/minúsculas
    List<Libro> findByTituloContainingIgnoreCase(String titulo);

    // Buscar libros por autor, ignorando mayúsculas/minúsculas
    List<Libro> findByAutorContainingIgnoreCase(String autor);
}
