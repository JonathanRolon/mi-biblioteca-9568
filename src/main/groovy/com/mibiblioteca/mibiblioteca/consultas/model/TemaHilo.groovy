package com.mibiblioteca.mibiblioteca.consultas.model

import groovy.transform.CompileStatic

@CompileStatic
enum TemaHilo {
    CS_LENGUAJE{
        @Override
        String getDescripcion(){
            "Cs. del Lenguaje"
        }
    }, CS_BIOLOGICAS{
        @Override
        String getDescripcion(){
            "Cs. biológicas"
        }
    }, CS_SOCIALES{
        @Override
        String getDescripcion(){
            "Cs. Sociales"
        }
    }, CS_FORMALES{
        @Override
        String getDescripcion(){
            "Cs. formales"
        }
    }, CS_FACTICAS{
        @Override
        String getDescripcion(){
            "Cs. fácticas"
        }
    }, TECNOLOGIA{
        @Override
        String getDescripcion(){
            "Tecnología"
        }
    }, ARTE{
        @Override
        String getDescripcion(){
            "Arte"
        }
    }, OTRO{
        @Override
        String getDescripcion(){
            "Otros"
        }
    }

    abstract String getDescripcion()
}