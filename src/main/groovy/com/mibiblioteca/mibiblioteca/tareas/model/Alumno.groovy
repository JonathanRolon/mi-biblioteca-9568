package com.mibiblioteca.mibiblioteca.tareas.model


import com.mibiblioteca.mibiblioteca.compras.model.ComprobantePago
import com.mibiblioteca.mibiblioteca.compras.model.exception.PagoDobleException
import com.mibiblioteca.mibiblioteca.tareas.model.exception.CreditosExcedeCantidadDisponibleException
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
                          CONS_TOPE_CALIF_FORO_PRO = 30,
                          CONS_COMPRA_CRED_NOVATO = 50,
                          CONS_COMPRA_CRED_MEDIO = 100,
                          CONS_COMPRA_CRED_PRO = 200,
                          CONS_COMPRAS_NOVATO_SUBIR_NIVEL = 3,
                          CONS_COMPRAS_MEDIO_SUBIR_NIVEL = 5

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

    @Column(nullable = false)
    String curso

    //private ResourceBundleMessageSource messageSource

    Alumno(Long DNI, String nombre, String apellido, Timestamp fechaNacimiento, String curso) {
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

    void restarCreditos(Integer creditos) {
        if (creditos > this.creditos) {
            throw new CreditosExcedeCantidadDisponibleException("Error: el alumno no posee esa cantidad de creditos disponibles")
        }
        this.creditos -= creditos
    }

    boolean esNovato() {
        getNivel() == NivelAlumno.NOVATO
    }

    boolean esMedio() {
        getNivel() == NivelAlumno.MEDIO
    }

    boolean esPRO() {
        getNivel() == NivelAlumno.PRO
    }

    boolean esRegular() {
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

    private Alumno sumarCreditos(Integer creditos) {
        setCreditos(getCreditos() + creditos)
        this
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

        if (sumarCreditosNovato) sumarCreditos(CONS_CREDITOS_NOVATO)
        if (sumarCreditosMedio) sumarCreditos(CONS_CREDITOS_MEDIO)
        if (sumarCreditosPRO) sumarCreditos(CONS_CREDITOS_PRO)

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

    private void validarCreditosCompra() {

        switch (nivel) {
            case NivelAlumno.NOVATO:
                sumarCreditos(CONS_COMPRA_CRED_NOVATO)
                break
            case NivelAlumno.MEDIO:
                sumarCreditos(CONS_COMPRA_CRED_MEDIO)
                break
            default: //PRO
                sumarCreditos(CONS_COMPRA_CRED_PRO)
        }

    }

    private void validarNivelCompra() {

        def compras = comprobantesPago.findAll { it -> it.getSaldoAbonado() > 0 }
        def subeNivelNovato = esNovato() && compras.size() >= CONS_COMPRAS_NOVATO_SUBIR_NIVEL,
            subeNivelMedio = esMedio() && compras.size() >= CONS_COMPRAS_MEDIO_SUBIR_NIVEL

        if (subeNivelNovato || subeNivelMedio) {
            subirNivel()
        }

    }

    void desbloquearMaterial(ComprobantePago comprobantePago) {
        def existe = comprobantesPago.find { it -> it.getIdMaterial() === comprobantePago.getIdMaterial() }
        if (existe)
            throw new PagoDobleException("Se realizó un pago doble de un material ya adquirido.")
        comprobantesPago.push(comprobantePago)
        validarCreditosCompra()
        validarNivelCompra()
    }

    void suspender() {
        setRegular(Regularidad.SUSPENDIDO)
    }

    Integer getCantLibrosComprados() {
        return comprobantesPago.size()
    }

    Boolean yaCompre(String idMaterial) {
        def encontrado = comprobantesPago.find {
            it -> it.getIdMaterial() === idMaterial
        }
        encontrado !== null
    }
}
