package com.cperales.biblioteca.servicio;

import com.cperales.biblioteca.modelo.Categoria;
import com.cperales.biblioteca.repositorio.CategoriaRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepo categoriaRepo;

    public CategoriaService(CategoriaRepo categoriaRepo) {
        this.categoriaRepo = categoriaRepo;
    }

    /**
     * Devuelve todas las categorías
     */
    public List<Categoria> obtenerTodas() {
        return categoriaRepo.findAll();
    }

    /**
     * Guardar una nueva categoría
     */
    public Categoria guardar(Categoria categoria) {
        return categoriaRepo.save(categoria);
    }

    /**
     * Eliminar categoría por ID
     */
    public void eliminar(Integer id) {
        categoriaRepo.deleteById(id);
    }
}
