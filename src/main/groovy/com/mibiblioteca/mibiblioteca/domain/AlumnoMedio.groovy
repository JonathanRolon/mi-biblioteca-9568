package com.mibiblioteca.mibiblioteca.domain

import groovy.transform.CompileStatic

@CompileStatic
class AlumnoMedio extends Alumno{

    AlumnoMedio(Integer DNI, String curso) {
        super(DNI, curso)
    }

    AlumnoPRO subirNivel(){
        //convertirse en pro
        return new AlumnoPRO(this.getDNI(),this.getCurso())
    }

    @Override
    Alumno sumarCreditos(Integer creditos) {
        //validar calificaciones en el foro (10 calificaciones por encima de 5) -> 20 cred
        //validar compra? (validar que se acredito la compra) -> 100 cred
        this.creditos +=creditos
        this
    }

    @Override void restarCreditos(Integer creditos){
        this.creditos -= creditos
    }


}
