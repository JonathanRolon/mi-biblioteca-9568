package com.mibiblioteca.mibiblioteca.compras

import com.mibiblioteca.mibiblioteca.compras.model.EntidadBancaria
import com.mibiblioteca.mibiblioteca.compras.model.TarjetaDeCredito
import groovy.transform.CompileStatic
import org.junit.jupiter.api.Test

import java.sql.Timestamp

@CompileStatic
class TarjetaDeCreditoTest {

    @Test
    void acreditarSaldoEncimaDelLimiteArrojaError(){
        def limite = 50000.0
        def errorAlAcreditar = false
        def entidadBanc = EntidadBancaria.BANCO_DEL_PLATA
        def vto = Timestamp.valueOf("2025-09-23 10:10:10.0")
        def tarjetaDeCredito = new TarjetaDeCredito(5461354 as BigInteger,
                54654646466644,546, entidadBanc ,vto,3435434)
        try{
            tarjetaDeCredito.acreditar(limite + 0.001)
        }catch(RuntimeException ex){
            errorAlAcreditar = true
        }

        assert(errorAlAcreditar)

    }

    @Test
    void acreditarSaldoNegativoArrojaError(){
        def monto = -1
        def errorAlAcreditar = false
        def entidadBanc = EntidadBancaria.BANCO_DEL_PLATA
        def vto = Timestamp.valueOf("2025-09-23 10:10:10.0")
        def tarjetaDeCredito = new TarjetaDeCredito(5461354 as BigInteger,
                54654646466644,546, entidadBanc ,vto,3435434)
        try{
            tarjetaDeCredito.acreditar(monto as BigDecimal)
        }catch(RuntimeException ex){
            errorAlAcreditar = true
        }

        assert(errorAlAcreditar)
    }

    @Test
    void impactarPagoMayorSaldoArrojaError(){

    }

}
