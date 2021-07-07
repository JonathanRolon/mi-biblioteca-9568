package com.mibiblioteca.mibiblioteca.compras.model

import com.mibiblioteca.mibiblioteca.compras.model.exception.TarjetaErrorAcreditacion
import com.mibiblioteca.mibiblioteca.compras.model.exception.TarjetaExcedeLimiteException
import com.mibiblioteca.mibiblioteca.compras.model.exception.TarjetaMontoNoValidoException
import groovy.transform.CompileStatic
import org.hibernate.annotations.CreationTimestamp

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Temporal
import javax.persistence.TemporalType
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import java.sql.Timestamp
import java.time.LocalDateTime

@Entity
@CompileStatic
class TarjetaDeCredito {

    private final BigDecimal LIMITE_CONTADO = 50000.00

    @Id
    TarjetaIdentity tarjetaIdentity

    @Column(nullable = false)
    Long cliente

    @Column(nullable = false)
    BigInteger CBUCuenta

    @Column(nullable = false)
    private BigDecimal saldo

    @Column(nullable = false)
    Timestamp vencimiento

    TarjetaDeCredito(BigInteger CBUCuenta,Long nroTarjeta, Integer CSV, EntidadBancaria entidadBancaria, Timestamp vto, Long cliente) {
        tarjetaIdentity = new TarjetaIdentity()
        tarjetaIdentity.setNroTarjeta(nroTarjeta)
        tarjetaIdentity.setEntidad(entidadBancaria)
        tarjetaIdentity.setCSV(CSV)
        this.vencimiento = vto
        this.cliente = cliente
        this.CBUCuenta = CBUCuenta
        saldo = 0
    }

    TarjetaDeCredito() {}

    Long getNroTarjeta() {
        this.getTarjetaIdentity().getNroTarjeta()
    }

    Integer getCVV() {
        this.getTarjetaIdentity().getCSV()
    }

    void acreditar(BigDecimal monto){
        try{
            if(monto > LIMITE_CONTADO - saldo)
                throw new TarjetaExcedeLimiteException("Error: El monto a acreditar excede el limite permitido por la tarjeta")
            if(monto <= 0)
                throw new TarjetaMontoNoValidoException("Error: El monto a acreditar no es valido.")
            saldo += monto
        }catch(RuntimeException ex){
            throw new TarjetaErrorAcreditacion("Error: No fue posible acreditar la tarjeta")
        }
    }

    Boolean validar(Long DNI, BigDecimal monto) {
        def fechaActual = Timestamp.valueOf(LocalDateTime.now())
        def result = fechaActual.compareTo(vencimiento);

        if ((!cliente === DNI) ||
                (result > 0) ||
                (saldo < 0 || saldo < monto || monto > LIMITE_CONTADO)) false
        true
    }

    void impactarPago(BigDecimal montoTarjeta) {
        if(montoTarjeta <= 0 || montoTarjeta > LIMITE_CONTADO)
            throw new TarjetaMontoNoValidoException("Error: el monto a descontar no es valido")
        saldo -= montoTarjeta
    }
}
