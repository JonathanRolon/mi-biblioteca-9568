package com.mibiblioteca.mibiblioteca.domain
import groovy.transform.CompileStatic

@CompileStatic
interface Calificador {

    calificar(Respuesta resp, Integer calificacion)

}