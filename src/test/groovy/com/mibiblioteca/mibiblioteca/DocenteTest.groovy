package com.mibiblioteca.mibiblioteca

import groovy.transform.CompileStatic
import org.junit.jupiter.api.Test

@CompileStatic
class DocenteTest {

    /*================= Tareas ==================*/

    /*
    Dado que se cumplió la fecha de entrega para una tarea dada a un curso dado
    y la tarea está en estado abierta vencida
    y elijo un alumno particular del curso
    Cuando elijo la tarea en cuestión entre sus tareas pendientes y la cierro
    Entonces la tarea queda en estado cerrada pendiente de calificación.
    * */
    @Test
    void docenteCierraTareaEstadoTareaEsCerradaPendienteCalificacion(){

    }

    /*
    Dado que cerré una tarea dada para un alumno dado de un curso dado
    y la tarea está en estado cerrada pendiente de calificación
    Cuando elijo calificar
    Entonces tengo la posibilidad de asignarle una nota entre 1 y 10.
    * */
    @Test
    void posiblesValoresDeCalificacionDeTarea1a10(){

    }

    /*
    Dado que califique la tarea de un alumno
    Cuando elijo finalizar
    Entonces puedo ver la lista de tareas calificadas y las no calificadas.
    * */
    @Test
    void docenteCalificaTareaAlFinalizarVisualizaEstadoTareaCerradaCalificada(){

    }

}
