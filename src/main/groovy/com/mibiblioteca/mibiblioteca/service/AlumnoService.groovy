package com.mibiblioteca.mibiblioteca.service

import com.mibiblioteca.mibiblioteca.model.Alumno
import com.mibiblioteca.mibiblioteca.model.Calificacion
import com.mibiblioteca.mibiblioteca.model.Respuesta

import java.sql.Timestamp

interface AlumnoService {
    Alumno create(Long dni, String nombre, String apellido, Timestamp fecNac, String curso)

    Alumno update(Alumno alumno)

    void deleteById(Long dni)

    Optional<Alumno> findById(Long dni)

    List<Alumno> findAll()


}