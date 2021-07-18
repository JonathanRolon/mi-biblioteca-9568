package com.mibiblioteca.mibiblioteca.principal.controller

import groovy.transform.CompileStatic
import org.springframework.boot.Banner
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

@Controller
@CompileStatic
class MiBibliotecaController {
    @RequestMapping("/")
    def home() {
        new ModelAndView("views/mibibliotecavirtual",
                [bootVersion: Banner.package.implementationVersion,
                 groovyVersion: GroovySystem.version])
    }


}
