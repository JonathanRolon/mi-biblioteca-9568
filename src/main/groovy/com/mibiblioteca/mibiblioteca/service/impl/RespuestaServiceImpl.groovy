package com.mibiblioteca.mibiblioteca.service.impl

import com.mibiblioteca.mibiblioteca.model.Alumno
import com.mibiblioteca.mibiblioteca.model.Hilo
import com.mibiblioteca.mibiblioteca.model.Respuesta
import com.mibiblioteca.mibiblioteca.repository.RespuestaRepository
import com.mibiblioteca.mibiblioteca.service.RespuestaService
import org.springframework.stereotype.Service

@Service
class RespuestaServiceImpl implements RespuestaService{

    private final RespuestaRepository respuestaRepository

    RespuestaServiceImpl(RespuestaRepository rr){
        this.respuestaRepository = rr
    }

    @Override
    Respuesta responder(Long dniEmisor, Hilo hilo, String respuesta){
        def respNew = new Respuesta(dniEmisor, respuesta, hilo.getId())
        respuestaRepository.save(respNew)
    }

    Respuesta encontrarRespuesta(Alumno alumno, hilo){

    }

}
