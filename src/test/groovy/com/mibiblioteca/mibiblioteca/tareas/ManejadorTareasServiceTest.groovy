package com.mibiblioteca.mibiblioteca.tareas

import com.mibiblioteca.mibiblioteca.tareas.model.Alumno
import com.mibiblioteca.mibiblioteca.tareas.model.Curso
import com.mibiblioteca.mibiblioteca.tareas.model.Docente
import com.mibiblioteca.mibiblioteca.tareas.model.EstadoAsignacionTarea
import com.mibiblioteca.mibiblioteca.tareas.model.Tarea
import com.mibiblioteca.mibiblioteca.tareas.repository.AlumnoRepository
import com.mibiblioteca.mibiblioteca.tareas.repository.DocenteRepository
import com.mibiblioteca.mibiblioteca.tareas.repository.TareaAlumnoRepository
import com.mibiblioteca.mibiblioteca.tareas.service.AlumnoService
import com.mibiblioteca.mibiblioteca.tareas.service.DocenteService
import com.mibiblioteca.mibiblioteca.tareas.service.Impl.AlumnoServiceImpl
import com.mibiblioteca.mibiblioteca.tareas.service.Impl.DocenteServiceImpl
import com.mibiblioteca.mibiblioteca.tareas.service.Impl.ManejadorTareasServiceImpl
import com.mibiblioteca.mibiblioteca.tareas.service.ManejadorTareasService
import groovy.transform.CompileStatic
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

import javax.transaction.Transactional
import java.sql.Timestamp
import java.time.LocalDateTime
import java.util.concurrent.ThreadLocalRandom

@Transactional
@CompileStatic
@SpringBootTest
class ManejadorTareasServiceTest {

    /* repositorios */
    @Autowired
    private AlumnoRepository alumnoRepository
    @Autowired
    private DocenteRepository docenteRepository
    @Autowired
    private TareaAlumnoRepository tareaRepository

    /* servicios */
    @Autowired
    private ManejadorTareasService tareasService
    @Autowired
    private DocenteService docenteService
    @Autowired
    private AlumnoService alumnoService

    /* data */
    private Curso curso

    /* útiles */

    @BeforeEach
    void setup() {
        //Se inyecta de esta manera porque el repository arroja null pointer exc.
        tareasService = new ManejadorTareasServiceImpl(alumnoRepository, docenteRepository, tareaRepository)
        alumnoService = new AlumnoServiceImpl(alumnoRepository)
        docenteService = new DocenteServiceImpl(docenteRepository)
        curso = new Curso("A")
    }

    @AfterEach
    void teardown() {
        tareaRepository.deleteAll()
        docenteRepository.deleteAll()
        alumnoRepository.deleteAll()
        curso = null
    }

    Long generarDNIAleatorio() {
        ThreadLocalRandom.current().nextInt(6000000, 99999998 + 1);
    }

    private Docente getDocente() {
        def fecNac = new Timestamp(1975,6,13,23,59,59,59)
        def cursos = new ArrayList<Curso>()
        cursos.push(curso)
        docenteService.create(generarDNIAleatorio(), "Pepe", "Perez", fecNac, cursos)
    }

    private Alumno getAlumno() {
        def fechaNac = new Timestamp(2004, 9, 15, 23, 59, 59,59)
        alumnoService.create(generarDNIAleatorio(), "AlumnoX",
                "Perez", fechaNac, curso.getDenominacion())
    }

    private Tarea asignarTarea(Docente docente, Curso curso) {
        def fechaEntrega = Timestamp.valueOf("2021-09-23 10:10:10.0"),
            consigna = "Relevar cuales son los puntos mas importantes del libro Pepito.",
            tarea = new Tarea(consigna, fechaEntrega)

        tareasService.asignar(tarea, curso, docente)
        tarea
    }

    /* Tests Alumno */

    @Test
    void dadoQueNoSeCumplioFechaEntregaYEstoyEnCursoDocenteAsignadorCuandoResuelvoTareaEntoncesTareaPendienteCalif() {
        def alumno = getAlumno(),
            docente = getDocente(),
            tareaCurso,
            respuestaValida = "Los pepes.",
            tareaAlumno

        tareaCurso = asignarTarea(docente, curso)
        assert (tareaCurso.getFechaEntrega().after(Timestamp.valueOf(LocalDateTime.now())))
        tareaAlumno = tareasService.resolver(tareaCurso, respuestaValida)
        assert(tareaAlumno.getEstado() === EstadoAsignacionTarea.ABIERTA_PEND_CALIF)
    }

    /* Tests Docente */

    @Test
    void dadoQueTareaAbiertaPendienteCalificacionCuandoCierroTareaEntoncesTareaCerradaPendienteCalificacion() {
        def alumno = getAlumno(),
            docente = getDocente(),
            tareaCurso,
            respuestaValida = "Los pepes.",
            tareaAlumno

        tareaCurso = asignarTarea(docente, curso)
        tareaAlumno = tareasService.resolver(tareaCurso, respuestaValida)

        tareaAlumno = tareasService.cerrarTarea(tareaAlumno)
        assert(tareaAlumno.getEstado() === EstadoAsignacionTarea.CERRADA_PEND_CALIF)
    }

    @Test
    void dadoQueTareaPendienteCalificacionSoloPuedoCalificarEntre1Y10() {
        def alumno = getAlumno(),
            docente = getDocente(),
            tareaCurso,
            respuestaValida = "Los pepes.",
            tareaAlumno, errorCalifNeg, errorCalifExcedida


        tareaCurso = asignarTarea(docente, curso)
        tareaAlumno = tareasService.resolver(tareaCurso, respuestaValida)

        tareaAlumno = tareasService.cerrarTarea(tareaAlumno)

        try{
            tareasService.calificar(tareaAlumno, -1)
        }catch(RuntimeException ex){
            errorCalifNeg = true
        }

        try{
            tareasService.calificar(tareaAlumno, 11)
        }catch(RuntimeException ex){
            errorCalifExcedida = true
        }

        tareasService.calificar(tareaAlumno, 8)

        assert(errorCalifNeg && errorCalifExcedida && tareaAlumno.getCalificacion() === 8)
    }

}
