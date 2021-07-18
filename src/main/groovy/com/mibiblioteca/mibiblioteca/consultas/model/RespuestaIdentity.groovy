package com.mibiblioteca.mibiblioteca.consultas.model

import com.sun.istack.NotNull
import groovy.transform.CompileStatic

import javax.persistence.Embeddable
import java.sql.Timestamp
import java.time.LocalDateTime

@Embeddable
@CompileStatic
class RespuestaIdentity implements Serializable{

    @NotNull
    Long publicador

    @NotNull
    Long nroHilo

    @NotNull
    Timestamp fechaCreacion

    RespuestaIdentity() {
        this.fechaCreacion = Timestamp.valueOf(LocalDateTime.now())
    }

    RespuestaIdentity(Long dniPublicador, Long nroHilo) {
        this.nroHilo = nroHilo
        this.publicador = dniPublicador
        this.fechaCreacion = Timestamp.valueOf(LocalDateTime.now())
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