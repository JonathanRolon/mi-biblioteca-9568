package com.mibiblioteca.mibiblioteca

import com.mibiblioteca.mibiblioteca.model.Material
import com.mibiblioteca.mibiblioteca.model.PedidoMaterial
import com.mibiblioteca.mibiblioteca.model.TipoMaterial
import com.mibiblioteca.mibiblioteca.repository.PedidoMaterialRepository
import com.mibiblioteca.mibiblioteca.service.PedidoMaterialService
import com.mibiblioteca.mibiblioteca.service.impl.PedidoMaterialServiceImpl
import groovy.transform.CompileStatic
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

import javax.transaction.Transactional
import java.sql.Timestamp
import java.time.LocalDateTime

@ExtendWith(MockitoExtension.class)
@Transactional
@CompileStatic
@SpringBootTest
class DocenteTest {

    @Autowired
    private PedidoMaterialRepository pedidoMaterialRepository

    @Autowired
    private PedidoMaterialService pedidoMaterialService

    @BeforeEach
    void setup(){
        pedidoMaterialService = new PedidoMaterialServiceImpl(pedidoMaterialRepository)
    }

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
