package com.cperales.biblioteca.repositorio;

import com.cperales.biblioteca.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

// Repositorio para manejar operaciones sobre usuarios
public interface UsuarioRepo extends JpaRepository<Usuario, Integer> {

    // Buscar un usuario por su email
    Usuario findByEmail(String email);
}
