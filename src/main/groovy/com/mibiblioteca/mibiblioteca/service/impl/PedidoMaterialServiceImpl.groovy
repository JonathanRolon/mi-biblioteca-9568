package com.mibiblioteca.mibiblioteca.service.impl

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

}
