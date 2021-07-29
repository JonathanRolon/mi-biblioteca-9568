package com.mibiblioteca.mibiblioteca.compras.controller

import com.mibiblioteca.mibiblioteca.compras.model.PedidoMaterial
import com.mibiblioteca.mibiblioteca.compras.service.CompradorService
import com.mibiblioteca.mibiblioteca.compras.service.MaterialService
import com.mibiblioteca.mibiblioteca.principal.service.Sesion
import com.mibiblioteca.mibiblioteca.tareas.repository.AlumnoRepository
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@CompileStatic
class ComprasController {

    @Autowired
    private final CompradorService compradorService

    @Autowired
    private final MaterialService materialService

    @Autowired
    private AlumnoRepository alumnoRepository

    private void configModelAlumno(Model model){
        def pedido = compradorService.getPedido(Sesion.alumno)
        def alumno = alumnoRepository.findById(Sesion.alumno.getDNI())?.get()
        model.addAttribute("pedido", pedido)
        model.addAttribute("alumno", alumno)
        model.addAttribute("tipoUsuario", (Sesion.tipoUsuario).toString())
    }

    @GetMapping('/tienda')
    ModelAndView showAll(Model model) {
        configModelAlumno(model)
        new ModelAndView('views/tienda/catalogo', [catalogo: materialService.findAll()])
    }

    @GetMapping(value = '/pedido')
    ModelAndView showPedido(Model model){
        configModelAlumno(model)
        new ModelAndView('views/tienda/pedido')
    }

    @PostMapping(value = '/pedirArticulo/{id}')
    ModelAndView solicitarArticulo(Model model, RedirectAttributes flash,
                                   @PathVariable('id') String ISBN){
        def pedido = compradorService.getPedido(Sesion.alumno)
        def material = materialService.findById(ISBN)?.get()
        try{
            compradorService.agregarArticulo(pedido, material)
        }catch(RuntimeException ex){
            flash.addFlashAttribute("error", "Error al intentar agregar el artículo.")
        }
        configModelAlumno(model)
        new ModelAndView('views/tienda/catalogo', [catalogo: materialService.findAll()])
    }

    @PostMapping(value = '/cancelarArticulo/{id}')
    ModelAndView cancelarArticulo(Model model, RedirectAttributes flash,
                                   @PathVariable('id') String ISBN){
        def pedido = compradorService.getPedido(Sesion.alumno)
        def material = materialService.findById(ISBN)?.get()
        try{
            compradorService.eliminarArticulo(pedido, material)
        }catch(RuntimeException ex){
            flash.addFlashAttribute("error", "Error al intentar eliminar el artículo.")
        }

        new ModelAndView('redirect:/pedido')
    }

    @PostMapping(value = '/cancelarPedido')
    ModelAndView cancelarPedido(Model model, RedirectAttributes flash){
        def pedido = compradorService.getPedido(Sesion.alumno)
        try{
            compradorService.cancelarPedido(pedido)
            flash.addFlashAttribute("infoMsg", "Tu pedido fue cancelado!")
        }catch(RuntimeException e){
            flash.addFlashAttribute("error", "Error al intentar cancelar tu pedido.")
        }

        new ModelAndView('redirect:/tienda')
    }

    @GetMapping(value = '/completarDatosPago')
    String completarDatosPago(Model model){
        configModelAlumno(model)
        'views/tienda/pago'
    }

    @GetMapping(value = '/cancelarPago')
    ModelAndView cancelarPago(Model model){
        new ModelAndView('redirect:/pedido')
    }

    @PostMapping(value = '/pago/{nroTarjeta}')
    ModelAndView pagar(Model model, @ModelAttribute PedidoMaterial pedidoMaterial,
                        @PathVariable('nroTarjeta') String nroTarjeta,
                        RedirectAttributes flash){

        def comprador = alumnoRepository.findById(Sesion.alumno.getDNI())?.get()
        def pedido = compradorService.getPedido(Sesion.alumno)
        def tarjeta = comprador.getTarjetaRegistrada(nroTarjeta)

        pedido.pagoConCreditos = pedidoMaterial.pagoConCreditos ? true : false

        try{
            compradorService.pagar(pedido,tarjeta)
            flash.addFlashAttribute("success", "El pago se realizó con éxito! " +
                    "Podés acceder a tu material de estudio desde 'Mi biblioteca'.")
            new ModelAndView("redirect:/tienda")
        }catch(RuntimeException ex){
            flash.addFlashAttribute("error", "Ocurrió un error al intentar realizar el pago. " +
                    "Si el problema persiste, por favor contactate con un administrador del sitio.")
            new ModelAndView('redirect:/completarDatosPago')
        }

    }


}
