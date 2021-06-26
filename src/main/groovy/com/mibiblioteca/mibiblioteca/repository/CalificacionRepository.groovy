package com.mibiblioteca.mibiblioteca.repository

import com.mibiblioteca.mibiblioteca.model.Calificacion
import com.mibiblioteca.mibiblioteca.model.CalificacionIdentity
import groovy.transform.CompileStatic
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@CompileStatic
@Repository
interface CalificacionRepository extends JpaRepository<Calificacion, CalificacionIdentity> {

    List<Calificacion> findAll()

    Calificacion save(Calificacion calificacion)

    Optional<Calificacion> findById(CalificacionIdentity calificacionIdentity)

    void deleteById(CalificacionIdentity calificacionIdentity)

    void deleteAll()

}
