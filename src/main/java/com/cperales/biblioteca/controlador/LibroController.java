package com.cperales.biblioteca.controlador;

import com.cperales.biblioteca.modelo.Libro;
import com.cperales.biblioteca.servicio.LibroService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/libros")
public class LibroController {

    private final LibroService servicioLibros;

    public LibroController(LibroService servicioLibros) {
        this.servicioLibros = servicioLibros;
    }

    // Mostrar lista de todos los libros
    @GetMapping
    public String mostrarLista(Model modelo) {
        modelo.addAttribute("libros", servicioLibros.listarTodos());
        return "libros/lista";
    }

    // Mostrar formulario para crear un libro nuevo
    @GetMapping("/nuevo")
    public String crearNuevo(Model modelo) {
        Libro libroTemporal = new Libro(); // objeto temporal
        modelo.addAttribute("libro", libroTemporal);
        return "libros/form";
    }

    // Guardar libro nuevo o editado
    @PostMapping
    public String guardarLibro(@ModelAttribute Libro libro) {
        servicioLibros.guardar(libro);
        return "redirect:/libros";
    }

    // Mostrar formulario de edición de un libro
    @GetMapping("/editar/{id}")
    public String editarLibro(@PathVariable Integer id, Model modelo) {
        Libro libroExistente = servicioLibros.obtenerPorId(id);
        modelo.addAttribute("libro", libroExistente);
        return "libros/form";
    }

    // Eliminar un libro por su id
    @GetMapping("/eliminar/{id}")
    public String borrarLibro(@PathVariable Integer id) {
        servicioLibros.eliminar(id);
        return "redirect:/libros";
    }
}
