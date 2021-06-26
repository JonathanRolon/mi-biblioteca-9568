package com.mibiblioteca.mibiblioteca.service.impl

import com.mibiblioteca.mibiblioteca.model.Alumno
import com.mibiblioteca.mibiblioteca.model.Calificacion
import com.mibiblioteca.mibiblioteca.model.NivelAlumno
import com.mibiblioteca.mibiblioteca.model.Respuesta
import com.mibiblioteca.mibiblioteca.repository.AlumnoRepository
import com.mibiblioteca.mibiblioteca.repository.CalificacionRepository
import com.mibiblioteca.mibiblioteca.service.CalificacionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CalificacionServiceImpl implements CalificacionService {

    private final MIN_CALIF_RESP = 0,  MAX_CALIF_RESP = 10, UMBRAL_CALIF_POS = 5


    @Autowired
    private CalificacionRepository calificacionRepository

    @Autowired
    private AlumnoRepository alumnoRepository

    CalificacionServiceImpl(CalificacionRepository califRepository, AlumnoRepository alumnoRepository) {
        this.calificacionRepository = califRepository
        this.alumnoRepository = alumnoRepository
    }

    @Override
    List<Calificacion> getCalificaciones(Long dniAlumno, Long idHilo) {
        List<Calificacion> calificaciones = calificacionRepository.findAll()
        calificaciones.findAll {
            (it.getCalificador() == dniAlumno) &&
                    (it.getNroHilo() == idHilo)
        }
    }

    @Override
    List<Calificacion> getCalificadoEnTodasLasRespuestas(Alumno alumno) {
        def calificaciones = calificacionRepository.findAll()
        calificaciones.findAll {
            it.getCalificado() == alumno.getDNI()
        }
    }

    @Override
    List<Calificacion> getCalificadoEncimaDe(Alumno calificado, Integer calif) {
        def calificacionesEncimaDe = getCalificadoEnTodasLasRespuestas(calificado)
        calificacionesEncimaDe.findAll { it ->
            it.getCalificacion() > calif
        }
    }

    @Override
    Calificacion calificar(Alumno calificador, Respuesta respuesta, Integer calificacion) {
        def calificado = alumnoRepository.findById(respuesta.getPublicador())?.get()
        def calif = new Calificacion(respuesta, calificador.getDNI(), calificacion)

        if(calificacion < MIN_CALIF_RESP || calificacion > MAX_CALIF_RESP) return //excepcion
        if(calificador.getNivel() == NivelAlumno.NOVATO) return //excepcion
        if(respuesta.getPublicador() == calificador.getDNI()) return //excepcion
        if(!calificado.esRegular() || !calificador.esRegular()) return //excepcion

        if(calificacion > UMBRAL_CALIF_POS){
            calificado.incrementarCalifPositivas()
            alumnoRepository.save(calificado)
        }

        calificacionRepository.save(calif)
    }

}
