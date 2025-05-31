package com.libros.sthephenking.service;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
