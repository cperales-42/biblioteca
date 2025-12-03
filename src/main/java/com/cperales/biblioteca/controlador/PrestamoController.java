package com.cperales.biblioteca.controlador;

import com.cperales.biblioteca.modelo.Libro;
import com.cperales.biblioteca.modelo.Prestamo;
import com.cperales.biblioteca.modelo.Usuario;
import com.cperales.biblioteca.servicio.PrestamoService;
import com.cperales.biblioteca.servicio.UsuarioService;
import com.cperales.biblioteca.repositorio.LibroRepo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/prestamos")
public class PrestamoController {

    private final PrestamoService prestamoService;
    private final UsuarioService usuarioService;
    private final LibroRepo libroRepo;

    public PrestamoController(PrestamoService prestamoService,
                              UsuarioService usuarioService,
                              LibroRepo libroRepo) {
        this.prestamoService = prestamoService;
        this.usuarioService = usuarioService;
        this.libroRepo = libroRepo;
    }

    // Listar préstamos
    @GetMapping
    public String listarPrestamos(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        Usuario usuarioActual = usuarioService.buscarPorEmail(email);

        List<Prestamo> prestamos;

        if ("ADMIN".equals(usuarioActual.getRol())) {
            prestamos = prestamoService.listarTodos();
        } else {
            prestamos = prestamoService.listarPorUsuario(usuarioActual);
        }

        model.addAttribute("prestamos", prestamos);
        model.addAttribute("usuarioActual", usuarioActual);

        return "prestamos/lista";
    }

    // Crear préstamo desde un libro
    @PostMapping("/crear/{idLibro}")
    public String crearPrestamo(@PathVariable Integer idLibro,
                                @AuthenticationPrincipal UserDetails userDetails,
                                Model model) {

        Usuario usuarioActual = usuarioService.buscarPorEmail(userDetails.getUsername());

        try {
            prestamoService.crearPrestamo(usuarioActual, idLibro);
            return "redirect:/libros";
        } catch (RuntimeException e) {
            return mostrarLibrosConError(model, usuarioActual, e.getMessage());
        }
    }

    // Devolver préstamo
    @PostMapping("/devolver/{idPrestamo}")
    public String devolverPrestamo(@PathVariable Integer idPrestamo,
                                   @AuthenticationPrincipal UserDetails userDetails,
                                   Model model) {

        Usuario usuarioActual = usuarioService.buscarPorEmail(userDetails.getUsername());

        try {
            // Verificar que el préstamo pertenece al usuario si no es ADMIN
            if (!"ADMIN".equals(usuarioActual.getRol())) {
                List<Prestamo> prestamosUsuario = prestamoService.listarPorUsuario(usuarioActual);
                boolean perteneceAlUsuario = prestamosUsuario.stream()
                        .anyMatch(p -> p.getIdPrestamo().equals(idPrestamo));

                if (!perteneceAlUsuario) {
                    throw new RuntimeException("No puedes devolver un préstamo que no es tuyo.");
                }
            }

            prestamoService.devolverPrestamo(idPrestamo);
            return "redirect:/prestamos";

        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return listarPrestamos(model, userDetails);
        }
    }

    // Método privado para mostrar libros con un mensaje de error
    private String mostrarLibrosConError(Model model, Usuario usuario, String mensajeError) {
        List<Libro> todosLosLibros = libroRepo.findAll();

        model.addAttribute("libros", todosLosLibros);
        model.addAttribute("usuarioActual", usuario);
        model.addAttribute("error", mensajeError);

        return "libros/lista";
    }
}
