import java.util.Hashtable;

public class Vertices {
    private Hashtable<Integer, Vertice> vertices = new Hashtable<>();

    public boolean agregarVertice(Vertice vertice) {
        if(vertice == null){
            return false;
        }
        vertices.put(vertice.getId(), vertice);
        return true;
    }
    public Vertice obtenerVertice(int id) {return vertices.get(id);}
    public boolean existeVertice(int id) {return vertices.containsKey(id);}
    public int cantidadVertices() {return vertices.size();}

}
