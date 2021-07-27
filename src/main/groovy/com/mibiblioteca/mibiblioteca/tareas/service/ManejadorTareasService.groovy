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

    AsignacionTareaAlumno resolver(Long nroTarea, Long alumnoDNI, String respuesta)

    AsignacionTareaAlumno cerrarTarea(AsignacionTareaAlumno asignacionTareaAlumno)

    AsignacionTareaAlumno getAsignacionTarea(Long nroDNI, Long nroTarea)

    AsignacionTareaAlumno calificar(AsignacionTareaAlumno tarea, Integer calificacion)

    List<AsignacionTareaAlumno> getAsignacionesTareas(Alumno alumno)

    List<AsignacionTareaAlumno> getAsignacionesTareas(Long nroTarea)

    String getConsignaTarea(Long nroTarea)

    AsignacionTareaAlumno cerrarAsignacionTarea(Long nroTarea, Long dniAlumno)

    AsignacionTareaAlumno calificar(Long nroTarea, Long dniAlumno, Integer calificacion)
}