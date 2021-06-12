package com.mibiblioteca.mibiblioteca.domain
import groovy.transform.CompileStatic

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@CompileStatic
@Entity
class Editor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer DNI

    void publicarMaterial(Material m){

    }

    void aprobarSolicitud(Material m, Alumno alumno){

    }

    void rechazarSolicitud(Material m, Alumno alumno){

    }
}
