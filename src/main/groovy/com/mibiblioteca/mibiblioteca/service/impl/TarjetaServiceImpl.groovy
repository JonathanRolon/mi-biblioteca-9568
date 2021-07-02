package com.mibiblioteca.mibiblioteca.service.impl

import com.mibiblioteca.mibiblioteca.model.TarjetaDeCredito
import com.mibiblioteca.mibiblioteca.repository.TarjetaRepository
import com.mibiblioteca.mibiblioteca.service.TarjetaService
import groovy.transform.CompileStatic
import org.springframework.stereotype.Service

@Service
@CompileStatic
class TarjetaServiceImpl implements TarjetaService{

    private final TarjetaRepository  tarjetaRepository

    TarjetaServiceImpl(TarjetaRepository tarjetaRepository){
        this.tarjetaRepository = tarjetaRepository
    }

    @Override
    Boolean validarTarjeta(TarjetaDeCredito tarjetaDeCredito){
        true
    }

}
