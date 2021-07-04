package com.mibiblioteca.mibiblioteca.consultas.service

import com.mibiblioteca.mibiblioteca.tareas.model.Alumno
import com.mibiblioteca.mibiblioteca.consultas.model.Calificacion
import com.mibiblioteca.mibiblioteca.consultas.model.Hilo
import com.mibiblioteca.mibiblioteca.consultas.model.Respuesta
import com.mibiblioteca.mibiblioteca.consultas.model.TemaHilo
import groovy.transform.CompileStatic

@CompileStatic
interface PublicadorService {

    Calificacion calificar(Alumno calificador, Respuesta respuesta, Integer calificacion)

    Respuesta responder(Long dniEmisor, Hilo hilo, String respuesta)

    Hilo crearHilo(Long dniPreguntador, TemaHilo temaHilo, String consulta)

}