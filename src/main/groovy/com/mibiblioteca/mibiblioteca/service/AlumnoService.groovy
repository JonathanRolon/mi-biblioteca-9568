package com.mibiblioteca.mibiblioteca.service

import com.mibiblioteca.mibiblioteca.domain.Alumno
import groovy.transform.CompileStatic

@CompileStatic
interface AlumnoService {

    List<Alumno> findAll()

    Optional<Alumno> findById(Long dni)

    Alumno create(Alumno alumno)

    Alumno update(Alumno alumno)

    void deleteById(Long dni)

}
