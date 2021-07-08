package com.mibiblioteca.mibiblioteca.compras

import com.mibiblioteca.mibiblioteca.compras.model.ContenidoMaterial
import com.mibiblioteca.mibiblioteca.compras.model.EstadoPedido
import com.mibiblioteca.mibiblioteca.compras.model.Material
import com.mibiblioteca.mibiblioteca.compras.model.MaterialVigencia
import com.mibiblioteca.mibiblioteca.compras.model.PedidoMaterial
import com.mibiblioteca.mibiblioteca.compras.model.TipoMaterial
import com.mibiblioteca.mibiblioteca.consultas.model.TemaHilo
import com.mibiblioteca.mibiblioteca.tareas.model.Alumno
import groovy.transform.CompileStatic
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.mock.web.MockMultipartFile

import java.sql.Timestamp
import java.time.LocalDateTime

@CompileStatic
class PedidoMaterialTest {

    MockMultipartFile libro

    @BeforeEach
    void setup() {
        libro = new MockMultipartFile("filearchivo", "orig", "pdf", "contenidolibro".getBytes())
    }

    /* Utiles */

    List<Material> generarMaterial(int cantidad) {
        def list = new ArrayList<Material>()
        def fec = Timestamp.valueOf(LocalDateTime.now())
        def tipoMat = TipoMaterial.LIBRO
        def tema = TemaHilo.CS_LENGUAJE
        def vigencia = MaterialVigencia.VIGENTE
        def contenido = new ContenidoMaterial("asd", "pdf", "cont".getBytes(), 5)

        for (int i = 0; i < cantidad; i++) {
            def mat = new Material("libX" + i.toString(), 123 as BigDecimal, "desc",
                    "title", "author", fec, "edit", tipoMat, tema, vigencia, contenido)
            list.push(mat)
        }

        list
    }

    /* Tests */

    @Test
    void agregoArticuloNoVigenteError() {

        def fec = Timestamp.valueOf(LocalDateTime.now())
        def tipoMat = TipoMaterial.LIBRO
        def tema = TemaHilo.CS_LENGUAJE
        def vigencia = MaterialVigencia.NO_VIGENTE
        def contenido = new ContenidoMaterial("asd", "pdf", "cont".getBytes(), 5)
        def mat = new Material("libX", 123 as BigDecimal, "desc",
                "title", "author", fec, "edit", tipoMat, tema, vigencia, contenido)
        def pedido = new PedidoMaterial(35353535)
        def cliente = new Alumno(35353535, "Nombre", "Apellido", fec, "A")
        def errorAlAgregar = false

        try {
            pedido.agregar(mat, cliente)
        } catch (RuntimeException ex) {
            errorAlAgregar = true
        }
        assert (errorAlAgregar)
    }

    @Test
    void agregoArticuloDuplicadoArrojaError() {
        def fec = Timestamp.valueOf(LocalDateTime.now())
        def material = (generarMaterial(1)).pop()
        def pedido = new PedidoMaterial(35353535)
        def cliente = new Alumno(35353535, "Nombre", "Apellido", fec, "A")
        def errorAlAgregar = false

        pedido.agregar(material, cliente)

        try {
            pedido.agregar(material, cliente)
        } catch (RuntimeException ex) {
            errorAlAgregar = true
        }
        assert (errorAlAgregar)
    }

    @Test
    void agregoMasDe50ArticulosAlPedidoArrojaError() {
        def materiales = generarMaterial(51)
        def fec = Timestamp.valueOf(LocalDateTime.now())
        def cliente = new Alumno(35353535, "Nombre", "Apellido", fec, "A")
        def pedido = new PedidoMaterial(35353535)
        def errorAlAgregar = false
        for (int i = 0; i < 50; i++) {
            pedido.agregar(materiales.pop(), cliente)
        }
        try {
            pedido.agregar(materiales.pop(), cliente)
        } catch (RuntimeException ex) {
            errorAlAgregar = true
        }
        assert(errorAlAgregar)
    }

    @Test
    void cerrarPedidoPendienteQuedaCerrado() {
        def materiales = generarMaterial(50)
        def fec = Timestamp.valueOf(LocalDateTime.now())
        def cliente = new Alumno(35353535, "Nombre", "Apellido", fec, "A")
        def pedido = new PedidoMaterial(35353535)
        for (int i = 0; i < 50; i++) {
            pedido.agregar(materiales.pop(), cliente)
        }
        pedido.cerrar()
        assert(pedido.getEstadoPedido() === EstadoPedido.CERRADO)
    }

    @Test
    void cerrarPedidoCanceladoArrojaError() {
        def materiales = generarMaterial(50)
        def fec = Timestamp.valueOf(LocalDateTime.now())
        def cliente = new Alumno(35353535, "Nombre", "Apellido", fec, "A")
        def pedido = new PedidoMaterial(35353535)
        def errorAlCerrar = false
        for (int i = 0; i < 50; i++) {
            pedido.agregar(materiales.pop(), cliente)
        }
        pedido.cancelar()

        try{
            pedido.cerrar()
        }catch(RuntimeException ex){
            errorAlCerrar = true
        }

        assert(pedido.getEstadoPedido() === EstadoPedido.CANCELADO && errorAlCerrar)
    }

    @Test
    void borrarArticuloDelPedidoDecrementaNroArticulos() {
        def materiales = generarMaterial(50)
        def fec = Timestamp.valueOf(LocalDateTime.now())
        def cliente = new Alumno(35353535, "Nombre", "Apellido", fec, "A")
        def pedido = new PedidoMaterial(35353535)
        def aBorrar = materiales.get(0)

        for (int i = 0; i < 50; i++) {
            pedido.agregar(materiales.pop(), cliente)
        }
        assert(pedido.getArticulosSolicitados().size() === 50)

        pedido.borrarArticuloMaterial(aBorrar)
        assert(pedido.getArticulosSolicitados().size() === 49)
    }

    @Test
    void cancelarPedidoPendienteDejaPedidoCancelado() {
        def materiales = generarMaterial(50)
        def fec = Timestamp.valueOf(LocalDateTime.now())
        def cliente = new Alumno(35353535, "Nombre", "Apellido", fec, "A")
        def pedido = new PedidoMaterial(35353535)

        for (int i = 0; i < 50; i++) {
            pedido.agregar(materiales.pop(), cliente)
        }
        pedido.cancelar()
        assert(pedido.getEstadoPedido() === EstadoPedido.CANCELADO)
    }

    @Test
    void cancelarPedidoCerradoArrojaError() {
        def materiales = generarMaterial(50)
        def fec = Timestamp.valueOf(LocalDateTime.now())
        def cliente = new Alumno(35353535, "Nombre", "Apellido", fec, "A")
        def pedido = new PedidoMaterial(35353535)
        def errorAlCancelar = false
        for (int i = 0; i < 50; i++) {
            pedido.agregar(materiales.pop(), cliente)
        }
        pedido.cerrar()
        try{
            pedido.cancelar()
        }catch(RuntimeException ex){
            errorAlCancelar = true
        }
        assert(errorAlCancelar && pedido.getEstadoPedido() === EstadoPedido.CERRADO)
    }

}
