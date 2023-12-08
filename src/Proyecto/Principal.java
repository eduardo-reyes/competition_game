package edd.src.Proyecto;

import java.util.Random;
import java.lang.Math;
import java.util.Arrays;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.*;

public class Principal {

  /* Historial de cuentas */
  private static Historial historial;
  /* Sesión activa de usuario. */
  private static Cuenta cuentaActiva;
  /* Historial de torneo. */
  private static HistorialTorneo historialT = new HistorialTorneo();
  /* Historial de carreras. */
  private static HistorialCarrera historialC = new HistorialCarrera();
  /* Path para el historial del torneo. */
  private static String path = "historialTorneo.txt";

  /**
    * Método que lee el archivo que se encuentra en el path.
    * @return file La cadena de texto que contiene
    * lo que tenía el archivo del path.
    */
   private static String readFile() {
     String file = "";
     try {
         BufferedReader in = new BufferedReader(new FileReader(path));
         String line;
         while((line = in.readLine()) != null) {
             file = file + line + "\n";
         }
         in.close();
     } catch (FileNotFoundException e) {
         System.err.println("No se encontró el archivo en el path dado.");
     } catch (IOException ioe) {
         System.err.println("Error al leer el contenido del path.");
     }
     return file;
   }

   /**
    * Método que sobreescribe la cadena de texto ingresada en el archivo del path
    * @param text El texto a escribir en el archivo del path.
    */
   private static void writeFile(String text) {
     String nombreArchivo = path;
     try(PrintStream fout = new PrintStream(nombreArchivo)) {
         fout.println(text);
     } catch(FileNotFoundException fnfe) {
         System.err.println("No se encontró el archivo " + nombreArchivo + " y no pudo ser creado.");
     } catch(SecurityException se) {
         System.err.println("No se tiene permiso de escribir en el archivo.");
     }
   }

   /**
    * Método que escribe la cadena de texto ingresada en el archivo del path.
    * @param text El texto a escribir en el archivo del path.
    */
   private static void appendFile(String text) {
     String nombreArchivo = path;
     try {
       BufferedWriter out = new BufferedWriter(new FileWriter(nombreArchivo, true));
            out.write(text);
            out.newLine();
            out.close();
        }
        catch (IOException e) {
            System.out.println("Ocurrió una excpeción." + e);
        }
   }

  /* Método que lee de la consola un entero
   * que debe estar en un intervalo entre
   * [menor, mayor].
   *
   * @param menor la cota inferior.
   * @param mayor la cota superior.
   * @return int la lectura de consola.
   */
  private static int leerEntero(int menor, int mayor) {
   Scanner sc = new Scanner(System.in);
   boolean bandera=true;
   int entero=-1;
   while(bandera) {
     if(sc.hasNextInt()){
       entero=sc.nextInt();
       if(entero >= menor && entero <= mayor){
         bandera=false;
       }
       else{
         System.out.println("Opción inválida, ingresa un valor válido.");
         System.out.println();
       }
     }
     else{
       System.out.println("Opción inválida, ingresa un valor válido.");
       System.out.println();
       sc.next();
     }
   }
   return entero;
 }

 /* Método que registra a un nuevo usuario
  * creando una nueva cuenta con el nombre
  * de usuario creado si ese nombre no existe
  * en el historial.
  */
 private static void registrarse() {
   boolean condicion = true;
   Scanner sc = new Scanner(System.in);
   String usu = null;
   while(condicion) {
     try {
       System.out.println("Ingrese el nombre de su nueva cuenta:");
        usu = sc.nextLine();
        if(historial.registraCuenta(usu, 0)) {
          break;
        }
        else {
          System.out.println("Cuenta ya existente, ingrese otro nombre.");
          sc.nextLine();
          continue;
        }
     }
     catch (InputMismatchException ime) {
       System.out.println("Nombre inválido.");
       sc.next();
       continue;
     }
   }
   cuentaActiva = historial.buscaCuenta(usu);
   System.out.println("¡Bienvenido " + cuentaActiva.getNombre() + "!");
 }

