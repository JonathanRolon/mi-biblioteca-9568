package com.mibiblioteca.mibiblioteca.tareas.controller

import com.mibiblioteca.mibiblioteca.tareas.service.ManejadorTareasService
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView

@Controller
@CompileStatic
@RequestMapping('/tareas')
class TareaController {

    @Autowired
    private final ManejadorTareasService manejadorTareasService

    @RequestMapping(method = RequestMethod.GET)
    ModelAndView showAll(Model model) {
        //model.addAttribute("hilo", new Hilo())
        new ModelAndView('views/tarea/tareas', [tareas: manejadorTareasService.findAll()])
    }

}
