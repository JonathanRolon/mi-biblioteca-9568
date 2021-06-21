package com.mibiblioteca.mibiblioteca.repository

import com.mibiblioteca.mibiblioteca.domain.Editor
import com.mibiblioteca.mibiblioteca.domain.Respuesta
import com.mibiblioteca.mibiblioteca.domain.RespuestaIdentity
import groovy.transform.CompileStatic
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@CompileStatic
@Repository
interface RespuestaRepository extends JpaRepository<Respuesta, RespuestaIdentity> {

    List<Respuesta> findAll()

    Editor save(Editor editor)

    Optional<Respuesta> findById(RespuestaIdentity respuestaIdentityi)

    void deleteById(RespuestaIdentity respuestaIdentity)
}
