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

    // Bean para encriptar contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Proveedor de autenticación usando nuestro UserDetailsService
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(usuarioService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // Configuración de seguridad web
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // Configuramos qué URLs puede ver cada rol
        http.authorizeHttpRequests(auth -> auth
                // URLs públicas
                .requestMatchers("/", "/login", "/usuarios/registro",
                        "/css/**", "/js/**", "/images/**").permitAll()
                // URLs de libros y préstamos para lectores y admins
                .requestMatchers("/libros/**", "/prestamos/**").hasAnyRole("ADMIN", "LECTOR")
                // URLs de usuarios solo para admins
                .requestMatchers("/usuarios/**").hasRole("ADMIN")
                // cualquier otra URL requiere estar autenticado
                .anyRequest().authenticated()
        );

        // Configuración del login
        http.formLogin(form -> form
                .loginPage("/login")          // template login.html
                .loginProcessingUrl("/login") // acción del form
                .defaultSuccessUrl("/", true) // a dónde ir al logearse correctamente
                .permitAll()
        );

        // Configuración del logout
        http.logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
        );

        // Manejo de acceso denegado
        http.exceptionHandling(ex -> ex
                .accessDeniedPage("/error")
        );

        // Asociamos nuestro proveedor de autenticación
        http.authenticationProvider(daoAuthenticationProvider());

        return http.build();
    }
}
