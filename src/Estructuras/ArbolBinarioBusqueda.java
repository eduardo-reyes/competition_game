package edd.src.Estructuras;
import java.util.*;

public class ArbolBinarioBusqueda<T extends Comparable<T>> extends ArbolBinario<T> {
    private class Iterador implements Iterator<T>{
        private Pila<Vertice> pila;

        public Iterador(){
            pila = new Pila<Vertice>();
            Vertice p = raiz;
            while (p!= null) {
                pila.push(p);
                p = p.izquierdo;
            }
        }

        public boolean hasNext() {
          return !pila.isEmpty();
        }

        public T next(){
            if(pila.isEmpty()){
                throw new NoSuchElementException("vacio");
            }
            Vertice v = pila.pop();
            if(v.derecho != null){
                Vertice u = v.derecho;
                while (u!=null) {
                    pila.push(u);
                    u=u.izquierdo;
                }
            }

            return v.elemento;
        }

    }

    /**
      * Constructor sin parámetros. Para no perder el constructor sin parámetros
      * de {@link ArbolBinario}.
      */
    public ArbolBinarioBusqueda() {
      super();
    }

    public ArbolBinarioBusqueda(Collection<T> coleccion) {
      super(coleccion);
    }

    /**
     * Constructor de la clase.
     *
     * @param lista es la lista de la que se hará el árbol.
     * @param isSorted es true si la lista está ordenada y viceversa.
     */
    public ArbolBinarioBusqueda(Lista<T> lista, boolean isSorted ){
        if (isSorted) {
            buildSorted(lista);
        }
        else{
            buildUnsorted(lista);
        }

    }

    /**
     * Método auxiliar del constructor que recibe una
     * lista desordenada y la ordena para llamar al
     * método buildSorted.
     *
     * @param elemento el elemento a agregar.
     *
     */
    private void buildUnsorted(Lista<T> lista) {
      ArrayList<T> a = new ArrayList<T>();
      IteradorLista<T> iter = lista.iteradorLista();
      while(iter.hasNext()) {
        a.add(iter.next());
      }
      Collections.sort(a);
      lista.empty();
      for (int i = 0; i < a.size(); i++) {
        lista.add(a.get(i));
      }
      buildSorted(lista);
    }

    /**
     * Método auxiliar del constructor que recibe una
     * lista ordenada y construye el BST.
     *
     * @param lista ordenada a partir de la cual se construirá.
     *
     */
    private void buildSorted(Lista<T> lista) {
      IteradorLista<T> iter = lista.iteradorLista();
      raiz = buildRec(iter, lista.size());
    }

    /**
     * Método auxiliar del constructor que recibe el
     * iterador de una lista ordenada y un tamaño n,
     * y crea un BST desde las hojas.
     *
     * @param iter es el iterador de la lista.
     * @param n es el número de elementos.
     *
     */
    private Vertice buildRec(IteradorLista<T> iter, int n) {
        if (n <= 0) {
          return null;
        }
        Vertice izq = buildRec(iter, n / 2);
        Vertice v = nuevoVertice(iter.next());
        elementos++;
        v.izquierdo = izq;
        v.derecho = buildRec(iter, n - (n / 2) - 1);
        return v;
    }

    /**
     * Construye el BST balanceado a partir de un árbol binario.
     *
     * @param arbol es el árbol binario a convertir en BST.
     */
    public void convertBST(ArbolBinario<T> arbol) {
      empty();
      Lista<T> lista = new Lista<T>();
      lista = inorden(arbol.raiz, lista);
      buildUnsorted(lista);
    }

    /**
     * Hace un recorrido inorden de un árbol binario y va
     * agregando sus elementos a una lista según lo recorre.
     *
     * @param nodo es el nodo sobre el cual se hacen las verificaciones.
     * @param lista es la lista a la cual se agregan los elementos.
     */
    private Lista<T> inorden(Vertice nodo, Lista<T> lista) {
      if (nodo == null) {
        return lista;
      }
      inorden(nodo.izquierdo, lista);
      lista.add(nodo.get());
      inorden(nodo.derecho, lista);
      return lista;
    }

    /**
     * Función que nos permite preguntar por la existencia del elemento en el BST.
     *
     * @param elemento el elemento a buscar.
     */
    public boolean search(T elemento) {
      return search(raiz, elemento);
    }

    /**
     * Función que nos permite preguntar por la existencia del elemento en el BST.
     *
     * @param nodo es el nodo con el cual se harán las verificaciones.
     * @param elemento el elemento a buscar.
     */
    public boolean search(Vertice nodo, T elemento) {
      boolean b = true;
      if(nodo == null) {
        b = false;
        return false;
      }
      if(nodo.get().compareTo(elemento) == 0) {
        b = true;
        return true;
      }
      if(nodo.get().compareTo(elemento) > 0) {
        b = search(nodo.izquierdo, elemento);
      }
      if(nodo.get().compareTo(elemento) < 0) {
        b = search(nodo.derecho, elemento);
      }
      return b;
    }

    /**
     * Función que nos permite insertar un elemento en el BST.
     *
     * @param elemento el elemento a insertar.
     */
    public void insert(T elemento) {
      insert(raiz,elemento);
    }

    /**
     * Función que nos permite insertar un elemento en el BST.
     *
     * @param nodo es el nodo con el cual se harán las verificaciones.
     * @param elemento el elemento a insertar.
     */
    public void insert(Vertice nodo, T elemento) {
      if(raiz == null) {
        Vertice v = nuevoVertice(elemento);
        elementos++;
        raiz = v;
      }
      insertAux(raiz, elemento);
    }

