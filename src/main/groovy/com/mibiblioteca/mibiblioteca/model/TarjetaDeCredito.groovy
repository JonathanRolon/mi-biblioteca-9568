package com.mibiblioteca.mibiblioteca.model

import groovy.transform.CompileStatic

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import java.sql.Timestamp

@Entity
@CompileStatic
class TarjetaDeCredito {

    @Id
    TarjetaIdentity tarjetaIdentity

    @Column(nullable = false)
    Long cliente

    @Column(nullable = false)
    private Double saldo

    @Column(nullable = false)
    Timestamp vencimiento

    TarjetaDeCredito(Long nroTarjeta, Integer CSV, EntidadBancaria entidadBancaria, Timestamp vto, Long cliente) {
        tarjetaIdentity = new TarjetaIdentity()
        tarjetaIdentity.setNroTarjeta(nroTarjeta)
        tarjetaIdentity.setEntidad(entidadBancaria)
        tarjetaIdentity.setCSV(CSV)
        this.vencimiento = vto
        this.cliente = cliente
        saldo = 0
    }

    TarjetaDeCredito(){}

    Long getNroTarjeta() {
        this.getTarjetaIdentity().getNroTarjeta()
    }

    Integer getCSV() {
        this.getTarjetaIdentity().getCSV()
    }
}
