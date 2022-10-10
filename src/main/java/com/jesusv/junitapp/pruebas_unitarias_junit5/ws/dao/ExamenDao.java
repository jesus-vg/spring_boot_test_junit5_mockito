package com.jesusv.junitapp.pruebas_unitarias_junit5.ws.dao;

import com.jesusv.junitapp.pruebas_unitarias_junit5.ws.models.Examen;

import java.util.List;

public interface ExamenDao {
  List<Examen> findAll();
}
