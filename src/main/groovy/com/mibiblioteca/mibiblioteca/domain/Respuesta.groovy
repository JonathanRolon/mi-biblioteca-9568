package com.mibiblioteca.mibiblioteca.domain
import groovy.transform.CompileStatic

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@CompileStatic
@Entity
class Respuesta implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String nroRespuesta

    @Id
    String nroHilo

    @Column(nullable = false)
    String contenido

    Respuesta (String respuesta, Long idHilo){
        contenido = respuesta
        nroHilo = idHilo
        //hilo = hiloService.findById(idHilo)
    }

    @Override
    public boolean equals(Object o) {

        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Respuesta)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        Respuesta r = (Respuesta) o;

        // Compare the data members and return accordingly
        return (r.nroHilo).equals(o.nroHilo)
                && (r.nroRespuesta).equals(o.nroRespuesta);
    }

    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return (nroHilo + nroRespuesta).hashCode();
    }

    Respuesta(){}

    void calificar(Integer calificacion){

    }
}
