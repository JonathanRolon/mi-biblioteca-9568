package com.mibiblioteca.mibiblioteca.compras.service.Impl

import com.mibiblioteca.mibiblioteca.compras.model.ContenidoMaterial
import com.mibiblioteca.mibiblioteca.compras.model.Material
import com.mibiblioteca.mibiblioteca.compras.model.MaterialVigencia
import com.mibiblioteca.mibiblioteca.compras.model.TipoMaterial
import com.mibiblioteca.mibiblioteca.compras.model.exception.FileStorageException
import com.mibiblioteca.mibiblioteca.compras.repository.MaterialRepository
import com.mibiblioteca.mibiblioteca.compras.service.MaterialService
import com.mibiblioteca.mibiblioteca.consultas.model.TemaHilo
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile

import java.sql.Timestamp

@Service
@CompileStatic
class MaterialServiceImpl implements MaterialService {

    @Autowired
    private MaterialRepository materialRepository

    MaterialServiceImpl(MaterialRepository rep) {
        materialRepository = rep
    }

    @Override
    Material crear(String idMaterial, Double precio, String descripcion, String titulo,
                   String autor, Timestamp fechaPublicacion, String editorial,
                   TipoMaterial tipoMaterial, TemaHilo tema, MaterialVigencia vigencia,
                   MultipartFile archivo) {

        def contenidoMaterial, material
        Optional<Material> mat = this.findById(idMaterial)

        // Normalizar nombre de archivo
        String fileName = StringUtils.cleanPath(archivo.getOriginalFilename())
        try {
            // Chequear si el nombre de archivo tiene caracteres invalidos
            if (fileName.contains("..")) {
                throw new FileStorageException("El archivo contiene una ruta invalida: " + fileName)
            }
            contenidoMaterial = new ContenidoMaterial(fileName, archivo.getContentType(),
                                                    archivo.getBytes(), archivo.getSize())
            if (!mat) {
                material =  new Material(idMaterial, precio, descripcion, titulo, autor,
                        fechaPublicacion, editorial, tipoMaterial, tema, vigencia, contenidoMaterial)
            }
            materialRepository.save(material)
            material
        } catch (RuntimeException ex) {
            throw new FileStorageException("No se pudo cargar el archivo: " + fileName + ". Intentalo de nuevo!");
        }

    }

    @Override
    Material update(Material material) {
        Material mat = this.findById(material.getIdMaterial())?.get()
        mat.setAutor(material.getAutor())
        mat.setDescripcion(material.getDescripcion())
        mat.setEditorial(material.getEditorial())
        mat.setFechaPublicacion(material.getFechaPublicacion())
        mat.setTema(material.getTema())
        mat.setTipoMaterial(material.getTipoMaterial())
        mat.setTitulo(material.getTitulo())
        mat.setVigencia(material.getVigencia())
        materialRepository.save(mat)
    }

    @Override
    void deleteById(String idMaterial) {
        materialRepository.deleteById(idMaterial)
    }

    @Override
    Optional<Material> findById(String idMaterial) {
        materialRepository.findById(idMaterial)
    }

    @Override
    List<Material> findAll() {
        materialRepository.findAll()
    }
}
