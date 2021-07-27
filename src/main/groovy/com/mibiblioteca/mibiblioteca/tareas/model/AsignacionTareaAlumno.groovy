package com.mibiblioteca.mibiblioteca.tareas.model

import com.mibiblioteca.mibiblioteca.tareas.model.exception.CalificacionInvalidaException
import com.mibiblioteca.mibiblioteca.tareas.model.exception.TareaNoCalificableException
import com.mibiblioteca.mibiblioteca.tareas.model.exception.TareaNoCerrableException
import com.mibiblioteca.mibiblioteca.tareas.model.exception.TareaNoResolubleException
import groovy.transform.CompileStatic

import javax.persistence.Column
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import java.sql.Timestamp
import java.time.LocalDateTime

enum EstadoAsignacionTarea {
    ABIERTA_PEND_RES, ABIERTA_PEND_CALIF, CERRADA_PEND_CALIF, CERRADA
}

@Entity
@CompileStatic
class AsignacionTareaAlumno {

    private final Integer MIN_CALIF = 1, MAX_CALIF = 10

    @EmbeddedId
    TareaAlumnoIdentity id

    @Column(nullable = false)
    private Timestamp fechaAsignacion

    @Column(nullable = true)
    private Timestamp fechaCierre

    @Column(nullable = true)
    private Timestamp fechaCalificacion

    @Column(nullable = true)
    String respuesta

    @Column(nullable = false)
    private Timestamp fechaLimite

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    EstadoAsignacionTarea estado

    @Column(nullable = true)
    Integer calificacion

    AsignacionTareaAlumno(Long nroTarea, Long DNIAlumno, Timestamp entrega) {

        id = new TareaAlumnoIdentity()
        id.setDNI(DNIAlumno)
        id.setIdTarea(nroTarea)
        estado = EstadoAsignacionTarea.ABIERTA_PEND_RES
        fechaAsignacion = Timestamp.valueOf(LocalDateTime.now())
        fechaLimite = entrega
    }

    AsignacionTareaAlumno() {}

    Long getAlumno() {
        this.id.getDNI()
    }

    Long getNroTarea() {
        this.id.getIdTarea()
    }

    Timestamp getFechaAsignacion(){
        fechaAsignacion
    }

    Timestamp getFechaCierre(){
        fechaCierre
    }

    Timestamp getFechaCalificacion(){
        fechaCalificacion
    }

    Timestamp getFechaLimite(){
        fechaLimite
    }

    Boolean esResoluble(){
        def ahora = Timestamp.valueOf(LocalDateTime.now())
        def resultado = ahora.compareTo(fechaLimite)
        def estadoOk = (estado.toString() == EstadoAsignacionTarea.ABIERTA_PEND_RES.toString())
        (resultado < 0) && estadoOk
    }

    AsignacionTareaAlumno resolverTarea(String respuesta) {
        def fechaEnvio = Timestamp.valueOf(LocalDateTime.now())

        if (estado.toString() != EstadoAsignacionTarea.ABIERTA_PEND_RES.toString())
            throw new TareaNoResolubleException("La tarea ya no puede resolverse.")

        estado = EstadoAsignacionTarea.ABIERTA_PEND_CALIF
        fechaCierre = fechaEnvio
        this.respuesta = respuesta
        this
    }

    Integer getCalificacion() {
        calificacion
    }

    AsignacionTareaAlumno cerrar() {
        def invalido = (estado.toString() != EstadoAsignacionTarea.ABIERTA_PEND_CALIF.toString()) &&
                        (estado.toString() != EstadoAsignacionTarea.ABIERTA_PEND_RES.toString())
        if (invalido)
         {
            throw new TareaNoCerrableException("La tarea no se encuentra abierta.")
        }
        estado = EstadoAsignacionTarea.CERRADA_PEND_CALIF
        fechaCierre = Timestamp.valueOf(LocalDateTime.now())
        this
    }

    void calificar(Integer calificacion) {
        if (calificacion < MIN_CALIF || calificacion > MAX_CALIF)
            throw new CalificacionInvalidaException("Error: Puntuación no permitida.")
        if (estado.toString() != EstadoAsignacionTarea.CERRADA_PEND_CALIF.toString())
            throw new TareaNoCalificableException("Error: La tarea debe estar pendiente de calificación.")

        this.calificacion = calificacion
        estado = EstadoAsignacionTarea.CERRADA
        fechaCalificacion = Timestamp.valueOf(LocalDateTime.now())
    }

    Boolean estaCerrada(){
        def estado = estado.toString() == EstadoAsignacionTarea.CERRADA_PEND_CALIF.toString() ||
        estado.toString() == EstadoAsignacionTarea.CERRADA.toString()
        estado
    }

    Boolean estaCerradaPendiente(){
        estado.toString() == EstadoAsignacionTarea.CERRADA_PEND_CALIF.toString()
    }

    Boolean estaVencidaPendiente(){
        def ahora = Timestamp.valueOf(LocalDateTime.now())
        def resultado = ahora.compareTo(fechaLimite)
        resultado >= 0 && !estaCerrada()
    }

}
