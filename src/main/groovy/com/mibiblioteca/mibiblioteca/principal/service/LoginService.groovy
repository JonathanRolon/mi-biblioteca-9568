package com.mibiblioteca.mibiblioteca.principal.service

import groovy.transform.CompileStatic
import org.springframework.stereotype.Service

@CompileStatic
class LoginService {

    private static LoginService loginService = new LoginService()
    private static Long usuario = 35711111

    static Long getUsuarioLogueado(){
        usuario
    }

    static Long setUsuarioLogueado(Long DNI){
        usuario = DNI
    }
/*
    static getLoginService(){
        if (loginService === null) {
            loginService = new LoginService()
        }
        loginService
    }*/

}