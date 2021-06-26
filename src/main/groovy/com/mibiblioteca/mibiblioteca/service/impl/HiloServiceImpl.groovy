package com.mibiblioteca.mibiblioteca.service.impl

import com.mibiblioteca.mibiblioteca.model.Alumno
import com.mibiblioteca.mibiblioteca.model.Hilo
import com.mibiblioteca.mibiblioteca.repository.HiloRepository
import com.mibiblioteca.mibiblioteca.service.HiloService
import org.springframework.stereotype.Service
import com.mibiblioteca.mibiblioteca.model.TemaHilo

@Service
class HiloServiceImpl implements HiloService{

    private final HiloRepository hiloRepository

    HiloServiceImpl(HiloRepository hiloRepository){
        this.hiloRepository = hiloRepository
    }

    @Override
    Hilo crearHilo(Long dniPreguntador, TemaHilo temaHilo, String consulta){

        def hilo = new Hilo(dniPreguntador, consulta, temaHilo)
        hiloRepository.save(hilo)
    }


}