 /* Método que sirve para iniciar sesión
  * en el programa con el nombre de usuario.
  * En caso de no existir la cuenta manda
  * al registro.
  */
 private static void iniciarSesion() {
   System.out.println("Inicio de sesión.");
   boolean condicion = true;
   Scanner sc = new Scanner(System.in);
   while(condicion) {
     try {
       System.out.println("¿Ya tiene cuenta? (y/n)");
       char opcion = sc.next().charAt(0);
       if(opcion == 'y') {
         System.out.println("Ingrese su nombre de usuario.");
         sc.nextLine();
         String nombre = sc.nextLine();
         cuentaActiva = historial.buscaCuenta(nombre);
         if(cuentaActiva == null) {
           System.out.println("Cuenta inexistente.");
           continue;
         }
         else {
           break;
         }
       }
       else if(opcion == 'n'){
         registrarse();
         break;
       }
       else {
         System.out.println("Opción inválida.");
         sc.nextLine();
         continue;
       }
     }
     catch (InputMismatchException ime) {
       System.out.println("Opción inválida.");
       sc.nextLine();
       continue;
     }
   }
   return;
 }

 /* Método que ofrece un menú para
  * que el programa pueda interaccionar
  * con el usuario y checar su perfil
  * o ver lo relacionado con las apuestas.
  */
 private static void menu() {
   boolean condicion = true;
   Scanner sc = new Scanner(System.in);
   while(condicion) {
     System.out.println();
     System.out.println("========== MENÚ ==========");
     System.out.println("1. Consultar saldo.");
     System.out.println("2. Abonar saldo.");
     System.out.println("3. Historial de movimientos.");
     System.out.println("4. Consultar apuestas ganadas y perdidas.");
     System.out.println("5. Ir a apostar.");
     System.out.println("6. Cerrar sesión.");
     System.out.println("7. Borrar cuenta.");
     System.out.println("8. Salir del programa.");
     int opcion = leerEntero(1, 8);
     switch(opcion) {
       case 1:
        System.out.println("Saldo disponible: " + cuentaActiva.getMonto());
        break;
       case 2:
        System.out.println("Ingrese el monto que desea abonar:");
        float bono = sc.nextFloat();
        cuentaActiva.setMonto(bono);
        break;
       case 3:
        System.out.println("Historial de movimientos:");
        cuentaActiva.imprimeDepositos();
        break;
       case 4:
        System.out.println("Apuestas ganadas " +  cuentaActiva.getGanadas());
        System.out.println("Apuestas perdidas " +  cuentaActiva.getPerdidas());
        break;
       case 5:
        return;
       case 6:
        historial.actualizaCuenta(cuentaActiva);
        cuentaActiva = null;
        return;
       case 7:
        System.out.println("¿Está seguro que desea borrar su cuenta? (y/n)");
        char c = sc.next().charAt(0);
        if(c == 'y') {
          historial.borrarCuenta(cuentaActiva.getNombre());
          cuentaActiva = null;
          return;
        }
        break;
       case 8:
        historial.actualizaCuenta(cuentaActiva);
        historial.escribirCuentas();
        historialC.escribirCarreras();
        System.exit(0);
       default:
        System.out.println("Opción no válida dentro del menú");
     }
   }
 }

 /* Método que le pide al usuario
  * su elección para decidir en que
  * apartado del programa decide apostar.
  * El usuario ingresa un 1 si quiere ir al
  * torneo o 2 si quiere ir a las carreras.
  *
  * @returns int donde quiere apostar.
  */
 private static int decideApostar() {
   int decision = -1;
   while(true) {
     System.out.println("Apostar en:\n 1. Torneo\n 2. Carreras");
     decision = leerEntero(1, 2);
     break;
   }
   return decision;
 }

