package com.mibiblioteca.mibiblioteca.tareas.service

import com.mibiblioteca.mibiblioteca.tareas.model.Alumno
import com.mibiblioteca.mibiblioteca.tareas.model.Curso

import java.sql.Timestamp

interface AlumnoService {

    Alumno create(Long dni, String nombre, String apellido, Timestamp fecNac, String curso)

    Alumno update(Alumno alumno)

    void deleteById(Long dni)

    Optional<Alumno> findById(Long dni)

    List<Alumno> findAll()


}