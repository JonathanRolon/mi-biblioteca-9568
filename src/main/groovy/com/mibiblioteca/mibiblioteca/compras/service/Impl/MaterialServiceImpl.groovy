package com.mibiblioteca.mibiblioteca.compras.service.Impl

import com.mibiblioteca.mibiblioteca.compras.model.ContenidoMaterial
import com.mibiblioteca.mibiblioteca.compras.model.Material
import com.mibiblioteca.mibiblioteca.compras.model.MaterialVigencia
import com.mibiblioteca.mibiblioteca.compras.model.TipoMaterial
import com.mibiblioteca.mibiblioteca.compras.model.exception.FileStorageException
import com.mibiblioteca.mibiblioteca.compras.repository.MaterialRepository
import com.mibiblioteca.mibiblioteca.compras.service.MaterialService
import com.mibiblioteca.mibiblioteca.consultas.model.TemaHilo
import com.mibiblioteca.mibiblioteca.tareas.model.Alumno
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile

import java.sql.Timestamp

@Service
@CompileStatic
@Transactional
class MaterialServiceImpl implements MaterialService {

    @Autowired
    private MaterialRepository materialRepository

    @Override
    Material crear(String idMaterial, BigDecimal precio, String descripcion, String titulo,
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

        } catch (RuntimeException ex) {
            throw new FileStorageException("No se pudo cargar el archivo: " + fileName + ". Intentalo de nuevo!");
        }

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

    @Override
    List<Material> getMaterialDe(Alumno alumno){
        def lista = findAll()
        lista.findAll {materialDeEstudio ->
            alumno.getComprobantesPago().find {pago ->
                materialDeEstudio.getIdMaterial() == pago.getIdMaterial()
            }
        }
    }
}
