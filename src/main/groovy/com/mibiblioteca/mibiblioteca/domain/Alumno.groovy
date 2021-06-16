package com.mibiblioteca.mibiblioteca.domain

import groovy.transform.CompileStatic

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id

enum Regularidad{
    REGULAR, SUSPENDIDO, EGRESADO
}

@CompileStatic
@Entity
abstract class Alumno {

    @Id
    Integer DNI

    @Column(nullable = false)
    Integer creditos

    @Column(nullable = false)
    String curso

    @Column(nullable = true)
    Integer notaPrimerSemestre

    @Column(nullable = true)
    Integer notaSegundoSemestre

    @Column(nullable = true)
    Integer notaAnual

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    Regularidad regular

    Alumno(Integer DNI,String curso) {
        this.DNI = DNI
        this.curso = curso
        creditos = 0
        regular = Regularidad.REGULAR
    }

    Alumno(){

    }

    abstract Alumno sumarCreditos(Integer creditos)

    abstract void restarCreditos(Integer creditos)

    void preguntar(String pregunta) {
        def publicador = new Publicador()
        publicador.preguntar(pregunta)
    }

    void responder(String respuesta, Long idHilo) {
        def publicador = new Publicador()
        publicador.responder(respuesta, idHilo)
    }

    Integer getDNI(){
        return this.DNI
    }

    String getCurso(){
        return this.curso
    }

}
