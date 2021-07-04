
package com.mibiblioteca.mibiblioteca.compras.model

import groovy.transform.CompileStatic

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Convert
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import java.sql.Timestamp
import java.time.LocalDateTime

enum EstadoPedido {
    PENDIENTE, CANCELADO, CERRADO
}

@Entity
@CompileStatic
class PedidoMaterial {

    private final Integer MAX_CANT_ARTICULOS = 50

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long nroPedido

    @Column(nullable = false)
    Long cliente

    @Column(nullable = false)
    Timestamp fechaCreacion

    @Column(nullable = true)
    Timestamp fechaCierre

    @Column(nullable = true)
    Timestamp fechaCancelacion

    @Column(nullable = false)
    EstadoPedido estadoPedido

    @Column(nullable = true)
    @Embedded
    private ComprobantePago comprobantePago

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    List<Material> articulosSolicitados

    PedidoMaterial(Long dniCliente) {
        cliente = dniCliente
        estadoPedido = EstadoPedido.PENDIENTE
        fechaCreacion = Timestamp.valueOf(LocalDateTime.now())
        articulosSolicitados = new ArrayList<Material>()
    }

    PedidoMaterial() {}

    void agregar(Material material) {
        def vigente = material.estaVigente()
        if(!vigente) return //excepcion

        def existe = articulosSolicitados.find { it ->
            it.getIdMaterial() === material.getIdMaterial()
        }
        if (!existe && articulosSolicitados.size() < MAX_CANT_ARTICULOS) {
            articulosSolicitados.push(material)

        }else{
            //excepcion
        }

    }

    void borrarMaterial(Material material) {
        def posicion = articulosSolicitados.findIndexOf { it ->
            it.getIdMaterial() == material.getIdMaterial()
        }
        articulosSolicitados.remove(posicion)
    }

    Double getTotal() {
        def total = articulosSolicitados.inject(0 as Double, { suma, it -> suma + it.getPrecio()})
        total
    }

    void cancelar() {
        articulosSolicitados = null
    }
}
