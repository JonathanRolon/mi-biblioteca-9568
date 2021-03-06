package com.mibiblioteca.mibiblioteca.compras.model

import com.mibiblioteca.mibiblioteca.consultas.model.TemaHilo
import groovy.transform.CompileStatic

import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id
import java.sql.Timestamp

@CompileStatic
@Entity
class Material {

    @Id
    String idMaterial

    @Column(nullable = true)
    BigDecimal precio

    @Column(nullable = true)
    String descripcion

    @Column(nullable = false)
    String titulo

    @Embedded
    @Column(nullable = true)
    private ContenidoMaterial contenido;

    @Column(nullable = false)
    String autor

    @Column(nullable = true)
    String portada

    @Column(nullable = false)
    Timestamp fechaPublicacion

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    MaterialVigencia vigencia

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    TipoMaterial tipoMaterial

    @Column(nullable = false)
    String editorial

    @Column(nullable = true)
    TemaHilo tema

    Material() {}

    Material(String idMaterial, BigDecimal precio, String descripcion, String titulo, String autor,
             Timestamp fechaPublicacion, String editorial, TipoMaterial tipoMaterial, TemaHilo tema,
             MaterialVigencia vigencia, ContenidoMaterial contenido) {
        this.idMaterial = idMaterial
        this.tipoMaterial = tipoMaterial
        this.precio = precio
        this.descripcion = descripcion
        this.titulo = titulo
        this.autor = autor
        this.fechaPublicacion = fechaPublicacion
        this.editorial = editorial
        this.tema = tema
        this.vigencia = vigencia
        this.contenido = contenido

    }

    Boolean estaVigente() {
        return this.vigencia === MaterialVigencia.VIGENTE
    }

}
