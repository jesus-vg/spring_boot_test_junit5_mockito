package com.jesusv.junitapp.pruebas_unitarias_junit5.ws.services;

import com.jesusv.junitapp.pruebas_unitarias_junit5.ws.dao.ExamenDao;
import com.jesusv.junitapp.pruebas_unitarias_junit5.ws.models.Examen;

import java.util.Optional;

class ExamenServiceImp implements ExamenService {
  private ExamenDao examenDao;

  public ExamenServiceImp( ExamenDao examenDao ) {
    this.examenDao = examenDao;
  }

  @Override
  public Optional<Examen> findExamenPorNombre( String nombre ) {
    return examenDao
        .findAll()
        .stream()
        .filter( examen -> examen.getNombre().contains( nombre ) )
        .findFirst();
  }
}