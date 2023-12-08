package edd.src.Proyecto;

import edd.src.Estructuras.*;
import java.io.Serializable;
/*
*Clase que simula la cuenta de un usuario.
*/
public class Cuenta implements Serializable {

  /*
  * nombre del usuario de la cuenta.
  */
  private String nombre;
  /*
  * cantidad de dinero que tiene el usuario.
  */
  private float monto;
  /*
  * Lista con los depósitos hechos por el usuario.
  */
  private Lista<Float> depositos;
  /*
  *Número de apuestas ganadas.
  */
  private int ganadas;
  /*
  *Número de apuestas perdidas.
  */
  private int perdidas;
  /*
  * Método constructor de la cuenta.
  * @param nombre Nombre del usuario
  * @param monto dinero que tendrá inicialmente el jugador.
  */
  public Cuenta(String nombre, float monto){
    this.nombre=nombre;
    this.monto=monto;
    this.depositos = new Lista<Float>();
    depositos.add(monto);
  }
  /*
  * Método que regresa el nombre del jugador.
  * @return String nombre del jugador.
  */
  public String getNombre(){
    return this.nombre;
  }
  /*
  * Método que devuelve el número de apuestas ganadas.
  * @return int No de apuestas ganadas.
  */
  public int getGanadas(){
    return this.ganadas;
  }
  /*
  * Método que devuelve el número de apuestas perdidas.
  * @return int No de apuestas perdidas.
  */
  public int getPerdidas(){
    return this.perdidas;
  }
  /*
  * Método que regresa el dinero que dispone el usuario para apostar.
  * @return floar Dinero disponible.
  */
  public float getMonto(){
    return this.monto;
  }
  /*
  * Método que incrementa en 1 el número de apuestas ganadas.
  */
  public void setGanadas(){
    this.ganadas++;
  }
  /*
  * Método que incrementa en 1 el número de apuestas ganadas.
  */
  public void setPerdidas(){
    this.perdidas++;
  }
  /*
  * Método para depositar incrementar el monto disponible de dinero del usuario.
  * @param monto dinero que se le agregará al monto disponible.
  */
  public void setMonto(float monto){
    this.monto+=monto;
    this.depositos.add(monto);
  }
  /*
  * Método que retira el monto la candidad de dinero que será apostada.
  * @param apuesta cantidad de dinero a apostar.
  */
  public void apuestaMonto(float apuesta){
    this.monto-=apuesta;
  }
  /*
  * Método que deposita las gancias obtenidas cuando se gana una apuesta.
  * @param ganancia dinero ganado en una apuesta.
  */
  public void ganoApuesta(double ganancia) {
    this.monto += ganancia;
  }
  /*
  * Método imprime todos los depositos hechos por el usuario a la cuenta.
  */
  public void imprimeDepositos(){
    IteradorLista<Float> iter = depositos.iteradorLista();
    float dep;
    int contador=1;
    while(iter.hasNext()){
      dep = iter.next();
      System.out.println("Deposito " + contador + ": $" + dep);
      contador++;
    }
  }
  /*
  * Método realiza una apuesta en una carrera.
  * @param nombre nombre del competidor por el que se apostará.
  * @param ganador competidor ganador de la carrera.
  @param apuesta cantidad de dinero apostada.
  */
  public void apostarCarrera(String nombre, Competidor ganador, float apuesta){
   apuestaMonto(apuesta);
   if(nombre.equals(ganador.getNombre())){
     float gc= ganador.cuotaDecimal()*apuesta;
     setMonto(gc);
     System.out.println("Usted ganó: $"+gc);
     setGanadas();
   }else{
     System.out.println("Ya fueron esos $"+apuesta+ " XD");
     setPerdidas();
   }
 }

  @Override
  public String toString() {
    return "Nombre: " + this.nombre + " Monto: " + this.monto;
  }

}
