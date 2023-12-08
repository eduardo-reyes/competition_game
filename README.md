# Simulador de torneos y carreras

## Autores
    1. Eduardo Alfonso Reyes López
    2. Jonathan Bautista Parra

## Ejecución

Compilar -> javac -d . src/Estructuras/*.java
            javac -d . src/Proyecto/*.java

Ejecutar -> java edd.src.Proyecto.Principal

## Descripción

Se creó una clase llamada Cuenta, la cual se encarga de realizar las operaciones necesarias para poder apostar. Se tiene una clase competidor, la cual nos sirve para representar a los competidores de la carrera. Otra clase que se tiene es la clase carrera, la cual se encarga de hacer la simulación de las carreras, así como de hacer las operaciones necesarias para determinar las posiciones de cada uno de los competidores.
La clase torneo nos sirve para realizar las operaciones requeridas para determinar el ganador de cada partido.
Tenemos una clase Datos (un hilo) la cual se encarga de hacer la recolección de información ingresados por el usuario para apostar ya sea en un torneo o en una carrera.

En las clases Historial, HistorialCarrera e HistorialTorneo se hacen los procesos necesarios para poder escribir y leer los archivos donde se guardan las cuentas, las carreras y torneos realizados.

Finalmente en la clase principal se corre todo el programa para hacer la simulación de las apuestas, además se simulan los torneos en dicha clase.