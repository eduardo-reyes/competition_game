package edd.src.Proyecto;

import edd.src.Estructuras.*;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

public class Historial {

  /* Lista con las cuentas guardadas. */
  private Lista<Cuenta> cuentas;
  /* Flujo de salida. */
  private FileOutputStream fos = null;
  /* Flujo de entrada. */
  private FileInputStream fis = null;
  /* Archivo de salida. */
  private ObjectOutputStream oos = null;
  /* Archivo de entrada. */
  private ObjectInputStream ois = null;

  /*
  * Método constructor vacío de la cuenta.
  */
  public Historial() {
    return;
  }

  /*
  * Método que verifica si la lista
  * con las cuentas está vacía.
  *
  * @returns boolean true si está vacía.
  */
  public boolean esVacio() {
    if(cuentas.isEmpty()) {
      return true;
    }
    return false;
  }

  /*
  * Método getter que devuelve la Lista
  * con las cuentas d elos usuarios.
  *
  * @returns lista con las cuentas.
  */
  public Lista<Cuenta> getCuentas() {
    if(cuentas != null) {
      return this.cuentas;
    }
    return null;
  }

  /*
  * Método que escribe en el archivo historialCuentas.txt
  * el historial de las cuentas en forma de lista,
  * serializando la lista.
  *
  */
  public void escribirCuentas() {
    try {
      fos = new FileOutputStream("historialCuentas.txt");
      oos = new ObjectOutputStream(fos);
      oos.writeObject(cuentas);
      fos.close();
    }
    catch (Exception e) {
      System.out.println("Excepción de escritura de cuentas.");
    }
  }

  /*
  * Método que lee el archivo historialCuentas.txt
  * y extrae una lista con el historial de las cuentas.
  *
  */
  public void leerCuentas() {
    try {
      fis = new FileInputStream("historialCuentas.txt");
      ois = new ObjectInputStream(fis);
      if(fis.available() > 0) {
         cuentas = (Lista<Cuenta>) ois.readObject();
      }
      fis.close();
    }
    catch (Exception e) {
      System.out.println("Aún no existe ningún historial de cuentas.");
    }
    if(cuentas == null) {
      cuentas = new Lista<Cuenta>();
    }
  }

  /*
  * Método que registra una nueva cuenta
  * de usuario en caso de no existir una
  * cuenta con el mismo nombre.
  *
  * @param nombre es la cadena con el nuevo nombre de usuario.
  * @param monto el monto inicial con el que comienza la cuenta.
  * @return boolean true si se pudo crear la cuenta.
  */
  public boolean registraCuenta(String nombre, float monto) {
    if(cuentas == null) {
      cuentas = new Lista<Cuenta>();
      cuentas.add(new Cuenta(nombre, monto));
      return true;
    }
    if(cuentas.isEmpty()) {
      cuentas.add(new Cuenta(nombre, monto));
      return true;
    }
    if(verificaCuenta(nombre)) {
      return false;
    }
    IteradorLista<Cuenta> iter = cuentas.iteradorLista();
    Cuenta c;
    while(iter.hasNext()){
      c = iter.next();
      if(c.getNombre() == nombre) return false;
    }
    cuentas.add(new Cuenta(nombre, monto));
    return true;
  }

  /*
  * Método que actualiza el status de una
  * cuenta borrando su versión anterior del
  * historial y añadiendo su versión nueva
  * a la lista con las cuentas.
  *
  * @param cuenta es la cuenta que sea desea actualizar.
  * @return boolean true si se pudo actualizar la cuenta.
  */
  public boolean actualizaCuenta(Cuenta cuenta) {
    if(cuentas == null || cuentas.isEmpty()) {
      return false;
    }
    if(verificaCuenta(cuenta.getNombre())) {
      borrarCuenta(cuenta.getNombre());
      cuentas.add(cuenta);
      return true;
    }
    return false;
  }

  /*
  * Método que busca si existe una cuenta
  * buscando en la lista con el historial
  * de las cuentas.
  *
  * @param nombre es la cadena con el nombre de usuario.
  * @return Cuenta la cuenta buscada.
  */
  public Cuenta buscaCuenta(String nombre) {
    if(cuentas == null || nombre == null) {
      return null;
    }
    IteradorLista<Cuenta> iter = cuentas.iteradorLista();
    Cuenta c;
    while(iter.hasNext()){
      c = iter.next();
      if(c.getNombre().equals(nombre)) return c;
    }
    return null;
  }

  /*
  * Método que busca si existe una cuenta
  * buscando en la lista con el historial
  * de las cuentas.
  *
  * @param nombre es la cadena con el nombre de usuario.
  * @return boolean true si existe la cuenta en el historial.
  */
  public boolean verificaCuenta(String nombre) {
    if(cuentas == null || nombre == null) {
      return false;
    }
    IteradorLista<Cuenta> iter = cuentas.iteradorLista();
    Cuenta c;
    while(iter.hasNext()){
      c = iter.next();
      if(c.getNombre().equals(nombre)) return true;
    }
    return false;
  }

  /*
  * Método que elimina una cuenta de la
  * lista del historial de cuentas.
  *
  * @param nombre es la cadena con el nombre de usuario.
  * @return boolean true si eliminó la cuenta.
  */
  public boolean borrarCuenta(String nombre) {
    if(cuentas == null || nombre == null) {
      return false;
    }
    IteradorLista<Cuenta> iter = cuentas.iteradorLista();
    Cuenta c;
    while(iter.hasNext()){
      c = iter.next();
      if(c.getNombre().equals(nombre)) {
        return cuentas.delete(c);
      }
    }
    return false;
  }
  
}
