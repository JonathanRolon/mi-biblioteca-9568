package com.mibiblioteca.mibiblioteca.tareas.service

import com.mibiblioteca.mibiblioteca.tareas.model.Alumno
import com.mibiblioteca.mibiblioteca.consultas.model.Calificacion
import com.mibiblioteca.mibiblioteca.tareas.model.Curso
import com.mibiblioteca.mibiblioteca.tareas.model.Docente
import com.mibiblioteca.mibiblioteca.consultas.model.Respuesta
import com.mibiblioteca.mibiblioteca.tareas.model.Tarea
import groovy.transform.CompileStatic

import java.sql.Timestamp

@CompileStatic
interface DocenteService {

    List<Docente> findAll()

    Optional<Docente> findById(Long dni)

    Docente create(Long dni, String nombre, String apellido, Timestamp fecNac, List<Curso> cursos)

    Docente update(Docente docente)

    void deleteById(Long dni)

    void asignarTarea(Alumno al, Tarea t)

    void calificarTarea(Tarea t, float nota)

    Calificacion calificar(Respuesta respuesta, Long publicador, Integer calificacion)

    Respuesta responder(Long dniPublicador, String respuesta, Long idHilo)

}
