package com.cperales.biblioteca.servicio;

import com.cperales.biblioteca.modelo.Usuario;
import com.cperales.biblioteca.UsuarioPrincipal;
import com.cperales.biblioteca.repositorio.UsuarioRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepo repo;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepo repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
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
     * Devuelve todos los usuarios
     */
    public List<Usuario> listarTodos() {
        return repo.findAll();
    }

    // Buscar usuario por ID
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
    public void guardar(Usuario usuario) {
        if (usuario.getPassword() != null) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
        repo.save(usuario);
    }

    /**
     * Actualizar un usuario existente
     */
    public void actualizar(Usuario usuario) {
        Usuario existente = repo.findById(usuario.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Actualizamos campos manualmente
        existente.setNombre(usuario.getNombre());
        existente.setEmail(usuario.getEmail());
        
        // Solo actualizamos la contraseña si se proporcionó una nueva
        if (usuario.getPassword() != null && !usuario.getPassword().isBlank()) {
            existente.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
        
        existente.setRol(usuario.getRol());

        repo.save(existente);
    }

    /**
     * Eliminar usuario por ID
     */
    public void eliminar(Integer id) {
        repo.deleteById(id);
    }
}
