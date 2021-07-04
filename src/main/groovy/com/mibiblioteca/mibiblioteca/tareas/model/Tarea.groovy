package com.mibiblioteca.mibiblioteca.tareas.model

import groovy.transform.CompileStatic

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

enum EstadoTarea{
    ABIERTA_PEND_RES, ABIERTA_PEND_CALIF, ABIERTA_VENCIDA, CERRADA_PEND_CALIF, CERRADA
}

@CompileStatic
@Entity
class Tarea implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long nroTarea

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    EstadoTarea estadoTarea

    @Column(nullable = false)
    Long docente

    Tarea(){}

    Tarea(Long docenteResponsable){
        this.estadoTarea = EstadoTarea.ABIERTA_PEND_CALIF
        this.docente = docenteResponsable
    }


}
