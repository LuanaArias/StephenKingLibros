package com.libros.sthephenking.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "libro")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    private Integer añoDePublicacion;
    private Integer numeroDePaginas;
    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Villanos> villanos = new ArrayList<>();

    public Libro() {}

    public Libro(DatosLibros datosLibros) {
        this.titulo = datosLibros.titulo();
        this.añoDePublicacion = datosLibros.añoDePublicacion();
        this.numeroDePaginas = datosLibros.numeroDePaginas();
        this.villanos = datosLibros.villanos()
                .stream()
                .map(v -> new Villanos(v.nombre(), v.url(), this))
                .collect(Collectors.toList());
    }
    public Long getId() {
        return id;
    }

    public Integer getNumeroDePaginas() {
        return numeroDePaginas;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @Override
    public String toString(){
        return "Titulo: "  + titulo +
                ", Año de publicacion :" + añoDePublicacion +
                ", Numero de paginas: " + numeroDePaginas +
                ", Villanos: " + villanos;
    }

    public List<Villanos> getVillanos() {
        return villanos;
    }

    public void setVillanos(List<Villanos> villanos) {
        villanos.forEach(v -> v.setLibro(this));
        this.villanos = villanos;
    }

    public Integer getAñoDePublicacion() {
        return añoDePublicacion;
    }
}
