package edd.src.Estructuras;

import java.time.Year;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Arrays;

/**
 *
 * Clase para monticulos maximos (maxheaps)
*/
public class MonticuloMaximo<T extends ComparableIndexable<T>> implements Collection<T>{


    private class Iterador implements Iterator<T>{

        private int indice;

        @Override public boolean hasNext(){
            return indice < elementos;
        }

        @Override public T next(){
            if (hasNext()) {
                return arbol[indice++];
            }
            throw new NoSuchElementException("No hay, no existe");
        }

    }

    private static class Adaptador<T extends Comparable<T>>
    implements ComparableIndexable<Adaptador<T>>{
        /* El elemento. */
        private T elemento;
        /* El índice. */
        private int indice;

        /* Crea un nuevo comparable indexable. */
        public Adaptador(T elemento) {
            this.elemento = elemento;
            this.indice = -1;
        }

        /* Regresa el índice. */
        @Override
        public int getIndice() {
            return this.indice;
        }

        /* Define el índice. */
        @Override
        public void setIndice(int indice) {
            this.indice = indice;
        }

        /* Compara un indexable con otro. */
        @Override
        public int compareTo(Adaptador<T> adaptador) {
            return this.elemento.compareTo(adaptador.elemento);
        }

        /* Regresa una representación en cadena del adaptador. */
        @Override
        public String toString() {
          //return elemento + " ";
          return "Elemento " + elemento + " índice " + indice;
        }

    }

    /* numero de elementos en el arreglo */
    private int elementos;
    /* Nuestro arbol representado como arreglo */
    private T[] arbol;

    /* Con esto podemos crear arreglos genericos sin que el compilador marque error */
    @SuppressWarnings("unchecked") private T[] nuevoArreglo(int n){
        return (T[])(new ComparableIndexable[n]);
    }

    /* Constructor vacío de montículo mínimo. */
    public MonticuloMaximo(){
        elementos = 0;
        arbol = nuevoArreglo(100);
    }

    /**
    * Constructor con parámetros. Dado una
    * colección iterable de un cierto número
    * de elementos n, construye el
    * montículo mínimo.
    *
    * @param iterable la colección iterable.
    * @param n el número de elementos.
    */
    public MonticuloMaximo(Iterable<T> iterable, int n){
        elementos = n;
        arbol = nuevoArreglo(n);
        int i = 0;
        for (T e : iterable) {
           arbol[i] = e;
           arbol[i].setIndice(i);
           i++;
        }
        for(int j = (elementos-1) /2; j >= 0; j--){
            monticuloMax(j);
        }
    }

    /**
    * Método auxiliar al constructor que convierte
    * el arreglo arbol[] en un max heap.
    *
    * @param i entero que representa el índice.
    */
    private void monticuloMax(int i){
        int izq = i * 2 + 1;
        int der = i * 2 + 2;
        int maximo = i;

        if (elementos <= i) {
            return;
        }
        if(izq < elementos && arbol[izq].compareTo(arbol[i]) > 0){
            maximo = izq;
        }
        if(der < elementos && arbol[der].compareTo(arbol[maximo]) > 0){
            maximo = der;
        }
        if(maximo == i){
            return;
        }
        else {
            swap(arbol[maximo],arbol[i]);
            monticuloMax(maximo);
        }
    }

    /**
     * Agrega un elemento al max heap.
     *
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *                                  <code>null</code>.
    */
    @Override public void add(T elemento){
        if (elementos == arbol.length) {
            duplicaSize();
        }
        elemento.setIndice(elementos);
        arbol[elementos] = elemento;
        elementos++;
        recorreArriba(elementos - 1);
    }

    /**
    * Método que duplica la longitud
    * del arreglo arbol[] que representa
    * el max heap.
    */
    private void duplicaSize(){
        T[] arr = nuevoArreglo(arbol.length * 2);
        elementos = 0;
        for(T e: arbol){
            arr[elementos++] = e;
        }
        this.arbol = arr;
    }

    /**
    * Método que reacomoda hacia arriba.
    *
    * @param i entero que representa el índice.
    */
    private void recorreArriba(int i){
        int padre = (i - 1) / 2;
        int m = i;
        if(padre >= 0 && arbol[padre].compareTo(arbol[i]) < 0){
            m = padre;
        }
        if (m != i) {
            this.swap(arbol[i],arbol[m]);
            recorreArriba(m);
        }
    }

    /**
     * Elimina el elemento maximo del monticulo
     *
     * @returns el elemento máximo del max heap.
     */
    public T delete(){
        if(elementos == 0){
            throw new IllegalStateException("Monticulo vacio");
        }
        T e = arbol[0];
        boolean bool = delete(e);
        if(bool){
            return e;
        }
        else{
            return null;
        }
    }

    /**
     * Elimina un elmento del monticulo
     *
     * @param elemento el elemento a eliminar en el max heap.
     * @returns boolean que es true si lo encontró y eliminó.
     */
    @Override public boolean delete(T elemento){
        if(elemento == null || isEmpty()) {
            return false;
        }
        if(!contains(elemento)){
            return false;
        }
        int i = elemento.getIndice();
        if(i < 0 || elementos <= i)
            return false;
        swap(arbol[i], arbol[elementos - 1]);
        arbol[elementos - 1] = null;
        elementos --;
        recorreAbajo(i);
        return true;
    }

    /**
    * Método que intercambia los índices de dos
    * elementos del arreglo.
    *
    * @param i el índice del primer elemento.
    * @param j el índice del segundo elemento.
    */
    private void swap(T i, T j) {
        int aux = j.getIndice();
        arbol[i.getIndice()] = j;
        arbol[j.getIndice()] = i;
        j.setIndice(i.getIndice());
        i.setIndice(aux);
    }

