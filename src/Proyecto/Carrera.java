package edd.src.Proyecto;
import edd.src.Estructuras.*;
import java.io.Serializable;
import java.util.Random;
import java.util.Scanner;
public class Carrera implements Serializable{
/*
* Clase que simula una carrera de 10 competidores.
*/

/*
* Competidores de la carrera.
*/
Competidor[] competidores = new Competidor[10];
/*
* Constructor de la clase. Genera los 10 competidores así como las últimas
* 5 partiduas de cada uno de ellos ( de manera aleatoria).
*/
public Carrera(){
  String[] nombres={"Lucho","Zanahoria","Goku","Tortillota","Flash","RayoMakuin","Resistol","Bichota","Saitama","krispin"};
  Random random = new Random();

  int pos=-1;
  for(int i=0;i<10;i++){
    competidores[i]= new Competidor(nombres[i]);
  }

  for(int j=0;j<5;j++){
    int[] posusadas= {0,0,0,0,0,0,0,0,0,0};
     for(int k=0;k<10;k++){
        pos= random.nextInt(10) + 1;
        if(posusadas[pos-1]==0){

          posusadas[pos-1]=pos;
          competidores[k].agregarPosicion(pos,j);

        }else{
          k--;
        }

     }
  }

}
/*
* Métod que regresa la lista de los competidores creados con el constructor.
* @return Competidor[] competidores de la carrera.
*/
public Competidor[] getCompetidores(){
  return competidores;
}
/*
* Método que simula una carrera entre los competidores.
* @return Competidor competidor ganador de la carrera.
*/
public Competidor JugarCarrera(){
  Competidor[] posicionesC=new Competidor[10];
  int posgan=-1;
  Random random = new Random();
  float proba;
  float[] probas= new float[10];

  probas[0]=competidores[0].probabilidad();
  for(int i=1;i<10; i++){
    probas[i]= probas[i-1]+competidores[i].probabilidad();

  }
  proba= random.nextFloat();
  if(proba<=probas[0]){
    posgan=0;
  }else{
    for(int i=1;i<10; i++){
      if(proba>probas[i-1]&& proba<=probas[i]){
        posgan=i;
      }
    }
  }
  competidores[posgan].actualizarHistorial(1);
  posicionesC[0]=competidores[posgan];
  System.out.println("ganó : "+ posicionesC[0]);

  int pos=0;
  int contador=0;
  for( int k=0;k<10;k++){

    pos= random.nextInt(9) + 1;
    if(posicionesC[pos]==null&&k!=posgan){
      competidores[k].actualizarHistorial(pos+1);
      posicionesC[pos]=competidores[k];
      contador++;
    }else{
      if(contador<9&&k!=posgan){
        k--;
      }
    }

  }
  for(int j=0;j<10;j++){
    System.out.println(j+".- "+competidores[j]);
  }
  return competidores[posgan];
  }
}
