package com.cperales.biblioteca.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idLibro;

    private String titulo;
    private String autor;
    private String editorial;
    private Integer anioPublicacion;
    private Integer ejemplaresTotales;
    private Integer ejemplaresDisponibles;

    // Getters y setters
    public Integer getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Integer id_libro) {
        this.idLibro = id_libro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public Integer getAnioPublicacion() {
        return anioPublicacion;
    }

    public void setAnioPublicacion(Integer anio_publicacion) {
        this.anioPublicacion = anio_publicacion;
    }

    public Integer getEjemplaresTotales() {
        return ejemplaresTotales;
    }

    /**
     * Al actualizar los ejemplares totales, ajustamos automáticamente
     * los ejemplares disponibles para que no excedan los totales
     * y nunca sean negativos.
     */
    public void setEjemplaresTotales(Integer totales) {
        if (totales == null) return;

        if (this.ejemplaresTotales == null) {
            // Caso libro nuevo: disponibles = totales
            this.ejemplaresDisponibles = totales;
        } else {
            // Ajustar disponibles según la diferencia
            int diferencia = totales - this.ejemplaresTotales;
            int nuevosDisponibles = this.ejemplaresDisponibles + diferencia;

            if (nuevosDisponibles > totales) {
                nuevosDisponibles = totales;
            }

            if (nuevosDisponibles < 0) {
                nuevosDisponibles = 0;
            }

            this.ejemplaresDisponibles = nuevosDisponibles;
        }

        this.ejemplaresTotales = totales;
    }

    public Integer getEjemplaresDisponibles() {
        return ejemplaresDisponibles;
    }

    public void setEjemplaresDisponibles(Integer ejemplares_disponibles) {
        this.ejemplaresDisponibles = ejemplares_disponibles;
    }
}
