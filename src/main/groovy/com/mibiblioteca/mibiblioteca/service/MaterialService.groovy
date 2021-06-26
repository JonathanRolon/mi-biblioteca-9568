package com.mibiblioteca.mibiblioteca.service

import com.mibiblioteca.mibiblioteca.model.Material
import com.mibiblioteca.mibiblioteca.model.Material
import groovy.transform.CompileStatic

@CompileStatic
interface MaterialService {

    List<Material> findAll()

    Optional<Material> findById(String id)

    Material create(Material material)

    Material update(Material material)

    void deleteById(String id)

    void visualizar(Material material)

}
