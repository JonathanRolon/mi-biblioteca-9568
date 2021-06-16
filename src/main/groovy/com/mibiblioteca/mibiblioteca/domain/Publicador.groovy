package com.mibiblioteca.mibiblioteca.domain
import groovy.transform.CompileStatic

@CompileStatic
class Publicador {

    void preguntar(String pregunta){
        //publicadorService.crearHilo(pregunta)
    }

    void responder(String respuesta, Long idHilo){
        Respuesta r = new Respuesta(respuesta, idHilo)
        //publicadorService.publicar(respuesta)
    }

}
