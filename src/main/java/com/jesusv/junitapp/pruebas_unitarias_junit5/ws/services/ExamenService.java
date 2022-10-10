package com.jesusv.junitapp.pruebas_unitarias_junit5.ws.services;

import com.jesusv.junitapp.pruebas_unitarias_junit5.ws.dao.ExamenDao;
import com.jesusv.junitapp.pruebas_unitarias_junit5.ws.models.Examen;

import java.util.Optional;

public interface ExamenService {
  Optional<Examen> findExamenPorNombre( String nombre);


}
