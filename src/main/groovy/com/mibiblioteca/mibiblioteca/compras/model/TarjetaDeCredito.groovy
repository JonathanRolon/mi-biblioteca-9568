package com.mibiblioteca.mibiblioteca.compras.model


import groovy.transform.CompileStatic

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import java.sql.Timestamp
import java.time.LocalDateTime

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

    TarjetaDeCredito() {}

    Long getNroTarjeta() {
        this.getTarjetaIdentity().getNroTarjeta()
    }

    Integer getCVV() {
        this.getTarjetaIdentity().getCSV()
    }

    Boolean validar(Long DNI, Double monto) {
        def fechaActual = Timestamp.valueOf(LocalDateTime.now())
        def result = fechaActual.compareTo(vencimiento);

        if ((!cliente === DNI) ||
                (result > 0) ||
                (saldo < 0 || saldo < monto)) false
        true
    }
}
