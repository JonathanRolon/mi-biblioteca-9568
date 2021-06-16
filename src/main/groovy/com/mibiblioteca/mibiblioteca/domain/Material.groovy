package com.mibiblioteca.mibiblioteca.domain
import groovy.transform.CompileStatic

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id
import javax.persistence.Lob
import java.sql.Blob
import java.sql.Timestamp

enum TipoMaterial{
    LIBRO, REVISTA, AUDIOLIBRO
}

@CompileStatic
@Entity
abstract class Material {

    @Id
    String id

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

    Material(String id, Float precio,String  descripcion,String  titulo,String autor,
             Timestamp fechaPublicacion,String editorial,String material){

        this.tipoMaterial = this.str2Enum(material)
        this.precio = precio
        this.descripcion = descripcion
        this.titulo = titulo
        this.autor = autor
        this.fechaPublicacion = fechaPublicacion
        this.editorial = editorial

    }

    Material(){}

    float getPrecio() {
        return precio
    }

    String getDescripcion() {
        return descripcion
    }

    String getTitulo() {
        return titulo
    }

    private TipoMaterial str2Enum(String name) {
        return TipoMaterial.valueOf(TipoMaterial.class, name);
    }

    String getAutor() {
        return autor
    }

    Timestamp getFechaPublicacion() {
        return fechaPublicacion
    }

    TipoMaterial getTipoMaterial() {
        return tipoMaterial
    }

    String getEditorial() {
        return editorial
    }

    void setPrecio(Float precio){
        this.precio = precio
    }

    abstract void prestar(Lector l)

    abstract void visualizar()

}
