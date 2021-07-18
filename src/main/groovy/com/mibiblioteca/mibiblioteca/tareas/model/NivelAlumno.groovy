package com.mibiblioteca.mibiblioteca.tareas.model

enum NivelAlumno {

    NOVATO{
        private final Integer  CONS_COMPRA_CRED_NOVATO = 50
        @Override
        void sumarCreditos(Alumno alumno){
            alumno.sumarCreditos(CONS_COMPRA_CRED_NOVATO)
        }

        @Override
        void subirNivel(Alumno alumno){
            alumno.setNivel(NivelAlumno.MEDIO)
        }
    }, MEDIO{
        private final CONS_COMPRA_CRED_MEDIO = 100
        @Override
        void sumarCreditos(Alumno alumno){
            alumno.sumarCreditos(CONS_COMPRA_CRED_MEDIO)
        }
        @Override
        void subirNivel(Alumno alumno){
            alumno.setNivel(NivelAlumno.PRO)
        }
    }, PRO{
        private final CONS_COMPRA_CRED_PRO = 200
        @Override
        void sumarCreditos(Alumno alumno){
            alumno.sumarCreditos(CONS_COMPRA_CRED_PRO)
        }
        @Override
        void subirNivel(Alumno alumno){
            return //nivel pro no sube de nivel
        }
    };

    abstract void sumarCreditos(Alumno alumno)
    abstract void subirNivel(Alumno alumno)
}