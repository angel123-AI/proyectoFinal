public class Vertice {
    private int id;
    private int fila;
    private int columna;

    public Vertice(int id, int fila, int columna) {
        this.id = id;
        this.fila = fila;
        this.columna = columna;
    }
    public int getId() {

        return id;
    }
    public int getFila(){

        return fila;
    }
    public int getColumna(){

        return columna;
    }

    public String toString(){

        return "Vertice" + id + "(" + fila + ", " + columna + ")";
    }

}
