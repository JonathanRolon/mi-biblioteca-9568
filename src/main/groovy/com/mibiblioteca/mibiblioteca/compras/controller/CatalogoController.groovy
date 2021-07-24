package com.mibiblioteca.mibiblioteca.compras.controller

import com.mibiblioteca.mibiblioteca.compras.service.CompradorService
import com.mibiblioteca.mibiblioteca.compras.service.MaterialService
import com.mibiblioteca.mibiblioteca.principal.service.Sesion
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.servlet.ModelAndView

@Controller
@CompileStatic
class CatalogoController {

    @Autowired
    private final CompradorService compradorService

    @Autowired
    private final MaterialService materialService

    @GetMapping('/tienda')
    ModelAndView showAll(Model model) {
        def pedido = compradorService.getPedido(Sesion.alumno)
        model.addAttribute("pedido", pedido)
        model.addAttribute("alumno", Sesion.alumno)
        new ModelAndView('views/tienda/catalogo', [catalogo: materialService.findAll()])
    }

    @GetMapping(value = '/pedido')
    ModelAndView showPedido(Model model){
        def pedido = compradorService.getPedido(Sesion.alumno)
        model.addAttribute("pedido", pedido)
        model.addAttribute("alumno", Sesion.alumno)
        new ModelAndView('views/tienda/pedido')
    }

    @PostMapping(value = '/pedirArticulo/{id}')
    ModelAndView solicitarArticulo(Model model,
                                   @PathVariable('id') String ISBN){
        def pedido = compradorService.getPedido(Sesion.alumno)
        def material = materialService.findById(ISBN)?.get()
        compradorService.agregarArticulo(pedido, material)
        model.addAttribute("pedido", pedido)
        model.addAttribute("alumno", Sesion.alumno)
        new ModelAndView('views/tienda/catalogo', [catalogo: materialService.findAll()])
    }

    @PostMapping(value = '/cancelarArticulo/{id}')
    ModelAndView cancelarArticulo(Model model,
                                   @PathVariable('id') String ISBN){
        def pedido = compradorService.getPedido(Sesion.alumno)
        def material = materialService.findById(ISBN)?.get()
        compradorService.eliminarArticulo(pedido, material)
        model.addAttribute("pedido", pedido)
        model.addAttribute("alumno", Sesion.alumno)
        new ModelAndView('redirect:/pedido')
    }

    @PostMapping(value = '/cancelarPedido')
    ModelAndView cancelarPedido(Model model){
        def pedido = compradorService.getPedido(Sesion.alumno)
        compradorService.cancelarPedido(pedido)
        model.addAttribute("pedido", pedido)
        model.addAttribute("alumno", Sesion.alumno)
        new ModelAndView('redirect:/tienda')
    }

}
