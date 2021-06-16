package com.mibiblioteca.mibiblioteca.domain
import groovy.transform.CompileStatic
import java.sql.Timestamp

@CompileStatic
class Libro extends Material{

    Libro(String id, Float precio, String descripcion, String titulo, String autor, Timestamp fechaPublicacion, String editorial, String material) {
        super(id, precio, descripcion, titulo, autor, fechaPublicacion, editorial, material)
    }

    @Override
    void prestar(Lector l) {

    }

    @Override
    void visualizar() {

    }


}
