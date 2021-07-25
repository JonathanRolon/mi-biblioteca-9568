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
class CatalogoController {

    @Autowired
    private final CompradorService compradorService

    @Autowired
    private final MaterialService materialService

    @Autowired
    private AlumnoRepository alumnoRepository

    @GetMapping('/tienda')
    ModelAndView showAll(Model model) {
        def pedido = compradorService.getPedido(Sesion.alumno)
        def alumno = alumnoRepository.findById(Sesion.alumno.getDNI())?.get()
        model.addAttribute("pedido", pedido)
        model.addAttribute("alumno", alumno)
        model.addAttribute("tipoUsuario", (Sesion.tipoUsuario).toString())
        new ModelAndView('views/tienda/catalogo', [catalogo: materialService.findAll()])
    }

    @GetMapping(value = '/pedido')
    ModelAndView showPedido(Model model){
        def pedido = compradorService.getPedido(Sesion.alumno)
        def alumno = alumnoRepository.findById(Sesion.alumno.getDNI())?.get()
        model.addAttribute("pedido", pedido)
        model.addAttribute("alumno", alumno)
        model.addAttribute("tipoUsuario", (Sesion.tipoUsuario).toString())
        new ModelAndView('views/tienda/pedido')
    }

    @PostMapping(value = '/pedirArticulo/{id}')
    ModelAndView solicitarArticulo(Model model,
                                   @PathVariable('id') String ISBN){
        def pedido = compradorService.getPedido(Sesion.alumno)
        def material = materialService.findById(ISBN)?.get()
        def alumno = alumnoRepository.findById(Sesion.alumno.getDNI())?.get()
        compradorService.agregarArticulo(pedido, material)
        model.addAttribute("pedido", pedido)
        model.addAttribute("alumno", alumno)
        model.addAttribute("tipoUsuario", (Sesion.tipoUsuario).toString())
        new ModelAndView('views/tienda/catalogo', [catalogo: materialService.findAll()])
    }

    @PostMapping(value = '/cancelarArticulo/{id}')
    ModelAndView cancelarArticulo(Model model,
                                   @PathVariable('id') String ISBN){
        def pedido = compradorService.getPedido(Sesion.alumno)
        def material = materialService.findById(ISBN)?.get()
        def alumno = alumnoRepository.findById(Sesion.alumno.getDNI())?.get()
        compradorService.eliminarArticulo(pedido, material)
        model.addAttribute("pedido", pedido)
        model.addAttribute("alumno", alumno)
        model.addAttribute("tipoUsuario", (Sesion.tipoUsuario).toString())
        new ModelAndView('redirect:/pedido')
    }

    @PostMapping(value = '/cancelarPedido')
    ModelAndView cancelarPedido(Model model){
        def pedido = compradorService.getPedido(Sesion.alumno)
        def alumno = alumnoRepository.findById(Sesion.alumno.getDNI())?.get()
        compradorService.cancelarPedido(pedido)
        model.addAttribute("pedido", pedido)
        model.addAttribute("alumno", alumno)
        model.addAttribute("tipoUsuario", (Sesion.tipoUsuario).toString())
        new ModelAndView('redirect:/tienda')
    }

    @GetMapping(value = '/completarDatosPago')
    String completarDatosPago(Model model){
        def comprador = alumnoRepository.findById(Sesion.alumno.getDNI())?.get()
        def pedido = compradorService.getPedido(Sesion.alumno)
        model.addAttribute("pedido", pedido)
        model.addAttribute("alumno", comprador)
        model.addAttribute("tipoUsuario", (Sesion.tipoUsuario).toString())
        'views/tienda/pago'
    }

    @GetMapping(value = '/cancelarPago')
    ModelAndView cancelarPago(Model model){
        new ModelAndView('redirect:/pedido')
    }

    @PostMapping(value = '/pago/{nroTarjeta}')
    ModelAndView pagar(Model model, @ModelAttribute PedidoMaterial pedidoMaterial,
                        @PathVariable('nroTarjeta') Long nroTarjeta,
                        RedirectAttributes flash){
        def comprador = alumnoRepository.findById(Sesion.alumno.getDNI())?.get()
        def pedido = compradorService.getPedido(Sesion.alumno)
        def tarjeta = comprador.getTarjetaRegistrada(nroTarjeta)

        pedido.pagoConCreditos = pedidoMaterial.pagoConCreditos ? pedidoMaterial.pagoConCreditos : false

        try{
            compradorService.pagar(pedido,tarjeta)
            flash.addFlashAttribute("success", "El pago se realizó con éxito! " +
                    "Podés acceder a tu material de estudio desde 'Mi biblioteca'.")
            new ModelAndView("redirect:/tienda")
        }catch(RuntimeException ex){
            flash.addFlashAttribute("error", "Ocurrió un error al intentar realizar el pago. " +
                    "Si el problema persiste, por favor contactate con un administrador del sitio.")
            model.addAttribute("pedido", pedido)
            model.addAttribute("alumno", comprador)
            model.addAttribute("tipoUsuario", (Sesion.tipoUsuario).toString())
            new ModelAndView('redirect:/completarDatosPago')
        }

    }


}
