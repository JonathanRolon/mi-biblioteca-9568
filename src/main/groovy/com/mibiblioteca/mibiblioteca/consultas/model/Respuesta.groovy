package com.mibiblioteca.mibiblioteca.consultas.model


import groovy.transform.CompileStatic

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.validation.constraints.NotEmpty
import java.sql.Timestamp
import java.time.LocalDateTime

@CompileStatic
@Entity
@Table(name = "respuestas")
class Respuesta implements Serializable {

    @EmbeddedId
    private RespuestaIdentity respuestaIdentity;

    @Column(nullable = false)
    @NotEmpty
    String contenido

    @Column(nullable = false)
    private Timestamp fechaPublicacion

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Calificacion> calificaciones

    Respuesta(Long dniPublicador, String respuesta, Long idHilo) {
        contenido = respuesta
        respuestaIdentity = new RespuestaIdentity()
        respuestaIdentity.setPublicador(dniPublicador)
        respuestaIdentity.setNroHilo(idHilo)
        fechaPublicacion = Timestamp.valueOf(LocalDateTime.now())
        calificaciones = new ArrayList<Calificacion>()
    }

    Respuesta(){}

    void agregarCalificacion(Calificacion calificacion){
        calificaciones.push(calificacion)
    }

    RespuestaIdentity getIdentity(){
        return respuestaIdentity
    }

    Long getHiloId(){
        return respuestaIdentity.getNroHilo()
    }

    Long getPublicador(){
        return respuestaIdentity.getPublicador()
    }

    Timestamp getFechaPublicacion(){
        return fechaPublicacion
    }

   Boolean esPublicador(Long dni){
       respuestaIdentity.getPublicador() === dni
   }

    Boolean esCalificador(){

    }

}
