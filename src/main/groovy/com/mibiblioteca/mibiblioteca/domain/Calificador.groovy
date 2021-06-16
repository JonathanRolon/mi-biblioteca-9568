package com.mibiblioteca.mibiblioteca.domain
import groovy.transform.CompileStatic

@CompileStatic
interface Calificador {

    void calificar(Respuesta resp, Integer calificacion)

}