    /**
     * Función auxiliar de insertar que aplica recursión.
     *
     * @param nodo es el nodo con el cual se harán las verificaciones.
     * @param elemento el elemento a insertar.
     */
    private void insertAux(Vertice nodo, T elemento) {
      if(elemento.compareTo(nodo.get()) < 0) {
        if(nodo.izquierdo == null) {
          Vertice v = nuevoVertice(elemento);
          elementos++;
          nodo.izquierdo = v;
        }
        else {
          insertAux(nodo.izquierdo, elemento);
        }
      }
      if(elemento.compareTo(nodo.get()) > 0) {
        if(nodo.derecho == null) {
          Vertice v = nuevoVertice(elemento);
          elementos++;
          nodo.derecho = v;
        }
        else {
          insertAux(nodo.derecho, elemento);
        }
      }
    }

    /**
     * Función que nos permite eliminar un elemento en el BST. Si el nodo tiene un hijo,
     * se sube el hijo al lugar del padre. si el nodo tiene más de un hijo, deberás encontrar al sucesor inOrder
     * y reemplazarlo en el árbol. El cual será el mínimo de el subárbol derecho del nodo a eliminar.
     *
     * @param elemento el elemento a eliminar.
     */
     @Override
    public boolean delete(T elemento) {
      return delete(raiz, elemento);
    }

    /**
     * Función que nos permite eliminar un elemento en el BST. Si el nodo tiene un hijo,
     * se sube el hijo al lugar del padre. si el nodo tiene más de un hijo, deberás encontrar al sucesor inOrder
     * y reemplazarlo en el árbol. El cual sera el mínimo de el subárbol derecho del nodo a eliminar.
     *
     * @param nodo es el nodo con el cual se harán las verificaciones.
     * @param elemento el elemento a eliminar.
     */
    public boolean delete(Vertice nodo, T elemento) {
      boolean b = search(elemento);
      if(b) {
        raiz = deleteR(raiz, elemento);
      }
      return b;
    }

    /**
     * Función que nos permite eliminar un elemento en el BST. Si el nodo tiene un hijo,
     * se sube el hijo al lugar del padre. si el nodo tiene más de un hijo, deberás encontrar al sucesor inOrder
     * y reemplazarlo en el árbol. El cual sera el mínimo de el subárbol derecho del nodo a eliminar.
     *
     * @param nodo es el nodo con el cual se harán las verificaciones.
     * @param elemento el elemento a eliminar.
     */
    private Vertice deleteR(Vertice nodo, T elemento) {
      if(nodo == null) {
        return nodo;
      }
      if(elemento.compareTo(nodo.get()) < 0) {
        nodo.izquierdo = deleteR(nodo.izquierdo, elemento);
      }
      else if(elemento.compareTo(nodo.get()) > 0) {
        nodo.derecho = deleteR(nodo.derecho, elemento);
      }
      else {
        // Vértice con uno o dos hijos.
        if (nodo.izquierdo == null) {
          elementos--;
          return nodo.derecho;
        }
        else if (nodo.derecho == null) {
          elementos--;
          return nodo.izquierdo;
        }
        // Vértice con dos hijos.
        nodo.elemento = valorMin(nodo.derecho);
        // Eliminar el sucesor inorden.
        nodo.derecho = deleteR(nodo.derecho, nodo.get());
        elementos--;
      }
      return nodo;
    }

    /**
     * Método auxiliar que encuentra el valor mínimo
     * en el subárbol deseado.
     *
     * @param nodo es el nodo con el cual se harán las verificaciones.
     * @return min es el mínimo del subárbol.
     */
    protected T valorMin(Vertice nodo) {
      T min = nodo.get();
      while (nodo.izquierdo != null) {
        min = nodo.izquierdo.get();
        nodo = nodo.izquierdo;
      }
      return min;
    }

    /**
     * Método que imprime el árbol con in-Order DFS.
     *
     * @param nodo es el nodo con el cual se harán las verificaciones.
     * @return String el recorrido in-Order.
     */
    /*public String toString() {
      return toString(raiz);
    }

    /**
     * Método que imprime el árbol con in-Order DFS.
     *
     * @param nodo es el nodo con el cual se harán las verificaciones.
     * @return String el recorrido in-Order.
     */
    public String toString(Vertice nodo) {
      Lista<T> lista = new Lista<T>();
      Iterator<T> iter = iterator();
      while(iter.hasNext()) {
        lista.add(iter.next());
      }
      return lista.toString();
    }

    /**
     * Método que balancea el árbol BST.
     *
     * @param nodo es el nodo con el cual se harán las verificaciones.
     */
    public void balance() {
      balance(raiz);
    }

    /**
     * Método que balancea el árbol BST.
     *
     * @param nodo es el nodo con el cual se harán las verificaciones.
     */
    public void balance(Vertice nodo) {
      if(isEmpty()) return;
      ArrayList<T> a = new ArrayList<T>();
      Iterator<T> iter = iterator();
      while(iter.hasNext()) {
        a.add(iter.next());
      }
      empty();
      int mitad = (int) a.size() / 2;
      Vertice v = nuevoVertice(a.get(mitad));
      raiz = v;
      for(T elemento : a) {
        insert(raiz, elemento);
      }
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden.
     *
     * @return un iterador para iterar el árbol.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterador();
    }

    //@Override
    public T pop() {
      return null;
    }

    @Override
    public void add(T elemento) {
      return;
    }

}