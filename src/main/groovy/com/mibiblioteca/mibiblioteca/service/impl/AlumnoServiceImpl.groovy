package com.mibiblioteca.mibiblioteca.service.impl

import com.mibiblioteca.mibiblioteca.model.Alumno
import com.mibiblioteca.mibiblioteca.model.NivelAlumno
import com.mibiblioteca.mibiblioteca.repository.AlumnoRepository
import com.mibiblioteca.mibiblioteca.service.AlumnoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.sql.Timestamp

@Service
class AlumnoServiceImpl implements AlumnoService{

    @Autowired
    private final AlumnoRepository alumnoRepository

    AlumnoServiceImpl(AlumnoRepository alumnoRepository){
        this.alumnoRepository = alumnoRepository
    }

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
        Alumno al = this.findById(alumno.getDNI())?.get()
        al.setCreditos(alumno.getCreditos())
        al.setCurso(alumno.getCurso())
        al.setNotaAnual(alumno.getNotaAnual())
        al.setNotaPrimerSemestre(alumno.getNotaPrimerSemestre())
        al.setNotaSegundoSemestre(alumno.getNotaSegundoSemestre())
        al.setRegular(alumno.getRegular())
        alumnoRepository.save(al)
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
