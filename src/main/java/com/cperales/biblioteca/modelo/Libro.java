package com.cperales.biblioteca.modelo;

import jakarta.persistence.*;
import java.util.List;

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

    @OneToMany(mappedBy = "libro")
    private List<Prestamo> prestamos;

    // Constructor vacío requerido por JPA
    public Libro() {}

    // Constructor principal
    public Libro(String titulo, String autor, String editorial, Integer anioPublicacion, Integer ejemplaresTotales) {
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.anioPublicacion = anioPublicacion;
        this.ejemplaresTotales = (ejemplaresTotales != null && ejemplaresTotales > 0) ? ejemplaresTotales : 1;
        this.ejemplaresDisponibles = this.ejemplaresTotales; // al inicio todos disponibles
    }

    // Getters y setters
    public Integer getIdLibro() { return idLibro; }
    public void setIdLibro(Integer idLibro) { this.idLibro = idLibro; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getEditorial() { return editorial; }
    public void setEditorial(String editorial) { this.editorial = editorial; }

    public Integer getAnioPublicacion() { return anioPublicacion; }
    public void setAnioPublicacion(Integer anioPublicacion) { this.anioPublicacion = anioPublicacion; }

    public Integer getEjemplaresTotales() { return ejemplaresTotales; }
    public void setEjemplaresTotales(Integer ejemplaresTotales) {
        this.ejemplaresTotales = (ejemplaresTotales != null && ejemplaresTotales > 0) ? ejemplaresTotales : 1;
        if (this.ejemplaresDisponibles == null || this.ejemplaresDisponibles > this.ejemplaresTotales) {
            this.ejemplaresDisponibles = this.ejemplaresTotales;
        }
    }

    public Integer getEjemplaresDisponibles() { return ejemplaresDisponibles; }
    public void setEjemplaresDisponibles(Integer ejemplaresDisponibles) { this.ejemplaresDisponibles = ejemplaresDisponibles; }

    public List<Prestamo> getPrestamos() { return prestamos; }
    public void setPrestamos(List<Prestamo> prestamos) { this.prestamos = prestamos; }

}
