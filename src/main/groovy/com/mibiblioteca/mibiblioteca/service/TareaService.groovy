package com.mibiblioteca.mibiblioteca.service

import com.mibiblioteca.mibiblioteca.domain.Tarea
import groovy.transform.CompileStatic

@CompileStatic
interface TareaService {
    
    List<Tarea> findAll()

    Optional<Tarea> findById(Long nroTarea)

    Tarea create(Tarea tarea)

    Tarea update(Tarea tarea)

    void deleteById(Long idTarea)
}
