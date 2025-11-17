package com.cperales.biblioteca.servicio;

import com.cperales.biblioteca.modelo.Usuario;
import com.cperales.biblioteca.repositorio.UsuarioRepo;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepo repo;

    public UsuarioService(UsuarioRepo repo) {
        this.repo = repo;
    }

    // Para Spring Security
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = repo.findByEmail(email);
        if (usuario == null) throw new UsernameNotFoundException("Usuario no encontrado");
        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getPassword())
                .roles(usuario.getRol())
                .build();
    }

    // Listar todos los usuarios
    public List<Usuario> listarTodos() {
        List<Usuario> todos = repo.findAll();
        List<Usuario> copia = new ArrayList<>();
        int i = 0;
        while (i < todos.size()) {
            copia.add(todos.get(i));
            i++;
        }
        return copia;
    }

    // Obtener usuario por ID
    public Usuario obtenerPorId(Integer id) {
        return repo.findById(id).orElse(null);
    }

    // Guardar usuario
    public Usuario guardar(Usuario u) {
        return repo.save(u);
    }

    // Actualizar usuario
    public Usuario actualizar(Usuario u) {
        Usuario existente = repo.findById(u.getIdUsuario()).orElse(null);
        if (existente == null) throw new RuntimeException("Usuario no encontrado");

        existente.setNombre(u.getNombre());
        existente.setEmail(u.getEmail());
        existente.setPassword(u.getPassword());
        existente.setRol(u.getRol());
        return repo.save(existente);
    }

    // Eliminar usuario
    public void eliminar(Integer id) {
        repo.deleteById(id);
    }
}
