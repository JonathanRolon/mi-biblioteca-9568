package com.mibiblioteca.mibiblioteca.principal.controller

import com.mibiblioteca.mibiblioteca.compras.service.MaterialService
import com.mibiblioteca.mibiblioteca.principal.service.Login
import com.mibiblioteca.mibiblioteca.principal.service.Sesion
import com.mibiblioteca.mibiblioteca.principal.service.TipoUsuario
import com.mibiblioteca.mibiblioteca.tareas.repository.AlumnoRepository
import com.mibiblioteca.mibiblioteca.tareas.repository.DocenteRepository
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.Banner
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

@Controller
@CompileStatic
class MiBibliotecaController {

    @Autowired
    private AlumnoRepository alumnoRepository
    @Autowired
    private DocenteRepository docenteRepository
    @Autowired
    private MaterialService materialService

    @RequestMapping("/")
    def home(Model model) {
        model.addAttribute("user", new Login())
        new ModelAndView("views/mibibliotecavirtual",
                [bootVersion  : Banner.package.implementationVersion,
                 groovyVersion: GroovySystem.version])
    }

    @PostMapping("/entrar")
    ModelAndView login(Model model, @ModelAttribute('user') Login login) {

        def logueado = alumnoRepository.findById(login.getUsuario())

        if(!logueado){
            logueado = docenteRepository.findById(login.getUsuario())
            model.addAttribute("tipo", "DOCENTE")
            Sesion.tipoUsuario = TipoUsuario.DOCENTE
            Sesion.docente = logueado?.get()
        }else{
            model.addAttribute("tipo", "ALUMNO")
            Sesion.tipoUsuario = TipoUsuario.ALUMNO
            Sesion.alumno = logueado.get()
        }

        Sesion.usuario = login.getUsuario()
        return new ModelAndView("redirect:/tareas")
    }

    @GetMapping("/biblioteca")
    ModelAndView misArticulos(Model model){
        def alumno = alumnoRepository.findById(Sesion.alumno.getDNI())?.get()
        model.addAttribute("alumno", alumno)
        model.addAttribute("tipoUsuario", (Sesion.tipoUsuario).toString())
        new ModelAndView("views/biblioteca/mibiblioteca", [mibiblioteca: materialService.getMaterialDe(alumno)])
    }
}
