package com.cperales.biblioteca.servicio;

import com.cperales.biblioteca.modelo.Libro;
import com.cperales.biblioteca.repositorio.LibroRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroService {

    private final LibroRepo repo;

    public LibroService(LibroRepo repo) {
        this.repo = repo;
    }

    // Buscar libros por título y autor al mismo tiempo
    public List<Libro> buscarPorTituloYAutor(String titulo, String autor) {
        String t = titulo != null ? titulo : "";
        String a = autor != null ? autor : "";
        return repo.findByTituloContainingIgnoreCaseAndAutorContainingIgnoreCase(t, a);
    }

    // Obtener un libro por su ID
    public Libro obtenerPorId(Integer id) {
        return repo.findById(id).orElse(null);
    }

    // Guardar un libro nuevo
    public Libro guardar(Libro libro) {
        return repo.save(libro);
    }

    // Actualizar libro existente
    public void actualizar(Libro libroActualizado) {
        Libro libroBD = repo.findById(libroActualizado.getIdLibro())
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        // Actualizamos el resto de campos
        libroBD.setTitulo(libroActualizado.getTitulo());
        libroBD.setAutor(libroActualizado.getAutor());
        libroBD.setEditorial(libroActualizado.getEditorial());
        libroBD.setAnioPublicacion(libroActualizado.getAnioPublicacion());
        libroBD.setCategoria(libroActualizado.getCategoria());
        libroBD.setEjemplaresTotales(libroActualizado.getEjemplaresTotales());

        repo.save(libroBD);
    }
}