package com.mibiblioteca.mibiblioteca.compras.service

import com.mibiblioteca.mibiblioteca.tareas.model.Alumno
import com.mibiblioteca.mibiblioteca.compras.model.Material
import com.mibiblioteca.mibiblioteca.compras.model.PedidoMaterial
import com.mibiblioteca.mibiblioteca.compras.model.TarjetaDeCredito
import groovy.transform.CompileStatic

@CompileStatic
interface CompradorService {

    PedidoMaterial pagar(PedidoMaterial pedido, TarjetaDeCredito tarjeta)

    PedidoMaterial crearPedido(Alumno alumno)

    void agregarArticulo(PedidoMaterial pedido, Material material)

    void eliminarArticulo(PedidoMaterial pedido, Material material)

    void cancelarPedido(PedidoMaterial pedido)

    PedidoMaterial getPedido(Alumno alumno)
}