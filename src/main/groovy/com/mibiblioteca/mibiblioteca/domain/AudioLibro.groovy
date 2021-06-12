package com.mibiblioteca.mibiblioteca.domain

import groovy.transform.CompileStatic

import javax.persistence.Entity
import javax.persistence.Id

@CompileStatic
@Entity
class AudioLibro extends Material{

    @Id
    String firmaDigital

    @Override
    void prestar(Lector l) {

    }

    @Override
    void visualizar() {

    }
}
