package com.mibiblioteca.mibiblioteca.domain

import com.sun.istack.NotNull
import com.sun.istack.Nullable
import groovy.transform.CompileStatic

import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

enum EstadoHilo{
     ABIERTO, SUSPENDIDO, CERRADO
}

@CompileStatic
class Hilo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id

    @NotNull
    String consulta

    EstadoHilo estadoHilo

    String tema
}
