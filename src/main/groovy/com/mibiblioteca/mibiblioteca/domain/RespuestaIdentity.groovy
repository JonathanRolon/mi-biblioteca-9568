package com.mibiblioteca.mibiblioteca.domain

import com.sun.istack.NotNull
import groovy.transform.CompileStatic

import javax.persistence.Embeddable
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.validation.constraints.Size

@Embeddable
@CompileStatic
class RespuestaIdentity implements Serializable{

    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long nroRespuesta

    @NotNull
    long nroHilo

    RespuestaIdentity() {

    }

    RespuestaIdentity(long hilo) {
        this.nroHilo = nroHilo;
    }

    @Override
    boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RespuestaIdentity that = (RespuestaIdentity) o;

        if (!nroHilo.equals(that.nroHilo)) return false;
        return nroRespuesta.equals(that.nroRespuesta);
    }

    @Override
    int hashCode() {
        int result = nroHilo.hashCode();
        result = 31 * result + nroRespuesta.hashCode();
        return result;
    }

}
