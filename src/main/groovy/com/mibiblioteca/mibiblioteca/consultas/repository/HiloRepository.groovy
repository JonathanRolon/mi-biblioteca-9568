package com.mibiblioteca.mibiblioteca.consultas.repository

import com.mibiblioteca.mibiblioteca.consultas.model.Hilo
import groovy.transform.CompileStatic
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@CompileStatic
@Repository
interface HiloRepository extends JpaRepository<Hilo, Long> {

    List<Hilo> findAll()

    Optional<Hilo> findById(Long id)

    Hilo save(Hilo hilo)

    void deleteById(Long idHilo)

    void deleteAll()
}