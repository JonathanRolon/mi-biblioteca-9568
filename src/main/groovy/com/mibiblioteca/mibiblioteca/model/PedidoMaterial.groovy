package com.mibiblioteca.mibiblioteca.model

import groovy.transform.CompileStatic

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany

enum EstadoPedido{
    PENDIENTE, CANCELADO, CERRADO
}

@Entity
@CompileStatic
class PedidoMaterial{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long nroPedido

    @Column(nullable = false)
    Long cliente

    @Column(nullable = false)
    EstadoPedido estadoPedido

    @OneToMany
    List<Material> articulosSolicitados

    PedidoMaterial(Long dniCliente, List<Material> productos){
        cliente = dniCliente
        estadoPedido = EstadoPedido.PENDIENTE
        articulosSolicitados = productos
    }

    PedidoMaterial(){}



}
