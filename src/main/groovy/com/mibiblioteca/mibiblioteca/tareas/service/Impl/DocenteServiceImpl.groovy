package com.mibiblioteca.mibiblioteca.tareas.service.Impl

import com.mibiblioteca.mibiblioteca.tareas.model.Alumno
import com.mibiblioteca.mibiblioteca.consultas.model.Calificacion
import com.mibiblioteca.mibiblioteca.tareas.model.Docente
import com.mibiblioteca.mibiblioteca.consultas.model.Respuesta
import com.mibiblioteca.mibiblioteca.tareas.model.Tarea
import com.mibiblioteca.mibiblioteca.tareas.repository.DocenteRepository

import com.mibiblioteca.mibiblioteca.tareas.service.DocenteService

import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@CompileStatic
@Service
class DocenteServiceImpl implements DocenteService{

    @Autowired
    private final DocenteRepository docenteRepository

    /*@Override
    void comprar(Material m, List<Object> metodosPago) {

    }

    @Override
    void solicitarPrestamo(Material m) {

    }*/

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

    @Override
    Calificacion calificar(Respuesta respuesta, Long publicador, Integer calificacion) {
        return null
    }

    @Override
    Respuesta responder(Long dniPublicador, String respuesta, Long idHilo){
        null
    }
}