 /* Método que recupera el estado de
  * un torneo y lo sigue ejecutando.
  *
  * @return boolean true si desea continuar.
  */
 private static boolean reanudarTorneo() {
   historialT.recuperarTorneo();
   Torneo torneo = historialT.getTorneo();
   int ronda = torneo.getRonda();
   int partida = torneo.getPartida();
   System.out.println("Quedó en la partida: " + partida);
   float apuesta = 0;
   int candidato = 0;
   int[] candidatos = torneo.getCandidatos();
   for(int i = 0; i < candidatos.length; i++) {
     System.out.println("Candidato " + i + ". Habilidad: " + candidatos[i]);
     appendFile("Candidato " + i + ". Habilidad: " + candidatos[i]);
   }
   while(ronda < 5) {
     torneo.setRonda(ronda);
     System.out.println();
     appendFile("\n");
     System.out.println("=============== RONDA " + ronda + " ===============");
     appendFile("=============== RONDA " + ronda + " ===============");
     System.out.println("Cuotas de la ronda " +  ronda);
     appendFile("Cuotas de la ronda " +  ronda);
     double[] cuotas = torneo.getCuotas(ronda);
     System.out.println();
     appendFile("\n");
     for(int i = 0; i < (16 / Math.pow(2, ronda - 1)); i++) {
       System.out.printf("Cuota candidato %d : $ %.2f %n", i, cuotas[i]);
       appendFile("Candidato " + i + ". Cuota: " + cuotas[i]);
     }
     torneo.juegaTorneo(ronda);
     int ganador = -1;
     int[] ganadores = torneo.getGanadores();
     for(int i = partida; i < (8 / Math.pow(2, ronda - 1)); i++) {
       torneo.setPartida(i);
       System.out.println();
       appendFile("\n");
       System.out.println("##########################################################");
       appendFile("##########################################################");
       System.out.println("Candidato " + (2 * i) + " vs candidato " + (2 * i + 1));
       appendFile("Candidato " + (2 * i) + " vs candidato " + (2 * i + 1));
       System.out.println();
       System.out.printf("Cuota candidato %d : %.2f %n", 2 * i, cuotas[2 * i]);
       appendFile("Cuota candidato: " + (2 * i) + ": " + cuotas[2 * i]);
       System.out.printf("Cuota candidato %d : %.2f %n", 2 * i + 1, cuotas[2 * i + 1]);
       appendFile("Cuota candidato: " + (2 * i + 1) + ": " + cuotas[2 * i + 1]);
       System.out.println();
       System.out.println("Ingrese el número del candidato por el que desea apostar.");
       System.out.println("Ingrese -2 en jugador o apuesta si desea salir al menú.");
       System.out.println();
       System.out.println("Tu saldo: " + cuentaActiva.getMonto());
       Datos d = new Datos();
       System.out.println();
       System.out.println("La carrera inicia en:  " + 10 + " segundos.");
       System.out.println("Tienes 10 s para apostar.");
       d.run();
       try {
         Thread.sleep((10-d.getTiempo())*1000);
       }
       catch(InterruptedException ie) {
         System.out.println("Interupción en torneo.");
       }
       apuesta = Float.parseFloat(d.getAp());
       candidato = Integer.parseInt(d.getCompetidor());
       if(apuesta == -2 || candidato == -2) {
         historialT.setTorneo(torneo);
         return false;
       }
       if(apuesta == -1){
         System.out.println();
         System.out.println("No apostaste");
       } else {
         System.out.println();
         System.out.printf("Usted apostó %f por el candidato %d ", apuesta, candidato);
         cuentaActiva.apuestaMonto(apuesta);
       }
       ganador = ganadores[i];
       double cuota = 0;
       if(ganador == 2 * i) {
         System.out.println();
         System.out.println("Ganó el candidato " + (2 * i));
         appendFile("Ganó el candidato " + (2 * i));
         cuota = cuotas[2 * i];
       }
       else {
         System.out.println();
         System.out.println("Ganó el candidato " + (2 * i + 1));
         appendFile("Ganó el candidato " + (2 * i + 1));
         cuota = cuotas[2 * i + 1];
       }
       System.out.println("Ahora es el candidato " + i);
       appendFile("Ahora es el candidato " + i);
       if(ganador == candidato) {
         cuentaActiva.setGanadas();
         double gano = apuesta * cuota;
         cuentaActiva.ganoApuesta(gano);
         System.out.println();
         System.out.printf("Usted ganó $%f",gano);
       }
       else {
         cuentaActiva.setPerdidas();
         System.out.println();
         if(apuesta != -1) System.out.printf("Usted perdió $%f", apuesta);
       }
     }
     ronda++;
     partida = 0;
   }
   historialT.setTorneo(torneo);
   return true;
 }

