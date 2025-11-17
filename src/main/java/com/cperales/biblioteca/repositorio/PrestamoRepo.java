package com.cperales.biblioteca.repositorio;

import com.cperales.biblioteca.modelo.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// Repositorio para manejar préstamos de libros
public interface PrestamoRepo extends JpaRepository<Prestamo, Integer> {

    // Obtener todos los préstamos realizados por un usuario específico
    List<Prestamo> findByUsuarioIdUsuario(Integer idUsuario);

    // Obtener todos los préstamos de un libro específico
    List<Prestamo> findByLibroIdLibro(Integer idLibro);
}
