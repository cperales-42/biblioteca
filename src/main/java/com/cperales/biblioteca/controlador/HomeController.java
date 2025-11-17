package com.tuapp.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Método que gestiona la página de inicio
    @GetMapping("/")
    public String mostrarInicio(Model modelo, @AuthenticationPrincipal UserDetails usuarioAutenticado) {

        // Si hay un usuario logeado, añadimos su nombre al modelo
        if (usuarioAutenticado != null) {
            String nombreUsuario = usuarioAutenticado.getUsername();
            modelo.addAttribute("nombreUsuario", nombreUsuario);
        }

        return "index"; // Thymeleaf renderiza templates/index.html
    }

    // Método para mostrar la página de login
    @GetMapping("/login")
    public String mostrarLogin() {
        return "login"; // templates/login.html
    }

    // Método para mostrar el registro de usuarios
    @GetMapping("/registro")
    public String mostrarRegistro() {
        return "registro"; // templates/registro.html
    }
}
