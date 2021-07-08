package com.mibiblioteca.mibiblioteca.consultas

import com.mibiblioteca.mibiblioteca.consultas.model.Hilo
import com.mibiblioteca.mibiblioteca.consultas.model.TemaHilo
import groovy.transform.CompileStatic
import org.junit.jupiter.api.Test

@CompileStatic
class HiloTest {

    @Test
    void cancelarHiloCanceladoArrojaError() {

        def errorAlCancelar = false
        def hilo = new Hilo(34553135, "consulta", TemaHilo.CS_FORMALES)
        hilo.suspender("Se suspende porque infringe normas comunitarias.")
        try {
            hilo.suspender("Se intenta volver a cancelar.")
        } catch (RuntimeException ex) {
            errorAlCancelar = true
        }

        assert (errorAlCancelar)
    }

    @Test
    void cancelarHiloCerradoArrojaError() {
        def errorAlCancelar = false
        def hilo = new Hilo(34553135, "consulta", TemaHilo.CS_FORMALES)
        hilo.cerrar("Se da por contestada la pregunta.")
        try {
            hilo.suspender("Se intenta cancelar.")
        } catch (RuntimeException ex) {
            errorAlCancelar = true
        }

        assert (errorAlCancelar)
    }

}
