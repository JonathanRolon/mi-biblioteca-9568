package com.mibiblioteca.mibiblioteca.service

import com.mibiblioteca.mibiblioteca.model.Alumno
import com.mibiblioteca.mibiblioteca.model.Calificacion
import com.mibiblioteca.mibiblioteca.model.Hilo
import com.mibiblioteca.mibiblioteca.model.Respuesta
import com.mibiblioteca.mibiblioteca.model.TemaHilo
import groovy.transform.CompileStatic

@CompileStatic
interface PublicadorService {

    Calificacion calificar(Alumno calificador, Respuesta respuesta, Integer calificacion)

    Respuesta responder(Long dniEmisor, Hilo hilo, String respuesta)

    Hilo crearHilo(Long dniPreguntador, TemaHilo temaHilo, String consulta)

}