package com.mibiblioteca.mibiblioteca.compras.integration

import com.mibiblioteca.mibiblioteca.compras.model.CuentaBancaria
import com.mibiblioteca.mibiblioteca.compras.model.EntidadBancaria
import com.mibiblioteca.mibiblioteca.compras.model.EstadoArticulo
import com.mibiblioteca.mibiblioteca.compras.model.Material
import com.mibiblioteca.mibiblioteca.compras.model.MaterialVigencia
import com.mibiblioteca.mibiblioteca.compras.model.PedidoMaterial
import com.mibiblioteca.mibiblioteca.compras.model.TarjetaDeCredito
import com.mibiblioteca.mibiblioteca.compras.model.TipoCuenta
import com.mibiblioteca.mibiblioteca.compras.model.TipoMaterial
import com.mibiblioteca.mibiblioteca.compras.repository.CuentaBancariaRepository
import com.mibiblioteca.mibiblioteca.compras.service.CBUColegioService
import com.mibiblioteca.mibiblioteca.compras.service.CompradorService
import com.mibiblioteca.mibiblioteca.compras.service.Impl.CBUColegioServiceImpl
import com.mibiblioteca.mibiblioteca.compras.service.MaterialService
import com.mibiblioteca.mibiblioteca.consultas.model.TemaHilo
import com.mibiblioteca.mibiblioteca.tareas.model.Alumno
import com.mibiblioteca.mibiblioteca.tareas.model.Curso
import com.mibiblioteca.mibiblioteca.tareas.service.AlumnoService
import groovy.transform.CompileStatic
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockMultipartFile

import javax.transaction.Transactional
import java.sql.Timestamp
import java.util.concurrent.ThreadLocalRandom

@Transactional
@CompileStatic
@SpringBootTest
class CompradorServiceTest {

    /* repositorios */
    @Autowired
    private final CuentaBancariaRepository cuentaBancariaRepository

    /* servicios */
    @Autowired
    private CompradorService compradorService
    @Autowired
    private AlumnoService alumnoService
    @Autowired
    private MaterialService materialService
    @Autowired
    private CBUColegioService cbuColegioService

    /* data */

    Alumno novato
    Curso curso
    Timestamp fecNac
    Material materialTipo
    Material materialTipo2
    Material materialTipo3
    Material materialTipo4
    Material materialTipo5
    Timestamp fechaPublicacion
    Timestamp fechaVto
    MockMultipartFile libro
    MockMultipartFile revista
    CuentaBancaria cuentaColegio
    CuentaBancaria cuentaAlumnoNovato

    /* útiles */

    private void setupServicios() {
        cbuColegioService = new CBUColegioServiceImpl()
    }

    private void setupData1() {
        curso = new Curso("A")
        fecNac = new Timestamp(System.currentTimeMillis())
        fechaPublicacion = new Timestamp(System.currentTimeMillis())
        fechaVto = new Timestamp(2025, 12, 25, 23, 59, 59, 59)
        libro = new MockMultipartFile("filearchivo", "orig", "pdf", "contenidolibro".getBytes())
        revista = new MockMultipartFile("revista", "Amazon", "pdf", "contenidorevista".getBytes())
    }

    private void setupData2() {
        materialTipo = materialService.crear("ISBN56151", 500.0 as BigDecimal,
                "Libro de literatura", "Los pepitos",
                "El pepe", fechaPublicacion, "editorial", TipoMaterial.LIBRO,
                TemaHilo.CS_LENGUAJE, MaterialVigencia.VIGENTE, libro)
        materialTipo2 = materialService.crear("DOI23431", 200.0 as BigDecimal,
                "Amazon web services", "Revista Magazine",
                "El pepe", fechaPublicacion, "editorial2", TipoMaterial.REVISTA,
                TemaHilo.TECNOLOGIA, MaterialVigencia.VIGENTE, revista)
        materialTipo3 = materialService.crear("ISBN654615", 300.0 as BigDecimal,
                "Libro sobre biologia animal", "Libro biologia y naturaleza",
                "El pepe", fechaPublicacion, "editorial3", TipoMaterial.LIBRO,
                TemaHilo.CS_BIOLOGICAS, MaterialVigencia.VIGENTE, libro)
        materialTipo4 = materialService.crear("ISBN6548888", 300.0 as BigDecimal,
                "Libro sobre biologia animal", "Libro biologia y naturaleza 2",
                "El pepe", fechaPublicacion, "editorial3", TipoMaterial.LIBRO,
                TemaHilo.CS_BIOLOGICAS, MaterialVigencia.VIGENTE, libro)
        materialTipo5 = materialService.crear("ISBN111111", 300.0 as BigDecimal,
                "Libro sobre biologia animal", "Libro biologia y naturaleza 3",
                "El pepe", fechaPublicacion, "editorial3", TipoMaterial.LIBRO,
                TemaHilo.CS_BIOLOGICAS, MaterialVigencia.VIGENTE, libro)
    }

