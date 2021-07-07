package com.mibiblioteca.mibiblioteca.consultas.model

import com.mibiblioteca.mibiblioteca.consultas.model.exception.HiloYaCerradoException
import com.mibiblioteca.mibiblioteca.consultas.model.exception.HiloYaSuspendidoException
import groovy.transform.CompileStatic

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany

enum EstadoHilo {
    ABIERTO, SUSPENDIDO, CERRADO
}

@CompileStatic
@Entity
class Hilo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @Column(nullable = false)
    String consulta

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    TemaHilo tema

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    EstadoHilo estadoHilo

    @Column(nullable = true)
    String motivoCierre

    @Column(nullable = true)
    String motivoSuspension

    @Column(nullable = false)
    Long dniPublicador

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Respuesta> respuestas

    Hilo(Long dniPublicador, String consulta, TemaHilo tema) {
        this.consulta = consulta
        this.tema = tema
        this.estadoHilo = EstadoHilo.ABIERTO
        this.dniPublicador = dniPublicador
        this.respuestas = new ArrayList<Respuesta>()
    }

    Hilo() {}

    private Boolean estaCerrado() {
        this.estadoHilo == EstadoHilo.CERRADO
    }

    void agregarRespuesta(Respuesta respuesta) {
        if (estaCerrado()) return //excepcion
        respuestas.push(respuesta)
        this
    }

    void actualizarRespuesta(Respuesta respuesta) {
        def indexResp = respuestas.findIndexOf { it ->
            it.getIdentity().getPublicador() === respuesta.getIdentity().getPublicador() &&
                    it.getIdentity().getNroHilo() === respuesta.getIdentity().getNroHilo()
        }
        respuestas.set(indexResp, respuesta)
    }

    Respuesta getRespuesta(RespuestaIdentity respuestaIdentity) {
        respuestas.find(it -> it.getIdentity().getNroHilo() == respuestaIdentity.getNroHilo() &&
                it.getIdentity().getPublicador() == respuestaIdentity.getPublicador())
    }

    void cerrar(String motivoCierre) {

        if (estadoHilo.toString() === EstadoHilo.CERRADO.toString())
            throw new HiloYaCerradoException("El hilo ya se encuentra cerrado.")

        this.motivoCierre = motivoCierre
        this.estadoHilo = EstadoHilo.CERRADO
    }

    void suspender(String motivoSuspension) {
        if (estadoHilo.toString() === EstadoHilo.SUSPENDIDO.toString())
            throw new HiloYaSuspendidoException("El hilo ya se encuentra suspendido.")
        if (estadoHilo.toString() === EstadoHilo.CERRADO.toString())
            throw new HiloYaCerradoException("El hilo se encuentra cerrado.")

        this.motivoSuspension = motivoSuspension
        this.estadoHilo = EstadoHilo.SUSPENDIDO
    }

    void retomar() {
        this.estadoHilo = EstadoHilo.ABIERTO
    }
}
