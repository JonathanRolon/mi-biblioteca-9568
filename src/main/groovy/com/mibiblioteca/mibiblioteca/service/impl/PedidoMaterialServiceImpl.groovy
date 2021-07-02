package com.mibiblioteca.mibiblioteca.service.impl

import com.mibiblioteca.mibiblioteca.model.Material
import com.mibiblioteca.mibiblioteca.model.PedidoMaterial
import com.mibiblioteca.mibiblioteca.repository.PedidoMaterialRepository
import com.mibiblioteca.mibiblioteca.service.PedidoMaterialService
import groovy.transform.CompileStatic
import org.springframework.stereotype.Service

@Service
@CompileStatic
class PedidoMaterialServiceImpl implements PedidoMaterialService{

    private final PedidoMaterialRepository pedidoMaterialRepository

    PedidoMaterialServiceImpl(PedidoMaterialRepository pedidoMaterialRepository){
        this.pedidoMaterialRepository = pedidoMaterialRepository
    }

    @Override
    PedidoMaterial crear(Long cliente, List<Material> carrito) {
        def pedido = new PedidoMaterial(cliente, carrito)
        pedidoMaterialRepository.save(pedido)
    }
}
