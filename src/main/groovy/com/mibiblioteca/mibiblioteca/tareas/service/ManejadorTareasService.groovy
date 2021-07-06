package com.mibiblioteca.mibiblioteca.tareas.service

import com.mibiblioteca.mibiblioteca.tareas.model.Alumno
import com.mibiblioteca.mibiblioteca.tareas.model.AsignacionTareaAlumno
import com.mibiblioteca.mibiblioteca.tareas.model.Curso
import com.mibiblioteca.mibiblioteca.tareas.model.Docente
import com.mibiblioteca.mibiblioteca.tareas.model.Tarea
import groovy.transform.CompileStatic

@CompileStatic
interface ManejadorTareasService {


    void asignar(Tarea tarea, Curso curso, Docente docente)

    List<AsignacionTareaAlumno> getAsignacionesTareas(Curso curso, Alumno alumno)

    AsignacionTareaAlumno getAsignacionTarea(Long nroTarea)

    AsignacionTareaAlumno resolver(Tarea tareaCurso, String respuesta)

    AsignacionTareaAlumno cerrarTarea(AsignacionTareaAlumno asignacionTareaAlumno)

    AsignacionTareaAlumno calificar(AsignacionTareaAlumno tarea, Integer calificacion)
}