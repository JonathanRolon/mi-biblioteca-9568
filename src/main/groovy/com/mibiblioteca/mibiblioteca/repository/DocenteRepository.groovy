package com.mibiblioteca.mibiblioteca.repository

import com.mibiblioteca.mibiblioteca.domain.Docente
import groovy.transform.CompileStatic
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository


@CompileStatic
@Repository
interface DocenteRepository extends JpaRepository<Docente, Long> {

    List<Docente> findAll()

    Optional<Docente> findById(Long DNI)

    Docente save(Docente docente)

    void deleteById(Long dni)

}
