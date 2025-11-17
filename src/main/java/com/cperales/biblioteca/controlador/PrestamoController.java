package com.cperales.biblioteca.controlador;

import com.cperales.biblioteca.modelo.Libro;
import com.cperales.biblioteca.modelo.Prestamo;
import com.cperales.biblioteca.modelo.Usuario;
import com.cperales.biblioteca.servicio.LibroService;
import com.cperales.biblioteca.servicio.PrestamoService;
import com.cperales.biblioteca.servicio.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/prestamos")
public class PrestamoController {

    private final PrestamoService prestamoService;
    private final UsuarioService usuarioService;
    private final LibroService libroService;

    public PrestamoController(PrestamoService prestamoService, UsuarioService usuarioService, LibroService libroService) {
        this.prestamoService = prestamoService;
        this.usuarioService = usuarioService;
        this.libroService = libroService;
    }

    // Listar todos los préstamos
    @GetMapping
    public String listarPrestamos(Model model) {
        List<Prestamo> lista = prestamoService.listarTodos();
        model.addAttribute("prestamos", lista);
        return "prestamos/lista";
    }

    // Mostrar formulario de nuevo préstamo
    @GetMapping("/nuevo")
    public String nuevoPrestamo(Model model) {
        List<Usuario> usuarios = usuarioService.listarTodos();
        List<Libro> libros = libroService.listarTodos();
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("libros", libros);
        return "prestamos/form";
    }

    // Guardar préstamo nuevo
    @PostMapping
    public String guardarPrestamo(@RequestParam Integer usuarioId, @RequestParam Integer libroId) {
        Usuario usuarioSeleccionado = usuarioService.obtenerPorId(usuarioId);
        Libro libroSeleccionado = libroService.obtenerPorId(libroId);
        prestamoService.prestarLibro(usuarioSeleccionado, libroSeleccionado);
        return "redirect:/prestamos";
    }

    // Devolver libro
    @GetMapping("/devolver/{id}")
    public String devolverLibro(@PathVariable Integer id) {
        prestamoService.devolverPrestamo(id);
        return "redirect:/prestamos";
    }
}
