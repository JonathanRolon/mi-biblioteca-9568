package com.mibiblioteca.mibiblioteca

import com.mibiblioteca.mibiblioteca.model.Alumno
import com.mibiblioteca.mibiblioteca.model.NivelAlumno
import com.mibiblioteca.mibiblioteca.model.Respuesta
import com.mibiblioteca.mibiblioteca.model.TemaHilo
import com.mibiblioteca.mibiblioteca.repository.AlumnoRepository

import com.mibiblioteca.mibiblioteca.repository.HiloRepository

import com.mibiblioteca.mibiblioteca.service.AlumnoService
import com.mibiblioteca.mibiblioteca.service.PublicadorService
import com.mibiblioteca.mibiblioteca.service.impl.AlumnoServiceImpl
import com.mibiblioteca.mibiblioteca.service.impl.PublicadorServiceImpl
import groovy.transform.CompileStatic
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

import javax.transaction.Transactional
import java.sql.Timestamp
import java.util.concurrent.ThreadLocalRandom

@ExtendWith(MockitoExtension.class)
@Transactional
@CompileStatic
@SpringBootTest
class AlumnoServiceTest {

    /* repositorios */
    @Autowired
    private HiloRepository hiloRepository
    @Autowired
    private AlumnoRepository alumnoRepository

    /* servicios */
    @Autowired
    private AlumnoService alumnoService
    @Autowired
    private PublicadorService publicadorService

    @BeforeEach
    public void setup() {
        alumnoService = new AlumnoServiceImpl(alumnoRepository)
        publicadorService = new PublicadorServiceImpl(hiloRepository, alumnoRepository)
    }

    @AfterEach
    void teardown() {
        alumnoRepository.deleteAll()
        hiloRepository.deleteAll()
    }

    /*================= Foro de consultas y creditos ==================*/

    Long generarDNIAleatorio() {
        ThreadLocalRandom.current().nextInt(6000000, 99999998 + 1);
    }

    Integer generarCalificacionPosRandom() {
        ThreadLocalRandom.current().nextInt(6, 9 + 1)
    }

    Alumno dadoQuesoyAlumnoNovatoRegular() {
        def fecNac = new Timestamp(System.currentTimeMillis())
        alumnoService.create(132456,"nombre","apellido",fecNac , "B")
    }

    Respuesta dadoQuePublicoUnaRespuestaEnElForo(Alumno alumno) {
        def fecNac = new Timestamp(System.currentTimeMillis())
        def otroAlumno = alumnoService.create(1111045654,"nombre","apellido",fecNac , "B")
        def hilo = publicadorService.crearHilo(otroAlumno.getDNI(), TemaHilo.CS_LENGUAJE,
                "Como interpreto el cap. 3 del libro X?")
        publicadorService.responder(alumno.getDNI(), hilo, "respuestahilo1")
    }

    boolean noPuedoCalificarRespForo(Alumno alumno) {

        def fecNac = new Timestamp(System.currentTimeMillis()),
            preguntador = alumnoService.create(132789,"nombre","apellido",fecNac , "C"),
            contestador = alumnoService.create(4678,"nombre","apellido",fecNac , "B")

        preguntador.subirNivel()
        contestador.subirNivel()
        contestador.subirNivel()

        def hilo = publicadorService.crearHilo(preguntador.getDNI(), TemaHilo.CS_BIOLOGICAS,
                "Como interpreto el cap. 3 del libro Y?")
        def respuesta = publicadorService.responder(contestador.getDNI(), hilo, "respuestahilo2")
        def calificac = publicadorService.calificar(alumno, respuesta, 7)

        calificac === null

    }

