package com.mibiblioteca.mibiblioteca.model

import com.sun.istack.NotNull
import groovy.transform.CompileStatic

import javax.persistence.Embeddable


@Embeddable
@CompileStatic
class CalificacionIdentity implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    RespuestaIdentity respuestaIdentity

    @NotNull
    Long idCalif

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        CalificacionIdentity that = (CalificacionIdentity) o

        if (idCalif != that.idCalif) return false
        if (!respuestaIdentity.publicador.equals(that.respuestaIdentity.publicador) ||
                !respuestaIdentity.nroHilo.equals(that.respuestaIdentity.nroHilo)) return false
        return true
    }

    int hashCode() {
        int result
        result = (respuestaIdentity != null ? respuestaIdentity.hashCode() : 0)
        result = 31 * result + (idCalif != null ? idCalif.hashCode() : 0)
        return result
    }
}
