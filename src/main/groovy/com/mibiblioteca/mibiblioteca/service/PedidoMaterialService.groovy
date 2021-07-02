package com.mibiblioteca.mibiblioteca.service

import com.mibiblioteca.mibiblioteca.model.Material
import com.mibiblioteca.mibiblioteca.model.PedidoMaterial

interface PedidoMaterialService {

    PedidoMaterial crear(Long cliente, List<Material> carrito)


}
