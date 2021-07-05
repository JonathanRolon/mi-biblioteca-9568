package com.mibiblioteca.mibiblioteca.compras.service.Impl

import com.mibiblioteca.mibiblioteca.compras.service.CBUColegioService
import groovy.transform.CompileStatic
import org.springframework.stereotype.Service


@CompileStatic
@Service
class CBUColegioServiceImpl implements CBUColegioService{

    // Se trucha la cuenta del colegio a fines prácticos...
    private final BigInteger CBU_COLEGIO = 1234561234561

    BigInteger obtenerCBUCuentaBancaria(){
        return CBU_COLEGIO
    }

}
