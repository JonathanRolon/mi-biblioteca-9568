package com.mibiblioteca.mibiblioteca.tareas

import com.mibiblioteca.mibiblioteca.compras.model.Pago
import com.mibiblioteca.mibiblioteca.tareas.model.Alumno
import com.mibiblioteca.mibiblioteca.tareas.model.NivelAlumno
import groovy.transform.CompileStatic
import org.junit.jupiter.api.Test
import java.sql.Timestamp
import java.time.LocalDateTime

@CompileStatic
class AlumnoTest {

    @Test
    void restarCreditosMayorCreditosAlumnoArrojaError(){

        def fecNac = Timestamp.valueOf(LocalDateTime.now())
        def alumno = new Alumno(5413513,"nombre","apellido",fecNac,"A")
        def errorAlRestarCred = false
        assert(alumno.getCreditos() === 0)
        try{
           alumno.restarCreditos(5)
        }catch(RuntimeException ex){
            errorAlRestarCred = true
        }
        assert(errorAlRestarCred)
    }

    @Test
    void subirNivelNovatoSubeAMedio(){
        def fecNac = Timestamp.valueOf(LocalDateTime.now())
        def alumno = new Alumno(5413513,"nombre","apellido",fecNac,"A")
        alumno.subirNivel()
        assert(alumno.getNivel() === NivelAlumno.MEDIO)
    }

    @Test
    void subirNivelMedioSubeAPRO(){
        def fecNac = Timestamp.valueOf(LocalDateTime.now())
        def alumno = new Alumno(5413513,"nombre","apellido",fecNac,"A")
        alumno.subirNivel()
        alumno.subirNivel()

        assert(alumno.getNivel() === NivelAlumno.PRO)
    }

    @Test
    void incrementarHasta10CalificacionesNovatoSubeNivel(){
        def fecNac = Timestamp.valueOf(LocalDateTime.now())
        def alumno = new Alumno(5413513,"nombre","apellido",fecNac,"A")
        def calif = 0

        while(alumno.getNivel() === NivelAlumno.NOVATO){
            alumno.incrementarCalifPositivas()
            calif++
        }

        assert(calif === 10)
    }

    @Test
    void incrementarHasta100oMasCalificacionesEnForoMedioSubeNivel(){
        def fecNac = Timestamp.valueOf(LocalDateTime.now())
        def alumno = new Alumno(5413513,"nombre","apellido",fecNac,"A")
        alumno.subirNivel()
        while(alumno.getNivel() === NivelAlumno.MEDIO){
            alumno.incrementarCalifPositivas()
        }
        assert(alumno.getCalificTotalesNivelEnForo() === 100)
    }

    @Test
    void desbloquear3MaterialesNovatoSubeNivel(){
        def fechaPago = Timestamp.valueOf(LocalDateTime.now())
        def comprobantePago1 = new Pago(120 as BigDecimal,fechaPago,"libx")
        def comprobantePago2 = new Pago(120  as BigDecimal,fechaPago,"liby")
        def comprobantePago3 = new Pago(120  as BigDecimal,fechaPago,"libz")
        def alumno = new Alumno(5413513,"nombre","apellido",fechaPago,"A")
        alumno.desbloquearMaterial(comprobantePago1)
        alumno.desbloquearMaterial(comprobantePago2)
        alumno.desbloquearMaterial(comprobantePago3)
        assert(alumno.getNivel() === NivelAlumno.MEDIO)
    }

    @Test
    void desbloquear5MaterialesMedioSubeNivel(){
        def fechaPago = Timestamp.valueOf(LocalDateTime.now())
        def comprobantePago1 = new Pago(120 as BigDecimal,fechaPago,"libr")
        def comprobantePago2 = new Pago(120  as BigDecimal,fechaPago,"libs")
        def comprobantePago3 = new Pago(120  as BigDecimal,fechaPago,"libt")
        def comprobantePago4 = new Pago(120  as BigDecimal,fechaPago,"libu")
        def comprobantePago5 = new Pago(120  as BigDecimal,fechaPago,"libv")
        def alumno = new Alumno(5413513,"nombre","apellido",fechaPago,"A")
        alumno.subirNivel()

        alumno.desbloquearMaterial(comprobantePago1)
        alumno.desbloquearMaterial(comprobantePago2)
        alumno.desbloquearMaterial(comprobantePago3)
        alumno.desbloquearMaterial(comprobantePago4)
        alumno.desbloquearMaterial(comprobantePago5)

        assert(alumno.getNivel() === NivelAlumno.PRO)
    }

}
