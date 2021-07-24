package com.mibiblioteca.mibiblioteca.consultas.integration

import com.mibiblioteca.mibiblioteca.tareas.model.Alumno
import com.mibiblioteca.mibiblioteca.tareas.model.Curso
import com.mibiblioteca.mibiblioteca.tareas.model.NivelAlumno
import com.mibiblioteca.mibiblioteca.consultas.model.Respuesta
import com.mibiblioteca.mibiblioteca.consultas.model.TemaHilo
import com.mibiblioteca.mibiblioteca.tareas.service.AlumnoService
import com.mibiblioteca.mibiblioteca.consultas.service.PublicadorService

import groovy.transform.CompileStatic
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

import javax.transaction.Transactional
import java.sql.Timestamp
import java.util.concurrent.ThreadLocalRandom

@Transactional
@CompileStatic
@SpringBootTest
class PublicadorServiceTest {

    /* servicios */
    @Autowired
    private AlumnoService alumnoService
    @Autowired
    private PublicadorService publicadorService

    /* data */
    Curso curso
    Timestamp fecNac

    /* útiles */

    @BeforeEach
    public void setup() {
        curso = new Curso("A")
        fecNac = new Timestamp(System.currentTimeMillis())
    }

    @AfterEach
    void teardown() {
        curso = null
        fecNac = null
    }

    Long generarDNIAleatorio() {
        ThreadLocalRandom.current().nextInt(6000000, 99999998 + 1);
    }

    Integer generarCalificacionPosRandom() {
        ThreadLocalRandom.current().nextInt(6, 9 + 1)
    }

    Alumno dadoQuesoyAlumnoNovatoRegular() {

        alumnoService.create(132456, "nombre", "apellido", fecNac, curso.getDenominacion())
    }

    Respuesta dadoQuePublicoUnaRespuestaEnElForo(Alumno alumno) {
        def otroAlumno = alumnoService.create(1111045654, "nombre", "apellido", fecNac,
                curso.getDenominacion())
        def hilo = publicadorService.crearHilo(otroAlumno.getDNI(), TemaHilo.CS_LENGUAJE,
                "Como interpreto el cap. 3 del libro X?")
        publicadorService.responder(alumno.getDNI(), hilo, "respuestahilo1")
    }

    boolean noPuedoCalificarRespForo(Alumno alumno) {

        def preguntador = alumnoService.create(132789, "nombre", "apellido", fecNac,
                curso.getDenominacion()),
            contestador = alumnoService.create(4678, "nombre", "apellido", fecNac,
                    curso.getDenominacion())

        preguntador.subirNivel()
        contestador.subirNivel()
        contestador.subirNivel()

        def hilo = publicadorService.crearHilo(preguntador.getDNI(), TemaHilo.CS_BIOLOGICAS,
                "Como interpreto el cap. 3 del libro Y?")
        def respuesta = publicadorService.responder(contestador.getDNI(), hilo, "respuestahilo2")

        try {
            publicadorService.calificar(alumno, respuesta, 7)
            false
        } catch (RuntimeException ex) {
            true
        }

    }

    void dadoQueYaTengoNCalificaciones(Respuesta respuesta, Integer cantidad) {
        for (int i = 0; i < cantidad; i++) {
            def otroCalif
            Integer randomCalif = generarCalificacionPosRandom();
            while (!otroCalif) {
                otroCalif = alumnoService.create(generarDNIAleatorio(), "nombre", "apellido", fecNac,
                        curso.getDenominacion())
            }
            otroCalif.subirNivel()
            try {
                publicadorService.calificar(otroCalif, respuesta, randomCalif)
            } catch (RuntimeException ex) {
            }
        }
    }

    Alumno getCalificadorMedio() {
        def calificador
        while (!calificador) {
            calificador = alumnoService.create(generarDNIAleatorio(), "nombre", "apellido", fecNac,
                    curso.getDenominacion())
        }
        calificador.subirNivel()
        calificador
    }