 /* Método que se encarga de toda la
  * gestión relacionada a hacer el torneo
  * y las interacciones con el usuario.
  *
  * @return true si desea continuar.
  */
 private static boolean apostarTorneo() {
   char opcionH = 'n';
   Scanner sc = new Scanner(System.in);
   try {
        System.out.println();
        System.out.println("¿Desea ver el último torneo? (y/n)");
        opcionH = sc.next().charAt(0);
      } catch (InputMismatchException ime) {
        System.out.println("Para elegir la opción escriba sólo una opción válida.");
        opcionH = 'n';
        sc.nextLine();
      }
   if(opcionH == 'y') {
     System.out.println(readFile());
     try {
          System.out.println();
          System.out.println("¿Desea continuar el último torneo? (y/n)");
          opcionH = sc.next().charAt(0);
        } catch (InputMismatchException ime) {
          System.out.println("Para elegir la opción escriba sólo una opción válida.");
          opcionH = 'n';
          sc.nextLine();
        }
        if(opcionH == 'y') {
          boolean b = reanudarTorneo();
          if(b == false) return false;
        }
   }
   writeFile("");
   Torneo torneo = new Torneo();
   int[] candidatos = torneo.getCandidatos();
   for(int i = 0; i < candidatos.length; i++) {
     System.out.println("Candidato " + i + ". Habilidad: " + candidatos[i]);
     appendFile("Candidato " + i + ". Habilidad: " + candidatos[i]);
   }
   try {
     System.out.println("Faltan 5 segundos para comenzar.");
     Thread.sleep(5000);
   }
   catch(InterruptedException ie) {
     System.out.println("Se acabó el tiempo.");
   }
   float apuesta = 0;
   int candidato = 0;
   System.out.println();
   appendFile("\n");
   System.out.println("COMIENZA EL TORNEO");
   appendFile("COMIENZA EL TORNEO");
   int ronda = 1;
   while(ronda < 5) {
     torneo.setRonda(ronda);
     System.out.println("Ronda guardada " + torneo.getRonda());
     System.out.println();
     appendFile("\n");
     System.out.println("=============== RONDA " + ronda + " ===============");
     appendFile("=============== RONDA " + ronda + " ===============");
     System.out.println("Cuotas de la ronda " +  ronda);
     appendFile("Cuotas de la ronda " +  ronda);
     double[] cuotas = torneo.getCuotas(ronda);
     System.out.println();
     appendFile("\n");
     for(int i = 0; i < (16 / Math.pow(2, ronda - 1)); i++) {
       System.out.printf("Cuota candidato %d : $ %.2f %n", i, cuotas[i]);
       appendFile("Candidato " + i + ". Cuota: " + cuotas[i]);
     }
     torneo.juegaTorneo(ronda);
     int ganador = -1;
     int[] ganadores = torneo.getGanadores();
     for(int i = 0; i < (8 / Math.pow(2, ronda - 1)); i++) {
       torneo.setPartida(i);
       System.out.println();
       appendFile("\n");
       System.out.println("##########################################################");
       appendFile("##########################################################");
       System.out.println("Candidato " + (2 * i) + " vs candidato " + (2 * i + 1));
       appendFile("Candidato " + (2 * i) + " vs candidato " + (2 * i + 1));
       System.out.println();
       System.out.printf("Cuota candidato %d : %.2f %n", 2 * i, cuotas[2 * i]);
       appendFile("Cuota candidato: " + (2 * i) + ": " + cuotas[2 * i]);
       System.out.printf("Cuota candidato %d : %.2f %n", 2 * i + 1, cuotas[2 * i + 1]);
       appendFile("Cuota candidato: " + (2 * i + 1) + ": " + cuotas[2 * i + 1]);
       System.out.println();
       System.out.println("Ingrese el número del candidato por el que desea apostar.");
       System.out.println("Ingrese -2 en jugador o apuesta si desea salir al menú.");
       System.out.println();
       System.out.println("Tu saldo: " + cuentaActiva.getMonto());
       Datos d = new Datos();
       System.out.println();
       System.out.println("La carrera inicia en:  " + 10 + " segundos.");
       System.out.println("Tienes 10 s para apostar.");
       d.run();
       try {
         Thread.sleep((10-d.getTiempo())*1000);
       }
       catch(InterruptedException ie) {
         System.out.println("Interupción en torneo.");
       }
       apuesta = Float.parseFloat(d.getAp());
       candidato = Integer.parseInt(d.getCompetidor());
       if(apuesta == -2 || candidato == -2) {
         historialT.setTorneo(torneo);
         return false;
       }
       if(apuesta == -1){
         System.out.println();
         System.out.println("No apostaste");
       } else {
         System.out.println();
         System.out.printf("Usted apostó %f por el candidato %d ", apuesta, candidato);
         cuentaActiva.apuestaMonto(apuesta);
       }
       ganador = ganadores[i];
       double cuota = 0;
       if(ganador == 2 * i) {
         System.out.println();
         System.out.println("Ganó el candidato " + (2 * i));
         appendFile("Ganó el candidato " + (2 * i));
         cuota = cuotas[2 * i];
       }
       else {
         System.out.println();
         System.out.println("Ganó el candidato " + (2 * i + 1));
         appendFile("Ganó el candidato " + (2 * i + 1));
         cuota = cuotas[2 * i + 1];
       }
       System.out.println("Ahora es el candidato " + i);
       appendFile("Ahora es el candidato " + i);
       if(ganador == candidato) {
         cuentaActiva.setGanadas();
         double gano = apuesta * cuota;
         cuentaActiva.ganoApuesta(gano);
         System.out.println();
         System.out.printf("Usted ganó $%f",gano);
       }
       else {
         cuentaActiva.setPerdidas();
         System.out.println();
         if(apuesta != -1) System.out.printf("Usted perdió $%f", apuesta);
       }
     }
     ronda++;
     torneo.setRonda(ronda);
   }
   historialT.setTorneo(torneo);
   return true;
 }

