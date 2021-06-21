package com.mibiblioteca.mibiblioteca


import groovy.transform.CompileStatic
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Before
import org.junit.jupiter.api.Test

@CompileStatic
class AlumnoTest{

    @Before
    void init() {

    }

    @After
    void teardown() {
        //clean memory
    }

    /*================= Foro de consultas y creditos ==================*/

    /* *
    Dado soy alumno regular
    y que publiqué una respuesta en el foro
    y tengo nivel novato
    y tengo 0 calificaciones por encima de 5  en otras respuestas
    Cuando otro usuario califica por encima de 5 mi respuesta
    Entonces no sumo creditos y no puedo calificar respuestas de otros alumnos.
    * */
    @Test
    void alumnoNovatoRegularRecibePrimerCalificacionPorEncimaDeCincoNoSubeHastaAlumnoMedio(){


    }

    /* *
    Dado soy alumno regular
    y que publiqué una respuesta en el foro
    y tengo nivel novato
    y tengo nueve calificaciones por encima de 5  en otras respuestas
    Cuando otro usuario califica por encima de 5 mi respuesta
    Entonces se agregan 50 créditos a mi cuenta,  paso a ser alumno Medio y puedo calificar respuestas de otros alumnos.
    * */
    @Test
    void alumnoNovatoRegularRecibeDecimaCalificacionPorEncimaDeCincoSubeHastaAlumnoMedio(){


    }

    @Test
    void alumnoNovatoRegularRecibeDecimaCalificacionPeroDebajoDeCincoNoSubeNivel(){

    }

    @Test
    void alumnoMedioRegularRecibeDecimaCalificacionEncimaDeCincoSuma30Creditos(){

    }

    @Test
    void alumnoMedioNoRegularRecibeDecimaCalificacionEncimaDeCincoNoSumaCreditos(){

    }

    @Test
    void alumnoMedioRegularSinCalificacionesEnRtasRecibe10EncimaDe5Suma30Creditos(){

    }

    /*================= Compra ==================*/

    /*
    Dado que soy alumno regular nivel Novato
    y busco un libro de pago de mi interés
    Cuando  lo guardo como posible compra
    Entonces el libro queda pendiente de pago.
    * */
    @Test
    void alumnoNovatoRegularEligeLibroDePagoYQuedaDisponibleEnSuCanastaDeCompra(){

    }

    /* *
    Dado que soy alumno regular nivel Novato
    y tengo un libro pendiente de pago
    y tengo tarjeta de crédito válida
    y tengo menos de 2 libros comprados
    Cuando  completo correctamente los datos de pago y elijo pagar
    Entonces el libro se añade a mi biblioteca y se me añaden 100 créditos a mi cuenta.
    * */
    @Test
    void alumnoNovatoRegularConPrecondicionesAnterioresCompraLibroSuma100creditos() {

    }

    /* *
     Dado que soy alumno regular nivel Medio
     y tengo un  libro de ciencias de la computación pendiente de pago
     y tengo tarjeta de crédito válida
     y tengo al menos 4  libros comprados
     Cuando  completo correctamente los datos de pago y elijo pagar
     Entonces el libro se añade a mi biblioteca, se me añaden 100 créditos a mi cuenta y subo a nivel PRO.
     * */
    @Test
    void alumnoMedioRegularConPrecondicionesAnterioresPagaLibroPendienteSuma100credSubeAPro(){

    }

    /* *
    Dado que soy alumno regular nivel Medio
    y tengo 4 libros comprados
    y tengo un  libro pendiente de pago
    y tengo al menos 400 creditos en mi cuenta
    y tengo tarjeta de crédito válida
	y elijo pagar con créditos
	y completo correctamente los datos de pago
    Cuando elijo pagar
    Entonces el libro se añade a mi biblioteca, se me añaden 100 créditos a mi cuenta y subo a nivel PRO.
     * */
    @Test
    void alumnoMedioRegularConPrecondicionesAnterioresPagaConCreditoSubeAPROSuma100cred(){

    }

    /*================= Préstamo de material ==================*/
    /* *
    Dado que soy alumno regular
    y seleccione el material que necesito
    y tengo menos de 3 tipos de material prestados actualmente
    Cuando elijo solicitar préstamo
    Entonces el material queda pendiente de aprobación en un periodo de 72 hs max. de parte de un editor.
     * */
    @Test
    void alumnoRegularCumpleCondicionesAnterioresSolicitaPrestamoMaterialQuedaPendienteAprob72hs(){

    }

    @Test
    void alumnoRegularVisualizaElPrestamoAprobadoPorUnEditorEnSuBiblioteca(){

    }

    /*================= Tareas ==================*/

    /*
     Dado que soy alumno regular
     y estoy dado de alta en el curso del docente que me asigna la tarea
     Cuando elijo una tarea asignada
     Entonces puedo visualizar el estado de la misma, fecha de entrega, la consigna.
    * */
    @Test
    void alumnoRegularSeleccionaTareaAsignadaPuedeVisualizarAtributosDeLaMisma(){

    }

    /*
    Dado que soy alumno regular
    y estoy dado de alta en el curso del docente que me asigna la tarea
    y elegí una tarea asignada
    y la fecha de entrega aún no se cumplió
    Cuando completo los ítems de la tarea
    Entonces la tarea queda pendiente de envío.
    * */
    @Test
    void alumnoRegularCompletaItemsTareaAsignadaEstadoTareaEsPendienteEnvio(){

    }

    /*
    Dado que soy alumno regular
    y estoy dado de alta en el curso del docente que me asigna la tarea
    y la tarea se encuentra pendiente de envío
    Cuando elijo enviar la tarea para corrección
    Entonces no me es posible abrirla nuevamente y queda pendiente de calificación de parte del docente.
    * */
    @Test
    void alumnoRegularCumpleCondicionesAnterioresEnviaTareaEstadoTareaEsPendiendeCalificacion(){

    }

    @Test
    void alumnoRegularRecibeCalificacionDelDocenteEnTareaEnviadaEstadoTareaEsCalificada(){

    }

}
