
package com.mibiblioteca.mibiblioteca.compras.model

import com.mibiblioteca.mibiblioteca.compras.model.exception.ArticuloExistenteEnPedidoException
import com.mibiblioteca.mibiblioteca.compras.model.exception.MaterialNoVigenteException
import com.mibiblioteca.mibiblioteca.compras.model.exception.PedidoNoCancelableException
import com.mibiblioteca.mibiblioteca.compras.model.exception.PedidoNoCerrableException
import com.mibiblioteca.mibiblioteca.tareas.model.Alumno
import groovy.transform.CompileStatic

import javax.persistence.CascadeType
import javax.persistence.Column
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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    List<ArticuloMaterial> articulosSolicitados

    PedidoMaterial(Long dniCliente) {
        cliente = dniCliente
        estadoPedido = EstadoPedido.PENDIENTE
        fechaCreacion = Timestamp.valueOf(LocalDateTime.now())
        articulosSolicitados = new ArrayList<ArticuloMaterial>()
    }

    PedidoMaterial() {}

    ArticuloMaterial agregar(Material material, Alumno cliente) {
        def vigente = material.estaVigente()

        if(!vigente)
            throw new MaterialNoVigenteException("Error: El articulo a agregar no esta disponible.")

        def existeEnPedido = articulosSolicitados.find { it ->
            it.getIdMaterial() === material.getIdMaterial()
        }

        def existeEnAlumno = cliente.yaCompre(material.getIdMaterial())

        if (!existeEnPedido && !existeEnAlumno && articulosSolicitados.size() < MAX_CANT_ARTICULOS) {
            def articuloMaterial = new ArticuloMaterial(material.getIdMaterial(), getNroPedido()
            ,material.getPrecio())
            articulosSolicitados.push(articuloMaterial)
            articuloMaterial
        }else{
           throw new ArticuloExistenteEnPedidoException("Error: el articulo a agregar ya existe.")
        }

    }

    void cerrar(){
        if(estadoPedido === EstadoPedido.CANCELADO)
            throw new PedidoNoCerrableException("Error: El pedido no se puede cerrar, esta cancelado.")
        if(estadoPedido === EstadoPedido.CERRADO)
            throw new PedidoNoCerrableException("Error: El pedido ya esta cerrado.")
        fechaCierre = Timestamp.valueOf(LocalDateTime.now())
        estadoPedido = EstadoPedido.CERRADO
    }

    void borrarArticuloMaterial(Material material) {
        def posicion = articulosSolicitados.findIndexOf { it ->
            it.getIdMaterial() == material.getIdMaterial()
        }
        articulosSolicitados.remove(posicion)
    }

    BigDecimal getTotal() {
        def total = articulosSolicitados.inject(0 as BigDecimal, { suma, it -> suma + it.getPrecioVenta()})
        total
    }

    void cancelar() {
        if(estadoPedido === EstadoPedido.CANCELADO)
            throw new PedidoNoCancelableException("Error: El pedido ya esta cancelado.")
        if(estadoPedido === EstadoPedido.CERRADO)
            throw new PedidoNoCancelableException("Error: El pedido ya no se puede cancelar, esta cerrado.")

        fechaCancelacion = Timestamp.valueOf(LocalDateTime.now())
        articulosSolicitados = new ArrayList<ArticuloMaterial>()
        estadoPedido =  EstadoPedido.CANCELADO
    }

}
