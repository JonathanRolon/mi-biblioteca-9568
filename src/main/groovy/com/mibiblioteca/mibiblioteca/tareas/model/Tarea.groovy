package com.mibiblioteca.mibiblioteca.tareas.model

import groovy.transform.CompileStatic

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import java.sql.Timestamp
import java.text.SimpleDateFormat
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

    Timestamp getFechaCreacion() {
        fechaCreacion
    }

    Timestamp getFechaEntrega() {
        fechaEntrega
    }

    String getFechaCreacionFormat(){
        SimpleDateFormat format =
                new SimpleDateFormat("dd-MM-YYYY HH:mm:ss")
        format.format(new Date(fechaCreacion.getTime()))
    }

    String getFechaEntregaFormat(){
        SimpleDateFormat format =
                new SimpleDateFormat("dd-MM-YYYY HH:mm:ss")
        format.format(new Date(fechaEntrega.getTime()))
    }

}
