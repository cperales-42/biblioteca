package com.cperales.biblioteca.controlador;

import com.cperales.biblioteca.modelo.Categoria;
import com.cperales.biblioteca.servicio.CategoriaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    // Mostrar la lista de categorías
    @GetMapping
    public String listarCategorias(Model model) {
        List<Categoria> categorias = categoriaService.obtenerTodas();
        model.addAttribute("categorias", categorias);
        model.addAttribute("nuevaCategoria", new Categoria());
        return "categorias/lista";
    }

    // Crear una categoría
    @PostMapping("/crear")
    public String crearCategoria(@ModelAttribute Categoria categoria) {
        categoriaService.guardar(categoria);
        return "redirect:/categorias";
    }

    // Eliminar una categoría
    @PostMapping("/eliminar/{id}")
    public String eliminarCategoria(@PathVariable Integer id) {
        categoriaService.eliminar(id);
        return "redirect:/categorias";
    }
}
