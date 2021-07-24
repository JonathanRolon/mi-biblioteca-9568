package com.mibiblioteca.mibiblioteca.compras.service.Impl

import com.mibiblioteca.mibiblioteca.compras.model.ArticuloMaterial
import com.mibiblioteca.mibiblioteca.compras.model.ComprobantePago
import com.mibiblioteca.mibiblioteca.compras.model.EstadoPedido
import com.mibiblioteca.mibiblioteca.compras.model.exception.ComprobantePagoNoAdjuntoException
import com.mibiblioteca.mibiblioteca.compras.repository.CuentaBancariaRepository

import com.mibiblioteca.mibiblioteca.compras.service.CBUColegioService
import com.mibiblioteca.mibiblioteca.compras.service.exception.MetodosPagoNoValidos
import com.mibiblioteca.mibiblioteca.compras.service.exception.NoExistePagadorException
import com.mibiblioteca.mibiblioteca.compras.service.exception.PedidoPendienteAlumnoException
import com.mibiblioteca.mibiblioteca.compras.service.exception.TarjetaErrorDescontarSaldoException
import com.mibiblioteca.mibiblioteca.tareas.model.Alumno
import com.mibiblioteca.mibiblioteca.compras.model.Material
import com.mibiblioteca.mibiblioteca.compras.model.PedidoMaterial
import com.mibiblioteca.mibiblioteca.compras.model.TarjetaDeCredito
import com.mibiblioteca.mibiblioteca.tareas.model.exception.CreditosExcedeCantidadDisponibleException
import com.mibiblioteca.mibiblioteca.tareas.repository.AlumnoRepository
import com.mibiblioteca.mibiblioteca.compras.repository.PedidoMaterialRepository
import com.mibiblioteca.mibiblioteca.compras.service.CompradorService
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import java.sql.Timestamp
import java.time.LocalDateTime

@Service
@CompileStatic
@Transactional
class CompradorServiceImpl implements CompradorService {

    private final BigDecimal DTO_400_CREDITOS = 0.3
    private final Integer LIMITE_CRED_DTO = 400

    @Autowired
    private AlumnoRepository alumnoRepository

    @Autowired
    private PedidoMaterialRepository pedidoMaterialRepository

    @Autowired
    private CuentaBancariaRepository cuentaBancariaRepository

    @Autowired
    private CBUColegioService cbuColegioService

    private void adjuntarComprobantesAlumno(Alumno cliente, List<ArticuloMaterial> articulosSolicitados, Timestamp fechaCierre) {

        articulosSolicitados.each {
            it ->

                try {
                    def comprobante = new ComprobantePago(it.getPrecioVenta(), fechaCierre, it.getIdMaterial())
                    cliente.desbloquearMaterial(comprobante)
                } catch (RuntimeException ex) {
                    throw new ComprobantePagoNoAdjuntoException("Ocurrio un error al intentar adjuntar el comprobante.")
                }
        }
    }

    private void restarCreditosCliente(Alumno alumno, Integer creditos) {
        try {
            if (creditos === 0) return
            alumno.restarCreditos(LIMITE_CRED_DTO)
        } catch (RuntimeException ex) {
            throw new CreditosExcedeCantidadDisponibleException("Ocurrió un error al intentar decrementar " +
                    "los creditos del alumno.")
        }
    }

    private void acreditarCuentas(BigInteger cbuCuentaCliente, BigDecimal valor) {

        def cbuColegio = cbuColegioService.obtenerCBUCuentaBancaria(),
            cuentaCliente = cuentaBancariaRepository.findById(cbuCuentaCliente).get(),
            cuentaColegio = cuentaBancariaRepository.findById(cbuColegio).get()

        try {
            if (valor === 0) return //es gratuito
            cuentaCliente.acreditar(valor)
            cuentaColegio.acreditar(valor)
        } catch (RuntimeException ex) {
            throw ex
        }
    }

    private void validarTarjeta(TarjetaDeCredito tarjeta, BigDecimal monto) {
        def esTarjetaValida = tarjeta.validar(monto)
        if (!esTarjetaValida)
            throw new MetodosPagoNoValidos("Tarjeta de credito no valida.")
    }

    private void validarCliente(Alumno cliente) {
        if (!cliente)
            throw new NoExistePagadorException("El cliente vinculado al DNI ingresado no existe.")
    }

    private void cerrar(PedidoMaterial pedido) {
        pedido.cerrar()
    }

    private void restarSaldoTarjeta(TarjetaDeCredito tarjetaDeCredito, BigDecimal monto) {
        try {
            tarjetaDeCredito.impactarPago(monto)
        } catch (RuntimeException ex) {
            throw new TarjetaErrorDescontarSaldoException("Error: Ocurrio un error al intentar descontar la tarjeta")
        }

    }

    @Override
    PedidoMaterial pagar(PedidoMaterial pedido, TarjetaDeCredito tarjeta, BigDecimal montoTarjeta, Integer creditos) {

        def cliente = alumnoRepository.findById(pedido.getCliente()).get()
        def fechaCierre = Timestamp.valueOf(LocalDateTime.now())
        def creditosValidos = (creditos >= LIMITE_CRED_DTO)
        def valorPedido = creditosValidos ? pedido.getTotal() * DTO_400_CREDITOS : pedido.getTotal()

        validarCliente(cliente)
        validarTarjeta(tarjeta, montoTarjeta)
        restarCreditosCliente(cliente, creditos)
        restarSaldoTarjeta(tarjeta, valorPedido)
        acreditarCuentas(tarjeta.getCBUCuenta(), valorPedido)
        adjuntarComprobantesAlumno(cliente, pedido.getArticulosSolicitados(), fechaCierre)
        cerrar(pedido)
        pedido

    }

    @Override
    PedidoMaterial getPedido(Alumno alumno) {

        def pedido = pedidoMaterialRepository.findAll().find {estePedido ->
            estePedido.cliente == alumno.getDNI() && estePedido.estadoPedido == EstadoPedido.PENDIENTE
        }
        // si no hay pedido pendiente o no existe, lo crea
        if(!pedido) return crearPedido(alumno)

        return pedido
    }

    @Override
    PedidoMaterial crearPedido(Alumno alumno) {

        def cliente = alumnoRepository.findById(alumno.getDNI()).get()
        if (!cliente)
            throw new NoExistePagadorException("El cliente vinculado al DNI no existe.")

        def pedidos = pedidoMaterialRepository.findAll(),
            existePendiente = pedidos.find { it ->
                it.getEstadoPedido() === EstadoPedido.PENDIENTE &&
                        it.getCliente() == alumno.getDNI()
            }

        if (existePendiente)
            throw new PedidoPendienteAlumnoException("Error: El cliente ya posee un pedido abierto.")

        def pedido = new PedidoMaterial(alumno.getDNI())
        pedidoMaterialRepository.save(pedido)
    }

    @Override
    void agregarArticulo(PedidoMaterial pedido, Material material) {
        def cliente = alumnoRepository.findById(pedido.getCliente()).get()
        if (!cliente)
            throw new NoExistePagadorException("El cliente vinculado al DNI no existe.")
        pedido.agregar(material, cliente)
    }

    @Override
    void eliminarArticulo(PedidoMaterial pedido, Material material) {
        pedido.borrarArticuloMaterial(material)
    }

    @Override
    void cancelarPedido(PedidoMaterial pedido) {
        pedido.cancelar()
    }

}
