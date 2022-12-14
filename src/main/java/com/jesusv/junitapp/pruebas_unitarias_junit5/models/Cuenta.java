package com.jesusv.junitapp.pruebas_unitarias_junit5.models;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.jesusv.junitapp.pruebas_unitarias_junit5.exceptions.DineroInsuficienteException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
public class Cuenta {
  private String     persona;
  private BigDecimal saldo;
  private Banco      banco;

  public Cuenta( String persona, BigDecimal saldo ) {
    this.persona = persona;
    this.saldo = saldo;
  }

  @Override
  public boolean equals( Object o ) {
    if ( this == o ) return true;
    if ( o == null || getClass() != o.getClass() ) return false;
    Cuenta cuenta = ( Cuenta ) o;
    return persona.equals( cuenta.persona ) && saldo.equals( cuenta.saldo );
  }

  @Override
  public int hashCode() {
    return Objects.hash( persona, saldo );
  }

  /**
   * Validar que no se permita tener valores negativos.
   * @param debito La cantidad a restar
   */
  public void debito( BigDecimal debito ) {
    BigDecimal nuevoSaldo = saldo.subtract( debito );

    if ( nuevoSaldo.compareTo( BigDecimal.ZERO ) < 0 ) {
      throw new DineroInsuficienteException( "Dinero insuficiente" );
    }

    saldo = nuevoSaldo;
  }

  public void credito( BigDecimal credito ) {
    saldo = saldo.add( credito );
  }
}
