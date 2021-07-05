package com.mibiblioteca.mibiblioteca.compras.service.exception

class MetodosPagoNoValidos extends RuntimeException{
    MetodosPagoNoValidos(String s) {
        super(s)
    }
}
