package com.mibiblioteca.mibiblioteca.service

import com.mibiblioteca.mibiblioteca.model.TarjetaDeCredito

interface TarjetaService {
    Boolean validarTarjeta(TarjetaDeCredito tarjetaDeCredito)
}