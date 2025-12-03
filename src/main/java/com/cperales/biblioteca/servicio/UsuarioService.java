package com.cperales.biblioteca.servicio;

import com.cperales.biblioteca.modelo.Usuario;
import com.cperales.biblioteca.UsuarioPrincipal;
import com.cperales.biblioteca.repositorio.UsuarioRepo;
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

    /**
     * Spring Security necesita este método para autenticar al usuario
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = repo.findByEmail(email);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con email: " + email);
        }

        return new UsuarioPrincipal(usuario);
    }

    /**
     * Devuelve todos los usuarios en una lista nueva
     */
    public List<Usuario> listarTodos() {
        List<Usuario> usuarios = repo.findAll();
        return new ArrayList<>(usuarios); // devolvemos copia para no exponer la lista original
    }

    /**
     * Buscar usuario por ID
     */
    public Usuario obtenerPorId(Integer id) {
        return repo.findById(id).orElse(null);
    }

    /**
     * Buscar usuario por email
     */
    public Usuario buscarPorEmail(String email) {
        return repo.findByEmail(email);
    }

    /**
     * Guardar un nuevo usuario
     */
    public Usuario guardar(Usuario usuario) {
        return repo.save(usuario);
    }

    /**
     * Actualizar un usuario existente
     */
    public Usuario actualizar(Usuario usuario) {
        Usuario existente = repo.findById(usuario.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Actualizamos campos manualmente
        existente.setNombre(usuario.getNombre());
        existente.setEmail(usuario.getEmail());
        existente.setPassword(usuario.getPassword());
        existente.setRol(usuario.getRol());

        return repo.save(existente);
    }

    /**
     * Eliminar usuario por ID
     */
    public void eliminar(Integer id) {
        repo.deleteById(id);
    }
}
