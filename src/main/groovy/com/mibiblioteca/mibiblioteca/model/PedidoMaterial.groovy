package com.mibiblioteca.mibiblioteca.model

import groovy.transform.CompileStatic

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

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
    String material

    @Column(nullable = false)
    Double valorDeCompra

    @Column(nullable = false)
    EstadoPedido estadoPedido

    @Column(nullable = false)
    Double valorCancelado

    PedidoMaterial(Long dniCliente, String idMaterial, Double valorDeCompra){
        cliente = dniCliente
        material = idMaterial
        this.valorDeCompra = valorDeCompra
        this.estadoPedido = EstadoPedido.PENDIENTE
        this.valorCancelado = 0
    }

    PedidoMaterial(){

    }

}
