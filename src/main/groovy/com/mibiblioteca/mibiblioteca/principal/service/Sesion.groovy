package com.mibiblioteca.mibiblioteca.principal.service

import com.mibiblioteca.mibiblioteca.tareas.model.Alumno
import com.mibiblioteca.mibiblioteca.tareas.model.Docente
import groovy.transform.CompileStatic

enum TipoUsuario {
    DOCENTE, ALUMNO
}

@CompileStatic
class Sesion {

    static Long usuario

    static TipoUsuario tipoUsuario

    static Alumno alumno

    static Docente docente

}
