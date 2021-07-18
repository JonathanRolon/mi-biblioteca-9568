package com.mibiblioteca.mibiblioteca.consultas.controller

import com.mibiblioteca.mibiblioteca.consultas.model.Hilo
import com.mibiblioteca.mibiblioteca.consultas.model.Respuesta
import com.mibiblioteca.mibiblioteca.consultas.service.PublicadorService
import com.mibiblioteca.mibiblioteca.principal.service.LoginService
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.AutowireCapableBeanFactory
import org.springframework.beans.factory.support.AutowireCandidateQualifier
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView

import javax.transaction.Transactional

@Controller
@CompileStatic
@RequestMapping('/hilos')
class HiloController {

    @Autowired
    private final PublicadorService publicadorService

    @RequestMapping(method = RequestMethod.GET)
    ModelAndView showAll(Model model) {
        model.addAttribute("hilo", new Hilo())
        new ModelAndView('views/hilo/hilos', [hilos: publicadorService.getForo()])
    }

    @Transactional
    @PostMapping("/add")
    ModelAndView add(Model model, @ModelAttribute('hilo') Hilo hilo) {
        try {
            def logueado = LoginService.getUsuarioLogueado()
            Hilo nuevoHilo = publicadorService.crearHilo(logueado, hilo.getTema(), hilo.getConsulta())
            findById(nuevoHilo.getId(), model)
        } catch (RuntimeException ex) {
            new ModelAndView('views/hilo/errorCreacionHilo')
        }
    }

    @RequestMapping(value = '/{id}', method = RequestMethod.GET)
    ModelAndView findById(@PathVariable('id') Long id, Model model) {
        def hilo = publicadorService.getHilo(id)
        model.addAttribute("hilo", hilo)
        model.addAttribute("respuesta", new Respuesta())
        new ModelAndView('views/hilo/hilo', [hilo: hilo])
    }

    @Transactional
    @RequestMapping(value = '/{id}/responder', method = RequestMethod.POST)
    ModelAndView responderHilo(@PathVariable('id') Long id, @ModelAttribute('hilo') Hilo hilo,
                               @ModelAttribute('respuesta') Respuesta respuesta, Model model) {
        try{
            def logueado = LoginService.getUsuarioLogueado()
            def hiloSeleccionado = publicadorService.getHilo(id)
            publicadorService.responder(logueado, hiloSeleccionado, respuesta.getContenido())
            findById(hilo.getId(), model)
        }catch (RuntimeException ex) {
            new ModelAndView('views/hilo/errorResponderHilo')
        }


    }

}
