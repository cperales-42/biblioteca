package com.cperales.biblioteca.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    // Mostrar página de inicio de sesión
    @GetMapping("/login")
    public String mostrarLogin() {
        return "login"; //templates/login.html
    }
}
