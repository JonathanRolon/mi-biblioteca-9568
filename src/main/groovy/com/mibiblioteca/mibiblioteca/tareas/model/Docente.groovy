package com.mibiblioteca.mibiblioteca.tareas.model


import com.mibiblioteca.mibiblioteca.tareas.model.exception.CursoExistenteNoAsignableException
import com.mibiblioteca.mibiblioteca.tareas.model.exception.CursoNoExisteException
import groovy.transform.CompileStatic
import org.hibernate.annotations.CreationTimestamp

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Temporal
import javax.persistence.TemporalType
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import java.sql.Timestamp

@CompileStatic
@Entity
class Docente {

    @Id
    Long DNI

    @Column(nullable = false)
    Timestamp fechaNacimiento

    @Column(nullable = false)
    @NotEmpty(message = "El nombre es requerido.")
    String nombre

    @Column(nullable = false)
    @NotEmpty(message = "El apellido es requerido.")
    String apellido

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Curso> cursos

    Docente (Long DNI, String nombre, String apellido, Timestamp fecNac){
        this.DNI = DNI
        this.nombre = nombre
        this.apellido = apellido
        this.fechaNacimiento = fecNac
        cursos = new ArrayList<Curso>()
    }

    void asignarCurso(Curso curso){
        def existe = cursos.find{it -> it.getDenominacion() === curso.getDenominacion()}
        if(existe)
           throw new CursoExistenteNoAsignableException("El curso ya se asigno al docente.")
        cursos.push(curso)
    }

    Docente(){}

    void asignarTarea(Tarea tarea, Curso curso) {
        def encontrado = cursos.find {it -> it.getDenominacion() === curso.getDenominacion()}
        if(!encontrado)
            throw new CursoNoExisteException("Error: No fue posible asignar la tarea. El curso no existe.")
        encontrado.asignar(tarea)
    }
}
