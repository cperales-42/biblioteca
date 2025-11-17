package com.cperales.biblioteca.controlador;

import com.cperales.biblioteca.modelo.Usuario;
import com.cperales.biblioteca.servicio.UsuarioService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    public UsuarioController(UsuarioService usuarioService, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    // Listar todos los usuarios (solo ADMIN)
    @GetMapping
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.listarTodos());
        return "usuarios/lista";
    }

    // Formulario de creación de usuario (ADMIN)
    @GetMapping("/nuevo")
    public String nuevoUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuarios/form";
    }

    // Guardar usuario nuevo o editado
    @PostMapping
    public String guardarUsuario(@ModelAttribute Usuario usuario) {
        // codificamos la contraseña antes de guardar
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioService.guardar(usuario);
        return "redirect:/usuarios";
    }

    // Formulario de edición de usuario (ADMIN)
    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable Integer id, Model model) {
        model.addAttribute("usuario", usuarioService.obtenerPorId(id));
        return "usuarios/form";
    }

    // Eliminar usuario (ADMIN)
    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Integer id) {
        usuarioService.eliminar(id);
        return "redirect:/usuarios";
    }

    // --- Registro público (LECTOR) ---

    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuarios/registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute("usuario") Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setRol("LECTOR"); // asignamos rol por defecto
        usuarioService.guardar(usuario);
        return "redirect:/login"; // redirigimos al login después de registrarse
    }

}
