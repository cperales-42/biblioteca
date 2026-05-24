package com.cperales.biblioteca.modelo;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

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

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    // Getters y setters
    public Integer getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Integer idLibro) {
        this.idLibro = idLibro;
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

    public void setAnioPublicacion(Integer anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    public Integer getEjemplaresTotales() {
        return ejemplaresTotales;
    }

    /**
     * Al actualizar los ejemplares totales, ajustamos automáticamente
     * los ejemplares disponibles para que no excedan los totales
     * y nunca sean negativos
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

    public void setEjemplaresDisponibles(Integer ejemplaresDisponibles) {
        this.ejemplaresDisponibles = ejemplaresDisponibles;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
