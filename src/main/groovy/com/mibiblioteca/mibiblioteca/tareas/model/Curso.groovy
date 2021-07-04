package com.mibiblioteca.mibiblioteca.tareas.model

import groovy.transform.CompileStatic

import javax.persistence.Entity
import javax.persistence.Id

@CompileStatic
@Entity
class Curso {

    @Id
    private String denominacion

    Curso(){}

    Curso(String denominacion){
        this.denominacion = denominacion
    }
}