package com.cperales.biblioteca.controlador;

import com.cperales.biblioteca.UsuarioPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Página de inicio
    @GetMapping("/")
    public String mostrarInicio(Model modelo, @AuthenticationPrincipal UsuarioPrincipal principal) {

        // Añadimos el nombre de usuario si está logeado
        if (principal != null) {
            modelo.addAttribute("nombreUsuario", principal.getUsername());
            modelo.addAttribute("usuarioActual", principal.getUsuario());
        }

        return "index"; // templates/index.html
    }

    // Login
    @GetMapping("/login")
    public String mostrarLogin() {
        return "login"; //templates/login.html
    }
}