    private void setupData3(){
        cuentaColegio = new CuentaBancaria(cbuColegioService.obtenerCBUCuentaBancaria(), TipoCuenta.CC, EntidadBancaria.BANCO_AZUL)
        cuentaAlumnoNovato = new CuentaBancaria(999999999978 as BigInteger, TipoCuenta.CRED, EntidadBancaria.BANCO_RIO)
        cuentaBancariaRepository.save(cuentaColegio)
        cuentaBancariaRepository.save(cuentaAlumnoNovato)
    }

    @BeforeEach
    void setup() {
        setupServicios()
        setupData1()
        setupData2()
        setupData3()
    }

    @AfterEach
    void teardown() {
        libro = null
        revista = null
        curso = null
        fecNac = null
        fechaVto = null
        fechaPublicacion = null
        cuentaAlumnoNovato = null
        cuentaColegio = null
        materialTipo4 = null
        materialTipo5 = null
    }

    Long generarDNIAleatorio() {
        ThreadLocalRandom.current().nextInt(6000000, 99999998 + 1);
    }

    Alumno getAlumnoNovato() {
        def alumno
        while (!alumno) {
            alumno = alumnoService.create(generarDNIAleatorio(), "nombre", "apellido", fecNac, curso.getDenominacion())
        }
        alumno
    }

    Alumno getAlumnoMedio() {
        def alumno
        while (!alumno) {
            alumno = alumnoService.create(generarDNIAleatorio(), "nombre", "apellido", fecNac, curso.getDenominacion())
        }
        alumno.subirNivel()
        alumno
    }

    private TarjetaDeCredito getTarjetaValida(Alumno alumno, CuentaBancaria cuentaAlumno) {
        def tarjetaValida = new TarjetaDeCredito(cuentaAlumno.getCBU(),
                '4566456645664656', 123,
                EntidadBancaria.BANCO_RIO, fechaVto)
        try {
            tarjetaValida.acreditar(5000.00)
        } catch (RuntimeException ex) {
            assert (false)
        }
        tarjetaValida
    }

    private void pagarPedido(PedidoMaterial pedido, TarjetaDeCredito tarjeta, Integer creditos) {
        try {
            def pagoConCreditos = creditos == 400
            pedido.pagoConCreditos = pagoConCreditos
            compradorService.pagar(pedido, tarjeta)
        } catch (RuntimeException ex) {
            assert (false)
        }
    }

    /* Tests */

    @Test
    void dadoQueSoyAlumnoRegularNovatoGuardoLibroEnPedidoQuedaPendientePago() {

        def novato = getAlumnoNovato()
        def pedido = compradorService.crearPedido(novato)
        def articuloMaterial = pedido.agregar(materialTipo,novato)
        assert (articuloMaterial.getEstado() === EstadoArticulo.PENDIENTE_PAGO)

    }

    @Test
    void dadoQueTengoUnLibroEnPedidoCuandoLoEliminoElPedidoQuedaVacio() {

        def novato = getAlumnoNovato()
        def pedido = compradorService.crearPedido(novato)
        def articuloMaterial = pedido.agregar(materialTipo,novato)

        assert (pedido.getArticulosSolicitados().size() === 1)
        pedido.cancelar()
        assert (pedido.getArticulosSolicitados().size() === 0)
    }

    @Test
    void dadoQueTengoMasDeUnLibroEnPedidoCuandoCanceloPedidoQuedaVacio() {
        def novato = getAlumnoNovato()
        def pedido = compradorService.crearPedido(novato)
        pedido.agregar(materialTipo,novato)
        pedido.agregar(materialTipo2,novato)
        assert (pedido.getArticulosSolicitados().size() === 2)
        pedido.cancelar()
        assert (pedido.getArticulosSolicitados().size() === 0)
    }

