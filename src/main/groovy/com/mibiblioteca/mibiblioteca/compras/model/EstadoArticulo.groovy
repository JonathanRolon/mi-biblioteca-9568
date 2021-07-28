package com.mibiblioteca.mibiblioteca.compras.model

enum EstadoArticulo {

    PENDIENTE_PAGO{
        @Override
        String getDescripcion(){
            "Pendiente de pago"
        }
    }, PAGO{
        @Override
        String getDescripcion(){
            "Adquirido"
        }
    }
    abstract String getDescripcion()
}