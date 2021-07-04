package com.mibiblioteca.mibiblioteca.compras.repository

import com.mibiblioteca.mibiblioteca.compras.model.TarjetaDeCredito
import com.mibiblioteca.mibiblioteca.compras.model.TarjetaIdentity
import groovy.transform.CompileStatic
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@CompileStatic
@Repository
interface  TarjetaRepository extends JpaRepository<TarjetaDeCredito, TarjetaIdentity> {

    List<TarjetaDeCredito> findAll()

    TarjetaDeCredito save(TarjetaDeCredito tarjeta)

    Optional<TarjetaDeCredito> findById(TarjetaIdentity tarjetaIdentity)

    void deleteById(TarjetaIdentity tarjetaIdentity)

    void deleteAll()

}
