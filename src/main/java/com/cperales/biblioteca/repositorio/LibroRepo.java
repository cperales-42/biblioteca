package com.cperales.biblioteca.repositorio;

import com.cperales.biblioteca.modelo.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepo extends JpaRepository<Libro, Integer> {

    // Buscar libros por título (contiene el texto, sin diferenciar mayúsculas/minúsculas)
    List<Libro> findByTituloContainingIgnoreCase(String titulo);

    // Buscar libros por autor (contiene el texto, case insensitive)
    List<Libro> findByAutorContainingIgnoreCase(String autor);

    // Buscar libros por título y autor al mismo tiempo
    List<Libro> findByTituloContainingIgnoreCaseAndAutorContainingIgnoreCase(String titulo, String autor);
}
