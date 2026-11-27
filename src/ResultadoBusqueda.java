import java.util.ArrayList;
import java.util.List;

public class ResultadoBusqueda {

    private String nombreAlgoritmo;
    private ArrayList<Vertice> visitados;
    private ArrayList<Vertice> camino;
    private boolean solucionEncontrada;
    private long tiempoNanosegundos;

    public ResultadoBusqueda(String nombreAlgoritmo,
                             List<Vertice> visitados, List<Vertice> camino,
                             boolean solucionEncontrada, long tiempoNanosegundos) {

        this.nombreAlgoritmo = nombreAlgoritmo;
        this.visitados = new ArrayList<Vertice>(visitados);
        this.camino = new ArrayList<Vertice>(camino);
        this.solucionEncontrada = solucionEncontrada;
        this.tiempoNanosegundos = tiempoNanosegundos;
    }

    public String getNombreAlgoritmo() {

        return nombreAlgoritmo;
    }

    public List<Vertice> getVisitados() {

        return visitados;
    }

    public List<Vertice> getCamino() {

        return camino;
    }

    public boolean isSolucionEncontrada() {

        return solucionEncontrada;
    }

    public long getTiempoNanosegundos() {

        return tiempoNanosegundos;
    }

    public int getCantidadVisitados() {

        return visitados.size();
    }

    public int getLongitudMovimientos() {

        if (!solucionEncontrada) {
            return -1;
        }

        return camino.size() - 1;
    }
}