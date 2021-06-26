package com.mibiblioteca.mibiblioteca.service.impl

import com.mibiblioteca.mibiblioteca.model.Tarea
import com.mibiblioteca.mibiblioteca.service.TareaService
import groovy.transform.CompileStatic
import org.springframework.stereotype.Service

@CompileStatic
@Service
class TareaServiceImpl implements TareaService{

    @Override
    List<Tarea> findAll() {
        return null
    }

    @Override
    Optional<Tarea> findById(Long nroTarea) {
        return null
    }

    @Override
    Tarea create(Tarea tarea) {
        return null
    }

    @Override
    Tarea update(Tarea tarea) {
        return null
    }

    @Override
    void deleteById(Long idTarea) {

    }
}
