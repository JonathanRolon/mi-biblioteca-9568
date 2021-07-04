package com.mibiblioteca.mibiblioteca.compras.model


import com.sun.istack.NotNull
import groovy.transform.CompileStatic

import javax.persistence.Embeddable

@Embeddable
@CompileStatic
class TarjetaIdentity implements Serializable{

    private static final long serialVersionUID = 1L;

    @NotNull
    Long nroTarjeta

    @NotNull
    EntidadBancaria entidad

    @NotNull
    Integer CSV

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        TarjetaIdentity that = (TarjetaIdentity) o

        if (nroTarjeta != that.nroTarjeta) return false
        if (!entidad.equals(that.entidad) ||
                !CSV.equals(that.CSV)) return false
        return true
    }

    int hashCode() {
        int result
        result = (nroTarjeta != null ? nroTarjeta.hashCode() : 0)
        result = (entidad != null ? entidad.hashCode() : 0)
        result = 31 * result + (CSV != null ? CSV.hashCode() : 0)
        return result
    }

}