    /**
    * Método que reacomoda hacia abajo.
    *
    * @param i entero que representa el índice.
    */
    private void recorreAbajo(int i){
        if(i < 0){
            return;
        }
        int izq = 2 * i + 1;
        int der = 2 * i + 2;
        int max = der;

        if(izq >= elementos && der >= elementos){
            return;
        }
        if(izq < elementos){
            if (der < elementos) {
                if (arbol[izq].compareTo(arbol[der]) > 0) {
                    max = izq;
                }
            }
            else{
                max = izq;
            }
        }
        if(arbol[max].compareTo(arbol[i]) > 0){
            //Este swap ya esta
            swap(arbol[i], arbol[max]);
            recorreAbajo(max); // recorreAbajo(i);
        }
    }

    /**
     * Nos dice si un elemento está contenido en el max heap.
     *
     * @param elemento el elemento que queremos verificar si está contenido en
     *                 la colección.
     * @return <code>true</code> si el elemento está contenido en la colección,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contains(T elemento){
        for(T e: arbol){
            if(elemento.equals(e))
                return true;
        }
        return false;
    }

    /**
     * Nos dice si el max heap está vacío.
     *
     * @return <code>true</code> si la colección es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean isEmpty(){
        return elementos == 0;
    }

    /**
     * Vacía el max heap.
     *
     */
    @Override
    public void empty() {
        for (int i = 0; i < elementos; i++) {
            arbol[i] = null;
        }
        elementos = 0;
    }

    /**
     * Regresa el número de elementos en el max heap.
     *
     * @return el número de elementos en la colección.
     */
    @Override
    public int size(){
        return elementos;
    }

    /**
    * Método que rregresa el elemento en cierto índice.
    *
    * @param i entero que representa el índice.
    */
    public T get(int i) {
        if(i < 0 || i >= elementos) {
            throw new NoSuchElementException("Indice no valido");
        }
        return arbol[i];
    }

    /**
     * Regresa una representación en cadena del max heap.
     *
     * @return una representación en cadena de la coleccion.
     */
    @Override public String toString(){
        if(isEmpty()) return "";
        String resultado ="";
        for (int i = 0; i < elementos - 1; i++) {
            resultado += arbol[i].toString() + ",";
        }
        resultado += arbol[elementos - 1].toString();
        return resultado;
    }

    /**
     * Nos dice si el max heap es igual a la colección recibida.
     *
     * @param coleccion la coleccion con el que hay que comparar.
     * @return <tt>true</tt> si la coleccion es igual a la coleccion recibido;
     *         <tt>false</tt> en otro caso.
     */
    @Override
    public boolean equals(Object obj) {
        if(obj==null || getClass() != obj.getClass()){
            return false;
        }
        @SuppressWarnings("unchecked") MonticuloMaximo<T> monticulo = (MonticuloMaximo<T>)obj;
        if (elementos != monticulo.elementos) {
            return false;
        }
        for (int i = 0; i < elementos; i++) {
            if(!arbol[i].equals(monticulo.arbol[i])){
                return false;
            }
        }
        return true;
    }

    /**
     * Regresa un iterador para iterar el montículo mínimo. El montículo se
     * itera en orden BFS.
     *
     * @return un iterador para iterar el montículo mínimo.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
    * Método que regresa true si el arreglo en el parámetro
    * es un montículo máximo.
    *
    * @param arr arreglo a verificar si es un max heap.
    * @return true si es max heap.
    */
    public boolean esMontMax(T[] arr) {
      if(arr == null) return false;
      return verificar(arr, 0);
    }

    /**
    * Método auxiliar recursivo de esMontMax que hace
    * toda la verificación acerca del cumplimiento de
    * las condiciones que debe cumplir el arreglo
    * para ser max heap.
    *
    * @param arr el arreglo a verificar.
    * @param i el índice desde el cual se examina si los
    * hijos de ese elemento son menores.
    * @return true si el elemento en el índice cumple la propiedad.
    */
    private boolean verificar(T[] arr, int i){
        if(i < 0){
          return false;
        }
        if(i >= arr.length){
          return true;
        }
        int longi = arr.length;
        int izq = 2 * i + 1;
        int der = 2 * i + 2;
        if(izq >= longi && der >= longi){
            return true;
        }
        boolean si = true;
        if(izq < longi){
          if(arr[izq].compareTo(arr[i]) < 0) {
            si = verificar(arr, izq);
          }
          else {
            return false;
          }
        }
        boolean sd = true;
        if(der < longi){
          if(arr[der].compareTo(arr[i]) < 0) {
            sd = verificar(arr, der);
          }
          else {
            return false;
          }
        }
        return si && sd;
      }

      /**
      * Método que convierte un arreglo que representa
      * un montículo mínimo a uno máximo en O(n).
      *
      * @param arr arreglo T que es max heap.
      * @return arreglo T convertido en un max heap.
      */
      public MonticuloMaximo<T> MontMin_MontMax(MonticuloMinimo<T> minh) {
        Iterator<T> it = minh.iterator();
        T[] arr = nuevoArreglo(minh.size());
        int count = 0;
        while (it.hasNext()) {
          arr[count] = it.next();
          count++;
        }
        MonticuloMaximo<T> m = new MonticuloMaximo<T>();
        m.arbol = arr;
        m.elementos = arr.length;
        for(int i = 0; i < m.elementos; i++) {
          m.arbol[i].setIndice(i);
        }
        for(int j = (m.elementos - 1) / 2; j >= 0; j--) {
          m.recorreAbajo(j);
        }
        return m;
      }
}