    @Test
    void dadoQueNovatoYTarjetaValidaYMenosDeDosLibrosEnBibliotecaCuandoPagoEntoncesLibroY50Cred() {
        def novato = getAlumnoNovato()
        def tarjeta = getTarjetaValida(novato, cuentaAlumnoNovato)
        def pedido = compradorService.crearPedido(novato)

        pedido.agregar(materialTipo,novato)
        assert (novato.getCantLibrosComprados() === 0)
        pagarPedido(pedido, tarjeta, 0)

        assert (novato.getCantLibrosComprados() === 1 &&
                novato.getCreditos() === 50)
    }

    @Test
    void dadoQueNovatoYTarjetaValidaYDosLibrosEnBibliotecaCuandoPagoEntoncesLibroY50CredYSuboNivel() {

        def novato = getAlumnoNovato()
        def tarjeta = getTarjetaValida(novato, cuentaAlumnoNovato)
        def pedido = compradorService.crearPedido(novato)
        def otroPedido

        pedido.agregar(materialTipo,novato)
        pedido.agregar(materialTipo2,novato)
        pagarPedido(pedido, tarjeta, 0)
        assert (novato.getCantLibrosComprados() === 2)
        //el pedido se crea luego de cerrado el otro de esta manera no arroja excepción
        otroPedido = compradorService.crearPedido(novato)
        otroPedido.agregar(materialTipo3,novato)
        pagarPedido(otroPedido, tarjeta, 0)

        assert (novato.getCantLibrosComprados() == 3 &&
                novato.getCreditos() == 150 && novato.esMedio())

    }

    @Test
    void dadoQueAlumnoMedioCuandoAgregoLibroEntoncesLibroPendientePagoEnPedido() {
        def medio = getAlumnoMedio()
        def pedido = compradorService.crearPedido(medio)
        def articuloMaterial = pedido.agregar(materialTipo, medio)
        assert (articuloMaterial.getEstado() === EstadoArticulo.PENDIENTE_PAGO)
    }

    @Test
    void dadoQueAlumnoMedio4LibrosCompradosConQuintoPendienteConTarjetaValida400CreditosAlPagarMasLibroSuma100CredYSubeNivel() {
        def medio = getAlumnoMedio()
        def tarjeta = getTarjetaValida(medio, cuentaAlumnoNovato)
        def pedido = compradorService.crearPedido(medio)
        def otroPedido

        pedido.agregar(materialTipo,medio)
        pedido.agregar(materialTipo2,medio)
        pedido.agregar(materialTipo3,medio)
        pedido.agregar(materialTipo4,medio)
        pagarPedido(pedido, tarjeta, 0)
        assert (medio.getCantLibrosComprados() === 4 && medio.getCreditos() == 400 )
        //el pedido se crea luego de cerrado el otro, de esta manera no arroja excepción
        otroPedido = compradorService.crearPedido(medio)
        otroPedido.agregar(materialTipo5,medio)
        pagarPedido(otroPedido, tarjeta, medio.getCreditos())

        assert (medio.getCantLibrosComprados() == 5 &&
                medio.getCreditos() == 100 && medio.esPRO())

    }

    @Test
    void dadoQueAlumnoMedioMenosDe4LibrosCompradosConUnoPendienteConTarjetaValidaAgregaLibroSuma100Cred() {
        def medio = getAlumnoMedio()
        def tarjeta = getTarjetaValida(medio, cuentaAlumnoNovato)
        def pedido = compradorService.crearPedido(medio)
        def otroPedido

        pedido.agregar(materialTipo,medio)
        pedido.agregar(materialTipo2,medio)
        pedido.agregar(materialTipo3,medio)
        pagarPedido(pedido, tarjeta, 0)
        assert (medio.getCantLibrosComprados() === 3 && medio.getCreditos() == 300 )
        //el pedido se crea luego de cerrado el otro, de esta manera no arroja excepción
        otroPedido = compradorService.crearPedido(medio)
        otroPedido.agregar(materialTipo4,medio)
        pagarPedido(otroPedido, tarjeta, 0)

        assert (medio.getCantLibrosComprados() == 4 &&
                medio.getCreditos() == 400 && medio.esMedio())
    }

}
