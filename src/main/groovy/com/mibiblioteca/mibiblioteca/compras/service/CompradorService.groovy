package com.mibiblioteca.mibiblioteca.compras.service

import com.mibiblioteca.mibiblioteca.tareas.model.Alumno
import com.mibiblioteca.mibiblioteca.compras.model.Material
import com.mibiblioteca.mibiblioteca.compras.model.PedidoMaterial
import com.mibiblioteca.mibiblioteca.compras.model.TarjetaDeCredito
import groovy.transform.CompileStatic

@CompileStatic
interface CompradorService {

    PedidoMaterial pagar(PedidoMaterial pedido, TarjetaDeCredito tarjeta, Double montoTarjeta, Integer creditos)

    PedidoMaterial crearPedido(Alumno alumno)

    void agregarArticulo(PedidoMaterial pedido, Material material)

    List<TarjetaDeCredito> obtenerTarjetaDeCredito(Alumno alumno)

    void registrarTarjetaDeCredito(TarjetaDeCredito tarjetaDeCredito)

    void eliminarArticulo(PedidoMaterial pedido, Material material)

    void cancelarPedido(PedidoMaterial pedido)


}