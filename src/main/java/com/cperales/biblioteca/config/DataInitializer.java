package com.cperales.biblioteca.config;

import com.cperales.biblioteca.modelo.Usuario;
import com.cperales.biblioteca.repositorio.UsuarioRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepo usuarioRepo;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UsuarioRepo usuarioRepo, PasswordEncoder passwordEncoder) {
        this.usuarioRepo = usuarioRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Crear administrador si no existe
        if (usuarioRepo.findByEmail("admin@email.com") == null) {
            Usuario admin = new Usuario();
            admin.setNombre("Administrador");
            admin.setEmail("admin@email.com");
            admin.setPassword(passwordEncoder.encode("cperales"));
            admin.setRol("ADMIN");
            usuarioRepo.save(admin);
            System.out.println("Usuario Administrador creado por defecto.");
        }

        // Crear lector si no existe
        if (usuarioRepo.findByEmail("lector@email.com") == null) {
            Usuario lector = new Usuario();
            lector.setNombre("Lector");
            lector.setEmail("lector@email.com");
            lector.setPassword(passwordEncoder.encode("cperales"));
            lector.setRol("LECTOR");
            usuarioRepo.save(lector);
            System.out.println("Usuario Lector creado por defecto.");
        }
    }
}
