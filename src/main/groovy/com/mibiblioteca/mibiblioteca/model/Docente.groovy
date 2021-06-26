package com.mibiblioteca.mibiblioteca.model
import groovy.transform.CompileStatic

import javax.persistence.Entity
import javax.persistence.Id

@CompileStatic
@Entity
class Docente {

    @Id
    long DNI

    //cursos[]
    //biblioteca[]

    Docente (long DNI){
        this.DNI = DNI
    }

    Docente(){}
}
