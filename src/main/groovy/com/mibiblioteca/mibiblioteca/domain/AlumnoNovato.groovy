package com.mibiblioteca.mibiblioteca.domain
import groovy.transform.CompileStatic

import javax.persistence.Entity

@CompileStatic
class AlumnoNovato extends Alumno{

    AlumnoNovato(Integer DNI, String curso) {
        super(DNI, curso)
    }

    @Override
    Alumno sumarCreditos(Integer creditos) {
        //por compra debe sumar 50 creditos
        //a partir de la 10ma calificacion por encima de 5 en el foro -> 50 creditos
        this.creditos += creditos
        this
        //ademas a partir de la 10ma calificacion o 3er compra se convierte en alumno medio
        //return new AlumnoMedio(this.getDNI, this.getCurso())

    }

    @Override
    void restarCreditos(Integer creditos) {
        this.creditos -= creditos
    }
}
