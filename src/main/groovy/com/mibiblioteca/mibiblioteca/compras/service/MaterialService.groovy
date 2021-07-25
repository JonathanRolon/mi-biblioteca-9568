package com.mibiblioteca.mibiblioteca.compras.service

import com.mibiblioteca.mibiblioteca.compras.model.Material
import com.mibiblioteca.mibiblioteca.compras.model.MaterialVigencia
import com.mibiblioteca.mibiblioteca.compras.model.TipoMaterial
import com.mibiblioteca.mibiblioteca.consultas.model.TemaHilo
import com.mibiblioteca.mibiblioteca.tareas.model.Alumno
import groovy.transform.CompileStatic
import org.springframework.web.multipart.MultipartFile

import java.sql.Timestamp

@CompileStatic
interface MaterialService {

    Material crear(String idMaterial, BigDecimal precio, String descripcion, String titulo, String autor,
                    Timestamp fechaPublicacion, String editorial, TipoMaterial tipoMaterial, TemaHilo tema, MaterialVigencia vigencia, MultipartFile contenido)

    void deleteById(String idMaterial)

    Optional<Material> findById(String idMaterial)

    List<Material> findAll()

    List<Material> getMaterialDe(Alumno alumno)

}
