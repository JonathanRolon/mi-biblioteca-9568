package com.mibiblioteca.mibiblioteca.tareas.model

import groovy.transform.CompileStatic

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany

@CompileStatic
@Entity
class Docente {

    @Id
    long DNI

    @OneToMany
    List<Curso> cursos

    Docente (long DNI){
        this.DNI = DNI
        cursos = new ArrayList<Curso>()
    }

    Docente(){}

    void agregarCurso(Curso curso){
        cursos.push(curso)
    }
}
