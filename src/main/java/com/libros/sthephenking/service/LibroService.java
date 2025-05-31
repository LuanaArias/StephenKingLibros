package com.libros.sthephenking.service;

import com.libros.sthephenking.dto.LibroDTO;
import com.libros.sthephenking.model.Libro;
import com.libros.sthephenking.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class LibroService {
    @Autowired
    private LibroRepository repository;

    public List<LibroDTO> obtenerTodosLosLibros (){
        return convertirDatosALista(repository.findAll());
    }
    public List<LibroDTO> convertirDatosALista(List<Libro> libros){
        return libros.stream()
                .map( l -> new LibroDTO(l.getTitulo(), l.getAñoDePublicacion(), l.getNumeroDePaginas()))
                .collect(Collectors.toList());
    }

    public List<LibroDTO> topLibrosCortos() {
        return convertirDatosALista(repository.findByNumeroDePaginasLessThanEqual(300));
    }

    public List<LibroDTO> librosNuevos() {
        return convertirDatosALista(repository.librosNuevos());
    }
}
