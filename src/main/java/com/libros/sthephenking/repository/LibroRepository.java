package com.libros.sthephenking.repository;

import com.libros.sthephenking.model.Libro;
import com.libros.sthephenking.model.Villanos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    Optional<Libro> findByTituloContainsIgnoreCase(String titulo);
    @Query("SELECT l FROM Libro l LEFT JOIN FETCH l.villanos WHERE UPPER(l.titulo) LIKE %:titulo%")
    Optional<Libro> findByTituloWithVillanos(@Param("titulo") String titulo);
    @Query("SELECT DISTINCT l FROM Libro l LEFT JOIN FETCH l.villanos")
    List<Libro> findAllWithVillanos();
    @Query("SELECT l FROM Libro l WHERE l.numeroDePaginas <= :numeroDePaginas")
    List<Libro> findByNumeroDePaginasLessThanEqual(int numeroDePaginas);
    @Query("SELECT v FROM Libro l JOIN l.villanos v WHERE v.nombre ILIKE %:nombreVillano")
    List<Villanos> villanosPorNombre(String nombreVillano);
    @Query ("SELECT l FROM Libro l WHERE l.añoDePublicacion >= 2020")
    List<Libro> librosNuevos();
}
