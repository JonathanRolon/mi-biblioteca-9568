package com.mibiblioteca.mibiblioteca.compras.service.Impl

import com.mibiblioteca.mibiblioteca.tareas.model.Alumno
import com.mibiblioteca.mibiblioteca.compras.model.Material
import com.mibiblioteca.mibiblioteca.compras.model.PedidoMaterial
import com.mibiblioteca.mibiblioteca.compras.model.TarjetaDeCredito
import com.mibiblioteca.mibiblioteca.tareas.repository.AlumnoRepository
import com.mibiblioteca.mibiblioteca.compras.repository.PedidoMaterialRepository
import com.mibiblioteca.mibiblioteca.compras.service.CompradorService
import groovy.transform.CompileStatic
import org.springframework.stereotype.Service

@Service
@CompileStatic
class CompradorServiceImpl implements CompradorService{

    private final float DTO_COMPRA_DOCENTE = 0.2

    private AlumnoRepository alumnoRepository

    private PedidoMaterialRepository pedidoMaterialRepository

    CompradorServiceImpl(AlumnoRepository a, PedidoMaterialRepository p){
        alumnoRepository = a
        pedidoMaterialRepository = p
    }

    @Override
    PedidoMaterial pagar(Alumno comprador, PedidoMaterial pedido, TarjetaDeCredito tarjeta, Double montoTarjeta, Integer creditos){

        def valorPedido = pedido.getTotal()
        def esTarjetaValida = tarjeta.validar(pedido.getCliente(), montoTarjeta)
        def creditosValidos = (valorPedido - creditos) === montoTarjeta
        def cliente = alumnoRepository.findById(pedido.getCliente()).get()
        if(!cliente) return //excepcion
        if(!creditosValidos || !esTarjetaValida) return //excepcion

    }

    @Override
    PedidoMaterial crearPedido(Alumno alumno) {

        def cliente = alumnoRepository.findById(alumno.getDNI()).get()
        if(!cliente)
            return
        if(!cliente) return //excepcion

        def pedido = new PedidoMaterial(alumno.getDNI())
        pedidoMaterialRepository.save(pedido)
    }

    @Override
    void agregarArticulo(PedidoMaterial pedido, Material material){
        pedido.agregar(material)
    }

    @Override
    void eliminarArticulo(PedidoMaterial pedido, Material material){
        pedido.borrarMaterial(material)
    }

    @Override
    void cancelarPedido(PedidoMaterial pedido){
        pedido.cancelar()
        pedidoMaterialRepository.delete(pedido)
    }


}
