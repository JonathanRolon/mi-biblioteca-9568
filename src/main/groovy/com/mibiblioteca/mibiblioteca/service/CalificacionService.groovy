package com.mibiblioteca.mibiblioteca.service

import com.mibiblioteca.mibiblioteca.model.Alumno
import com.mibiblioteca.mibiblioteca.model.Calificacion
import com.mibiblioteca.mibiblioteca.model.Respuesta

interface CalificacionService {

    List<Calificacion> getCalificaciones(Long dniAlumno, Long idHilo)

    List<Calificacion> getCalificadoEnTodasLasRespuestas(Alumno alumno)

    List<Calificacion> getCalificadoEncimaDe(Alumno calificado, Integer calif)

    Calificacion calificar(Alumno calificador, Respuesta respuesta, Integer calificacion)

}