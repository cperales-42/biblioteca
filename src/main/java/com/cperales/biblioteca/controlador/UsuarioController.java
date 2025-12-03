package com.cperales.biblioteca.controlador;

import com.cperales.biblioteca.modelo.Usuario;
import com.cperales.biblioteca.servicio.UsuarioService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Listar todos los usuarios
    @GetMapping
    public String listarUsuarios(Model model) {
        List<Usuario> listaUsuarios = usuarioService.listarTodos();
        model.addAttribute("usuarios", listaUsuarios);
        return "usuarios/lista";
    }

    // Mostrar formulario para editar usuario existente
    @GetMapping("/editar/{id}")
    public String editarUsuarioForm(@PathVariable Integer id, Model model) {
        Usuario usuario = usuarioService.obtenerPorId(id);
        if (usuario == null) {
            return "redirect:/usuarios";
        }
        model.addAttribute("usuario", usuario);
        return "usuarios/editar";
    }

    // Guardar cambios del usuario editado
    @PostMapping("/editar/{id}")
    public String guardarUsuarioEditado(@PathVariable Integer id,
                                        @ModelAttribute Usuario usuarioForm) {

        Usuario usuarioExistente = usuarioService.obtenerPorId(id);
        if (usuarioExistente == null) {
            return "redirect:/usuarios";
        }

        // Actualizar campos básicos
        usuarioExistente.setNombre(usuarioForm.getNombre());
        usuarioExistente.setEmail(usuarioForm.getEmail());
        usuarioExistente.setRol(usuarioForm.getRol());

        // Solo actualizar contraseña si se ingresa
        String nuevaPassword = usuarioForm.getPassword();
        if (nuevaPassword != null && !nuevaPassword.isBlank()) {
            usuarioExistente.setPassword(nuevaPassword);
        }

        usuarioService.actualizar(usuarioExistente);
        return "redirect:/usuarios";
    }

    // Eliminar usuario por ID
    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Integer id) {
        usuarioService.eliminar(id);
        return "redirect:/usuarios";
    }

    // Mostrar index con usuario autenticado
    @GetMapping("/")
    public String index(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            Usuario usuarioActual = usuarioService.buscarPorEmail(userDetails.getUsername());
            model.addAttribute("usuarioActual", usuarioActual);
        }
        return "index";
    }

    // Mostrar formulario de registro
    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        Usuario usuarioNuevo = new Usuario();
        model.addAttribute("usuario", usuarioNuevo);
        return "usuarios/registro";
    }

    // Procesar el registro de un nuevo usuario
    @PostMapping("/registro")
    public String procesarRegistro(@ModelAttribute Usuario usuario) {
        usuarioService.guardar(usuario);
        return "redirect:/login";
    }
}
