package com.mibiblioteca.mibiblioteca.tareas.repository

import com.mibiblioteca.mibiblioteca.tareas.model.Alumno
import groovy.transform.CompileStatic
import org.springframework.data.jpa.repository.JpaRepository
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

