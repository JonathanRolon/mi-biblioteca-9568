package com.mibiblioteca.mibiblioteca.consultas

import com.mibiblioteca.mibiblioteca.tareas.model.Alumno
import com.mibiblioteca.mibiblioteca.compras.model.Material
import com.mibiblioteca.mibiblioteca.compras.model.MaterialVigencia
import com.mibiblioteca.mibiblioteca.tareas.model.Curso
import com.mibiblioteca.mibiblioteca.tareas.model.NivelAlumno
import com.mibiblioteca.mibiblioteca.consultas.model.Respuesta
import com.mibiblioteca.mibiblioteca.consultas.model.TemaHilo
import com.mibiblioteca.mibiblioteca.compras.model.TipoMaterial
import com.mibiblioteca.mibiblioteca.tareas.repository.AlumnoRepository

import com.mibiblioteca.mibiblioteca.consultas.repository.HiloRepository
import com.mibiblioteca.mibiblioteca.compras.repository.PedidoMaterialRepository
import com.mibiblioteca.mibiblioteca.tareas.service.AlumnoService
import com.mibiblioteca.mibiblioteca.compras.service.CompradorService
import com.mibiblioteca.mibiblioteca.consultas.service.PublicadorService
import com.mibiblioteca.mibiblioteca.tareas.service.Impl.AlumnoServiceImpl
import com.mibiblioteca.mibiblioteca.consultas.service.Impl.PublicadorServiceImpl
import com.mibiblioteca.mibiblioteca.tareas.service.Impl.ManejadorTareasServiceImpl
import com.mibiblioteca.mibiblioteca.tareas.service.ManejadorTareasService
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
import java.time.LocalDateTime
import java.util.concurrent.ThreadLocalRandom

@ExtendWith(MockitoExtension.class)
@Transactional
@CompileStatic
@SpringBootTest
class PublicadorServiceTest {

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

    /* Entidades */
    Curso curso
    Timestamp fecNac


    @BeforeEach
    public void setup() {
        alumnoService = new AlumnoServiceImpl(alumnoRepository)
        publicadorService = new PublicadorServiceImpl(hiloRepository, alumnoRepository)
        curso =  new Curso("A")
        fecNac = new Timestamp(System.currentTimeMillis())
    }

    @AfterEach
    void teardown() {
        alumnoRepository.deleteAll()
        hiloRepository.deleteAll()
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

        alumnoService.create(132456, "nombre", "apellido", fecNac, curso)
    }

    Respuesta dadoQuePublicoUnaRespuestaEnElForo(Alumno alumno) {
        def otroAlumno = alumnoService.create(1111045654, "nombre", "apellido", fecNac, curso)
        def hilo = publicadorService.crearHilo(otroAlumno.getDNI(), TemaHilo.CS_LENGUAJE,
                "Como interpreto el cap. 3 del libro X?")
        publicadorService.responder(alumno.getDNI(), hilo, "respuestahilo1")
    }

    boolean noPuedoCalificarRespForo(Alumno alumno) {

        def preguntador = alumnoService.create(132789, "nombre", "apellido", fecNac, curso),
            contestador = alumnoService.create(4678, "nombre", "apellido", fecNac, curso)

        preguntador.subirNivel()
        contestador.subirNivel()
        contestador.subirNivel()

        def hilo = publicadorService.crearHilo(preguntador.getDNI(), TemaHilo.CS_BIOLOGICAS,
                "Como interpreto el cap. 3 del libro Y?")
        def respuesta = publicadorService.responder(contestador.getDNI(), hilo, "respuestahilo2")
        def calificac = publicadorService.calificar(alumno, respuesta, 7)

        calificac === null

    }

    void dadoQueYaTengoNCalificaciones(Respuesta respuesta,Integer cantidad){
        for (int i = 0; i < cantidad; i++) {
            def otroCalif
            Integer randomCalif = generarCalificacionPosRandom();
            while (!otroCalif) {
                otroCalif = alumnoService.create(generarDNIAleatorio(), "nombre", "apellido", fecNac, curso)
            }
            otroCalif.subirNivel()
            publicadorService.calificar(otroCalif, respuesta, randomCalif)
        }
    }

    Alumno getCalificadorMedio(){
        def calificador
        while (!calificador) {
            calificador = alumnoService.create(generarDNIAleatorio(), "nombre", "apellido", fecNac, curso)
        }
        calificador.subirNivel()
        calificador
    }

    Alumno getCalificadorPRO(){
        def pro = alumnoService.create(53135, "nombre", "apellido", fecNac, curso)
        for (int i = 0; i < 2; i++) pro.subirNivel()
        pro
    }

    @Test
    void alumnoNovatoRegularRecibePrimerCalificacionPorEncimaDeCincoNoSubeNivel() {

        def novato = dadoQuesoyAlumnoNovatoRegular(),
            respuesta = dadoQuePublicoUnaRespuestaEnElForo(novato),
            pro = getCalificadorPRO()

        assert (novato.getCalificPositivasEnForo() == 0)

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
        def medio = alumnoService.create(generarDNIAleatorio(), "nombre", "apellido", fecNac, curso),
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

        def medio = alumnoService.create(generarDNIAleatorio(), "nombre", "apellido", fecNac, curso),
            respuesta = dadoQuePublicoUnaRespuestaEnElForo(medio),
            calificador = getCalificadorMedio()

        medio.subirNivel()
        medio.suspender()

        dadoQueYaTengoNCalificaciones(respuesta, 99)

        publicadorService.calificar(calificador, respuesta, generarCalificacionPosRandom())

        assert (medio.getNivel() == NivelAlumno.MEDIO &&
                medio.getCreditos() == 0 &&
                noPuedoCalificarRespForo(medio)
        )
    }

}