package com.mibiblioteca.mibiblioteca.compras

import com.mibiblioteca.mibiblioteca.compras.model.ArticuloMaterial
import com.mibiblioteca.mibiblioteca.compras.model.ContenidoMaterial
import com.mibiblioteca.mibiblioteca.compras.model.EstadoArticulo
import com.mibiblioteca.mibiblioteca.compras.model.Material
import com.mibiblioteca.mibiblioteca.compras.model.MaterialVigencia
import com.mibiblioteca.mibiblioteca.compras.model.PedidoMaterial
import com.mibiblioteca.mibiblioteca.compras.model.TipoMaterial
import com.mibiblioteca.mibiblioteca.compras.repository.CuentaColegioRepository
import com.mibiblioteca.mibiblioteca.compras.repository.MaterialRepository
import com.mibiblioteca.mibiblioteca.compras.repository.PedidoMaterialRepository
import com.mibiblioteca.mibiblioteca.compras.repository.TarjetaRepository
import com.mibiblioteca.mibiblioteca.compras.service.CompradorService
import com.mibiblioteca.mibiblioteca.compras.service.Impl.CompradorServiceImpl
import com.mibiblioteca.mibiblioteca.compras.service.Impl.MaterialServiceImpl
import com.mibiblioteca.mibiblioteca.compras.service.MaterialService
import com.mibiblioteca.mibiblioteca.consultas.model.TemaHilo
import com.mibiblioteca.mibiblioteca.tareas.model.Alumno
import com.mibiblioteca.mibiblioteca.tareas.model.Curso
import com.mibiblioteca.mibiblioteca.tareas.repository.AlumnoRepository
import com.mibiblioteca.mibiblioteca.tareas.service.AlumnoService
import com.mibiblioteca.mibiblioteca.tareas.service.Impl.AlumnoServiceImpl
import groovy.transform.CompileStatic
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.multipart.MultipartFile

import javax.transaction.Transactional
import java.sql.Timestamp
import java.util.concurrent.ThreadLocalRandom

@Transactional
@CompileStatic
@SpringBootTest
class CompradorServiceTest {

    /* repositorios */
    @Autowired
    private final AlumnoRepository alumnoRepository
    @Autowired
    private final CuentaColegioRepository cuentaColegioRepository
    @Autowired
    private final TarjetaRepository tarjetaRepository
    @Autowired
    private final MaterialRepository materialRepository
    @Autowired
    private final PedidoMaterialRepository pedidoMaterialRepository

    /* servicios */
    @Autowired
    private CompradorService compradorService
    @Autowired
    private AlumnoService alumnoService
    @Autowired
    private MaterialService materialService

    /* data */

    Alumno novato, medio, pro
    Curso curso
    Timestamp fecNac
    Material materialTipo
    Timestamp fechaPublicacion
    MockMultipartFile libro

    /* útiles */
    @BeforeEach
    void setup(){
        //se inyecta de esta manera porque el repository arroja null pointer exc.
        compradorService = new CompradorServiceImpl(alumnoRepository, pedidoMaterialRepository, tarjetaRepository)
        alumnoService = new AlumnoServiceImpl(alumnoRepository)
        materialService = new MaterialServiceImpl(materialRepository)
        curso =  new Curso("A")
        fecNac = new Timestamp(System.currentTimeMillis())
        fechaPublicacion = new Timestamp(System.currentTimeMillis())
        libro = new MockMultipartFile("filearchivo", "orig", "pdf", "contenido".getBytes())
        materialTipo = materialService.crear("ISBN56151", 500.0 as Double,
                "Libro de literatura","Los pepitos",
                "El pepe",fechaPublicacion,"editorial", TipoMaterial.LIBRO,
                TemaHilo.CS_LENGUAJE, MaterialVigencia.VIGENTE,libro )

    }

    @AfterEach
    void teardown() {

        cuentaColegioRepository.deleteAll()
        tarjetaRepository.deleteAll()
        materialRepository.deleteAll()
        alumnoRepository.deleteAll()
        pedidoMaterialRepository.deleteAll()
        libro = null
        curso = null
        fecNac = null
        fechaPublicacion = null

    }

    Long generarDNIAleatorio() {
        ThreadLocalRandom.current().nextInt(6000000, 99999998 + 1);
    }

    Alumno getAlumnoNovato(){
        def alumno
        while(!alumno){
            alumno = alumnoService.create(generarDNIAleatorio(), "nombre", "apellido", fecNac, curso)
        }
        alumno
    }

    Alumno getAlumnoMedio(){
        def alumno
        while(!alumno){
            alumno = alumnoService.create(generarDNIAleatorio(), "nombre", "apellido", fecNac, curso)
        }
        alumno.subirNivel()
    }

    Alumno getAlumnoPRO(){
        def alumno
        while(!alumno){
            alumno = alumnoService.create(generarDNIAleatorio(), "nombre", "apellido", fecNac, curso)
        }
        alumno.subirNivel()
        alumno.subirNivel()
        alumno
    }

    /* Tests */
    @Test
    void dadoQueSoyAlumnoRegularNovatoGuardoLibroEnPedidoQuedaPendientePago(){

        def novato = getAlumnoNovato()
        def pedido = compradorService.crearPedido(novato)
        def articuloMaterial = pedido.agregar(materialTipo)
        assert(articuloMaterial.getEstado() === EstadoArticulo.PENDIENTE_PAGO)

    }

    @Test
    void dadoQueTengoUnLibroEnPedidoCuandoLoEliminoElPedidoQuedaVacio(){}

    @Test
    void dadoQueTengoMasDeUnLibroEnPedidoCuandoCanceloPedidoQuedaVacio(){}

    @Test
    void dadoQueNovatoYTarjetaValidaYMenosDeDosLibrosEnBibliotecaYNoPagoConCredCuandoPagoEntoncesLibroY100Cred(){

    }

    @Test
    void dadoQueNovatoYTarjetaValidaYDosLibrosEnBibliotecaYNoPagoConCredCuandoPagoEntoncesLibroY100CredYSuboNivel(){

    }

    @Test
    void dadoQueAlumnoMedioCuandoAgregoLibroEntoncesLibroPendientePagoEnPedido(){}

    @Test
    void dadoQueAlumnoMedio4LibrosCompradosConQuintoPendienteConTarjetaValida400CreditosAlPagarMasLibroMas100CredMasSubaNivel(){

    }

    @Test
    void dadoQueAlumnoMedioMenosDe4LibrosCompradosConUnoPendienteConTarjetaValida400CreditosAlPagarMasLibroMas100Cred(){

    }

}
