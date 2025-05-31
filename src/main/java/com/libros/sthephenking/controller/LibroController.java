package com.libros.sthephenking.controller;

import com.libros.sthephenking.dto.LibroDTO;
import com.libros.sthephenking.model.Libro;
import com.libros.sthephenking.repository.LibroRepository;
import com.libros.sthephenking.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/libros")
public class LibroController {
    @Autowired
    private LibroService service;

    @GetMapping()
    public List<LibroDTO> obtenerTodosLosLibros() {
        return service.obtenerTodosLosLibros();
    }

    @GetMapping("/top-libros-cortos")
    public List<LibroDTO> obtenerTopLibrosCortos() {
        return service.topLibrosCortos();
    }

    @GetMapping("/libros-nuevos")
    public List<LibroDTO> obtenerLibrosNuevos() {
        return service.librosNuevos();
    }

}
