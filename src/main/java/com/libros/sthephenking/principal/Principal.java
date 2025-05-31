package com.libros.sthephenking.principal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.libros.sthephenking.model.*;
import com.libros.sthephenking.repository.LibroRepository;
import com.libros.sthephenking.service.ConsumoApi;
import com.libros.sthephenking.service.ConvierteDatos;
import jakarta.transaction.Transactional;

import java.io.IOException;
import java.util.stream.Collectors;

import java.util.*;

public class Principal {
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConvierteDatos conversor = new ConvierteDatos();
    private final String URL_BASE = "https://stephen-king-api.onrender.com/api/books";
    private LibroRepository libroRepository;
    private Scanner scanner = new Scanner(System.in);
    private List<Libro> libros;

    public Principal(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    public void muestraElMenu() {
        var json = consumoApi.obtenerDatos(URL_BASE);
        System.out.println(json);
        var datos = conversor.obtenerDatos(json, Datos.class);
        System.out.println(datos);


        //Busqueda de libros por id
        while (true) {
            var opcion = 0;
            var menu = (""" 
                    1- Mostrar 10 libros mas nuevos de Stephen King:
                    2- Mostrar 10 libros mas viejos de Stephen King:
                    3- Ingrese el nombre del libro que desea buscar:
                    4- Buscar villanos por libro:
                    5- Mostrar libros buscados:
                    6- Buscar libro con limite de cantidad de paginas:
                    7- Buscar villano por nombre:
                    8- Salir
                    """);
            System.out.println(menu);
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    //Top 10 libros mas nuevos
                    System.out.println("Top 10 libros mas nuevos de Stephen King: ");
                    datos.resultados().stream()
                            .sorted(Comparator.comparing(DatosLibros::añoDePublicacion).reversed())
                            .limit(10)
                            .map(l -> l.titulo().toUpperCase())
                            .forEach(System.out::println);
                    break;
                case 2:
                    //Top 10 libros mas viejos
                    System.out.println("Top 10 libros mas nuevos de Stephen King: ");
                    datos.resultados().stream()
                            .sorted(Comparator.comparing(DatosLibros::añoDePublicacion))
                            .limit(10)
                            .map(l -> l.titulo().toUpperCase())
                            .forEach(System.out::println);
                    break;
                case 3:
                    buscarLibro();
                    break;
                case 4:
                    try {
                        buscarVillanoPorLibro();
                    } catch (IOException e) {
                        System.out.println("Error al obtener datos de villanos: " + e.getMessage());
                        e.printStackTrace();
                    }
                    break;
                case 5:
                    mostrarLibrosBuscados();
                    break;
                case 6:
                    buscarLibrosCantPaginas();
                    break;
                case 7:
                    buscarVillanoPorNombre();
                    break;
                case 8:
                    break;
                default:
                    System.out.println("Opcion invalida");
            }
        }
    }

    private void buscarVillanoPorNombre() {
        System.out.println("Buscar villano por nombre: ");
        var nombreVillano = scanner.nextLine();
        List<Villanos> villanosEncontrados = libroRepository.villanosPorNombre(nombreVillano);
        villanosEncontrados.forEach(v -> System.out.println("Villano encontrado: " + v.getNombre() + " con url: " + v.getUrl() + " pertenece al libro: " + v.getLibro().getTitulo()));
        if (villanosEncontrados.isEmpty()) {
            System.out.println("No se encontraron villanos con ese nombre.");
        }
        else {
            System.out.println("Se encontraron " + villanosEncontrados.size() + " villanos con ese nombre.");
        }
    }

    private void buscarLibrosCantPaginas() {
        System.out.println("Cantidad de paginas limite que puede tener el libro: ");
        var numeroDePaginas = scanner.nextInt();
        List<Libro> librosEncontrados = libroRepository.findByNumeroDePaginasLessThanEqual(numeroDePaginas);
        librosEncontrados.forEach( l -> System.out.println("Libro encontrado: " + l.getTitulo() + " con " + l.getNumeroDePaginas() + " paginas."));
    }

    private void buscarLibro() {
        System.out.println("Nombre del libro: ");
        var nombreTitulo = scanner.nextLine();
        Optional<Libro> libroBuscado = libroRepository.findByTituloContainsIgnoreCase(nombreTitulo);
        if (libroBuscado.isPresent()){
            System.out.println("Libro encontrado: " + libroBuscado.get());
        }
        else {
            System.out.println("Libro no encontrado. Intente de nuevo con otro nombre de libro.");
        }

    }

    public DatosLibros getDatosLibros() {
        System.out.println("Nombre del libro: ");
        var nombreTitulo = scanner.nextLine();

        var json = consumoApi.obtenerDatos(URL_BASE);
        Datos datos = conversor.obtenerDatos(json, Datos.class);
        for (DatosLibros libro : datos.resultados()) {
            if (libro.titulo().equalsIgnoreCase(nombreTitulo)) {
                return libro;
            }
        }
        return null;
    }

    private void buscarVillanoPorLibro() throws IOException {
        // Mostrar los libros ya buscados (guardados en la base)
        mostrarLibrosBuscados();

        System.out.println("Escribe el nombre del libro del cual quieres ver los villanos:");
        String nombreLibro = scanner.nextLine();

        // Buscar el libro en la lista cargada desde la base de datos
        Optional<Libro> libroOpt = libros.stream()
                .filter(libro -> libro.getTitulo().equalsIgnoreCase(nombreLibro))
                .findFirst();

        if (libroOpt.isEmpty()) {
            System.out.println("No se encontró ningún libro con ese nombre.");
            return;
        }

        // Definimos libroEncontrado como final para usar dentro de lambdas
        final Libro libroEncontrado = libroOpt.get();

        // Obtener JSON con todos los libros desde la API
        String json = consumoApi.obtenerDatos(URL_BASE);

        // Convertir el JSON en lista de DatosLibros usando ObjectMapper y TypeReference
        ObjectMapper mapper = new ObjectMapper();
        Datos datos = mapper.readValue(json, Datos.class);
        List<DatosLibros> listaDatosLibros = datos.resultados();

        // Buscar en la lista el libro que coincide con el nombre
        Optional<DatosLibros> libroApiOpt = listaDatosLibros.stream()
                .filter(dl -> dl.titulo().equalsIgnoreCase(nombreLibro))
                .findFirst();

        if (libroApiOpt.isEmpty()) {
            System.out.println("No se encontró el libro en la API.");
            return;
        }

        DatosLibros libroApi = libroApiOpt.get();

        // Crear la lista de villanos transformando los DatosVillano en Villanos, asignándoles el libro
        List<Villanos> listaVillanos = libroApi.villanos().stream()
                .map(v -> new Villanos(v.nombre(), v.url(), libroEncontrado))
                .collect(Collectors.toList());

        // Establecer relación bidireccional: para cada villano setear el libro
        listaVillanos.forEach(v -> v.setLibro(libroEncontrado));

        // Guardar la lista de villanos en el libro encontrado
        libroEncontrado.setVillanos(listaVillanos);

        // Guardar el libro actualizado en la base de datos
        libroRepository.save(libroEncontrado);

        // Mostrar villanos del libro en consola
        System.out.println("Villanos asociados al libro '" + libroEncontrado.getTitulo() + "':");
        libroEncontrado.getVillanos().forEach(System.out::println);
    }

    @Transactional
    public void mostrarLibrosBuscados(){
        libros = libroRepository.findAllWithVillanos();
        libros.forEach(System.out::println);
    }
}



