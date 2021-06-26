package com.mibiblioteca.mibiblioteca.repository

import com.mibiblioteca.mibiblioteca.model.Tarea
import groovy.transform.CompileStatic
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@CompileStatic
@Repository
interface TareaRepository extends JpaRepository<Tarea, Long> {

    List<Tarea> findAll()

    Optional<Tarea> findById(Long id)

    Tarea save(Tarea tarea)

    void deleteById(Long idTarea)

    void deleteAll()

}