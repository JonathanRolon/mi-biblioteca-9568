package com.mibiblioteca.mibiblioteca.tareas.model

import com.mibiblioteca.mibiblioteca.tareas.model.exception.TareaNoAsignableException
import groovy.transform.CompileStatic

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany

@CompileStatic
@Entity
class Curso {

    @Id
    private String denominacion

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    List<Tarea> tareasAsignadas

    Curso(){}

    Curso(String denominacion){
        this.denominacion = denominacion
        tareasAsignadas = new ArrayList<Tarea>()
    }

    String getDenominacion(){
        denominacion
    }

    void asignar(Tarea tarea) {
        def existe = tareasAsignadas.find{it -> it.getNroTarea() === tarea.getNroTarea()}
        if(existe)
            throw new TareaNoAsignableException("La tarea ya fue asignada al curso")
        tareasAsignadas.push(tarea)
    }
}