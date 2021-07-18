package com.mibiblioteca.mibiblioteca.principal.controller

import groovy.transform.CompileStatic
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.ModelAndView

@CompileStatic
@Controller
class AlumnoController {

    @GetMapping("/biblioteca")
    ModelAndView showAll(Model model) {
        //model.addAttribute("hilo", new Hilo())
        new ModelAndView('views/biblioteca/mibiblioteca')
    }

}
