package com.libros.sthephenking.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibros(
        @JsonAlias("Title") String titulo,
        @JsonAlias("Year") Integer añoDePublicacion,
        @JsonAlias("Pages") Integer numeroDePaginas,
        @JsonAlias("villains") List<DatosVillano> villanos
) {
}
