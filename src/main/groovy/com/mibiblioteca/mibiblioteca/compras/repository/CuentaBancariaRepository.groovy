package com.mibiblioteca.mibiblioteca.compras.repository

import com.mibiblioteca.mibiblioteca.compras.model.CuentaBancaria

import groovy.transform.CompileStatic
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@CompileStatic
@Repository
interface CuentaBancariaRepository extends JpaRepository<CuentaBancaria, BigInteger> {

    List<CuentaBancaria> findAll()

    CuentaBancaria save(CuentaBancaria cuentaColegio)

    Optional<CuentaBancaria> findById(BigInteger CBU)

    void deleteById(BigInteger CBU)

    void deleteAll()

}