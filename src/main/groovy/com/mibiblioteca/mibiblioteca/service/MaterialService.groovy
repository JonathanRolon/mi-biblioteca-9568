package com.mibiblioteca.mibiblioteca.service

import com.mibiblioteca.mibiblioteca.domain.Material
import com.mibiblioteca.mibiblioteca.domain.Material
import groovy.transform.CompileStatic

@CompileStatic
interface MaterialService {

    List<Material> findAll()

    Optional<Material> findById(String id)

    Material create(Material material)

    Material update(Material material)

    void deleteById(String id)

    void prestar(Material m, LectorService l)

    void visualizar(Material material)

}
