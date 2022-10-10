package com.jesusv.junitapp.pruebas_unitarias_junit5.ws.services;

import com.jesusv.junitapp.pruebas_unitarias_junit5.ws.dao.ExamenDao;
import com.jesusv.junitapp.pruebas_unitarias_junit5.ws.dao.ExamenDaoImp;
import com.jesusv.junitapp.pruebas_unitarias_junit5.ws.models.Examen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ExamenServiceImpTest {

  ExamenDao     examenDao;
  ExamenService examenService;

  @BeforeEach
  void setUp() {
    // examenDao = new ExamenDaoImp()
    examenDao = Mockito.mock( ExamenDao.class );
    examenService = new ExamenServiceImp( examenDao );
  }

  @Test
  void testFindExamenPorNombre() {

    when( examenDao.findAll() )
        .thenReturn( Arrays.asList(
            new Examen( 1L, "Jesus", new ArrayList<>() ),
            new Examen( 2L, "Villegas", new ArrayList<>() ),
            new Examen( 3L, "Gonzalez", new ArrayList<>() )
        ) );

    Optional<Examen> examen = examenService.findExamenPorNombre( "Jesus" );
    assertAll(
        () -> assertNotNull( examen, "Examen es null" ),
        () -> assertEquals( 1L, examen.orElseThrow().getId(), "El ID No son iguales" ),
        () -> assertEquals( "Jesus", examen.orElseThrow().getNombre(), "La materia no es igual" )
    );
  }

  @Test
  @DisplayName( "Probando lista vacía" )
  void testFindExamenPorNombreListaVacia() {
    when( examenDao.findAll() ).thenReturn( Collections.emptyList() );
    Optional<Examen> examen = examenService.findExamenPorNombre( "Jesus" );
    assertAll(
        () -> assertFalse( examen.isPresent(), "Encontrado, debería ser null" )
    );
  }

}