package com.mibiblioteca.mibiblioteca.domain
import groovy.transform.CompileStatic

import javax.persistence.Entity
import javax.persistence.Id

@CompileStatic
@Entity
class Libro extends Material{

    @Id
    Long ISBN

    @Override
    void prestar(Lector l) {

    }

    @Override
    void visualizar() {

    }
}
