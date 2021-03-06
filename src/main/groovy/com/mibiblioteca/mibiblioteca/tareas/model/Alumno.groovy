package com.mibiblioteca.mibiblioteca.tareas.model


import com.mibiblioteca.mibiblioteca.compras.model.Pago
import com.mibiblioteca.mibiblioteca.compras.model.Material
import com.mibiblioteca.mibiblioteca.compras.model.TarjetaDeCredito
import com.mibiblioteca.mibiblioteca.compras.model.exception.PagoDobleException
import com.mibiblioteca.mibiblioteca.tareas.model.exception.CreditosExcedeCantidadDisponibleException
import groovy.transform.CompileStatic
import org.springframework.transaction.annotation.Transactional

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.validation.constraints.NotEmpty
import java.sql.Timestamp

enum Regularidad {
    REGULAR, SUSPENDIDO, EGRESADO
}

@CompileStatic
@Entity
@Transactional
class Alumno {

    private final Integer CONS_CREDITOS_MEDIO = 30,
                          CONS_CREDITOS_NOVATO = 50,
                          CONS_CREDITOS_PRO = 40,
                          CONS_TOPE_NIVEL_CALIF_FORO_NOVATO = 10,
                          CONS_TOPE_NIVEL_CALIF_FORO_MEDIO = 100,
                          CONS_TOPE_CALIF_FORO_NOVATO = 10,
                          CONS_TOPE_CALIF_FORO_MEDIO = 20,
                          CONS_TOPE_CALIF_FORO_PRO = 30,
                          CONS_COMPRAS_NOVATO_SUBIR_NIVEL = 3,
                          CONS_COMPRAS_MEDIO_SUBIR_NIVEL = 5,
                          CONS_CREDITOS_DTO = 400

    @Id
    Long DNI

    @Column(nullable = false)
    @NotEmpty(message = "Nombre es requerido.")
    String nombre

    @Column(nullable = false)
    @NotEmpty(message = "Apellido es requerido.")
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
    Integer calificPositivasCredEnForo

    @Column(nullable = false)
    //Indica cuantas calificaciones positivas
    // (encima de 5) recibi?? en este nivel
    Integer calificTotalesNivelEnForo

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    NivelAlumno nivel

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    List<Pago> comprobantesPago

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    List<TarjetaDeCredito> tarjetasRegistradas

    @Column(nullable = false)
    @NotEmpty(message = "Curso es requerido.")
    String curso

    Alumno(Long DNI, String nombre, String apellido, Timestamp fechaNacimiento, String curso) {
        this.DNI = DNI
        this.curso = curso
        this.nivel = NivelAlumno.NOVATO
        this.nombre = nombre
        this.apellido = apellido
        this.fechaNacimiento = fechaNacimiento
        creditos = 0
        regular = Regularidad.REGULAR
        calificPositivasCredEnForo = 0
        calificTotalesNivelEnForo = 0
        comprobantesPago = new ArrayList<Pago>()
        tarjetasRegistradas = new ArrayList<TarjetaDeCredito>()
    }

    Alumno() {}


    Alumno sumarCreditos(Integer creditos) {
        setCreditos(getCreditos() + creditos)
        this
    }

    private Alumno reiniciarCalifPositivasCred() {
        calificPositivasCredEnForo = 0
        this
    }

    private Alumno reiniciarCalifPositivasNivel() {
        calificPositivasCredEnForo = 0
        this
    }

    String getNivelDescripcion(){
        nivel.getDescripcion()
    }

    private void validarNivelCompra() {

        def compras = comprobantesPago.findAll { it -> it.getSaldoAbonado() > 0 }
        def subeNivelNovato = esNovato() && compras.size() >= CONS_COMPRAS_NOVATO_SUBIR_NIVEL,
            subeNivelMedio = esMedio() && compras.size() >= CONS_COMPRAS_MEDIO_SUBIR_NIVEL

        if (subeNivelNovato || subeNivelMedio) {
            subirNivel()
        }

    }

    private Boolean sumaCreditos() {
        def califEncimaDeCinco = getCalificPositivasCredEnForo(),
            sumarCreditosNovato = (califEncimaDeCinco == CONS_TOPE_CALIF_FORO_NOVATO && esNovato()),
            sumarCreditosMedio = (califEncimaDeCinco == CONS_TOPE_CALIF_FORO_MEDIO && esMedio()),
            sumarCreditosPRO = (califEncimaDeCinco == CONS_TOPE_CALIF_FORO_PRO && esPRO())

        if (sumarCreditosNovato) sumarCreditos(CONS_CREDITOS_NOVATO)
        if (sumarCreditosMedio) sumarCreditos(CONS_CREDITOS_MEDIO)
        if (sumarCreditosPRO) sumarCreditos(CONS_CREDITOS_PRO)

        return sumarCreditosNovato || sumarCreditosMedio || sumarCreditosPRO

    }

    void restarCreditos(Integer creditos) {
        if (creditos > this.creditos) {
            throw new CreditosExcedeCantidadDisponibleException("Error: el alumno no posee esa " +
                    "cantidad de creditos disponibles")
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

    Alumno incrementarCalifPositivas() {
        calificPositivasCredEnForo += 1
        calificTotalesNivelEnForo += 1
        def sumaCreditos = sumaCreditos()
        validarNivel()
        if (sumaCreditos) reiniciarCalifPositivasCred()
    }

    Alumno validarNivel() {
        def califEncimaDeCinco = getCalificTotalesNivelEnForo(),
            subirNivelNovato = (califEncimaDeCinco == CONS_TOPE_NIVEL_CALIF_FORO_NOVATO && esNovato()),
            subirNivelMedio = (califEncimaDeCinco == CONS_TOPE_NIVEL_CALIF_FORO_MEDIO && esMedio())

        if (subirNivelNovato || subirNivelMedio) {
            subirNivel()
            reiniciarCalifPositivasNivel()
        }

        this
    }

    void desbloquearMaterial(Pago comprobantePago) {
        def existe = comprobantesPago.find { it -> it.getIdMaterial() === comprobantePago.getIdMaterial() }
        if (existe)
            throw new PagoDobleException("Se realiz?? un pago doble de un material ya adquirido.")
        comprobantesPago.push(comprobantePago)
        nivel.sumarCreditos(this)
        validarNivelCompra()
    }

    void suspender() {
        setRegular(Regularidad.SUSPENDIDO)
    }

    Integer getCantLibrosComprados() {
        return comprobantesPago.size()
    }

    Boolean yaCompre(Material material) {
        def encontrado = comprobantesPago.find {
            it -> it.getIdMaterial() == material.getIdMaterial()
        }
        encontrado !== null
    }

    void subirNivel() {
        nivel.subirNivel(this)
    }

    void registrarTarjeta(TarjetaDeCredito tarjetaDeCredito){
        tarjetasRegistradas.push(tarjetaDeCredito)
    }

    Boolean creditosDtoSuficientes(){
        def valor = creditos >= CONS_CREDITOS_DTO
        valor
    }

    TarjetaDeCredito getTarjetaRegistrada(String nroTarjeta) {
        tarjetasRegistradas.find {estaTarjeta ->
            estaTarjeta.getNroTarjeta() == nroTarjeta
        }
    }
}
