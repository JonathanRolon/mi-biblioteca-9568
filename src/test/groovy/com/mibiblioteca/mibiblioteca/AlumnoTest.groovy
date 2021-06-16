package com.mibiblioteca.mibiblioteca

import com.mibiblioteca.mibiblioteca.domain.AlumnoNovato
import groovy.transform.CompileStatic
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Before
import org.assertj.core.api.Assert
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@CompileStatic
class AlumnoTest {

    @Before
    void init() {

    }

    @After
    void teardown() {
        //clean memory
    }

    @Test
    void alumnoNovatoRecibePrimerCalificacionPorEncimaDeCincoNoSubeHastaAlumnoMedio(){
        def novato = new AlumnoNovato(35413351,"B")
        novato.sumarCreditos(50)
        assert novato.getCreditos() === 50
    }

    @Test
    void alumnoNovatoRecibeNovenaCalificacionPorEncimaDeCincoNoSubeHastaAlumnoMedio(){

    }

    @Test
    void alumnoNovatoRecibeDecimaCalificacionPorEncimaDeCincoSubeHastaAlumnoMedio(){

    }
}
