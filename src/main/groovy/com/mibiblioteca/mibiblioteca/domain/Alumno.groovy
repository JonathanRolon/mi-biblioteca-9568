package com.mibiblioteca.mibiblioteca.domain

import groovy.transform.CompileStatic

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@CompileStatic
@Entity
abstract class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer DNI

    String curso

    //calificaciones[]

    //biblioteca[]

    abstract void sumarCreditos()

    void preguntar(String pregunta){

    }

    void responder(String respuesta){

    }

}
