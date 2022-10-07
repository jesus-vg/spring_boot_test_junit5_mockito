package com.jesusv.junitapp.pruebas_unitarias_junit5.models;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class Banco {
  private List<Cuenta> cuentas;
  private String       nombre;

  public void tranferirDinero( Cuenta origen, Cuenta destino, BigDecimal monto ) {
    origen.debito( monto );
    destino.credito( monto );
  }

  public void addCuenta( Cuenta cuenta ) {
    cuentas.add( cuenta );
  }
}
