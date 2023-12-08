package edd.src.Proyecto;

import java.util.Random;
import java.lang.Math;
import java.util.Arrays;
import java.io.Serializable;

public class Torneo implements Serializable {

  private int[] candidatos = new int[16]; // Arreglo con con el nivel de habilidad de los candidatos.
  private double[] cuotas = new double[16]; // Arreglo con las cuotas por candidato.
  private int[] ganadores = new int[8]; // Arreglo con los ganadores de cada ronda.
  private int rondaActual = 1; // Ronda actual del torneo.
  private int partida = 0;

  /* Constructor de Torneo */
  public Torneo() {
    for(int i = 0; i < candidatos.length; i++) {
      candidatos[i] = (int) Math.floor(Math.random()*(400-50+1)+50);
    }
  }

  /* Getter de la ronda actual.
   *
   * @return int ronda guardada.
   */
  public int getRonda() {
    return this.rondaActual;
  }

  /* Setter de la ronda actual
   *
   * @param ronda actual.
   */
  public void setRonda(int r) {
    this.rondaActual = r;
  }

  /* Getter de la ronda actual.
   *
   * @return int ronda guardada.
   */
  public int getPartida() {
    return this.partida;
  }

  /* Setter de la ronda actual
   *
   * @param ronda actual.
   */
  public void setPartida(int p) {
    this.partida = p;
  }

  /* Getter del arreglo de enteros de la
   * habilidad de los candidatos.
   *
   * @return int[] habilidad de los candidatos.
   */
  public int[] getCandidatos() {
    return this.candidatos;
  }

  /* Getter del arreglo de enteros que
   * indica los ganadores por ronda.
   *
   * @return int[] ganadores por ronda.
   */
  public int[] getGanadores() {
    return this.ganadores;
  }

  /* Método que calcula las cuotas de una
   * partida cuando dos candidatos a y b
   * se enfrentan y regresa un arreglo de
   * tamaño dos con las cuotas de ambos.
   *
   * @param a habilidad del candidato a.
   * @param a habilidad del candidato b.
   * @return double[] cuotas de los candidatos.
   */
  private double[] calculaCuotas(int a, int b) {
    double[] c = new double[2];
    int suma = a + b;
    c[0] = 1.0 / (((double) a) / suma);
    c[1] = 1.0 / (((double) b) / suma);
    return c;
  }

  /* Método que regrea las cuotas por ronda de
   * todos los candidatos según el método
   * calculaCuotas tomando parejas.
   *
   * @param ronda es la ronda del torneo.
   * @return double[] cuotas de los candidatos.
   */
  public double[] getCuotas(int ronda) {
    double[] cuotasPareja = new double[2];
    for(int i = 0; i < (8 / Math.pow(2, ronda - 1)); i++) {
      cuotasPareja = calculaCuotas(candidatos[2 * i], candidatos[2 * i + 1]);
      cuotas[2 * i] = cuotasPareja[0];
      cuotas[2 * i + 1] = cuotasPareja[1];
    }
    return cuotas;
  }

  /* Método que calcula las probabilidades de una
   * partida cuando dos candidatos a y b
   * se enfrentan y regresa el número del
   * candidato ganador del encuentro.
   *
   * @param a habilidad del candidato a.
   * @param a habilidad del candidato b.
   * @return int número del ganador.
   */
  private int calculaProbabilidades(int a, int b) {
    int suma = a + b;
    double cota = 100.0 * ((double) a) / suma;
    int random = (int) Math.floor(Math.random()*(100 + 1));
    if(random <= cota) return a;
    return b;
  }

  /* Método que simula una ronda del torneo
   * calculando a los ganadores.
   *
   * @param ronda es la ronda del torneo.
   */
  public void juegaTorneo(int ronda) {
    int ganador = 0;
    for(int i = 0; i < (8 / Math.pow(2, ronda - 1)); i++) {
      ganador = calculaProbabilidades(candidatos[2 * i], candidatos[2 * i + 1]);
      //System.out.println("Candidato " + (2 * i) + " vs candidato " + (2 * i + 1));
      if(ganador == candidatos[2 * i]) {
        ganadores[i] = 2 * i;
        //System.out.println("Ganó el candidato " + (2 * i));
        candidatos[i] = candidatos[2 * i];
      }
      else {
        ganadores[i] = 2 * i + 1;
        //System.out.println("Ganó el candidato " + (2 * i + 1));
        candidatos[i] = candidatos[2 * i + 1];
      }
      //System.out.println("Ahora es el candidato " + i);
    }
    //System.out.println(Arrays.toString(ganadores));
  }

}
