package com.mibiblioteca.mibiblioteca.domain

import groovy.transform.CompileStatic

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

enum EstadoHilo{
     ABIERTO, SUSPENDIDO, CERRADO
}

@CompileStatic
@Entity
class Hilo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id

    @Column(nullable = false)
    String consulta

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    TemaHilo tema

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    EstadoHilo estadoHilo

    @Column(nullable = true)
    String motivoCierre

    @Column(nullable = true)
    String motivoSuspension

    Hilo(String consulta,TemaHilo tema ){
        this.consulta = consulta
        this.tema = tema
        this.estadoHilo = EstadoHilo.ABIERTO
    }

    Hilo(){}

    long getId(){
        id
    }

    void cerrar(String motivoCierre){
        this.motivoCierre = motivoCierre
        this.estadoHilo = EstadoHilo.CERRADO
    }

    void suspender(String motivoSuspension) {
        this.motivoSuspension = motivoSuspension
        this.estadoHilo = EstadoHilo.SUSPENDIDO
    }

    void retomar(){
        this.estadoHilo = EstadoHilo.ABIERTO
    }
}
