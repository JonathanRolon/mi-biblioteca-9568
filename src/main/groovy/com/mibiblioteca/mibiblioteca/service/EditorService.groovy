package com.mibiblioteca.mibiblioteca.service

import com.mibiblioteca.mibiblioteca.model.Editor
import groovy.transform.CompileStatic

@CompileStatic
interface EditorService {

    List<Editor> findAll()

    Optional<Editor> findById(Long dni)

    Editor create(Editor editor)

    Editor update(Editor editor)

    void deleteById(Long dni)

}