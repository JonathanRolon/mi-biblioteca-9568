package com.mibiblioteca.mibiblioteca.consultas.service.Impl

import com.mibiblioteca.mibiblioteca.consultas.service.exception.ErrorAlCalificarRespuestaException
import com.mibiblioteca.mibiblioteca.tareas.model.Alumno
import com.mibiblioteca.mibiblioteca.consultas.model.Calificacion
import com.mibiblioteca.mibiblioteca.consultas.model.Hilo
import com.mibiblioteca.mibiblioteca.consultas.model.Respuesta
import com.mibiblioteca.mibiblioteca.consultas.model.TemaHilo
import com.mibiblioteca.mibiblioteca.tareas.repository.AlumnoRepository
import com.mibiblioteca.mibiblioteca.consultas.repository.HiloRepository
import com.mibiblioteca.mibiblioteca.consultas.service.PublicadorService
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import javax.transaction.Transactional

@Service
@CompileStatic
@Transactional
class PublicadorServiceImpl implements PublicadorService {

    private final Integer MIN_CALIF_RESP = 0, MAX_CALIF_RESP = 10, UMBRAL_CALIF_POS = 5

    @Autowired
    private AlumnoRepository alumnoRepository

    @Autowired
    private HiloRepository hiloRepository


    private void validarCalificar(Hilo hilo, Alumno calificado,
                                  Alumno calificador, Integer calificacion,
                                  Respuesta respuesta) {
        if (!hilo ||
                (!calificado.esRegular() || !calificador.esRegular()) ||
                (calificacion < MIN_CALIF_RESP || calificacion > MAX_CALIF_RESP) ||
                (calificador.esNovato()) ||
                (respuesta.esPublicador(calificador.getDNI())))
            throw new ErrorAlCalificarRespuestaException("Error: al intentar calificar la respuesta")
    }

    @Override
    Calificacion calificar(Alumno calificador, Respuesta respuesta, Integer calificacion) {

        def calificado = alumnoRepository.findById(respuesta.getPublicador())?.get()
        def calif = new Calificacion(respuesta, calificador.getDNI(), calificacion)
        def hilo = hiloRepository.findById(respuesta.getHiloId()).get()

        validarCalificar(hilo, calificado, calificador, calificacion, respuesta)
        if (calificacion > UMBRAL_CALIF_POS) calificado.incrementarCalifPositivas()
        respuesta.agregarCalificacion(calif)
        hilo.actualizarRespuesta(respuesta)
        calif
    }

    @Override
    Respuesta responder(Long dniEmisor, Hilo hilo, String respuesta) {
        def respNew = new Respuesta(dniEmisor, respuesta, hilo.getId())
        hilo.agregarRespuesta(respNew)
        hiloRepository.save(hilo)
        respNew
    }

    @Override
    Hilo crearHilo(Long dniPreguntador, TemaHilo temaHilo, String consulta) {
        def hilo = new Hilo(dniPreguntador, consulta, temaHilo)
        hiloRepository.save(hilo)
    }

    @Override
    List<Hilo> obtenerHilosDe(Alumno alumno) {
        def hilos = hiloRepository.findAll()
        hilos.findAll { hilo -> hilo.getDniPublicador() === alumno }
    }

    @Override
    List<Hilo> getForo() {
        hiloRepository.findAll()
    }

    @Override
    Hilo getHilo(Long idHilo) {
        hiloRepository.findById(idHilo)?.get()
    }
}