 /* Método que se encarga de toda la
  * gestión relacionada a hacer una carrera
  * y las interacciones con el usuario.
  */
 private static void apostarCarreras() {
    historialC.leerCarreras();

    Carrera prueba = historialC.getCarreras();
    Competidor ganador;

    for(int i=0;i<10;i++){
      System.out.println(i+".-"+prueba.competidores[i]);
    }
    Competidor ganadorC;
    float apuestac=-1;
    int competidorc=-1;
    CuentaRegresiva cr= new CuentaRegresiva();
    Datos d= new Datos();
    boolean bandera=true;
    while(bandera){
      System.out.println();
      System.out.println("La carrera inicia en:  "+10+" segundos");
      System.out.println("tienes 10 s para apostar");
      System.out.println("Escribe e o E para salir de las carreras");
      d.run();
      if(d.getCompetidor().equals("E")||d.getCompetidor().equals("e")||d.getAp().equals("E")||d.getAp().equals("e")){
        bandera=false;
      }
      else{
        try {
          System.out.println("Faltan: "+(10-d.getTiempo())+" segundos para que incie la carrera.");
          Thread.sleep((10-d.getTiempo())*1000);
        }
        catch(InterruptedException ie) {

        }
        apuestac=Float.parseFloat(d.getAp());
        competidorc= Integer.parseInt(d.getCompetidor());
        if(apuestac==-1){
          System.out.println("No apostaste");
          ganadorC=prueba.JugarCarrera();
        }else{
          ganadorC=prueba.JugarCarrera();
          cuentaActiva.apostarCarrera(prueba.getCompetidores()[competidorc].getNombre(),ganadorC,apuestac);
          System.out.println("#############################");
        }
      }
    }
 }

  public static void main(String[] args) {

    historial = new Historial();
    Scanner sc = new Scanner(System.in);

    System.out.println("Bienvenido al programa de torneos y carreras");

    historial.leerCuentas();
    if(historial.esVacio()) {
      registrarse();
    }
    else {
      iniciarSesion();
    }
    System.out.println("CUENTA ACTIVA\n" + cuentaActiva.toString());
    while(true) {
      menu();
      if(cuentaActiva == null) {
        iniciarSesion();
        menu();
      }

      int decision = decideApostar();
      if(decision == 1) {
        boolean b = true;
        while(b) {
          b = apostarTorneo();
          historialT.guardarTorneo();
        }
      }
      else {
        apostarCarreras();
      }
    }
  }

}
