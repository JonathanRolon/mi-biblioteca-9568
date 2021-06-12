package com.mibiblioteca.mibiblioteca.domain
import groovy.transform.CompileStatic

import javax.persistence.Entity
import javax.persistence.Id

@CompileStatic
@Entity
class Revista extends Material{

    @Id
    String DOI

    @Override
    void prestar(Lector l) {

    }

    @Override
    void visualizar() {

    }
}