    @Test
    void alumnoNovatoRegularRecibePrimerCalificacionPorEncimaDeCincoNoSubeNivel() {

        def fecNac = new Timestamp(System.currentTimeMillis()),
            novato = dadoQuesoyAlumnoNovatoRegular(),
            respuesta = dadoQuePublicoUnaRespuestaEnElForo(novato),
            pro = alumnoService.create(53135,"nombre","apellido",fecNac , "C")


        assert (novato.getCalificPositivasEnForo() == 0)
        for (int i = 0; i < 2; i++) pro.subirNivel()

        //Cuando otro usuario califica por encima de 5 mi respuesta
        publicadorService.calificar(pro, respuesta, 6)
        //Entonces no sumo creditos y no puedo calificar respuestas de otros alumnos
        assert (novato.getCreditos() == 0 && noPuedoCalificarRespForo(novato))
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
    void alumnoNovatoRegularRecibeDecimaCalificacionPorEncimaDeCincoSubeHastaAlumnoMedio() {

        def fecNac = new Timestamp(System.currentTimeMillis()),
            novato = dadoQuesoyAlumnoNovatoRegular(),
            respuesta = dadoQuePublicoUnaRespuestaEnElForo(novato),
            calificador

        while (!calificador) {
            calificador = alumnoService.create(generarDNIAleatorio(),"nombre","apellido",fecNac , "C")
        }

        for (int i = 0; i < 9; i++) {
            def otroCalif
            Integer randomCalif = generarCalificacionPosRandom();
            while (!otroCalif) {
                otroCalif = alumnoService.create(generarDNIAleatorio(),"nombre","apellido",fecNac , "C")
            }
            otroCalif.subirNivel()
            publicadorService.calificar(otroCalif, respuesta, randomCalif)
        }

        calificador.subirNivel()
        publicadorService.calificar(calificador, respuesta, generarCalificacionPosRandom())

        assert (novato.getNivel() == NivelAlumno.MEDIO &&
                novato.getCreditos() == 50 &&
                !noPuedoCalificarRespForo(novato)
        )
    }

    @Test
    void alumnoNovatoRegularRecibeDecimaCalificacionPeroDebajoDeCincoNoSubeNivel() {
        def fecNac = new Timestamp(System.currentTimeMillis()),
            novato = dadoQuesoyAlumnoNovatoRegular(),
            respuesta = dadoQuePublicoUnaRespuestaEnElForo(novato),
            calificador

        while (!calificador) {
            calificador = alumnoService.create(generarDNIAleatorio(),"nombre","apellido",fecNac , "C")
        }

        for (int i = 0; i < 9; i++) {
            def otroCalif
            Integer randomCalif = generarCalificacionPosRandom();
            while (!otroCalif) {
                otroCalif = alumnoService.create(generarDNIAleatorio(),"nombre","apellido",fecNac , "C")
            }
            otroCalif.subirNivel()
            publicadorService.calificar(otroCalif, respuesta, randomCalif)
        }

        calificador.subirNivel()
        publicadorService.calificar(calificador, respuesta, 4)

        assert (novato.getNivel() == NivelAlumno.NOVATO &&
                novato.getCreditos() == 0 &&
                noPuedoCalificarRespForo(novato)
        )
    }

    @Test
    void alumnoMedioRegularRecibe20maCalificacionEncimaDeCincoSuma30Creditos() {
        def fecNac = new Timestamp(System.currentTimeMillis()),
                medio = alumnoService.create(generarDNIAleatorio(),"nombre","apellido",fecNac , "C"),
            respuesta = dadoQuePublicoUnaRespuestaEnElForo(medio),
            calificador

        medio.subirNivel()

        while (!calificador) {
            calificador = alumnoService.create(generarDNIAleatorio(),"nombre","apellido",fecNac , "C")
            calificador.subirNivel()
        }

        for (int i = 0; i < 19; i++) {
            def otroCalif
            Integer randomCalif = generarCalificacionPosRandom();
            while (!otroCalif) {
                otroCalif = alumnoService.create(generarDNIAleatorio(),"nombre","apellido",fecNac , "C")
            }
            otroCalif.subirNivel()
            publicadorService.calificar(otroCalif, respuesta, randomCalif)
        }

        publicadorService.calificar(calificador, respuesta, generarCalificacionPosRandom())

        assert (medio.getNivel() == NivelAlumno.MEDIO &&
                medio.getCreditos() == 30 &&
                !noPuedoCalificarRespForo(medio)
        )
    }

    @Test
    void alumnoMedioNoRegularRecibeDecimaCalificacionEncimaDeCincoNoSumaCreditos() {

        def fecNac = new Timestamp(System.currentTimeMillis()),
            medio = alumnoService.create(generarDNIAleatorio(), "nombre", "apellido", fecNac, "C"),
            respuesta = dadoQuePublicoUnaRespuestaEnElForo(medio),
            calificador

        medio.subirNivel()
        medio.suspender()

        while (!calificador) {
            calificador = alumnoService.create(generarDNIAleatorio(), "nombre", "apellido", fecNac, "C")
            calificador.subirNivel()
        }

        for (int i = 0; i < 99; i++) {
            def otroCalif
            Integer randomCalif = generarCalificacionPosRandom();
            while (!otroCalif) {
                otroCalif = alumnoService.create(generarDNIAleatorio(),"nombre","apellido",fecNac , "C")
            }
            otroCalif.subirNivel()
            publicadorService.calificar(otroCalif, respuesta, randomCalif)
        }

        publicadorService.calificar(calificador, respuesta, generarCalificacionPosRandom())

        assert (medio.getNivel() == NivelAlumno.MEDIO &&
                medio.getCreditos() == 0 &&
                noPuedoCalificarRespForo(medio)
        )
    }

    /*================= Compra ==================*/

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
        def fecNac = new Timestamp(System.currentTimeMillis()),
            novato = alumnoService.create(generarDNIAleatorio(), "nombre", "apellido", fecNac, "C")

        // crear pedido con libro pendiente
        // en el servicio cuando va a comprar: validarTarjeta en tarjetaservice
        // en el servicio cuando va a comprar: obtener libros comprados del alumno == 2
        // en el servicio cuando va a comprar: si son validos cambiar estado pedido a cerrado
        // y añadir el libro al alumno validando si debe sumar creditos

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
    void alumnoMedioRegularConPrecondicionesAnterioresPagaLibroPendienteSuma100credSubeAPro() {

        // crear pedido con libro pendiente
        // registrarle una tarjeta valida al alumno
        // en el servicio cuando va a comprar: validarTarjeta en tarjetaservice
        // en el servicio cuando va a comprar: obtener libros comprados del alumno == 4
        // en el servicio cuando va a comprar: si son validos cambiar estado pedido a cerrado
        // y añadir el libro al alumno validando si debe sumar creditos, validar nivel

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
    void alumnoMedioRegularConPrecondicionesAnterioresPagaConCreditoSubeAPROSuma100cred() {
        //
    }

    /*================= Tareas ==================*/

    /*
     Dado que soy alumno regular
     y estoy dado de alta en el curso del docente que me asigna la tarea
     Cuando elijo una tarea asignada
     Entonces puedo visualizar el estado de la misma, fecha de entrega, la consigna.
    * */

    @Test
    void alumnoRegularSeleccionaTareaAsignadaPuedeVisualizarAtributosDeLaMisma() {

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
    void alumnoRegularCompletaItemsTareaAsignadaEstadoTareaEsPendienteEnvio() {

    }

    /*
    Dado que soy alumno regular
    y estoy dado de alta en el curso del docente que me asigna la tarea
    y la tarea se encuentra pendiente de envío
    Cuando elijo enviar la tarea para corrección
    Entonces no me es posible abrirla nuevamente y queda pendiente de calificación de parte del docente.
    * */

    @Test
    void alumnoRegularCumpleCondicionesAnterioresEnviaTareaEstadoTareaEsPendiendeCalificacion() {

    }

    @Test
    void alumnoRegularRecibeCalificacionDelDocenteEnTareaEnviadaEstadoTareaEsCalificada() {

    }

}
