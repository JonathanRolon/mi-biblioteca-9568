package com.mibiblioteca.mibiblioteca.repository


import com.mibiblioteca.mibiblioteca.model.PedidoMaterial

import org.springframework.data.jpa.repository.JpaRepository

interface PedidoMaterialRepository extends JpaRepository<PedidoMaterial, Long> {

    List<PedidoMaterial> findAll()

    PedidoMaterial save(PedidoMaterial pedido)

    Optional<PedidoMaterial> findById(Long pedidoId)

    void deleteById(Long pedidoId)

    void deleteAll()
}