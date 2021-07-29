package com.mibiblioteca.mibiblioteca.tareas.controller

import com.mibiblioteca.mibiblioteca.principal.service.Sesion
import com.mibiblioteca.mibiblioteca.tareas.model.AsignacionTareaAlumno
import com.mibiblioteca.mibiblioteca.tareas.repository.AlumnoRepository
import com.mibiblioteca.mibiblioteca.tareas.repository.DocenteRepository
import com.mibiblioteca.mibiblioteca.tareas.service.ManejadorTareasService
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@CompileStatic
@RequestMapping('/tareas')
class TareasController {

    @Autowired
    private final ManejadorTareasService manejadorTareasService
    @Autowired
    private final AlumnoRepository alumnoRepository
    @Autowired
    private final DocenteRepository docenteRepository

    private void configModelDocente(Model model, Long idTarea) {
        def docente = docenteRepository.findById(Sesion.docente.getDNI())?.get()
        def tarea = docente.getTarea(idTarea)
        model.addAttribute("docente", docente)
        model.addAttribute("tarea", tarea)
        model.addAttribute("tipoUsuario", (Sesion.tipoUsuario).toString())
        model.addAttribute("asignacionAux", new AsignacionTareaAlumno())
    }

    private void configModelAlumno(Model model, Long idTarea) {
        def tareaAsignada = manejadorTareasService.getAsignacionTarea(Sesion.alumno.getDNI(), idTarea)
        def consignaTarea = manejadorTareasService.getConsignaTarea(idTarea)
        def alumno = alumnoRepository.findById(Sesion.alumno.getDNI())?.get()
        model.addAttribute("consigna", consignaTarea)
        model.addAttribute("alumno", alumno)
        model.addAttribute("tipoUsuario", (Sesion.tipoUsuario).toString())
        model.addAttribute("asignacion", tareaAsignada)
    }

    @RequestMapping(method = RequestMethod.GET)
    ModelAndView showAll(Model model) {
        def docente, alumno, tareas

        if (Sesion.tipoUsuario.toString() == "ALUMNO") {
            alumno = alumnoRepository.findById(Sesion.alumno.getDNI())?.get()
            tareas = manejadorTareasService.getAsignacionesTareas(alumno)
            model.addAttribute("alumno", alumno)
        } else { //docente
            docente = docenteRepository.findById(Sesion.docente.getDNI())?.get()
            model.addAttribute("docente", docente)
        }
        model.addAttribute("tipoUsuario", (Sesion.tipoUsuario).toString())

        new ModelAndView('views/tarea/tareas', [tareas: tareas])
    }

    @RequestMapping(value = '/{id}', method = RequestMethod.GET)
    ModelAndView findById(@PathVariable('id') Long id, Model model) {
        def asignaciones = manejadorTareasService.getAsignacionesTareas(id)

        configModelDocente(model, id)
        new ModelAndView('views/tarea/tarea', [asignaciones: asignaciones])
    }

    @RequestMapping(value = '/cerrarTarea/{nroTarea}/{dniAsignado}')
    ModelAndView cerrarAsignacionTarea(@PathVariable('nroTarea') Long nroTarea,
                                       @PathVariable('dniAsignado') Long dniAsignado,
                                       Model model, RedirectAttributes flash) {
        try{
            manejadorTareasService.cerrarAsignacionTarea(nroTarea, dniAsignado)
            configModelDocente(model, nroTarea)
            flash.addFlashAttribute("infoMsg","La tarea ha sido cerrada correcamente!")
        }catch(RuntimeException ex){
            flash.addFlashAttribute("error","Error al intentar cerrar la tarea del alumno!")
        }
        def asignaciones = manejadorTareasService.getAsignacionesTareas(nroTarea)
        new ModelAndView('views/tarea/tarea', [asignaciones: asignaciones])
    }

    @PostMapping(value = '/calificar/{nroTarea}/{dniAsignado}')
    ModelAndView calificarAsignacion(@PathVariable('nroTarea') Long nroTarea,
                                     @PathVariable('dniAsignado') Long dniAsignado,
                                     @ModelAttribute('asignacionAux') AsignacionTareaAlumno asignacion,
                                     Model model, RedirectAttributes flash) {
        try{
            manejadorTareasService.calificar(nroTarea, dniAsignado, asignacion.getCalificacion())
            configModelDocente(model, nroTarea)
            flash.addFlashAttribute("success","Tarea calificada correctamente!")
        }catch(RuntimeException ex){
            flash.addFlashAttribute("error","Error al intentar calificar la tarea del alumno!")
        }
        def asignaciones = manejadorTareasService.getAsignacionesTareas(nroTarea)
        new ModelAndView('views/tarea/tarea', [asignaciones: asignaciones])
    }

    @GetMapping(value = '/verTareaAsignada/{nroTarea}')
    ModelAndView verTareaAsignada(@PathVariable('nroTarea') Long nroTarea,
                                  Model model) {
        configModelAlumno(model,nroTarea)
        new ModelAndView('views/tarea/tareaAsignada')
    }

    @GetMapping(value = '/verTareaAsignada/{nroTarea}/{dniAlumno}')
    ModelAndView verTareaAlumno(@PathVariable('nroTarea') Long nroTarea,
                                @PathVariable('dniAlumno') Long dniAlumno,
                                Model model) {
        def tareaAsignada = manejadorTareasService.getAsignacionTarea(dniAlumno, nroTarea)
        def consignaTarea = manejadorTareasService.getConsignaTarea(nroTarea)
        def docente = docenteRepository.findById(Sesion.docente.getDNI())?.get()
        model.addAttribute("consigna", consignaTarea)
        model.addAttribute("asignacion", tareaAsignada)
        model.addAttribute("docente", docente)
        model.addAttribute("tipoUsuario", (Sesion.tipoUsuario).toString())
        new ModelAndView('views/tarea/tareaAsignada')
    }

    @PostMapping(value = '/resolver/{nroTarea}')
    resolverTarea(Model model, @PathVariable('nroTarea') Long nroTarea,
                  @ModelAttribute('asignacion') AsignacionTareaAlumno asignacion,
                    RedirectAttributes flash) {
        try{
            manejadorTareasService.resolver(nroTarea, Sesion.alumno.getDNI(), asignacion.respuesta)
            flash.addFlashAttribute("infoMsg","La tarea ha sido entregada, " +
                    "a la espera de calificación del docente.")
        }catch(RuntimeException ex){
            flash.addFlashAttribute("error","Error al intentar resolver la tarea!")
        }

        new ModelAndView("redirect:/tareas")
    }

}
