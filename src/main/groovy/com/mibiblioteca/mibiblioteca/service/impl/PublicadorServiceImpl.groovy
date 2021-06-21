package com.mibiblioteca.mibiblioteca.service.impl

import com.mibiblioteca.mibiblioteca.domain.Hilo
import com.mibiblioteca.mibiblioteca.domain.Respuesta
import com.mibiblioteca.mibiblioteca.domain.RespuestaIdentity
import com.mibiblioteca.mibiblioteca.domain.TemaHilo
import com.mibiblioteca.mibiblioteca.repository.HiloRepository
import com.mibiblioteca.mibiblioteca.repository.RespuestaRepository
import com.mibiblioteca.mibiblioteca.service.PublicadorService
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@CompileStatic
@Service
class PublicadorServiceImpl implements PublicadorService{

    @Autowired
    private final RespuestaRepository respuestaRepository

    @Autowired
    private final HiloRepository hiloRepository

    @Override
    List<Hilo> findAll() {
        hiloRepository.findAll()
    }

    @Override
    Optional<Hilo> findById(Long id) {
        hiloRepository.findById id
    }

    @Override
    Hilo create(Hilo hilo) {
        Optional<Hilo> oh = hilo.getId() ? this.findById(hilo.getId()) : null
        if (!oh)
            hiloRepository.save(hilo)
    }

    @Override
    Hilo update(Hilo hilo) {
        Hilo nh = this.findById(hilo.getId())?.get()
        nh.setEstadoHilo(hilo.getEstadoHilo())
        hiloRepository.save(nh)
    }

    @Override
    void deleteById(Long idHilo) {
        hiloRepository.deleteById(idHilo)
    }

    @Override
    long preguntar(String pregunta, TemaHilo temaHilo){
        def hilo = new Hilo(pregunta, temaHilo)
        hilo = create(hilo)
        hilo.getId()
    }

    @Override
    void responder(String respuesta, Long idHilo){
        Respuesta r = new Respuesta(respuesta, idHilo)
        respuestaRepository.save(r)
    }

    @Override
    String getRespuesta(Long idRespuesta,Long idHilo){
        def respuestaIdentity = new RespuestaIdentity(idHilo)
        respuestaIdentity.setNroRespuesta(idRespuesta)
        Optional<Respuesta> r = respuestaRepository.findById(respuestaIdentity)
        r.get()?.getContenido()
    }
}
