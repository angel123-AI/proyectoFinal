public class Arcos {

    private Vertice origen;
    private Vertice destino;
    private int costo;
    public Arcos(Vertice origen, Vertice destino, int costo){
        this.origen = origen;
        this.destino = destino;
        this.costo = costo;
    }
    public Vertice getOrigen(){
        return origen;
    }
    public Vertice getDestino(){
        return destino;
    }
    public int getCosto(){
        return costo;
    }
    public Vertice obtenerOtroExtremo(Vertice actual){
        if(actual.getId() == origen.getId()){
            return destino;
        }
        return origen;
    }
}
