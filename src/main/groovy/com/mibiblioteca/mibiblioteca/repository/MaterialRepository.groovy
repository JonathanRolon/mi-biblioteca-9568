package com.mibiblioteca.mibiblioteca.repository

import com.mibiblioteca.mibiblioteca.domain.Alumno
import com.mibiblioteca.mibiblioteca.domain.Material
import com.mibiblioteca.mibiblioteca.domain.Tarea
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

}
