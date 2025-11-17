package com.cperales.biblioteca.config;

import com.cperales.biblioteca.servicio.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final UsuarioService usuarioService;

    public SecurityConfig(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Encriptador de contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Proveedor de autenticación basado en el servicio de usuarios
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider prov = new DaoAuthenticationProvider();

        // Configuración manual para que no quede acoplado en el constructor
        prov.setUserDetailsService(usuarioService);
        prov.setPasswordEncoder(passwordEncoder());

        return prov;
    }

    // Configuración central del sistema de seguridad
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // Se prepara la parte de autorización
        http.authorizeHttpRequests(cfg -> {
            /*
             * Permitir acceso libre a las rutas públicas
             */
            cfg.requestMatchers("/", "/login", "/usuarios/registro",
                            "/css/**", "/js/**")
                    .permitAll();

            /*
             * Áreas accesibles para lectores y administradores
             */
            cfg.requestMatchers("/libros/**", "/prestamos/**")
                    .hasAnyRole("ADMIN", "LECTOR");

            /*
             * Gestión de usuarios reservada al administrador
             */
            cfg.requestMatchers("/usuarios/**")
                    .hasRole("ADMIN");

            /*
             * Todo lo demás requiere estar autenticado
             */
            cfg.anyRequest()
                    .authenticated();
        });

        // Configuración del inicio de sesión
        http.formLogin(form -> {
            form.loginPage("/login");
            form.defaultSuccessUrl("/", true);
            form.permitAll();
        });

        // Configuración del cierre de sesión
        http.logout(out -> {
            out.logoutUrl("/logout");
            out.logoutSuccessUrl("/login?logout");
            out.permitAll();
        });

        return http.build();
    }
}
