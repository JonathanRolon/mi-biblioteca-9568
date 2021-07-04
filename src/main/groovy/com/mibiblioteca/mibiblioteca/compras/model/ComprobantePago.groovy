package com.mibiblioteca.mibiblioteca.compras.model

import com.sun.beans.decoder.ValueObject
import com.sun.istack.NotNull
import groovy.transform.CompileStatic

import javax.persistence.Embeddable
import java.sql.Timestamp
import java.util.concurrent.ThreadLocalRandom

@CompileStatic
@Embeddable
class ComprobantePago implements ValueObject {

    @NotNull
    private Long nroPago

    @NotNull
    private Double saldoAbonado

    @NotNull
    private Timestamp fecha

    ComprobantePago() {}

    ComprobantePago(Double saldo, Timestamp fechaPago) {
        saldoAbonado = saldo
        fecha = fechaPago
        nroPago = ThreadLocalRandom.current().nextInt(100000000, 999999999 + 1);
    }

    @Override
    boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComprobantePago that = (ComprobantePago) o;
        nroPago.equals(that.nroPago) &&
                saldoAbonado.equals(that.saldoAbonado) &&
                fecha.equals(that.fecha)
    }

    @Override
    int hashCode() {
        nroPago.hashCode() +
                saldoAbonado.hashCode() +
                fecha.hashCode()
    }

    @Override
    Object getValue() {
        return null
    }

    @Override
    boolean isVoid() {
        return false
    }
}
