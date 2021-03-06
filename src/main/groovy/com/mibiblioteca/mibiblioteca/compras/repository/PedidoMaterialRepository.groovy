package com.mibiblioteca.mibiblioteca.compras.repository


import com.mibiblioteca.mibiblioteca.compras.model.PedidoMaterial
import groovy.transform.CompileStatic
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@CompileStatic
@Repository
interface PedidoMaterialRepository extends JpaRepository<PedidoMaterial, Long> {

    List<PedidoMaterial> findAll()

    PedidoMaterial save(PedidoMaterial pedido)

    Optional<PedidoMaterial> findById(Long pedidoId)

    void deleteById(Long pedidoId)

    void deleteAll()
}