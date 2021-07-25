package com.mibiblioteca.mibiblioteca.consultas.controller

import com.mibiblioteca.mibiblioteca.consultas.model.Calificacion
import com.mibiblioteca.mibiblioteca.consultas.model.Hilo
import com.mibiblioteca.mibiblioteca.consultas.model.Respuesta
import com.mibiblioteca.mibiblioteca.consultas.model.RespuestaIdentity
import com.mibiblioteca.mibiblioteca.consultas.service.PublicadorService
import com.mibiblioteca.mibiblioteca.principal.service.Sesion
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView

import javax.transaction.Transactional
import java.sql.Timestamp

@Controller
@CompileStatic
@RequestMapping('/hilos')
class HiloController {

    @Autowired
    private final PublicadorService publicadorService

    @RequestMapping(method = RequestMethod.GET)
    ModelAndView showAll(Model model) {
        model.addAttribute("hilo", new Hilo())
        model.addAttribute("alumno", Sesion.alumno)
        model.addAttribute("tipoUsuario", (Sesion.tipoUsuario).toString())
        new ModelAndView('views/hilo/hilos', [hilos: publicadorService.getForo()])
    }

    @Transactional
    @PostMapping("/add")
    ModelAndView add(Model model, @ModelAttribute('hilo') Hilo hilo) {
        try {
            Hilo nuevoHilo = publicadorService.crearHilo(Sesion.usuario, hilo.getTema(), hilo.getConsulta())
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
        model.addAttribute("calificacion", new Calificacion())
        model.addAttribute("usuario", Sesion.usuario)
        model.addAttribute("tipoUsuario", (Sesion.tipoUsuario).toString())
        model.addAttribute("alumno", Sesion.alumno)
        new ModelAndView('views/hilo/hilo', [hilo: hilo])
    }

    @Transactional
    @RequestMapping(value = '/{id}/responder', method = RequestMethod.POST)
    ModelAndView responderHilo(@PathVariable('id') Long id, @ModelAttribute('hilo') Hilo hilo,
                               @ModelAttribute('respuesta') Respuesta respuesta,
                               Model model) {
        try{
            def hiloSeleccionado = publicadorService.getHilo(id)
            publicadorService.responder(Sesion.usuario, hiloSeleccionado, respuesta.getContenido())
            model.addAttribute("tipoUsuario", (Sesion.tipoUsuario).toString())
            new ModelAndView("redirect:/hilos/" + hilo.getId())
        }catch (RuntimeException ex) {
            new ModelAndView('views/hilo/errorResponderHilo')
        }
    }

    @Transactional
    @RequestMapping(value = '/{id}/calificar/{publicador}/{fechaCreacion}', method = RequestMethod.POST)
    ModelAndView guardarCalificacionAlumno(@PathVariable('id') Long id, @PathVariable('publicador') Long publicadorResp,
                                           @PathVariable('fechaCreacion') Timestamp fechaCreacionResp,
                                           @ModelAttribute('calificacion') Calificacion calificacion, Model model) {
        try{
            def hilo = publicadorService.getHilo(id)
            def respuesta = hilo.getRespuesta(fechaCreacionResp, publicadorResp)
            publicadorService.calificar(Sesion.alumno, respuesta , calificacion.getCalificacion())
            model.addAttribute("tipoUsuario", (Sesion.tipoUsuario).toString())
            new ModelAndView("redirect:/hilos/" + hilo.getId())
        }catch (RuntimeException ex) {
            new ModelAndView('views/hilo/errorCalificarRta')
        }
    }

}
