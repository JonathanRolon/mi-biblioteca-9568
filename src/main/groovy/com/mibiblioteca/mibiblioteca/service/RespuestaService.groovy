package com.mibiblioteca.mibiblioteca.service

import com.mibiblioteca.mibiblioteca.model.Hilo
import com.mibiblioteca.mibiblioteca.model.Respuesta

interface RespuestaService {

    Respuesta responder(Long dniEmisor, Hilo hilo, String respuesta)

}