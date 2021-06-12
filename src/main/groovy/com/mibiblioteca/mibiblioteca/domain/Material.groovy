package com.mibiblioteca.mibiblioteca.domain
import groovy.transform.CompileStatic

import javax.persistence.Entity
import java.sql.Timestamp

@CompileStatic
abstract class Material {

    float precio

    String descripcion

    String titulo

    File contenido

    String autor

    Timestamp fechaPublicacion

    HashMap<Integer, String> indice

    String editorial

    abstract void prestar(Lector l)

    abstract void visualizar()

}
