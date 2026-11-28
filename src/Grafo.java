import java.util.*;

public class Grafo {
    private Vertices vertices = new Vertices();
    private Hashtable<Integer, LinkedList<Arcos>> adyacencia =
            new Hashtable<>();

    private int idEntrada = -1;
    private int idSalida= -1;
    private int maxId = 0;

    private ResultadoBusqueda resultadoDFS = null;
    private ResultadoBusqueda resultadoBFS = null;
    private ResultadoBusqueda resultadoDijkstra = null;

private int[] cambioFila ={-1,0,1,0};
private int[] cambioColumna ={0,1,0,-1};

public boolean agregarVertice(Vertice vertice){
    if (vertice == null){
        return false;
    }
    vertices.agregarVertice(vertice);
    adyacencia.put(vertice.getId(), new LinkedList<Arcos>());
    if (vertice.getId() > maxId){
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
public List<Vertice> DFS(){
    ArrayList<Vertice> visitados = new ArrayList<>();
    if (!tieneExtremos()) {
        return visitados;
    }
    long inicio = System.nanoTime();
    boolean[] procesado = new boolean[maxId + 1];
    int[] predecesor = new int[maxId + 1];
    Stack<Integer> pila = new Stack<>();
    pila.push(idEntrada);
    boolean encontrada = false;
    while(!pila.isEmpty() && !encontrada){
        int actual = pila.pop();
        if (procesado[actual]){
            continue;
        }
        procesado[actual] = true;
        visitados.add(vertices.obtenerVertice(actual));
        if (actual==idSalida){
            encontrada = true;
        }else {
            ArrayList<Vertice> vecinos = obtenerVecinos(actual);
            for (int i = vecinos.size() - 1; i >= 0; i--){
                int idVecino = vecinos.get(i).getId();
                if (!procesado[idVecino]){
                    predecesor[idVecino] = actual;
                    pila.push(idVecino);
                }

            }
        }
    }
    ArrayList<Vertice> camino = new ArrayList<>();
    if (encontrada){
        camino = reconstruirCamino(predecesor);
    }
    long tiempo = System.nanoTime() - inicio;
    resultadoDFS = new ResultadoBusqueda("DFS", visitados,camino,encontrada,tiempo);
    return visitados;
}
public List<Vertice> BFS(){
    ArrayList<Vertice> visitados = new ArrayList<>();
    if (!tieneExtremos()) {
        return visitados;
    }
    long inicio = System.nanoTime();
    boolean[] descubierto = new boolean[maxId + 1];
    int[] predecesor = new int [maxId + 1];

    LinkedList<Integer> cola = new LinkedList<>();
    cola.addLast(idEntrada);
    descubierto[idEntrada] = true;
    boolean encontrada = false;
    while (!cola.isEmpty() && !encontrada){
        int actual = cola.removeFirst();
        visitados.add(vertices.obtenerVertice(actual));
        if (actual==idSalida){
            encontrada = true;
        }else {
            ArrayList<Vertice> vecinos = obtenerVecinos(actual);
            for (int i = 0; i < vecinos.size(); i++){
                int idVecino = vecinos.get(i).getId();
                if (!descubierto[idVecino]){
                    descubierto[idVecino] = true;
                    predecesor[idVecino] = actual;
                    cola.addLast(idVecino);
                }
            }
        }
    }
    ArrayList<Vertice> camino = new ArrayList<>();
    if (encontrada){
        camino = reconstruirCamino(predecesor);
    }
    long tiempo = System.nanoTime() - inicio;
    resultadoBFS = new ResultadoBusqueda("BFS", visitados,camino, encontrada,tiempo);
    return visitados;

}
    public List<Vertice> caminoMasCorto() {

        ArrayList<Vertice> visitados = new ArrayList<Vertice>();

        if (!tieneExtremos()) {
            return visitados;
        }

        long inicio = System.nanoTime();

        int[] distancia = new int[maxId + 1];
        int[] predecesor = new int[maxId + 1];
        boolean[] definitivo = new boolean[maxId + 1];

        for (int i = 0; i <= maxId; i++) {
            distancia[i] = Integer.MAX_VALUE;
        }

        distancia[idEntrada] = 0;

        boolean encontrada = false;

        boolean seguir = true;

        while (seguir && !encontrada) {

            // Buscar el vertice no definitivo con menor distancia.
            // Se recorre de menor a mayor id, asi que en un empate
            // se queda el de menor id.
            int actual = -1;

            for (int id = 1; id <= maxId; id++) {

                if (!definitivo[id]
                        && distancia[id] != Integer.MAX_VALUE
                        && (actual == -1 || distancia[id] < distancia[actual])) {

                    actual = id;
                }
            }

            if (actual == -1) {

                seguir = false;

            } else {

                definitivo[actual] = true;

                Vertice verticeActual = vertices.obtenerVertice(actual);

                visitados.add(verticeActual);

                if (actual == idSalida) {

                    encontrada = true;


                } else {

                    ArrayList<Arcos> arcos = obtenerArcosOrdenados(actual);

                    for (int i = 0; i < arcos.size(); i++) {

                        Arcos arco = arcos.get(i);

                        int idVecino = arco.obtenerOtroExtremo(verticeActual).getId();

                        int nuevaDistancia = distancia[actual] + arco.getCosto();

                        if (!definitivo[idVecino]
                                && nuevaDistancia < distancia[idVecino]) {

                            distancia[idVecino] = nuevaDistancia;
                            predecesor[idVecino] = actual;
                        }
                    }
                }
            }
        }

        ArrayList<Vertice> camino = new ArrayList<Vertice>();

        if (encontrada) {
            camino = reconstruirCamino(predecesor);
        }

        long tiempo = System.nanoTime() - inicio;

        resultadoDijkstra = new ResultadoBusqueda(
                "Dijkstra", visitados, camino, encontrada, tiempo);

        return visitados;
    }
}

