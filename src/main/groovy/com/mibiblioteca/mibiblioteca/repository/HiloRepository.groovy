package com.mibiblioteca.mibiblioteca.repository

import com.mibiblioteca.mibiblioteca.domain.Hilo
import groovy.transform.CompileStatic
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@CompileStatic
@Repository
interface HiloRepository extends JpaRepository<Hilo, Long> {

    List<Hilo> findAll()

    Optional<Hilo> findById(Long id)

    Hilo save(Hilo hilo)

    void deleteById(Long idHilo)
}