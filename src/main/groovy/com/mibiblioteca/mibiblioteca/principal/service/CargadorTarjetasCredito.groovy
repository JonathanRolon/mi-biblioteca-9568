package com.mibiblioteca.mibiblioteca.principal.service

import com.mibiblioteca.mibiblioteca.compras.model.CuentaBancaria
import com.mibiblioteca.mibiblioteca.compras.model.EntidadBancaria
import com.mibiblioteca.mibiblioteca.compras.model.TarjetaDeCredito
import com.mibiblioteca.mibiblioteca.compras.model.TipoCuenta
import com.mibiblioteca.mibiblioteca.compras.repository.CuentaBancariaRepository
import com.mibiblioteca.mibiblioteca.compras.service.CBUColegioService
import com.mibiblioteca.mibiblioteca.tareas.repository.AlumnoRepository
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import javax.transaction.Transactional
import java.sql.Timestamp

@Service
@CompileStatic
@Transactional
class CargadorTarjetasCredito {

    @Autowired
    AlumnoRepository alumnoRepository

    @Autowired
    CuentaBancariaRepository cuentaBancariaRepository

    @Autowired
    CBUColegioService cbuColegioService

    void cargarCuentasBancarias(){
        def cuentaColegio = new CuentaBancaria(cbuColegioService.obtenerCBUCuentaBancaria(),
                TipoCuenta.CC, EntidadBancaria.BANCO_DEL_PLATA),
        cuentaAlumno1 = new CuentaBancaria(1111135715859 as BigInteger, TipoCuenta.CA, EntidadBancaria.BANCO_RIO),
        cuentaAlumno2 = new CuentaBancaria(1111135715860 as BigInteger, TipoCuenta.CA, EntidadBancaria.BANCO_RIO),
        cuentaAlumno3 = new CuentaBancaria(1111135715861 as BigInteger, TipoCuenta.CA, EntidadBancaria.BANCO_RIO),
        cuentaAlumno4 = new CuentaBancaria(1111135715862 as BigInteger, TipoCuenta.CA, EntidadBancaria.BANCO_RIO),
        cuentaAlumno5 = new CuentaBancaria(1111135715863 as BigInteger, TipoCuenta.CA, EntidadBancaria.BANCO_RIO)

        cuentaBancariaRepository.save(cuentaColegio)
        cuentaBancariaRepository.save(cuentaAlumno1)
        cuentaBancariaRepository.save(cuentaAlumno2)
        cuentaBancariaRepository.save(cuentaAlumno3)
        cuentaBancariaRepository.save(cuentaAlumno4)
        cuentaBancariaRepository.save(cuentaAlumno5)

    }

    void cargarTarjetaAlumno(){
        def fechaVto = new Timestamp(2025, 12, 25, 23, 59, 59, 59)
        def tarjeta1 = new TarjetaDeCredito(1111135715859 as BigInteger,4502123445244588,859,EntidadBancaria.BANCO_AZUL,fechaVto)
        def alumno1 = alumnoRepository.findById(35715859)?.get()
        alumno1.registrarTarjeta(tarjeta1)

        def tarjeta2 = new TarjetaDeCredito(1111135715860 as BigInteger,4654524655465456,123,EntidadBancaria.BANCO_RIO,fechaVto)
        def alumno2 = alumnoRepository.findById(35715860)?.get()
        alumno2.registrarTarjeta(tarjeta2)

        def tarjeta3 =  new TarjetaDeCredito(1111135715861 as BigInteger,486546546135434,456,EntidadBancaria.BANCO_DEL_PLATA,fechaVto)
        def alumno3 = alumnoRepository.findById(35715861)?.get()
        alumno3.registrarTarjeta(tarjeta3)

        def tarjeta4 =  new TarjetaDeCredito(1111135715862 as BigInteger,498744616131120,789,EntidadBancaria.BANCO_AZUL,fechaVto)
        def alumno4 = alumnoRepository.findById(35715862)?.get()
        alumno4.registrarTarjeta(tarjeta4)

        def tarjeta5 =  new TarjetaDeCredito(1111135715863 as BigInteger,473465131321324,101,EntidadBancaria.BANCO_RIO,fechaVto)
        def alumno5 = alumnoRepository.findById(35715863)?.get()
        alumno5.registrarTarjeta(tarjeta5)
    }

}
