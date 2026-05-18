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

    // Buscar categoría por ID
    public Categoria obtenerPorId(Integer id) {
        return categoriaRepo.findById(id).orElse(null);
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

    /**
     * Buscar categoría por nombre
     */
    public Categoria obtenerPorNombre(String nombre) {
        return categoriaRepo.findByNombre(nombre).orElse(null);
    }
}
