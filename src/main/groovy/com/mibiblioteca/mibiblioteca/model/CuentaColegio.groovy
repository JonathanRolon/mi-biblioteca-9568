package com.mibiblioteca.mibiblioteca.model

import groovy.transform.CompileStatic

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

enum TipoCuenta{
    CA, CC
}

enum EntidadColegio{
    SANRAFAEL, SANTOTO, COLEGIOPEPE
}

@Entity
@CompileStatic
class CuentaColegio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long nroCuenta

    @Enumerated(EnumType.STRING)
    EntidadBancaria entidad

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    TipoCuenta tipoCuenta

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    EntidadColegio colegio

    @Column(nullable = false)
    Double saldo

    CuentaColegio (TipoCuenta tipoCuenta, EntidadBancaria entidadBancaria, EntidadColegio colegio){
        this.tipoCuenta = tipoCuenta
        this.colegio = colegio
        this.entidad = entidadBancaria
    }

    CuentaColegio(){}

    CuentaColegio acreditarTransferencia(Double pago){
        if(pago > 0){
            saldo += pago
        }
        this
    }

    CuentaColegio debitar(Double monto){
        if(monto < saldo && monto > 0){
            saldo -= monto
        }
        this
    }

}
