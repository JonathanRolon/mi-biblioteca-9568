package com.mibiblioteca.mibiblioteca.service

import com.mibiblioteca.mibiblioteca.domain.Material
import groovy.transform.CompileStatic

@CompileStatic
interface LectorService {

    void comprar(Material m, List<Object> metodosPago)

    void solicitarPrestamo(Material m)

}