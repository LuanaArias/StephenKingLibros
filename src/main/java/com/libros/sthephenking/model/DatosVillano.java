package com.libros.sthephenking.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosVillano(
        @JsonAlias("name") String nombre,
        @JsonAlias("url") String url
) {

}
