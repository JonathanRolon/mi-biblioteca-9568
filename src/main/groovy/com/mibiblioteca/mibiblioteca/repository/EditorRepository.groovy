package com.mibiblioteca.mibiblioteca.repository

import com.mibiblioteca.mibiblioteca.model.Alumno
import com.mibiblioteca.mibiblioteca.model.Editor
import groovy.transform.CompileStatic
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@CompileStatic
@Repository
interface EditorRepository  extends JpaRepository<Editor, Long> {

    List<Editor> findAll()

    Optional<Editor> findById(Long DNI)

    Editor save(Editor editor)

    void deleteById(Long dni)

    void deleteAll()

}