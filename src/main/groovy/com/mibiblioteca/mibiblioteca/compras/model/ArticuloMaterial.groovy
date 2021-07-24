package com.mibiblioteca.mibiblioteca.compras.model

import com.mibiblioteca.mibiblioteca.compras.model.exception.precioExcedePermitidoException
import groovy.transform.CompileStatic

import javax.persistence.Column
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity
@CompileStatic
class ArticuloMaterial {

    private final BigDecimal PRECIO_MAX = 99999.0
    private final BigDecimal PRECIO_MIN = 1.0

    @EmbeddedId
    ArticuloMaterialIdentity articuloMaterialIdentity

    @Column(nullable = false)
    private BigDecimal precioVenta

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    EstadoArticulo estado

    ArticuloMaterial (String idMaterial, Long nroPedido, BigDecimal precioVenta){

        articuloMaterialIdentity = new ArticuloMaterialIdentity()
        articuloMaterialIdentity.setIdMaterial(idMaterial)
        articuloMaterialIdentity.setNroPedido(nroPedido)
        estado = EstadoArticulo.PENDIENTE_PAGO
        if(precioVenta > PRECIO_MAX || precioVenta < PRECIO_MIN){
            throw new precioExcedePermitidoException("Error: El precio no es valido.")
        }
        this.precioVenta = precioVenta
    }

    ArticuloMaterial(){}

    BigDecimal getPrecioVenta(){
        precioVenta
    }

    String getIdMaterial() {
        articuloMaterialIdentity.getIdMaterial()
    }

    Long getNroPedido() {
        articuloMaterialIdentity.getNroPedido()
    }
}
