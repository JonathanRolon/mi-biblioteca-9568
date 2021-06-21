package com.mibiblioteca.mibiblioteca.service.impl

import com.mibiblioteca.mibiblioteca.domain.Alumno
import com.mibiblioteca.mibiblioteca.service.AlumnoService
import groovy.transform.CompileStatic
import org.springframework.stereotype.Service

@CompileStatic
@Service
class AlumnoNovatoServiceImpl implements AlumnoService{

    @Override
    List<Alumno> findAll() {
        return null
    }

    @Override
    Optional<Alumno> findById(Long dni) {
        return null
    }

    @Override
    Alumno create(Alumno alumno) {
        return null
    }

    @Override
    Alumno update(Alumno alumno) {
        return null
    }

    @Override
    void deleteById(Long dni) {

    }
}
