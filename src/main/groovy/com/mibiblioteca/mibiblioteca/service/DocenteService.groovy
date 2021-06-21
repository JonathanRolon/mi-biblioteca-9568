package com.mibiblioteca.mibiblioteca.service

import com.mibiblioteca.mibiblioteca.domain.Alumno
import com.mibiblioteca.mibiblioteca.domain.Docente
import com.mibiblioteca.mibiblioteca.domain.Tarea
import groovy.transform.CompileStatic

@CompileStatic
interface DocenteService {

    List<Docente> findAll()

    Optional<Docente> findById(Long dni)

    Docente create(Docente docente)

    Docente update(Docente docente)

    void deleteById(Long dni)

    void asignarTarea(Alumno al, Tarea t)

    void calificarTarea(Tarea t, float nota)


}
