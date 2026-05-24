package com.cperales.biblioteca.controlador;

import com.cperales.biblioteca.UsuarioPrincipal;
import com.cperales.biblioteca.modelo.Libro;
import com.cperales.biblioteca.servicio.CategoriaService;
import com.cperales.biblioteca.servicio.LibroService;
import com.cperales.biblioteca.servicio.PrestamoService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class LibroController {

    private final LibroService libroService;
    private final PrestamoService prestamoService;
    private final CategoriaService categoriaService;

    public LibroController(LibroService libroService, PrestamoService prestamoService, CategoriaService categoriaService) {
        this.libroService = libroService;
        this.prestamoService = prestamoService;
        this.categoriaService = categoriaService;
    }

    // Mostrar la lista de libros
    @GetMapping("/libros")
    public String listarLibros(@RequestParam(required = false) String titulo,
                               @RequestParam(required = false) String autor,
                               Model model, @AuthenticationPrincipal UsuarioPrincipal principal) {

        // Verificar si hay usuario autenticado
        if (principal != null) {
            boolean prestamoActivo = prestamoService.tienePrestamoActivo(principal.getUsuario());

            model.addAttribute("usuarioActual", principal.getUsuario());
            model.addAttribute("tienePrestamoActivo", prestamoActivo);
        }

        // Obtener libros filtrados por título y autor
        List<Libro> librosFiltrados = libroService.buscarPorTituloYAutor(
                titulo != null ? titulo : "",
                autor != null ? autor : ""
        );

        model.addAttribute("libros", librosFiltrados);
        model.addAttribute("categorias", categoriaService.obtenerTodas());
        model.addAttribute("titulo", titulo);
        model.addAttribute("autor", autor);

        return "libros/lista";
    }

    // Crear un libro
    @PostMapping("/libros/crear")
    public String crearLibro(@ModelAttribute Libro libro) {
        Libro libroGuardado = libroService.guardar(libro);
        // Redirigir a la lista de libros después de guardar
        return "redirect:/libros";
    }

    // Actualizar ejemplares totales de un libro
    @PostMapping("/libros/actualizar/{id}")
    public String actualizarLibro(@PathVariable Integer id,
                                  @RequestParam Integer ejemplaresTotales) {

        Libro libroExistente = libroService.obtenerPorId(id);

        if (libroExistente != null) {
            // Cambiamos solo los ejemplares totales;
            // el servicio se encargará de ajustar los disponibles automáticamente
            libroExistente.setEjemplaresTotales(ejemplaresTotales);
            libroService.actualizar(libroExistente);
        }

        return "redirect:/libros";
    }
}
