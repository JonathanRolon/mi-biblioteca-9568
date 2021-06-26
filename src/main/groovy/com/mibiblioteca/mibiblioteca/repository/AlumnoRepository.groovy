package com.mibiblioteca.mibiblioteca.repository

import com.mibiblioteca.mibiblioteca.model.Alumno
import groovy.transform.CompileStatic
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@CompileStatic
@Repository
interface AlumnoRepository extends JpaRepository<Alumno, Long> {

    List<Alumno> findAll()

    Optional<Alumno> findById(Long DNI)

    Alumno save(Alumno alumno)

    void deleteById(Long dni)

    void deleteAll()

}

