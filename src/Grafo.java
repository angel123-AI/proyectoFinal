import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;

public class Grafo {
    private Vertices vertices = new Vertices();
    private Hashtable<Integer, LinkedList<Arcos>> adyacencia =
            new Hashtable<>();

    private int idEntrada = -1;
    private int idSalida= -1;
    private int maxId = 0;

    private ResultadoBusqueda resultadoDFS = null;
    private ResultadoBusqueda resultadoBFS = null;]
    private ResultadoBusqueda resultadoDijkstra = null;

private int[] cambioFila ={-1,0,1,0};
private int[] cambioColumna ={0,1,0,-1};

public boolean agregarVertice(Vertice vertice){
    if (vertice == null){
        return false;
    }
    vertices.agregarVertice(vertice);
    adyacencia.put(vertice.getId(), new LinkedList<Arcos>());
    if (vertice.getId() == maxId){
        maxId = vertice.getId();
    }
    return true;
}
public boolean agregarArco(int id1, int id2, int costo){
    Vertice v1 = vertices.obtenerVertice(id1);
    Vertice v2 = vertices.obtenerVertice(id2);

    if (v1 == null || v2 == null || costo <= 0){
        return false;
    }
    Arcos arco = new Arcos(v1,v2,costo);
    adyacencia.get(id1).add(arco);
    adyacencia.get(id2).add(arco);
    return true;
}

public boolean setEntrada(int id){
    if (!vertices.existeVertice(id)){
        return false;
    }
    idEntrada = id;
    return true;
}
public boolean setSalida(int id){
    if (!vertices.existeVertice(id)){
        return false;
    }
    idSalida = id;
    return true;
}
public boolean tieneExtremos(){
    return idEntrada != -1 && idSalida != -1;
}
public int getIdEntrada() {
    return idEntrada;
}
public int getIdSalida() {
    return idSalida;
}
public int getMaxId() {
    return maxId;
}
public Vertice obtenerVertice(int id){
    return vertices.obtenerVertice(id);
}
public int cantidadVertices(){
    return vertices.cantidadVertices();
}
public ResultadoBusqueda getResultadoDFS(){
    return resultadoDFS;
}
public ResultadoBusqueda getResultadoBFS(){
    return resultadoBFS;
}
public ResultadoBusqueda getResultadoDijkstra(){
    return resultadoDijkstra;
}
private ArrayList<Arcos> obtenerArcosOrdenados(int id){
    ArrayList<Arcos> orden = new ArrayList<>();
    Vertice actual = vertices.obtenerVertice(id);

    if (actual == null){
        return orden;
    }

    LinkedList<Arcos> lista = adyacencia.get(id);
    for(int d = 0; d < 4; d++){
        for(int i = 0; i < lista.size(); i++){
            Arcos arcos = lista.get(i);
            Vertice otro = arcos.obtenerOtroExtremo(actual);
            if(otro.getFila() == actual.getFila() + cambioFila[d] && otro.getColumna()
           == actual.getColumna() + cambioColumna[d] ){
                orden.add(arcos);
            }
        }
    }
    return orden;
}
private ArrayList<Vertice> obtenerVecinos(int id){
    ArrayList<Vertice> vecinos = new ArrayList<>();
    Vertice actual = vertices.obtenerVertice(id);
    ArrayList<Arcos> arcos = obtenerArcosOrdenados(id);

    for (int i = 0; i < arcos.size(); i++){
        vecinos.add(arcos.get(i).obtenerOtroExtremo(actual));

    }
    return vecinos;
}

private ArrayList<Vertice> reconstruirCamino(int[] procesador){
    ArrayList<Vertice> camino = new ArrayList<>();
    int actual = idSalida;
    camino.add(0, vertices.obtenerVertice(actual));
    while (actual != idEntrada){
        actual = procesador[actual];
        camino.add(0, vertices.obtenerVertice(actual));
    }
    return camino;
}
}
