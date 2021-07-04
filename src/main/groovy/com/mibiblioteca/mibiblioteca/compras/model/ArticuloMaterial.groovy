package com.mibiblioteca.mibiblioteca.compras.model

import groovy.transform.CompileStatic

import javax.persistence.Column
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity
@CompileStatic
class ArticuloMaterial {

    @EmbeddedId
    ArticuloMaterialIdentity articuloMaterialIdentity

    @Column(nullable = false)
    private Double precioVenta

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    EstadoArticulo estado

    ArticuloMaterial (String idMaterial, Long nroPedido, Double precioVenta){

        articuloMaterialIdentity = new ArticuloMaterialIdentity()
        articuloMaterialIdentity.setIdMaterial(idMaterial)
        articuloMaterialIdentity.setNroPedido(nroPedido)
        this.precioVenta = precioVenta
        estado = EstadoArticulo.PENDIENTE_PAGO
    }

    Double getPrecioVenta(){
        precioVenta
    }

    void pagar(){
        this.estado = EstadoArticulo.PAGO
    }

    String getIdMaterial() {
        articuloMaterialIdentity.getIdMaterial()
    }

    Long getNroPedido() {
        articuloMaterialIdentity.getNroPedido()
    }
}
