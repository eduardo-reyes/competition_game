package edd.src.Proyecto;

import edd.src.Estructuras.*;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
/*
* Clase utilizada para guardar el historial de las carreras.
*/
public class HistorialCarrera {
  /*
  * Carreras jugadas.
  */
  private  Carrera carreras;
  private FileOutputStream fos = null;
  private FileInputStream fis = null;
  private ObjectOutputStream oos = null;
  private ObjectInputStream ois = null;
  /*
  * Constructor del historial de carreras.
  */
  public HistorialCarrera() {
    //leerCuentas();
    return;
  }
  /*
  * Método que nos dice si el archivo de historial es vacío o no
  * @return boolean true si hay carreras guardades, false en caso contrario.
  */
  public boolean esVacio() {
    if(carreras==null) {
      return true;
    }
    return false;
  }
  /*
  * Método que nos devuelve las carreras que se han jugado.
  * @return Carrera carrerjas jugadas.
  */
  public Carrera getCarreras() {
    if(carreras != null) {
      return this.carreras;
    }
    return null;
  }
  /*
  * Método que guarda las carreras jugadas en un archivo txt.
  */
  public void escribirCarreras() {
    try {
      fos = new FileOutputStream("historialCarreras.txt");
      oos = new ObjectOutputStream(fos);
      oos.writeObject(carreras);
      fos.close();
    }
    catch (Exception e) {
      System.out.println("Excepción de escritura de carreras.");
    }
  }
  /*
  * Método que lee un archivo txt para recuperar las carreras jugadas en seciones
  *iniciadas anteriormente. Si no hay carreras, crea una.
  */
  public void leerCarreras() {
    try {
      fis = new FileInputStream("historialCarreras.txt");
      ois = new ObjectInputStream(fis);
      if(fis.available() > 0) {
        carreras = (Carrera) ois.readObject();
      }
      fis.close();
    }
    catch (Exception e) {
      System.out.println("Aún no hay historial de carreras. Este se creará al cerrar el programa.");
    }

    if(carreras == null) {
      carreras = new Carrera();
    }
  }
}
