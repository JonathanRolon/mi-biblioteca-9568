package com.mibiblioteca.mibiblioteca.tareas.model


import groovy.transform.CompileStatic

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.MapsId

@Entity
@CompileStatic
class AsignacionTareaAlumno {

    @EmbeddedId
    TareaAlumnoIdentity id

    @Column(nullable = true)
    Integer calificacion

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("nroTarea")
    Tarea tarea

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("DNI")
    Alumno alumno

    AsignacionTareaAlumno(Tarea tarea, Alumno alumno){
        this.tarea = tarea
        this.alumno = alumno
    }

}
