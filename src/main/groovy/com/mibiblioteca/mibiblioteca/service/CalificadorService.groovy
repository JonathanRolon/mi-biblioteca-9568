package com.mibiblioteca.mibiblioteca.service

import com.mibiblioteca.mibiblioteca.domain.Respuesta
import groovy.transform.CompileStatic

@CompileStatic
interface CalificadorService {

    void calificar(Respuesta respuesta, int calificacion)

}