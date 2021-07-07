package com.mibiblioteca.mibiblioteca.tareas.model

import groovy.transform.CompileStatic
import org.hibernate.annotations.CreationTimestamp

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Temporal
import javax.persistence.TemporalType
import javax.validation.constraints.NotBlank
import java.sql.Timestamp
import java.time.LocalDateTime

@CompileStatic
@Entity
class Tarea implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long nroTarea

    @Column(nullable = false)
    String consigna

    @Column(nullable = false)
    private Timestamp fechaCreacion

    @Column(nullable = false)
    private Timestamp fechaEntrega

    Tarea(){}

    Tarea(String consigna, Timestamp fechaEntrega){
        this.consigna = consigna
        fechaCreacion = Timestamp.valueOf(LocalDateTime.now())
        this.fechaEntrega = fechaEntrega
    }

    Timestamp getFechaEntrega() {
        fechaEntrega
    }
}
