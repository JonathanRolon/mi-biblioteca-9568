package com.mibiblioteca.mibiblioteca.tareas.service.Impl

import com.mibiblioteca.mibiblioteca.tareas.model.Alumno
import com.mibiblioteca.mibiblioteca.tareas.model.AsignacionTareaAlumno
import com.mibiblioteca.mibiblioteca.tareas.model.Curso
import com.mibiblioteca.mibiblioteca.tareas.model.Docente
import com.mibiblioteca.mibiblioteca.tareas.model.EstadoAsignacionTarea
import com.mibiblioteca.mibiblioteca.tareas.model.Tarea
import com.mibiblioteca.mibiblioteca.tareas.model.exception.TareaNoAsignableException
import com.mibiblioteca.mibiblioteca.tareas.model.exception.TareaNoResolubleException
import com.mibiblioteca.mibiblioteca.tareas.repository.AlumnoRepository
import com.mibiblioteca.mibiblioteca.tareas.repository.DocenteRepository
import com.mibiblioteca.mibiblioteca.tareas.repository.TareaAlumnoRepository
import com.mibiblioteca.mibiblioteca.tareas.service.ManejadorTareasService
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@CompileStatic
class ManejadorTareasServiceImpl implements ManejadorTareasService {

    @Autowired
    private AlumnoRepository alumnoRepository
    @Autowired
    private DocenteRepository docenteRepository
    @Autowired
    private TareaAlumnoRepository tareaAlumnoRepository

    ManejadorTareasServiceImpl(AlumnoRepository a, DocenteRepository doc, TareaAlumnoRepository tar) {
        this.alumnoRepository = a
        this.docenteRepository = doc
        this.tareaAlumnoRepository = tar
    }

    private void asignarTareaAlCurso(Docente docente, Tarea tarea, Curso curso) {
        try {
            docente.asignarTarea(tarea, curso)
        } catch (RuntimeException ex) {
            throw ex
        }
    }

    private void asignarTareaAlumnosDelCurso(Curso curso, Tarea tarea) {
        def tareaAlumnoAsignacion,
            alumnosCurso


        alumnosCurso = alumnoRepository.findAll().findAll {
            it -> it.getCurso() === curso.getDenominacion()
        }

        if (alumnosCurso.size() === 0)
            throw new TareaNoAsignableException("Error: No existen alumnos asignados al curso de la tarea.")

        alumnosCurso.each { it ->
            tareaAlumnoAsignacion = new AsignacionTareaAlumno(tarea.getNroTarea(),
                    it.getDNI(), tarea.getFechaEntrega())
            tareaAlumnoRepository.save(tareaAlumnoAsignacion)
        }

    }

    @Override
    void asignar(Tarea tarea, Curso curso, Docente docente) {
        asignarTareaAlCurso(docente, tarea, curso)
        asignarTareaAlumnosDelCurso(curso, tarea)
    }

    @Override
    List<AsignacionTareaAlumno> getAsignacionesTareas(Curso curso, Alumno alumno) {
        def tareasCurso = curso.getTareasAsignadas(),
            tareasAlumno = tareaAlumnoRepository.findAll().findAll { it ->
                it.getAlumno() === alumno.getDNI()
            }

        tareasAlumno = tareasAlumno.findAll { tarea ->
            tareasCurso.findAll { it -> it.getNroTarea() === tarea.getNroTarea() }
        }

        tareasAlumno
    }

    @Override
    AsignacionTareaAlumno getAsignacionTarea(Long nroTarea) {
        def tareaAlumno = tareaAlumnoRepository.findAll().find { it ->
            it.getNroTarea() === nroTarea
        }

        tareaAlumno
    }

    @Override
    AsignacionTareaAlumno resolver(Tarea tareaCurso, String respuesta) {
        def asignacion = tareaAlumnoRepository.findAll().find { it ->
            it.getNroTarea() === tareaCurso.getNroTarea()
        }

        if (!asignacion)
            throw new TareaNoResolubleException("Error: La tarea no se se encuentra " +
                    "asignada al alumno")
        asignacion = asignacion.resolverTarea(respuesta)
        tareaAlumnoRepository.save(asignacion)
    }

    @Override
    AsignacionTareaAlumno cerrarTarea(AsignacionTareaAlumno asignacionTareaAlumno) {
        asignacionTareaAlumno = asignacionTareaAlumno.cerrar()
        tareaAlumnoRepository.save(asignacionTareaAlumno)
    }

    @Override
    AsignacionTareaAlumno calificar(AsignacionTareaAlumno tarea, Integer calificacion){
        tarea.calificar(calificacion)
        tareaAlumnoRepository.save(tarea)
    }
}
