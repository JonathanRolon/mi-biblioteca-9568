package com.mibiblioteca.mibiblioteca.model

import groovy.transform.CompileStatic

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Lob
import java.sql.Blob
import java.sql.Timestamp

@CompileStatic
@Entity
class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id

    @Column(nullable = true)
    float precio

    @Column(nullable = true)
    String descripcion

    @Column(nullable = false)
    String titulo

    @Column(nullable = false)
    @Lob
    private Blob contenido;

    @Column(nullable = false)
    String autor

    @Column(nullable = false)
    Timestamp fechaPublicacion

    //HashMap<Integer, String> indice

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    TipoMaterial tipoMaterial

    @Column(nullable = false)
    String editorial

    Material(Float precio, String descripcion, String titulo, String autor,
             Timestamp fechaPublicacion, String editorial, TipoMaterial tipoMaterial) {

        this.tipoMaterial = tipoMaterial
        this.precio = precio
        this.descripcion = descripcion
        this.titulo = titulo
        this.autor = autor
        this.fechaPublicacion = fechaPublicacion
        this.editorial = editorial

    }

    Material() {}

}
