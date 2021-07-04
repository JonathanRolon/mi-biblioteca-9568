package com.mibiblioteca.mibiblioteca.tareas.model


import com.mibiblioteca.mibiblioteca.compras.model.ComprobantePago
import com.mibiblioteca.mibiblioteca.compras.model.exception.PagoDobleException
import com.mibiblioteca.mibiblioteca.compras.model.exception.ComprobantePagoNoAdjuntoException
import groovy.transform.CompileStatic

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import java.sql.Timestamp

enum Regularidad {
    REGULAR, SUSPENDIDO, EGRESADO
}

@CompileStatic
@Entity
class Alumno {

    private final Integer CONS_CREDITOS_MEDIO = 30,
                          CONS_CREDITOS_NOVATO = 50,
                          CONS_CREDITOS_PRO = 40,
                          CONS_TOPE_NIVEL_CALIF_FORO_NOVATO = 10,
                          CONS_TOPE_NIVEL_CALIF_FORO_MEDIO = 100,
                          CONS_TOPE_CALIF_FORO_NOVATO = 10,
                          CONS_TOPE_CALIF_FORO_MEDIO = 20,
                          CONS_TOPE_CALIF_FORO_PRO = 30

    @Id
    Long DNI

    @Column(nullable = false)
    String nombre

    @Column(nullable = false)
    String apellido

    @Column(nullable = false)
    Timestamp fechaNacimiento

    @Column(nullable = false)
    Integer creditos

    @Column(nullable = true)
    Integer notaPrimerSemestre

    @Column(nullable = true)
    Integer notaSegundoSemestre

    @Column(nullable = true)
    Integer notaAnual

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    Regularidad regular

    @Column(nullable = false)
    Integer calificPositivasEnForo

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    NivelAlumno nivel

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    List<ComprobantePago> comprobantesPago

    @ManyToOne(cascade = CascadeType.ALL)
    Curso curso

    //private ResourceBundleMessageSource messageSource

    Alumno(Long DNI, String nombre, String apellido, Timestamp fechaNacimiento, Curso curso) {
        this.DNI = DNI
        this.curso = curso
        this.nivel = NivelAlumno.NOVATO
        this.nombre = nombre
        this.apellido = apellido
        this.fechaNacimiento = fechaNacimiento
        creditos = 0
        regular = Regularidad.REGULAR
        calificPositivasEnForo = 0
        comprobantesPago = new ArrayList<ComprobantePago>()
        //initMessagesBundle()
    }

    Alumno() {}

    /*void initMessagesBundle() {
        messageSource = new ResourceBundleMessageSource()
        messageSource.setBasenames("lang/messages_ES")
    }*/

    /*
    void restarCreditos(Integer creditos) {

    }*/

    boolean esNovato() {
        getNivel() == NivelAlumno.NOVATO
    }

    boolean esMedio() {
        getNivel() == NivelAlumno.MEDIO
    }

    boolean esPRO() {
        getNivel() == NivelAlumno.PRO
    }

    boolean esRegular(){
        getRegular() == Regularidad.REGULAR
    }

    Alumno subirNivel() {
        def nuevoNivel = getNivel()

        switch (getNivel()) {
            case NivelAlumno.NOVATO:
                nuevoNivel = NivelAlumno.MEDIO
                break
            case NivelAlumno.MEDIO:
                nuevoNivel = NivelAlumno.PRO
                break
            default:
                //nivel pro no sube de nivel
                break
        }

        setNivel(nuevoNivel)

        this

    }

    private Alumno sumarCreditos(Alumno alumno, Integer creditos) {
        alumno.setCreditos(alumno.getCreditos() + creditos)
        alumno
    }

    Alumno incrementarCalifPositivas() {
        setCalificPositivasEnForo(getCalificPositivasEnForo() + 1)
        def sumaCreditos = sumaCreditos()
        validarNivel()
        if (sumaCreditos) reiniciarCalifPositivas()
    }

    private Alumno reiniciarCalifPositivas() {
        setCalificPositivasEnForo(0)
        this
    }

    private Boolean sumaCreditos() {
        def califEncimaDeCinco = getCalificPositivasEnForo(),
            sumarCreditosNovato = (califEncimaDeCinco == CONS_TOPE_CALIF_FORO_NOVATO && esNovato()),
            sumarCreditosMedio = (califEncimaDeCinco == CONS_TOPE_CALIF_FORO_MEDIO && esMedio()),
            sumarCreditosPRO = (califEncimaDeCinco == CONS_TOPE_CALIF_FORO_PRO && esPRO())

        if (sumarCreditosNovato) sumarCreditos(this, CONS_CREDITOS_NOVATO)
        if (sumarCreditosMedio) sumarCreditos(this, CONS_CREDITOS_MEDIO)
        if (sumarCreditosPRO) sumarCreditos(this, CONS_CREDITOS_PRO)

        return sumarCreditosNovato || sumarCreditosMedio || sumarCreditosPRO

    }

    Alumno validarNivel() {
        def califEncimaDeCinco = getCalificPositivasEnForo(),
            subirNivelNovato = (califEncimaDeCinco == CONS_TOPE_NIVEL_CALIF_FORO_NOVATO && esNovato()),
            subirNivelMedio = (califEncimaDeCinco == CONS_TOPE_NIVEL_CALIF_FORO_MEDIO && esMedio())

        if (subirNivelNovato) subirNivel()
        if (subirNivelMedio) subirNivel()

        this
    }

    void desbloquearMaterial(ComprobantePago comprobantePago){
        try{
            def existe = comprobantesPago.find{it-> it.getIdMaterial() === comprobantePago.getIdMaterial()}
            if(existe)
                throw new PagoDobleException("Se realizó un pago doble de un material ya adquirido.")
            comprobantesPago.push(comprobantePago)
        }catch(RuntimeException ex){
            throw new ComprobantePagoNoAdjuntoException("Ocurrio un error al intentar adjuntar el comprobante.")
        }

    }

    void suspender() {
        setRegular(Regularidad.SUSPENDIDO)
    }

    void regularizar() {
        setRegular(Regularidad.REGULAR)
    }

    void darDeBaja() {
        setRegular(Regularidad.EGRESADO)
    }
}
