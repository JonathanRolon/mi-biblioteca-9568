package com.mibiblioteca.mibiblioteca.service.impl

import com.mibiblioteca.mibiblioteca.repository.CuentaColegioRepository
import com.mibiblioteca.mibiblioteca.service.CuentaColegioService
import groovy.transform.CompileStatic
import org.springframework.stereotype.Service

@Service
@CompileStatic
class CuentaColegioServiceImpl implements CuentaColegioService{

    private final CuentaColegioRepository cuentaColegioRepository

    CuentaColegioServiceImpl(CuentaColegioRepository cuentaColegioRepository){
        this.cuentaColegioRepository = cuentaColegioRepository
    }

    //acreditarPago(Double saldo)
}
