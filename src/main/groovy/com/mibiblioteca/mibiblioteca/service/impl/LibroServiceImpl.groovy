package com.mibiblioteca.mibiblioteca.service.impl

import com.mibiblioteca.mibiblioteca.domain.Material
import com.mibiblioteca.mibiblioteca.service.LectorService
import com.mibiblioteca.mibiblioteca.service.MaterialService
import groovy.transform.CompileStatic
import org.springframework.stereotype.Service

@CompileStatic
@Service
class LibroServiceImpl implements MaterialService{
    @Override
    List<Material> findAll() {
        return null
    }

    @Override
    Optional<Material> findById(String id) {
        return null
    }

    @Override
    Material create(Material material) {
        return null
    }

    @Override
    Material update(Material material) {
        return null
    }

    @Override
    void deleteById(String id) {

    }

    @Override
    void prestar(Material m, LectorService l) {

    }

    @Override
    void visualizar(Material material) {

    }
}