    Alumno getCalificadorPRO() {
        def pro = alumnoService.create(53135, "nombre", "apellido", fecNac, curso.getDenominacion())
        for (int i = 0; i < 2; i++) pro.subirNivel()
        pro
    }

    /* Tests */

    @Test
    void alumnoNovatoRegularRecibePrimerCalificacionPorEncimaDeCincoNoSubeNivel() {

        def novato = dadoQuesoyAlumnoNovatoRegular(),
            respuesta = dadoQuePublicoUnaRespuestaEnElForo(novato),
            pro = getCalificadorPRO()

        assert (novato.getCalificTotalesNivelEnForo() == 0)

        //Cuando otro usuario califica por encima de 5 mi respuesta
        publicadorService.calificar(pro, respuesta, 6)
        //Entonces no sumo creditos y no puedo calificar respuestas de otros alumnos
        assert (novato.getCreditos() == 0 && noPuedoCalificarRespForo(novato))
    }

    @Test
    void alumnoNovatoRegularRecibeDecimaCalificacionPorEncimaDeCincoSubeHastaAlumnoMedio() {

        def novato = dadoQuesoyAlumnoNovatoRegular(),
            respuesta = dadoQuePublicoUnaRespuestaEnElForo(novato),
            calificador = getCalificadorMedio()

        dadoQueYaTengoNCalificaciones(respuesta, 9)

        publicadorService.calificar(calificador, respuesta, generarCalificacionPosRandom())

        assert (novato.getNivel() == NivelAlumno.MEDIO &&
                novato.getCreditos() == 50 &&
                !noPuedoCalificarRespForo(novato)
        )
    }


    @Test
    void alumnoNovatoRegularRecibeDecimaCalificacionPeroDebajoDeCincoNoSubeNivel() {
        def novato = dadoQuesoyAlumnoNovatoRegular(),
            respuesta = dadoQuePublicoUnaRespuestaEnElForo(novato),
            calificador = getCalificadorMedio()

        dadoQueYaTengoNCalificaciones(respuesta, 9)

        //decima calificacion pero debajo de 5
        publicadorService.calificar(calificador, respuesta, 4)

        assert (novato.getNivel() == NivelAlumno.NOVATO &&
                novato.getCreditos() == 0 &&
                noPuedoCalificarRespForo(novato)
        )
    }

    @Test
    void alumnoMedioRegularRecibe20maCalificacionEncimaDeCincoSuma30Creditos() {
        def medio = alumnoService.create(generarDNIAleatorio(), "nombre", "apellido", fecNac,
                curso.getDenominacion()),
            respuesta = dadoQuePublicoUnaRespuestaEnElForo(medio),
            calificador = getCalificadorMedio()

        medio.subirNivel()

        dadoQueYaTengoNCalificaciones(respuesta, 19)

        publicadorService.calificar(calificador, respuesta, generarCalificacionPosRandom())

        assert (medio.getNivel() == NivelAlumno.MEDIO &&
                medio.getCreditos() == 30 &&
                !noPuedoCalificarRespForo(medio)
        )
    }

    @Test
    void alumnoMedioNoRegularRecibeDecimaCalificacionEncimaDeCincoNoSumaCreditos() {

        def medio = alumnoService.create(generarDNIAleatorio(), "nombre", "apellido", fecNac,
                curso.getDenominacion()),
            respuesta = dadoQuePublicoUnaRespuestaEnElForo(medio),
            calificador = getCalificadorMedio()

        medio.subirNivel()
        medio.suspender()
        dadoQueYaTengoNCalificaciones(respuesta, 99)

        try{
            publicadorService.calificar(calificador, respuesta, generarCalificacionPosRandom())
        }catch(RuntimeException ex){
        }

        assert (medio.getNivel() == NivelAlumno.MEDIO &&
                medio.getCreditos() == 0 &&
                noPuedoCalificarRespForo(medio)
        )
    }

}
