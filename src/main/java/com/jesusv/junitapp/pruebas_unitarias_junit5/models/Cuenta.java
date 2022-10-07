package com.jesusv.junitapp.pruebas_unitarias_junit5.models;

import com.jesusv.junitapp.pruebas_unitarias_junit5.exceptions.DineroInsuficienteException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class Cuenta {
  private String     persona;
  private BigDecimal saldo;
  private Banco banco;

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
