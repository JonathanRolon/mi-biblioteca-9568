package com.mibiblioteca.mibiblioteca.tareas.service

import com.mibiblioteca.mibiblioteca.tareas.model.Alumno
import com.mibiblioteca.mibiblioteca.consultas.model.Calificacion
import com.mibiblioteca.mibiblioteca.tareas.model.Docente
import com.mibiblioteca.mibiblioteca.consultas.model.Respuesta
import com.mibiblioteca.mibiblioteca.tareas.model.Tarea
import groovy.transform.CompileStatic

@CompileStatic
interface DocenteService {

    List<Docente> findAll()

    Optional<Docente> findById(Long dni)

    Docente create(Docente docente)

    Docente update(Docente docente)

    void deleteById(Long dni)

    void asignarTarea(Alumno al, Tarea t)

    void calificarTarea(Tarea t, float nota)

    Calificacion calificar(Respuesta respuesta, Long publicador, Integer calificacion)

    Respuesta responder(Long dniPublicador, String respuesta, Long idHilo)

}
