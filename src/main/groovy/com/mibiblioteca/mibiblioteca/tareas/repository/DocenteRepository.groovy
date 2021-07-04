package com.mibiblioteca.mibiblioteca.tareas.repository

import com.mibiblioteca.mibiblioteca.tareas.model.Docente
import groovy.transform.CompileStatic
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@CompileStatic
@Repository
interface DocenteRepository extends JpaRepository<Docente, Long> {

    List<Docente> findAll()

    Optional<Docente> findById(Long DNI)

    Docente save(Docente docente)

    void deleteById(Long dni)

    void deleteAll()

}
