package com.mibiblioteca.mibiblioteca.compras.model

import com.sun.istack.NotNull
import groovy.transform.CompileStatic

import javax.persistence.Embeddable

@Embeddable
@CompileStatic
class ArticuloMaterialIdentity implements Serializable{

    @NotNull
    Long nroPedido

    @NotNull
    String idMaterial

    @Override
    boolean equals(o) {
        if (this.is(o)) true
        if (getClass() != o.class)  false

        ArticuloMaterialIdentity that = (ArticuloMaterialIdentity) o

        if (!nroPedido.equals(that.nroPedido) ||
                !idMaterial.equals(that.idMaterial)) false
        true
    }

    @Override
    int hashCode() {
        int result
        result = (nroPedido != null ? nroPedido.hashCode() : 0)
        result += (idMaterial != null ? idMaterial.hashCode() : 0)
        result = 31 * result
        return result
    }

}
