package com.mibiblioteca.mibiblioteca.repository

import com.mibiblioteca.mibiblioteca.model.Alumno
import com.mibiblioteca.mibiblioteca.model.Material
import com.mibiblioteca.mibiblioteca.model.Tarea
import groovy.transform.CompileStatic
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
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
