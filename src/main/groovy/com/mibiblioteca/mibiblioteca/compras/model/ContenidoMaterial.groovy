package com.mibiblioteca.mibiblioteca.compras.model

import com.sun.beans.decoder.ValueObject
import com.sun.istack.NotNull
import groovy.transform.CompileStatic
import javax.persistence.Embeddable
import javax.persistence.Lob

@Embeddable
@CompileStatic
class ContenidoMaterial implements ValueObject{

    @NotNull
    private String fileName
    @NotNull
    private String fileType
    @NotNull
    private Long size
    @Lob
    private byte[] data

    ContenidoMaterial() {

    }

    ContenidoMaterial(String fileName, String fileType, byte[] data, Long size) {
        this.fileName = fileName
        this.fileType = fileType
        this.data = data
        this.size = size
    }

    @Override
    Object getValue() {
        return null
    }

    @Override
    boolean isVoid() {
        return false
    }
}
