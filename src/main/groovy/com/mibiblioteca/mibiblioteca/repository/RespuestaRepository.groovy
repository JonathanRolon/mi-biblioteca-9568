package com.mibiblioteca.mibiblioteca.repository

import com.mibiblioteca.mibiblioteca.model.Editor
import com.mibiblioteca.mibiblioteca.model.Respuesta
import com.mibiblioteca.mibiblioteca.model.RespuestaIdentity
import groovy.transform.CompileStatic
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@CompileStatic
@Repository
interface RespuestaRepository extends JpaRepository<Respuesta, RespuestaIdentity> {

    List<Respuesta> findAll()

    Respuesta save(Respuesta respuesta)

    Optional<Respuesta> findById(RespuestaIdentity respuestaIdentityi)

    void deleteById(RespuestaIdentity respuestaIdentity)

    void deleteAll()
}
