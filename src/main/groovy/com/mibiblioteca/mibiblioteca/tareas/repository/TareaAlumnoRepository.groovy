package com.mibiblioteca.mibiblioteca.tareas.repository

import com.mibiblioteca.mibiblioteca.tareas.model.AsignacionTareaAlumno
import com.mibiblioteca.mibiblioteca.tareas.model.TareaAlumnoIdentity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TareaAlumnoRepository extends JpaRepository<AsignacionTareaAlumno, TareaAlumnoIdentity> {

    List<AsignacionTareaAlumno> findAll()

    AsignacionTareaAlumno save(AsignacionTareaAlumno asignacionTareaAlumno)

    Optional<AsignacionTareaAlumno> findById(TareaAlumnoIdentity id)

    void deleteById(TareaAlumnoIdentity id)

    void deleteAll()

}
