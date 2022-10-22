package com.jesusv.junitapp.pruebas_unitarias_junit5.models;

import com.jesusv.junitapp.pruebas_unitarias_junit5.exceptions.DineroInsuficienteException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class CuentaTest {

  Cuenta cuenta;

  /**
   * Este método se ejecuta por cada test
   */
  @BeforeEach
  void init() {
    cuenta = new Cuenta( "Jesus", BigDecimal.ONE );
    System.out.println( "Iniciando el método" );
  }

  /**
   * Este método se ejecuta por cada que finaliza un test
   */
  @AfterEach
  void tearDown() {
    System.out.println( "Finalizando el método de prueba" );
  }

  @BeforeAll
  static void beforeAll() {
    System.out.println( "Inicializando el test" );
  }

  @AfterAll
  static void afterAll() {
    System.out.println( "Finalizando el test" );
  }


  @Nested
  @DisplayName( "Realizando pruebas de la clase Banco y Cuenta" )
  class PruebasBancoCuenta {
    @Test
    @DisplayName( "Compara si el nombre de la persona es igual a la esperada en la prueba" )
    void testNombreCuenta() {
      String esperado = cuenta.getPersona();
      String real     = "Jesus";
      assertEquals( esperado, real );
      assertTrue( esperado.equals( real ) );

    }

    @Test
    @DisplayName( "Validar cuenta del usario" )
    void testSaldoCuenta() {
      assertEquals( 1, cuenta.getSaldo().doubleValue() );
      assertFalse( cuenta.getSaldo().compareTo( BigDecimal.ZERO ) < 0 );
    }

    @Test
    @DisplayName( "Validar que sea el mismo objeto" )
    void testReferenciaCuenta() {
      Cuenta cuenta  = new Cuenta( "jesus", BigDecimal.TEN );
      Cuenta cuenta2 = new Cuenta( "jesus", BigDecimal.TEN );
      // assertNotEquals( cuenta, cuenta2 );
      assertEquals( cuenta, cuenta2 );
    }

    @Test
    void testDebitoCuenta() {
      Cuenta cuenta = new Cuenta( "Jesus", new BigDecimal( "9000.67" ) );
      cuenta.debito( new BigDecimal( "5000.67" ) );
      assertEquals( 4000, cuenta.getSaldo().doubleValue() );
    }

    @Test
    void testCreditoCuenta() {
      Cuenta cuenta = new Cuenta( "Jesus", new BigDecimal( "9000.67" ) );
      cuenta.credito( new BigDecimal( "5000.67" ) );
      assertEquals( 14001.34, cuenta.getSaldo().doubleValue() );
    }

    @Test
    @DisplayName( "validar que el saldo no sea negativo" )
    void testDineroInsuficienteException() {
      Cuenta cuenta = new Cuenta( "Jesus", new BigDecimal( "9000.67" ) );
      Exception exception = assertThrows( DineroInsuficienteException.class, () -> {
        cuenta.debito( new BigDecimal( "20000" ) );
      } );
      String actual   = exception.getMessage();
      String esperado = "Dinero insuficiente";
      assertEquals( esperado, actual );
    }

    @Test
    @DisplayName( "Prueba para hacer la transferencia" )
    void testTranferirDineroCuentas() {
      Cuenta cuentaOrigen  = new Cuenta( "Jesus", new BigDecimal( "100000.67" ) );
      Cuenta cuentaDestino = new Cuenta( "Lupe", new BigDecimal( "50000.00" ) );

      Banco banco = new Banco();
      banco.setNombre( "Banco del estado" );
      banco.tranferirDinero( cuentaOrigen, cuentaDestino, new BigDecimal( "50000" ) );

      assertEquals( "50000.67", cuentaOrigen.getSaldo().toPlainString() );
      assertEquals( "100000.00", cuentaDestino.getSaldo().toPlainString() );
    }

    @Test
    @DisplayName( "Relacion entre cuentas" )
    void testRelacionEntreCuentas() {
      Cuenta cuentaOrigen  = new Cuenta( "Jesus", new BigDecimal( "100000.67" ) );
      Cuenta cuentaDestino = new Cuenta( "Lupe", new BigDecimal( "50000.00" ) );

      Banco banco = new Banco();
      banco.addCuenta( cuentaOrigen );
      banco.addCuenta( cuentaDestino );
      banco.setNombre( "Banco del estado" );
      banco.tranferirDinero( cuentaOrigen, cuentaDestino, new BigDecimal( "50000" ) );

      assertEquals( "50000.67", cuentaOrigen.getSaldo().toPlainString() );
      assertEquals( "100000.00", cuentaDestino.getSaldo().toPlainString() );

      assertEquals( 2, banco.getCuentas().size() );
      assertEquals( "Banco del estado", cuentaOrigen.getBanco().getNombre() );
      // validamos que la persona tenga una cuenta en el banco
      assertEquals(
          "Jesus",
          banco.getCuentas()
              .stream()
              .filter( cuenta -> cuenta.getPersona().equals( "Jesus" ) )
              .findFirst()
              .get()
              .getPersona()
      );
      // validamos que la persona tenga una cuenta en el banco OPCIÓN 2
      assertTrue(
          banco.getCuentas()
              .stream()
              .filter( cuenta -> cuenta.getPersona().equals( "Jesus" ) )
              .findFirst()
              .isPresent()
      );

      // validamos que la persona tenga una cuenta en el banco OPCIÓN 3
      assertTrue(
          banco.getCuentas()
              .stream()
              .anyMatch( cuenta -> cuenta.getPersona().equals( "Jesus" ) )
      );
    }

    @Test
    @DisplayName( "Relacion entre cuentas con asserstAll" )
    void testRelacionEntreCuentas2() {
      Cuenta cuentaOrigen  = new Cuenta( "Jesus", new BigDecimal( "100000.67" ) );
      Cuenta cuentaDestino = new Cuenta( "Lupe", new BigDecimal( "50000.00" ) );

      Banco banco = new Banco();
      banco.addCuenta( cuentaOrigen );
      banco.addCuenta( cuentaDestino );
      banco.setNombre( "Banco del estado" );
      banco.tranferirDinero( cuentaOrigen, cuentaDestino, new BigDecimal( "50000" ) );

      assertAll(
          () -> assertEquals(
              "50000.67",
              cuentaOrigen.getSaldo().toPlainString(),
              () -> "saldo restante es igual a esperado"
          ),
          () -> assertEquals(
              "100000.00",
              cuentaDestino.getSaldo().toPlainString(),
              () -> "El saldo transferido coincide"
          ),
          () -> assertEquals( 2, banco.getCuentas().size() ),
          () -> assertEquals( "Banco del estado", cuentaOrigen.getBanco().getNombre() ),
          () -> // validamos que la persona tenga una cuenta en el banco
              assertEquals(
                  "Jesus",
                  banco.getCuentas()
                      .stream()
                      .filter( cuenta -> cuenta.getPersona().equals( "Jesus" ) )
                      .findFirst()
                      .get()
                      .getPersona()
              ),
          () -> // validamos que la persona tenga una cuenta en el banco OPCION 2
              assertTrue(
                  banco.getCuentas()
                      .stream()
                      .filter( cuenta -> cuenta.getPersona().equals( "Jesus" ) )
                      .findFirst()
                      .isPresent()
              ),
          () -> // validamos que la persona tenga una cuenta en el banco OPCIÓN 3
              assertTrue(
                  banco.getCuentas()
                      .stream()
                      .anyMatch( cuenta -> cuenta.getPersona().equals( "Jesus" ) )
              )
      );
    }
  }

  @Disabled
  @DisplayName( "Hacer que no se ejecute el método" )
  void testMetodoFallido() {
    fail();
    System.out.println( "this = " + this );
    //  Probando test que falla a proposition
  }


  @Nested
  class SystemProperties {
    @Test
    @EnabledOnOs( OS.WINDOWS )
    void testSoloWindows() {
      System.out.println( "Solo se ejecuta en windows" );
    }

    @Test
    @EnabledOnOs( {OS.LINUX, OS.MAC} )
    void testSoloLinuxMac() {
      System.out.println( "Solo se ejecuta en linux y mac" );
    }

    @Test
    @DisabledOnOs( OS.WINDOWS )
    void testNoWindows() {
      System.out.println( "NO WINDOWS" );
    }
  }

  @Test
  @EnabledOnJre( JRE.JAVA_8 )
  void testSoloJDK8() {
    System.out.println( "Solo en java 8" );
  }


  @Test
  void imprimirSystemProperties() {
    System.getProperties().forEach( ( k, v ) -> {
      System.out.println( "k = " + k + "  v = " + v );
    } );
  }

  @Nested
  class JavaVersion {
    @Test
    @EnabledIfSystemProperty( named = "java.version", matches = "11.0.2" )
    void testJRE11() {
      System.out.println( "Solo en java 11" );
    }

    @Test
    @DisplayName( "Ejeutar una sentencia similar al if" )
    void testCondicionamiento() {
      Assumptions.assumingThat( false, () -> {
        assertTrue( 4 < 6 );
      } );
      assertFalse( 4 < 0 );
    }
  }

  @RepeatedTest( value = 5, name = "Step {currentRepetition} de {totalRepetitions}" )
  @DisplayName( "Test que se repite" )
  void testDebitoCuenta( RepetitionInfo repetitionInfo ) {
    if ( repetitionInfo.getCurrentRepetition() == repetitionInfo.getTotalRepetitions() ) {
      System.out.println( "Fin del bucle" );
    }
    Cuenta cuenta = new Cuenta( "Jesus", new BigDecimal( "9000.67" ) );
    cuenta.debito( new BigDecimal( "5000.67" ) );
    assertEquals( 4000, cuenta.getSaldo().doubleValue() );
  }

}