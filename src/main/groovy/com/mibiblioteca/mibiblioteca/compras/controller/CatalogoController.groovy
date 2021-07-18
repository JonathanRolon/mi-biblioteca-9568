package com.mibiblioteca.mibiblioteca.compras.controller

import com.mibiblioteca.mibiblioteca.compras.service.CompradorService
import com.mibiblioteca.mibiblioteca.compras.service.MaterialService
import com.mibiblioteca.mibiblioteca.consultas.model.Hilo
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView

@Controller
@CompileStatic
@RequestMapping('/tienda')
class CatalogoController {

    @Autowired
    private final CompradorService compradorService

    @Autowired
    private final MaterialService materialService

    @RequestMapping(method = RequestMethod.GET)
    ModelAndView showAll(Model model) {
        //model.addAttribute("hilo", new Hilo())
        new ModelAndView('views/tienda/catalogo', [catalogo: materialService.findAll()])
    }

}
