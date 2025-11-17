package com.cperales.biblioteca.servicio;

import com.cperales.biblioteca.modelo.Libro;
import com.cperales.biblioteca.modelo.Prestamo;
import com.cperales.biblioteca.modelo.Usuario;
import com.cperales.biblioteca.repositorio.PrestamoRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PrestamoService {

    private final PrestamoRepo prestamoRepo;

    public PrestamoService(PrestamoRepo prestamoRepo) {
        this.prestamoRepo = prestamoRepo;
    }

    // Listar todos los préstamos
    public List<Prestamo> listarTodos() {
        List<Prestamo> todos = prestamoRepo.findAll();
        List<Prestamo> copia = new ArrayList<>();
        int i = 0;
        while (i < todos.size()) {
            copia.add(todos.get(i));
            i++;
        }
        return copia;
    }

    // Prestar libro
    public Prestamo prestarLibro(Usuario usuario, Libro libro) {
        if (libro.getEjemplaresDisponibles() <= 0) {
            throw new RuntimeException("No hay ejemplares disponibles");
        }

        libro.setEjemplaresDisponibles(libro.getEjemplaresDisponibles() - 1);
        Prestamo prestamo = new Prestamo(LocalDate.now(), usuario, libro);
        return prestamoRepo.save(prestamo);
    }

    // Devolver libro
    public Prestamo devolverPrestamo(Integer idPrestamo) {
        Prestamo prestamo = prestamoRepo.findById(idPrestamo).orElse(null);
        if (prestamo == null) return null;

        Libro libro = prestamo.getLibro();
        libro.setEjemplaresDisponibles(libro.getEjemplaresDisponibles() + 1);

        prestamo.setFechaDevolucion(LocalDate.now());
        return prestamoRepo.save(prestamo);
    }

    // Obtener préstamos por usuario
    public List<Prestamo> obtenerPorUsuario(Integer usuarioId) {
        List<Prestamo> lista = prestamoRepo.findByUsuarioIdUsuario(usuarioId);
        List<Prestamo> copia = new ArrayList<>();
        int i = 0;
        while (i < lista.size()) {
            copia.add(lista.get(i));
            i++;
        }
        return copia;
    }

    // Obtener préstamos por libro
    public List<Prestamo> obtenerPorLibro(Integer libroId) {
        List<Prestamo> lista = prestamoRepo.findByLibroIdLibro(libroId);
        List<Prestamo> copia = new ArrayList<>();
        int i = 0;
        while (i < lista.size()) {
            copia.add(lista.get(i));
            i++;
        }
        return copia;
    }
}
