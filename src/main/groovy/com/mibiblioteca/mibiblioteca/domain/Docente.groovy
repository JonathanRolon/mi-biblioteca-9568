package com.mibiblioteca.mibiblioteca.domain
import groovy.transform.CompileStatic

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
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
