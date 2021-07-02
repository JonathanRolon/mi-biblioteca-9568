package com.mibiblioteca.mibiblioteca.model

import javax.persistence.Column
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "calificacionesforo")
class Calificacion{

    @EmbeddedId
    protected CalificacionIdentity calificacionIdentity;

    @Column(nullable = false)
    Integer calificacion

    Calificacion(){}

    Calificacion(Respuesta respuesta, Long publicador, Integer calificacion){

        this.calificacion = calificacion
        calificacionIdentity =  new CalificacionIdentity()
        calificacionIdentity.setRespuestaIdentity(respuesta.getIdentity())
        calificacionIdentity.setIdCalif(publicador)

    }

    Long getNroHilo(){
        this.calificacionIdentity.getRespuestaIdentity().getNroHilo()
    }

    Long getCalificado(){
        this.calificacionIdentity.getRespuestaIdentity().getPublicador()
    }

    Long getCalificador(){
        this.calificacionIdentity.getIdCalif()
    }

    Boolean esCalificador(Alumno alumno){
        alumno.getDNI() === calificacionIdentity.getIdCalif()
    }

}
