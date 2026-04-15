package com.cperales.biblioteca.controlador;

import com.cperales.biblioteca.config.SecurityUtils;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final SecurityUtils securityUtils;

    public HomeController(SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
    }

    // Página de inicio
    @GetMapping("/")
    public String mostrarInicio(Model modelo, @AuthenticationPrincipal UserDetails usuarioAutenticado) {

        // Añadimos el nombre de usuario si está logeado
        if (usuarioAutenticado != null) {
            String nombre = usuarioAutenticado.getUsername();
            modelo.addAttribute("nombreUsuario", nombre);
        }

        // Añadimos flags de roles para Thymeleaf
        boolean esAdmin = securityUtils.hasRole("ADMIN");
        boolean esLector = securityUtils.hasRole("LECTOR");

        modelo.addAttribute("isAdmin", esAdmin);
        modelo.addAttribute("isLector", esLector);

        return "index"; // templates/index.html
    }

    // Login
    @GetMapping("/login")
    public String mostrarLogin() {
        return "login"; //templates/login.html
    }
}
