package com.mibiblioteca.mibiblioteca.repository

import com.mibiblioteca.mibiblioteca.model.CuentaColegio
import groovy.transform.CompileStatic
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@CompileStatic
@Repository
interface CuentaColegioRepository extends JpaRepository<CuentaColegio, Long> {

    List<CuentaColegio> findAll()

    CuentaColegio save(CuentaColegio cuentaColegio)

    Optional<CuentaColegio> findById(Long idCuenta)

    void deleteById(Long idCuenta)

    void deleteAll()

}