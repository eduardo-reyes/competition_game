package edd.src.Estructuras;

public class Prueba implements ComparableIndexable<Prueba>{
    int i;
    int indice;

    public Prueba(int i) {
        this.i = i;
    }
    public Prueba(){
        this.i = 0;
    }

    @Override
    public String toString() {
      return Integer.toString(this.i);
    }

    @Override
    public int compareTo(Prueba otro) {
      if(this.i == otro.i) return 0;
      if(this.i < otro.i) return -1;
      return 1;
    }

    @Override
    public int getIndice() {
        return this.indice;
    }

    @Override
    public void setIndice(int indice) {
        this.indice = indice;
    }


}
