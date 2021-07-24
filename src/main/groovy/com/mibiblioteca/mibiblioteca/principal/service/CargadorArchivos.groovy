package com.mibiblioteca.mibiblioteca.principal.service

import com.mibiblioteca.mibiblioteca.compras.model.ContenidoMaterial
import com.mibiblioteca.mibiblioteca.compras.model.Material
import com.mibiblioteca.mibiblioteca.compras.model.MaterialVigencia
import com.mibiblioteca.mibiblioteca.compras.model.TipoMaterial
import com.mibiblioteca.mibiblioteca.compras.repository.MaterialRepository
import com.mibiblioteca.mibiblioteca.consultas.model.TemaHilo
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.sql.Timestamp
import java.time.LocalDateTime
import java.util.logging.Logger

@Service
@CompileStatic
class CargadorArchivos {

    @Autowired
    MaterialRepository materialRepository

    void leerArchivo(){

        Logger logger = Logger.getLogger("")

        File pdfFile = new File("C:\\Users\\Lenovo\\Downloads\\Libro3.pdf");
        byte[] pdfData = new byte[(int) pdfFile.length()];
        DataInputStream dis = new DataInputStream(new FileInputStream(pdfFile));
        dis.readFully(pdfData);  // read from file into byte[] array
        dis.close();

        def contenido1 = new ContenidoMaterial("Arte1","pdf",pdfData, pdfData.size())
        def material1 = new Material("ARTE13513515", 550 as BigDecimal,"Arte1","Arte para principiantes","Mandioca", Timestamp.valueOf(LocalDateTime.now()),"Estacion Mandioca", TipoMaterial.LIBRO, TemaHilo.ARTE, MaterialVigencia.VIGENTE,contenido1)
        materialRepository.save(material1)

/////////////////////////////////////////////////////////////////////////

        pdfFile = new File("C:\\Users\\Lenovo\\Downloads\\Libro4.pdf");
        pdfData = new byte[(int) pdfFile.length()];
        dis = new DataInputStream(new FileInputStream(pdfFile));
        dis.readFully(pdfData);  // read from file into byte[] array
        dis.close();

        def contenido2 = new ContenidoMaterial("LenguaYLiteratura2","pdf",pdfData, pdfData.size())
        def material2 = new Material("LYL213513515", 700 as BigDecimal,"LenguayLit2","Lengua y Literatura 2","Mandioca", Timestamp.valueOf(LocalDateTime.now()),"Estacion Mandioca", TipoMaterial.LIBRO, TemaHilo.CS_LENGUAJE, MaterialVigencia.VIGENTE,contenido2)
        materialRepository.save(material2)

/////////////////////////////////////////////////////////////////////////

        pdfFile = new File("C:\\Users\\Lenovo\\Downloads\\Libro5.pdf");
        pdfData = new byte[(int) pdfFile.length()];
        dis = new DataInputStream(new FileInputStream(pdfFile));
        dis.readFully(pdfData);  // read from file into byte[] array
        dis.close();

        def contenido3 = new ContenidoMaterial("LenguaYLiteratura3","pdf",pdfData, pdfData.size())
        def material3 = new Material("LYL333333333", 800 as BigDecimal,"Lengua y Literatura 3","Lengua y Literatura 3","Mandioca", Timestamp.valueOf(LocalDateTime.now()),"Estacion Mandioca", TipoMaterial.LIBRO, TemaHilo.CS_LENGUAJE, MaterialVigencia.VIGENTE,contenido3)
        materialRepository.save(material3)

/////////////////////////////////////////////////////////////////////////

        pdfFile = new File("C:\\Users\\Lenovo\\Downloads\\Libro6.pdf");
        pdfData = new byte[(int) pdfFile.length()];
        dis = new DataInputStream(new FileInputStream(pdfFile));
        dis.readFully(pdfData);  // read from file into byte[] array
        dis.close();

        def contenido4 = new ContenidoMaterial("Matematica4","pdf",pdfData, pdfData.size())
        def material4 = new Material("MAT351354488", 750 as BigDecimal,"Matematica4","Matematica 4","Pirineo", Timestamp.valueOf(LocalDateTime.now()),"Pirineo Editorial", TipoMaterial.LIBRO, TemaHilo.CS_FORMALES, MaterialVigencia.VIGENTE,contenido4)
        materialRepository.save(material4)

/////////////////////////////////////////////////////////////////////////

        pdfFile = new File("C:\\Users\\Lenovo\\Downloads\\Libro7.pdf");
        pdfData = new byte[(int) pdfFile.length()];
        dis = new DataInputStream(new FileInputStream(pdfFile));
        dis.readFully(pdfData);  // read from file into byte[] array
        dis.close();

        def contenido5 = new ContenidoMaterial("Matematica3","pdf",pdfData, pdfData.size())
        def material5 = new Material("MAT54613513", 700 as BigDecimal,"Matematica 3","Matematica 3","Pirineo", Timestamp.valueOf(LocalDateTime.now()),"Pirineo Editorial", TipoMaterial.LIBRO, TemaHilo.CS_LENGUAJE, MaterialVigencia.VIGENTE,contenido5)
        materialRepository.save(material5)

/////////////////////////////////////////////////////////////////////////

        pdfFile = new File("C:\\Users\\Lenovo\\Downloads\\Libro8.pdf");
        pdfData = new byte[(int) pdfFile.length()];
        dis = new DataInputStream(new FileInputStream(pdfFile));
        dis.readFully(pdfData);  // read from file into byte[] array
        dis.close();

        def contenido6 = new ContenidoMaterial("Fisica1","pdf",pdfData, pdfData.size())
        def material6 = new Material("FIS213513515", 730 as BigDecimal,"Fisica1","Fisica1","Pirineo", Timestamp.valueOf(LocalDateTime.now()),"Pirineo Editorial", TipoMaterial.LIBRO, TemaHilo.CS_LENGUAJE, MaterialVigencia.VIGENTE,contenido6)
        materialRepository.save(material6)

    }

    static void cargarArchivo(){

    }

}
