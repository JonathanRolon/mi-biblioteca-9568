package com.mibiblioteca.mibiblioteca.domain
import groovy.transform.CompileStatic

import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@CompileStatic
@Entity
@Table(name = "respuestas")
class Respuesta implements Serializable{

    @EmbeddedId
    private RespuestaIdentity respuestaIdentity;

    @Column(nullable = false)
    String contenido

    Respuesta (String respuesta, Long idHilo){
        contenido = respuesta
        respuestaIdentity.setNroHilo(idHilo)
    }

    Respuesta(){}

}
