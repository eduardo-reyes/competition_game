package edd.src.Proyecto;
import java.util.Scanner;
import java.io.*;

//import java.util.InterruptedException;

public class CuentaRegresiva extends Thread {
  Carrera prueba = new Carrera();
  Competidor ganador;
  float apuesta=-1;
  String competidor="";
  boolean bandera=true;

public String getCompetidor(){
  return competidor;
}
public Competidor getG(){
  return ganador;
}


  public void run() {

      for(int i = 10; i > 0; i--) {

        System.out.println("La carrera inicia en:  "+10+" segundos");
        try {
          Thread.sleep(10000);
        }
        catch(InterruptedException ie) {

        }
        ganador=prueba.JugarCarrera();
      }

  }

}
