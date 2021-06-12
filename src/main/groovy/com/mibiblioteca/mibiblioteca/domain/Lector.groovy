package com.mibiblioteca.mibiblioteca.domain
import groovy.transform.CompileStatic

@CompileStatic
interface Lector {

    comprar(Material m, List<Object> metodosPago)

    solicitarPrestamo(Material m)
}