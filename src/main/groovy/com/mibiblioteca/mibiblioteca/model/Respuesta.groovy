package com.mibiblioteca.mibiblioteca.model

import groovy.transform.CompileStatic

import javax.persistence.Column
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Table

@CompileStatic
@Entity
@Table(name = "respuestas")
class Respuesta implements Serializable {

    @EmbeddedId
    private RespuestaIdentity respuestaIdentity;

    @Column(nullable = false)
    String contenido

    Respuesta(Long dniPublicador, String respuesta, Long idHilo) {
        contenido = respuesta
        respuestaIdentity = new RespuestaIdentity()
        respuestaIdentity.setPublicador(dniPublicador)
        respuestaIdentity.setNroHilo(idHilo)
    }

    Respuesta() {}

    RespuestaIdentity getIdentity(){
        return respuestaIdentity
    }

    Long getHiloId(){
        return respuestaIdentity.getNroHilo()
    }

    Long getPublicador(){
        return respuestaIdentity.getPublicador()
    }

}
