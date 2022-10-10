package com.jesusv.junitapp.pruebas_unitarias_junit5.ws.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Examen {
  private Long         id;
  private String       nombre;
  private List<String> preguntas;
}
