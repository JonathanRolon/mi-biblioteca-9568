package com.mibiblioteca.mibiblioteca.compras.repository


import com.mibiblioteca.mibiblioteca.compras.model.Material
import groovy.transform.CompileStatic
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@CompileStatic
@Repository
interface MaterialRepository extends JpaRepository<Material, String> {

    List<Material> findAll()

    Optional<Material> findById(String idMaterial)

    Material save(Material tarea)

    void deleteById(String idMaterial)

    void deleteAll()

}
