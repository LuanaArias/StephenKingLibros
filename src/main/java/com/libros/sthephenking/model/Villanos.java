package com.libros.sthephenking.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.net.URL;

@Entity
@Table(name = "villanos")
public class Villanos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String url;
    @ManyToOne
    @JoinColumn(name = "libro_id", nullable = false)
    private Libro libro;

    public Villanos() {}

    public Villanos(DatosVillano d) {
        this.nombre = d.nombre();
        this.url = d.url();
    }

    public Villanos(String nombre, String url, Libro libro) {
        this.nombre = nombre;
        this.url = url;
        this.libro = libro;
    }

    public Libro getLibro() {
        return libro;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString(){
        return "Nombre del villano: " + nombre +
                ", URL: " + url;
    }
}
