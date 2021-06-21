package com.mibiblioteca.mibiblioteca.service.impl

import com.mibiblioteca.mibiblioteca.domain.Alumno
import com.mibiblioteca.mibiblioteca.domain.Docente
import com.mibiblioteca.mibiblioteca.domain.Material
import com.mibiblioteca.mibiblioteca.domain.Respuesta
import com.mibiblioteca.mibiblioteca.domain.Tarea
import com.mibiblioteca.mibiblioteca.repository.DocenteRepository
import com.mibiblioteca.mibiblioteca.service.CalificadorService
import com.mibiblioteca.mibiblioteca.service.DocenteService
import com.mibiblioteca.mibiblioteca.service.LectorService
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@CompileStatic
@Service
class DocenteServiceImpl implements DocenteService, CalificadorService, LectorService{

    @Autowired
    private final DocenteRepository docenteRepository

    @Override
    void comprar(Material m, List<Object> metodosPago) {

    }

    @Override
    void solicitarPrestamo(Material m) {

    }

    @Override
    void calificar(Respuesta respuesta, int calificacion) {

    }

    @Override
    List<Docente> findAll() {
        docenteRepository.findAll()
    }

    @Override
    Optional<Docente> findById(Long dni) {
        docenteRepository.findById dni
    }

    @Override
    Docente create(Docente docente) {
        Optional<Docente> doc = docente.getDNI() ? this.findById(docente.getDNI()) : null
        if (!doc)
            docenteRepository.save(doc.get())
    }

    @Override
    Docente update(Docente docente) {
        Docente doc = this.findById(docente.getDNI())?.get()

        docenteRepository.save(doc)
    }

    @Override
    void deleteById(Long dni) {
        docenteRepository.deleteById(dni)
    }

    @Override
    void asignarTarea(Alumno al, Tarea t) {

    }

    @Override
    void calificarTarea(Tarea t, float nota) {

    }
}
