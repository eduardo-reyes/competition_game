package edd.src.Proyecto;

import edd.src.Estructuras.*;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
/*
* Clase utilizada para guardar el historial de los torneos.
*/
public class HistorialTorneo {
  /*
  * Torneos jugados.
  */
  private Torneo torneo = new Torneo();
  private FileOutputStream fos = null;
  private FileInputStream fis = null;
  private ObjectOutputStream oos = null;
  private ObjectInputStream ois = null;

  /*
  * Constructor del historial de torneo.
  */
  public HistorialTorneo() {
    return;
  }

  /*
  * Método que nos dice si el archivo de historial es vacío o no
  * @return boolean true si hay torneos guardados, false en caso contrario.
  */
  public boolean esVacio() {
    if(torneo == null) {
      return true;
    }
    return false;
  }

  /*
  * Método que nos devuelve los torneos que se han jugado.
  * @return Torneo último torneo jugado.
  */
  public Torneo getTorneo() {
    if(torneo != null) {
      return this.torneo;
    }
    return null;
  }

  /*
  * Setter del torneo.
  *
  * @param t el torneo a settear.
  */
  public void setTorneo(Torneo t) {
    this.torneo = t;
  }

  /*
  * Método que escribe en el archivo ultimoTorneo.txt
  * el último torneo jugado.
  *
  */
  public void guardarTorneo() {
    try {
      fos = new FileOutputStream("ultimoTorneo.txt");
      oos = new ObjectOutputStream(fos);
      oos.writeObject(torneo);
      fos.close();
    }
    catch (Exception e) {
      System.out.println("Excepción de escritura de torneo.");
    }
  }

  /*
  * Método que lee el archivo ultimoTorneo.txt
  * y extrae el último torneo jugado.
  *
  */
  public void recuperarTorneo() {
    try {
      fis = new FileInputStream("ultimoTorneo.txt");
      ois = new ObjectInputStream(fis);
      if(fis.available() > 0) {
         torneo = (Torneo) ois.readObject();
      }
      fis.close();
    }
    catch (Exception e) {
      System.out.println("Aún no existe ningún historial de torneo.");
    }
    if(torneo == null) {
      System.out.println("No se recuperó ningún torneo.");
      torneo = new Torneo();
    }
  }

}
