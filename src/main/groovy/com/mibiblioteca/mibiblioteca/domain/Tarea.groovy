package com.mibiblioteca.mibiblioteca.domain

import groovy.transform.CompileStatic

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@CompileStatic
@Entity
class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long nroTarea

    Tarea(){}
}
