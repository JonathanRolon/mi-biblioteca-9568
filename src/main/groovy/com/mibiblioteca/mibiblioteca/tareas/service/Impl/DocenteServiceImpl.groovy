package com.mibiblioteca.mibiblioteca.tareas.service.Impl

import com.mibiblioteca.mibiblioteca.tareas.model.Alumno
import com.mibiblioteca.mibiblioteca.consultas.model.Calificacion
import com.mibiblioteca.mibiblioteca.tareas.model.Curso
import com.mibiblioteca.mibiblioteca.tareas.model.Docente
import com.mibiblioteca.mibiblioteca.consultas.model.Respuesta
import com.mibiblioteca.mibiblioteca.tareas.model.Tarea
import com.mibiblioteca.mibiblioteca.tareas.repository.DocenteRepository

import com.mibiblioteca.mibiblioteca.tareas.service.DocenteService

import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.sql.Timestamp

@CompileStatic
@Service
class DocenteServiceImpl implements DocenteService {

    @Autowired
    private final DocenteRepository docenteRepository

    DocenteServiceImpl(DocenteRepository doc) {
        docenteRepository = doc
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
    Docente create(Long dni, String nombre, String apellido, Timestamp fecNac, List<Curso> cursos) {
        Optional<Docente> doc = docenteRepository.findById(dni)
        Docente docente = doc ? doc.get() : new Docente(dni, nombre, apellido, fecNac)
        cursos.each { it ->

                docente.asignarCurso(it)

        }
        docenteRepository.save(docente)
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
    Respuesta responder(Long dniPublicador, String respuesta, Long idHilo) {
        null
    }
}
