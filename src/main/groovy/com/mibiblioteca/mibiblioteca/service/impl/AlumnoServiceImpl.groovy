package com.mibiblioteca.mibiblioteca.service.impl

import com.mibiblioteca.mibiblioteca.domain.Alumno
import com.mibiblioteca.mibiblioteca.domain.Material
import com.mibiblioteca.mibiblioteca.domain.Respuesta
import com.mibiblioteca.mibiblioteca.domain.TemaHilo
import com.mibiblioteca.mibiblioteca.repository.AlumnoRepository
import com.mibiblioteca.mibiblioteca.service.AlumnoService
import com.mibiblioteca.mibiblioteca.service.CalificadorService
import com.mibiblioteca.mibiblioteca.service.LectorService
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@CompileStatic
@Service
class AlumnoServiceImpl implements AlumnoService, LectorService, CalificadorService{

    @Autowired
    private final AlumnoRepository alumnoRepository

    void preguntar(TemaHilo temaHilo, String pregunta){

    }

    void responder(String respuesta, Long idHilo){

    }

    void sumarCreditos(Integer creditos) {

    }

    void restarCreditos(Integer creditos) {

    }

    @Override
    List<Alumno> findAll() {
        alumnoRepository.findAll()

    }

    @Override
    Optional<Alumno> findById(Long dni) {
       alumnoRepository.findById dni

    }

    @Override
    Alumno create(Alumno alumno) {
        Optional<Alumno> al = alumno.getDNI() ? this.findById(alumno.getDNI()) : null
        if (!al){
            alumnoRepository.save(al.get())
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
    void calificar(Respuesta respuesta, int calificacion) {

    }

    @Override
    void comprar(Material m, List<Object> metodosPago) {

    }

    @Override
    void solicitarPrestamo(Material m) {

    }
}
