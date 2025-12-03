package com.cperales.biblioteca.servicio;

import com.cperales.biblioteca.modelo.Libro;
import com.cperales.biblioteca.modelo.Prestamo;
import com.cperales.biblioteca.modelo.Usuario;
import com.cperales.biblioteca.repositorio.LibroRepo;
import com.cperales.biblioteca.repositorio.PrestamoRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class PrestamoService {

    private final PrestamoRepo prestamoRepo;
    private final LibroRepo libroRepo;

    public PrestamoService(PrestamoRepo prestamoRepo, LibroRepo libroRepo) {
        this.prestamoRepo = prestamoRepo;
        this.libroRepo = libroRepo;
    }

    // Listar todos los préstamos existentes
    public List<Prestamo> listarTodos() {
        return prestamoRepo.findAll();
    }

    // Listar solo los préstamos de un usuario específico
    public List<Prestamo> listarPorUsuario(Usuario usuario) {
        return prestamoRepo.findByUsuario(usuario);
    }

    /**
     * Comprueba si el usuario tiene algún préstamo activo (no devuelto aún)
     */
    public boolean tienePrestamoActivo(Usuario usuario) {
        List<Prestamo> prestamos = prestamoRepo.findByUsuario(usuario);

        for (Prestamo p : prestamos) {
            if (p.getFechaDevolucion() == null) {
                return true;
            }
        }

        return false;
    }

    /**
     * Crear un préstamo de un libro para un usuario
     */
    @Transactional
    public void crearPrestamo(Usuario usuario, Integer idLibro) {
        // Verificar si ya tiene préstamo activo
        if (tienePrestamoActivo(usuario)) {
            throw new RuntimeException("Ya tienes un préstamo activo. Devuélvelo antes de pedir otro.");
        }

        // Buscar el libro
        Libro libro = libroRepo.findById(idLibro)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado."));

        // Verificar disponibilidad
        if (libro.getEjemplaresDisponibles() <= 0) {
            throw new RuntimeException("No hay ejemplares disponibles.");
        }

        // Crear el préstamo
        Prestamo prestamo = new Prestamo(LocalDate.now(), usuario, libro);
        prestamoRepo.save(prestamo);

        // Reducir los ejemplares disponibles
        libro.setEjemplaresDisponibles(libro.getEjemplaresDisponibles() - 1);
        libroRepo.save(libro);
    }

    /**
     * Devolver un préstamo existente
     */
    @Transactional
    public void devolverPrestamo(Integer idPrestamo) {
        Prestamo prestamo = prestamoRepo.findById(idPrestamo)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado."));

        // Validar si ya fue devuelto
        if (prestamo.getFechaDevolucion() != null) {
            throw new RuntimeException("El préstamo ya fue devuelto.");
        }

        // Marcar la devolución
        prestamo.setFechaDevolucion(LocalDate.now());
        prestamoRepo.save(prestamo);

        // Incrementar los ejemplares disponibles del libro
        Libro libro = prestamo.getLibro();
        libro.setEjemplaresDisponibles(libro.getEjemplaresDisponibles() + 1);
        libroRepo.save(libro);
    }
}