package com.mibiblioteca.mibiblioteca.tareas

import com.mibiblioteca.mibiblioteca.tareas.model.Alumno
import com.mibiblioteca.mibiblioteca.tareas.model.AsignacionTareaAlumno
import com.mibiblioteca.mibiblioteca.tareas.model.EstadoAsignacionTarea
import com.mibiblioteca.mibiblioteca.tareas.model.Tarea
import groovy.transform.CompileStatic
import org.junit.jupiter.api.Test

import java.sql.Timestamp

@CompileStatic
class AsignacionTareaAlumnoTest {

    @Test
    void resolverTareaDejaTareaEnEstadoPendienteCalificacion() {

        def fecNac = Timestamp.valueOf("2004-09-23 10:10:10.0")
        def fechaEntrega = Timestamp.valueOf("2021-09-23 10:10:10.0")
        def alumno = new Alumno(5413513, "nombre", "apellido", fecNac, "A")
        def tarea = new Tarea("consigna", fechaEntrega)
        def asignacionTarea =
                new AsignacionTareaAlumno(tarea.getNroTarea(), alumno.getDNI(), fechaEntrega)
        assert(asignacionTarea.getEstado() === EstadoAsignacionTarea.ABIERTA_PEND_RES)
        asignacionTarea.resolverTarea("Respuesta.")
        assert(asignacionTarea.getEstado() === EstadoAsignacionTarea.ABIERTA_PEND_CALIF)
    }

    @Test
    void cerrarTareaDejaTareaEnEstadoCerradaPendienteCalificacion() {
        def fecNac = Timestamp.valueOf("2004-09-23 10:10:10.0")
        def fechaEntrega = Timestamp.valueOf("2021-09-23 10:10:10.0")
        def alumno = new Alumno(5413513, "nombre", "apellido", fecNac, "A")
        def tarea = new Tarea("consigna", fechaEntrega)
        def asignacionTarea =
                new AsignacionTareaAlumno(tarea.getNroTarea(), alumno.getDNI(), fechaEntrega)
        asignacionTarea.cerrar()
        assert(asignacionTarea.getEstado() === EstadoAsignacionTarea.CERRADA_PEND_CALIF)

    }
    @Test
    void calificarTareaCon10EfectivamentePuntuaCon10LaAsignacionDeTareaAlumno() {
        def fecNac = Timestamp.valueOf("2004-09-23 10:10:10.0")
        def fechaEntrega = Timestamp.valueOf("2021-09-23 10:10:10.0")
        def alumno = new Alumno(5413513, "nombre", "apellido", fecNac, "A")
        def tarea = new Tarea("consigna", fechaEntrega)
        def asignacionTarea =
                new AsignacionTareaAlumno(tarea.getNroTarea(), alumno.getDNI(), fechaEntrega)
        asignacionTarea.resolverTarea("")
        asignacionTarea.cerrar()
        asignacionTarea.calificar(10)
        assert(asignacionTarea.getCalificacion() === 10)
    }

}
