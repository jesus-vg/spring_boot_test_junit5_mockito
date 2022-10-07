package com.jesusv.junitapp.pruebas_unitarias_junit5.models;

import com.jesusv.junitapp.pruebas_unitarias_junit5.exceptions.DineroInsuficienteException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CuentaTest {

  @Test
  @DisplayName("Compara si el nombre de la persona es igual a la esperada en la prueba")
  void testNombreCuenta() {
    Cuenta cuenta   = new Cuenta( "Jesus", BigDecimal.ONE );
    String esperado = cuenta.getPersona();
    String real     = "Jesus";
    assertEquals( esperado, real );
    assertTrue( esperado.equals( real ) );

  }

  @Test
  @DisplayName("Validar cuenta del usario")
  void testSaldoCuenta() {
    Cuenta cuenta = new Cuenta( "Jesus", BigDecimal.ONE );
    assertEquals( 1, cuenta.getSaldo().doubleValue() );
    assertFalse( cuenta.getSaldo().compareTo( BigDecimal.ZERO ) < 0 );
  }

  @Test
  @DisplayName("Validar que sea el mismo objeto")
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
  @DisplayName("validar que el saldo no sea negativo")
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
  @DisplayName("Prueba para hacer la transferencia")
  void testTranferirDineroCuentas() {
    Cuenta cuentaOrigen = new Cuenta( "Jesus", new BigDecimal( "100000.67" ) );
    Cuenta cuentaDestino = new Cuenta( "Lupe", new BigDecimal( "50000.00" ) );

    Banco banco = new Banco();
    banco.setNombre( "Banco del estado" );
    banco.tranferirDinero( cuentaOrigen, cuentaDestino, new BigDecimal( "50000" ) );

    assertEquals( "50000.67", cuentaOrigen.getSaldo().toPlainString() );
    assertEquals( "100000.00", cuentaDestino.getSaldo().toPlainString() );
  }
}