package com.mibiblioteca.mibiblioteca.service

import com.mibiblioteca.mibiblioteca.domain.Hilo
import com.mibiblioteca.mibiblioteca.domain.TemaHilo

interface PublicadorService {

    List<Hilo> findAll()

    Optional<Hilo> findById(Long id)

    Hilo create(Hilo hilo)

    Hilo update(Hilo trainer)

    void deleteById(Long idHilo)

    long preguntar(String pregunta, TemaHilo temaHilo)

    void responder(String respuesta, Long idHilo)

    String getRespuesta(Long idRespuesta,Long idHilo)

}