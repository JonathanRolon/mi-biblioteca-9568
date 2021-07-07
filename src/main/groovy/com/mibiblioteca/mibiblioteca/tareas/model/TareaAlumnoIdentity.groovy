package com.mibiblioteca.mibiblioteca.tareas.model

import com.sun.istack.NotNull

import javax.persistence.Embeddable
import javax.validation.constraints.Max
import javax.validation.constraints.Min

@Embeddable
class TareaAlumnoIdentity implements Serializable {

    @NotNull
    Long idTarea

    @NotNull
    Long DNI

    TareaAlumnoIdentity() {

    }

    TareaAlumnoIdentity(Long DNI, Long idTarea) {
        this.idTarea = idTarea
        this.DNI = DNI
    }

    @Override
    boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false
        TareaAlumnoIdentity that = (TareaAlumnoIdentity) o
        if (!idTarea.equals(that.idTarea)) false
        DNI.equals(that.DNI);
    }

    @Override
    int hashCode() {
        int result = idTarea.hashCode()
       31 * result + DNI.hashCode()
    }
}
