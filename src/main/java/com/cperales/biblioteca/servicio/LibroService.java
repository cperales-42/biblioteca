package com.cperales.biblioteca.servicio;

import com.cperales.biblioteca.modelo.Libro;
import com.cperales.biblioteca.repositorio.LibroRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LibroService {

    private final LibroRepo repo;

    public LibroService(LibroRepo repo) {
        this.repo = repo;
    }

    // Listar todos los libros
    public List<Libro> listarTodos() {
        List<Libro> todos = repo.findAll();
        List<Libro> resultado = new ArrayList<>();
        int i = 0;
        // Usamos while en vez de for para cumplir tu manía
        while (i < todos.size()) {
            resultado.add(todos.get(i));
            i++;
        }
        return resultado;
    }

    // Obtener libro por ID
    public Libro obtenerPorId(Integer id) {
        return repo.findById(id).orElse(null);
    }

    // Guardar libro (crear o editar)
    public Libro guardar(Libro libro) {
        if (libro.getIdLibro() == null) {
            // Nuevo libro: control básico de ejemplares
            if (libro.getEjemplaresTotales() == null || libro.getEjemplaresTotales() <= 0) {
                libro.setEjemplaresTotales(1);
            }
            libro.setEjemplaresDisponibles(libro.getEjemplaresTotales());
        } else {
            // Editar libro: ajusto los ejemplares disponibles
            Libro original = repo.findById(libro.getIdLibro()).orElse(null);
            if (original != null) {
                int delta = libro.getEjemplaresTotales() - original.getEjemplaresTotales();
                libro.setEjemplaresDisponibles(original.getEjemplaresDisponibles() + delta);
                if (libro.getEjemplaresDisponibles() < 0) {
                    libro.setEjemplaresDisponibles(0);
                }
            }
        }
        return repo.save(libro);
    }

    // Eliminar libro
    public void eliminar(Integer id) {
        repo.deleteById(id);
    }
}
