package com.mibiblioteca.mibiblioteca.consultas.controller

import com.mibiblioteca.mibiblioteca.consultas.model.Calificacion
import com.mibiblioteca.mibiblioteca.consultas.model.Hilo
import com.mibiblioteca.mibiblioteca.consultas.model.Respuesta
import com.mibiblioteca.mibiblioteca.consultas.service.PublicadorService
import com.mibiblioteca.mibiblioteca.principal.service.Sesion
import com.mibiblioteca.mibiblioteca.tareas.repository.AlumnoRepository
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes

import javax.transaction.Transactional
import java.sql.Timestamp

@Controller
@CompileStatic
@RequestMapping('/hilos')
class ConsultasController {

    @Autowired
    private final PublicadorService publicadorService
    @Autowired
    private final AlumnoRepository alumnoRepository

    @RequestMapping(method = RequestMethod.GET)
    ModelAndView showAll(Model model) {
        def alumno = alumnoRepository.findById(Sesion.alumno.getDNI())?.get()
        model.addAttribute("hilo", new Hilo())
        model.addAttribute("alumno", alumno)
        model.addAttribute("tipoUsuario", (Sesion.tipoUsuario).toString())
        new ModelAndView('views/hilo/hilos', [hilos: publicadorService.getForo()])
    }

    @Transactional
    @PostMapping("/add")
    ModelAndView add(Model model, @ModelAttribute('hilo') Hilo hilo,
                    RedirectAttributes flash) {
        try {
            Hilo nuevoHilo = publicadorService.crearHilo(Sesion.usuario, hilo.getTema(), hilo.getConsulta())
            flash.addFlashAttribute("infoMsg","Creaste una nueva consulta!")
            findById(nuevoHilo.getId(), model)
        } catch (RuntimeException ex) {
            flash.addFlashAttribute("error","Error al intentar agregar una nueva consulta!")
            new ModelAndView('redirect:/hilos')
        }
    }

    @RequestMapping(value = '/{id}', method = RequestMethod.GET)
    ModelAndView findById(@PathVariable('id') Long id, Model model) {
        def hilo = publicadorService.getHilo(id)
        def alumno = alumnoRepository.findById(Sesion.alumno.getDNI())?.get()
        model.addAttribute("hilo", hilo)
        model.addAttribute("respuesta", new Respuesta())
        model.addAttribute("calificacion", new Calificacion())
        model.addAttribute("usuario", Sesion.usuario)
        model.addAttribute("tipoUsuario", (Sesion.tipoUsuario).toString())
        model.addAttribute("alumno", alumno)
        new ModelAndView('views/hilo/hilo', [hilo: hilo])
    }

    @Transactional
    @RequestMapping(value = '/{id}/responder', method = RequestMethod.POST)
    ModelAndView responderHilo(@PathVariable('id') Long id, @ModelAttribute('hilo') Hilo hilo,
                               @ModelAttribute('respuesta') Respuesta respuesta,
                               Model model, RedirectAttributes flash) {
        try{
            def hiloSeleccionado = publicadorService.getHilo(id)
            publicadorService.responder(Sesion.usuario, hiloSeleccionado, respuesta.getContenido())
            model.addAttribute("tipoUsuario", (Sesion.tipoUsuario).toString())
        }catch (RuntimeException ex) {
            flash.addFlashAttribute("error","Error al intentar responder la consulta!")
        }
        new ModelAndView("redirect:/hilos/" + hilo.getId())
    }

    @Transactional
    @RequestMapping(value = '/{id}/calificar/{publicador}/{fechaCreacion}', method = RequestMethod.POST)
    ModelAndView guardarCalificacionAlumno(@PathVariable('id') Long id,
                                           @PathVariable('publicador') Long publicadorResp,
                                           @PathVariable('fechaCreacion') Timestamp fechaCreacionResp,
                                           @ModelAttribute('calificacion') Calificacion calificacion,
                                           Model model, RedirectAttributes flash) {
        def hilo = publicadorService.getHilo(id)
        try{
            def respuesta = hilo.getRespuesta(fechaCreacionResp, publicadorResp)
            publicadorService.calificar(Sesion.alumno, respuesta , calificacion.getCalificacion())
            model.addAttribute("tipoUsuario", (Sesion.tipoUsuario).toString())
            flash.addFlashAttribute("infoMsg","Tu calificación se ha publicado!")
        }catch (RuntimeException ex) {
            flash.addFlashAttribute("error", "Error al intentar calificar la respuesta!")
        }
        new ModelAndView("redirect:/hilos/" + hilo.getId())
    }

}
