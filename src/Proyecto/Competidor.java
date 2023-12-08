package edd.src.Proyecto;
import edd.src.Estructuras.*;
import java.io.Serializable;
import java.util.Random;
public class Competidor implements Serializable{
  /*
  * Clase que simula a un competidor de una carrera.
  */

  /*
  * lista que tiene las últimas 5 posiciones que obtuvo el competidor.
  */
  private int[] ultimasPos= {0,0,0,0,0};
  /*
  * Nombre del competidor.
  */
  private String nombre;

  /*
  * Constructor de un competidor de carreras.
  * @param name nombre del competidor.
  */
  public Competidor(String name){
    nombre=name;
  }
  /*
  * método que devuelve el nombre del competidor.
  *@return String nombre del competidor.
  */
  public String getNombre(){
    return this.nombre;
  }
  /*
  * Método que agrega un posición de una carrera jugada a su lista de las ultimas 5 posiciones en cierto
  * lugar de la lista.
  * @param posicion posición del jugador obtuvido en una carrera.
  * @param lugar de la lista en donde se agregará la posición.
  */
  public void agregarPosicion(int posicion, int lugar ){
    ultimasPos[lugar]=posicion;
  }
  /*
  * método que devuelve la probabilidad de ganar de un competidor de carreras.
  * @return float probabilidad de ganar del competidor.
  */
  public float probabilidad(){
    float sumh=0;
    for(int i=0;i<5;i++){
      sumh+=ultimasPos[i];
    }
    float p=(5*11-sumh)/(5*((10*11)/2));

    return p;
  }
  /*
  * Método que devuelve la cuota decimal del jugador.
  * @return float cuota decimal del jugador.
  */
  public float cuotaDecimal(){
    return 1/probabilidad();
  }

  /*
  * Método que agrega la posición obtenida en su última carrera
  * y recorre las posiciones en la lista.
  * @param posicion posición obtenida en la carrera.
  */
  public void actualizarHistorial(int posicion){
    for(int i=0;i<4;i++){
      ultimasPos[i]=ultimasPos[i+1];
    }
    ultimasPos[4]=posicion;
  }

  public String toString(){
    String pos= "(";
    float cuota=1/probabilidad();
    for(int i=0;i<5;i++){
      pos+=ultimasPos[i];
      if(i<4){
         pos+=", ";
      }else{
        pos+=")";
      }
    }
    return nombre+ ", posiciones: " +pos +", cuota: "+ cuota;
  }

}
