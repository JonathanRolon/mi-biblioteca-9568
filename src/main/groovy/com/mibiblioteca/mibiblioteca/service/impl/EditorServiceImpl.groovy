package com.mibiblioteca.mibiblioteca.service.impl

import com.mibiblioteca.mibiblioteca.model.Editor
import com.mibiblioteca.mibiblioteca.service.EditorService
import groovy.transform.CompileStatic
import org.springframework.stereotype.Service

@CompileStatic
@Service
class EditorServiceImpl implements EditorService{

    @Override
    List<Editor> findAll() {
        return null
    }

    @Override
    Optional<Editor> findById(Long dni) {
        return null
    }

    @Override
    Editor create(Editor editor) {
        return null
    }

    @Override
    Editor update(Editor editor) {
        return null
    }

    @Override
    void deleteById(Long dni) {

    }
}
