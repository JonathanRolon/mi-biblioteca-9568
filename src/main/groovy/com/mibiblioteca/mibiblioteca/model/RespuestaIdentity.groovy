package com.mibiblioteca.mibiblioteca.model

import com.sun.istack.NotNull
import groovy.transform.CompileStatic

import javax.persistence.Embeddable

@Embeddable
@CompileStatic
class RespuestaIdentity implements Serializable{

    @NotNull
    Long publicador

    @NotNull
    Long nroHilo

    RespuestaIdentity() {

    }

    RespuestaIdentity(Long dniPublicador, Long hilo) {
        this.nroHilo = nroHilo
        this.publicador = dniPublicador
    }

    @Override
    boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RespuestaIdentity that = (RespuestaIdentity) o;

        if (!nroHilo.equals(that.nroHilo)) return false;
        return publicador.equals(that.publicador);
    }

    @Override
    int hashCode() {
        int result = nroHilo.hashCode();
        result = 31 * result + publicador.hashCode();
        return result;
    }

}
