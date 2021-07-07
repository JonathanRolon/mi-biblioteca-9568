package com.mibiblioteca.mibiblioteca.compras

import com.mibiblioteca.mibiblioteca.compras.model.CuentaBancaria
import com.mibiblioteca.mibiblioteca.compras.model.EntidadBancaria
import com.mibiblioteca.mibiblioteca.compras.model.TipoCuenta
import com.mibiblioteca.mibiblioteca.compras.model.exception.CuentaMontoInvalidoException
import groovy.transform.CompileStatic
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

import javax.transaction.Transactional

@Transactional
@CompileStatic
@SpringBootTest
class CuentaBancariaTest {

    @BeforeEach
    private void setup(){

    }

    @AfterEach
    private void teardown(){

    }

    @Test
    void intentoCargarSaldoNegativoArrojaError(){
        def error = false
        def cuenta = new CuentaBancaria(16533154131 as BigInteger, TipoCuenta.CRED,
                                        EntidadBancaria.BANCO_RIO)
        try{
            cuenta.acreditar(-1.0)
        }catch(CuentaMontoInvalidoException ex){
            error = true
        }
        assert(error)
    }

}
