package com.mibiblioteca.mibiblioteca.tareas.service.Impl

import com.mibiblioteca.mibiblioteca.tareas.model.Alumno
import com.mibiblioteca.mibiblioteca.tareas.repository.AlumnoRepository
import com.mibiblioteca.mibiblioteca.tareas.service.AlumnoService
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import javax.transaction.Transactional
import java.sql.Timestamp

@Service
@Transactional
@CompileStatic
class AlumnoServiceImpl implements AlumnoService{

    @Autowired
    private final AlumnoRepository alumnoRepository

    @Override
    Alumno create(Long dni, String nombre, String apellido, Timestamp fecNac, String curso) {
        Optional<Alumno> al = this.findById(dni)
        if (!al) {
            def alumno = new Alumno(dni, nombre, apellido, fecNac, curso)
            alumnoRepository.save(alumno)
        }
    }

    @Override
    Alumno update(Alumno alumno) {
        alumnoRepository.save(alumno)
    }

    @Override
    void deleteById(Long dni) {
        alumnoRepository.deleteById(dni)
    }

    @Override
    Optional<Alumno> findById(Long dni) {
        alumnoRepository.findById dni
    }

    @Override
    List<Alumno> findAll() {
        alumnoRepository.findAll()
    }



}
