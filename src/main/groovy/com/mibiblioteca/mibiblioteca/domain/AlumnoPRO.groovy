package com.mibiblioteca.mibiblioteca.domain

import groovy.transform.CompileStatic

@CompileStatic
class AlumnoPRO extends Alumno{

    AlumnoPRO(Integer DNI, String curso) {
        super(DNI, curso)
    }

    AlumnoMedio degradarNivel(){
        //convertirse en medio
        return new AlumnoMedio(this.getDNI(),this.getCurso())
    }

    @Override
    Alumno sumarCreditos(Integer creditos) {
        //validar calificaciones (30 calif. por encima de 5 en el foro de preg. y rtas) -> 40 cred
        //validar que se acreditó la compra -> 120 creditos
        this.creditos += creditos
        this
    }

    @Override void restarCreditos(Integer creditos){
        this.creditos -= creditos
    }
}
