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

    // Listar todos los libros disponibles
    public List<Libro> listarTodos() {
        return repo.findAll();
    }

    // Buscar libros por título (contiene, case insensitive)
    public List<Libro> buscarPorTitulo(String titulo) {
        return repo.findByTituloContainingIgnoreCase(titulo);
    }

    // Buscar libros por autor (contiene, case insensitive)
    public List<Libro> buscarPorAutor(String autor) {
        return repo.findByAutorContainingIgnoreCase(autor);
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
        // Asegurarnos de que ejemplaresDisponibles se ajuste automáticamente
        libro.setEjemplaresTotales(libro.getEjemplaresTotales());
        return repo.save(libro);
    }

    // Actualizar un libro existente
    public Libro actualizar(Libro libroActualizado) {
        Libro libroBD = repo.findById(libroActualizado.getIdLibro())
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        // Solo cambiamos los totales, el setter ajusta disponibles automáticamente
        libroBD.setEjemplaresTotales(libroActualizado.getEjemplaresTotales());

        // Actualizamos el resto de campos
        libroBD.setTitulo(libroActualizado.getTitulo());
        libroBD.setAutor(libroActualizado.getAutor());
        libroBD.setEditorial(libroActualizado.getEditorial());
        libroBD.setAnioPublicacion(libroActualizado.getAnioPublicacion());

        return repo.save(libroBD);
    }

    // Eliminar un libro por ID
    public void eliminar(Integer id) {
        repo.deleteById(id);
    }
}