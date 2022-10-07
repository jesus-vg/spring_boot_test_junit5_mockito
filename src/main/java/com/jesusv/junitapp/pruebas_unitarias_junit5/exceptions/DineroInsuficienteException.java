package com.jesusv.junitapp.pruebas_unitarias_junit5.exceptions;

public class DineroInsuficienteException extends RuntimeException {
  public DineroInsuficienteException( String mensaje ) {
    super( mensaje );
  }

}
