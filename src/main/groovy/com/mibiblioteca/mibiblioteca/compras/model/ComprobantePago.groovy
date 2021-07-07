package com.mibiblioteca.mibiblioteca.compras.model

import groovy.transform.CompileStatic
import org.hibernate.annotations.CreationTimestamp

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.NotEmpty
import java.sql.Timestamp

@Entity
@CompileStatic
class ComprobantePago{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nroPago

    @Column(nullable = false)
    private BigDecimal saldoAbonado

    @Column(nullable = false)
    private Timestamp fecha

    @Column(nullable = false)
    private String idMaterial

    ComprobantePago() {}

    ComprobantePago(BigDecimal abonado, Timestamp fechaPago, String material) {
        saldoAbonado = abonado
        fecha = fechaPago
       idMaterial = material
    }

    Long getNroPago() {
        return nroPago
    }

    void setNroPago(Long nroPago) {
        this.nroPago = nroPago
    }

    BigDecimal getSaldoAbonado() {
        return saldoAbonado
    }

    void setSaldoAbonado(BigDecimal saldoAbonado) {
        this.saldoAbonado = saldoAbonado
    }

    Timestamp getFecha() {
        return fecha
    }

    void setFecha(Timestamp fecha) {
        this.fecha = fecha
    }

    String getIdMaterial() {
        return idMaterial
    }

    void setIdMaterial(String idMaterial) {
        this.idMaterial = idMaterial
    }

}
