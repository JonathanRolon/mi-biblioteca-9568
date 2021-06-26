package com.mibiblioteca.mibiblioteca.model
import groovy.transform.CompileStatic

import javax.persistence.Entity
import javax.persistence.Id

@CompileStatic
@Entity
class Editor {

    @Id
    long DNI

    Editor(long DNI){
        this.DNI = DNI
    }

    Editor(){}

    void publicarMaterial(Material m){

    }

    void aprobarSolicitud(Material m, Alumno alumno){

    }

    void rechazarSolicitud(Material m, Alumno alumno){

    }


}
