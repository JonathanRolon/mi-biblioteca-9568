package com.mibiblioteca.mibiblioteca.compras.service.Impl

import com.mibiblioteca.mibiblioteca.compras.model.TarjetaIdentity
import com.mibiblioteca.mibiblioteca.compras.repository.TarjetaRepository
import com.mibiblioteca.mibiblioteca.tareas.model.Alumno
import com.mibiblioteca.mibiblioteca.compras.model.Material
import com.mibiblioteca.mibiblioteca.compras.model.PedidoMaterial
import com.mibiblioteca.mibiblioteca.compras.model.TarjetaDeCredito
import com.mibiblioteca.mibiblioteca.tareas.repository.AlumnoRepository
import com.mibiblioteca.mibiblioteca.compras.repository.PedidoMaterialRepository
import com.mibiblioteca.mibiblioteca.compras.service.CompradorService
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@CompileStatic
class CompradorServiceImpl implements CompradorService {

    @Autowired
    private AlumnoRepository alumnoRepository

    @Autowired
    private PedidoMaterialRepository pedidoMaterialRepository

    @Autowired
    private TarjetaRepository tarjetaRepository

    CompradorServiceImpl(AlumnoRepository a, PedidoMaterialRepository p, TarjetaRepository t) {
        alumnoRepository = a
        pedidoMaterialRepository = p
        tarjetaRepository = t
    }

    @Override
    PedidoMaterial pagar(PedidoMaterial pedido, TarjetaDeCredito tarjeta, Double montoTarjeta, Integer creditos) {

        def valorPedido = pedido.getTotal()
        def esTarjetaValida = tarjeta.validar(pedido.getCliente(), montoTarjeta)
        def creditosValidos = (valorPedido - creditos) === montoTarjeta
        def cliente = alumnoRepository.findById(pedido.getCliente()).get()
        if (!cliente) return //excepcion
        if (!creditosValidos || !esTarjetaValida) return //excepcion

    }

    @Override
    PedidoMaterial crearPedido(Alumno alumno) {

        def cliente = alumnoRepository.findById(alumno.getDNI()).get()
        if (!cliente) return //excepcion

        def pedido = new PedidoMaterial(alumno.getDNI())
        pedidoMaterialRepository.save(pedido)
    }

    @Override
    void agregarArticulo(PedidoMaterial pedido, Material material) {

        pedido.agregar(material)
    }

    @Override
    List<TarjetaDeCredito> obtenerTarjetaDeCredito(Alumno alumno) {

        def tarjetas = tarjetaRepository.findAll()
        tarjetas.findAll { it -> it.getCliente() === alumno.getDNI() }

    }

    @Override
    void registrarTarjetaDeCredito(TarjetaDeCredito tarjetaDeCredito) {
        tarjetaRepository.save(tarjetaDeCredito)
    }

    @Override
    void eliminarArticulo(PedidoMaterial pedido, Material material) {
        pedido.borrarArticuloMaterial(material)
    }

    @Override
    void cancelarPedido(PedidoMaterial pedido) {
        pedido.cancelar()
        pedidoMaterialRepository.delete(pedido)
    }


}
