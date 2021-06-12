package com.mibiblioteca.mibiblioteca.domain
import groovy.transform.CompileStatic

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@CompileStatic
@Entity
class Docente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer DNI

    //cursos[]
    //biblioteca[]

    void calificar(Alumno al){}

    void asignarTarea(Alumno al, Tarea t){ }

    void calificarTarea(Tarea t, float nota){

    }

    void responder( Hilo hilo, String respuesta){

    }
}
