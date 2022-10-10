package com.jesusv.junitapp.pruebas_unitarias_junit5.ws.dao;

import com.jesusv.junitapp.pruebas_unitarias_junit5.ws.models.Examen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExamenDaoImp implements ExamenDao {
  @Override
  public List<Examen> findAll() {
    return Arrays.asList(
        new Examen( 1L, "Jesus", new ArrayList<>() ),
        new Examen( 2L, "Villegas", new ArrayList<>() ),
        new Examen( 3L, "Gonzalez", new ArrayList<>() )
    );
  }
}
