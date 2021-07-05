package com.mibiblioteca.mibiblioteca.compras.model

import com.mibiblioteca.mibiblioteca.compras.model.exception.CuentaMontoInvalidoException
import groovy.transform.CompileStatic

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id

enum TipoCuenta{
    CA, CC, CRED
}
/*** La idea de esta clase es servir a las historias de usuario #2 y #3,
 * no pretende representar la realidad. ***/

@Entity
@CompileStatic
class CuentaBancaria{

    private final BigDecimal SALDO_LIMITE = 50000

    @Id
    BigInteger CBU

    @Enumerated(EnumType.STRING)
    EntidadBancaria entidad

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    TipoCuenta tipoCuenta

    @Column(nullable = false)
    BigDecimal saldo

    CuentaBancaria (BigInteger CBU, TipoCuenta tipoCuenta, EntidadBancaria entidadBancaria){
        this.CBU = CBU
        this.tipoCuenta = tipoCuenta
        this.entidad = entidadBancaria
        saldo = 0
    }

    CuentaBancaria(){}

    void acreditar(BigDecimal monto){
        if(monto < 0)
            throw new CuentaMontoInvalidoException("El monto a acreditar a la cuenta con CBU: " + CBU + " es invalido")
        saldo += monto
    }

    void debitar(BigDecimal monto){
        if(monto > SALDO_LIMITE || monto < 0)
            throw new CuentaMontoInvalidoException("El monto a debitar a la cuenta con CBU: " + CBU + " es invalido")
        saldo -= monto
    }



}
