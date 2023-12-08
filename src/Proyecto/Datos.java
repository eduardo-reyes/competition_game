package edd.src.Proyecto;
import java.util.Scanner;
import java.io.*;

/*
* Clase para la recolección de datos (numero del competidor y monto de la apuesta)
*/
public class Datos extends Thread {
  /*
  * apuesta que será recivida desde la terminal.
  */
  String apuesta;
  /*
  * número del competidor, ingresado desde la terminal.
  */
  String competidor;
  /*
  * tiempo transcurrido (en segudos) para establecer un límite de espera.
  */
  int tiempo=0;

  /*
  * Método que devuelve el tiempo transcurrido.
  * @return int tiempo transcurrido.
  */
  public int getTiempo(){
      return tiempo;
  }

  /*
  * Método que devuelve un string con el número del competidor a apostar.
  * @return String número del competidor por el cual se apostará.
  */
  public String getCompetidor(){
    return competidor;
  }

  /*
  * Método que devuelve un string con el monto de la apuesta.
  * @return String monto de la apuesta.
  */
  public String getAp(){
    return apuesta;
  }

  /*
  * Método encargado de la recolección de datos dando un límite de 10 segundos.
  */
  @Override
  public void run() {
    apuesta="-1";
    competidor="-1";
    tiempo=0;
    BufferedReader bufer = new BufferedReader(new InputStreamReader(System.in));

    try {
      System.out.println("Ingresa el número del Jugador");
      while(!bufer.ready()){
        try {
          Thread.sleep(1000);
          tiempo++;
          if(tiempo==10){
            break;
          }
        }catch(InterruptedException ie) {
          System.out.println("Interrupción dentro del ciclo en run.");
        }
      }
      if(tiempo<10){
        competidor= bufer.readLine();
        if(!competidor.equals("E")&&!competidor.equals("e")){
          System.out.println("Ingresa el monto de apuesta");
          while(!bufer.ready()){
            try {
              Thread.sleep(1000);
              tiempo++;
              if(tiempo==10){
                break;
              }
            }catch(InterruptedException ie) {
              System.out.println("Interrupción en run.");
            }
          }
          if(tiempo<10){

              apuesta= bufer.readLine();
          }
          else{
            bufer.reset();
          }
        }
      }
    } catch(IOException ie) {
      System.out.println("Excepción io en run.");
    }
  }
}
