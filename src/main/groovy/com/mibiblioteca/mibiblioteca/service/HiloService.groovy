package com.mibiblioteca.mibiblioteca.service

import com.mibiblioteca.mibiblioteca.model.Hilo
import com.mibiblioteca.mibiblioteca.model.TemaHilo

interface HiloService {

    Hilo crearHilo(Long dniPreguntador, TemaHilo temaHilo, String consulta)
}