import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
public class LectorLaberinto {
    private char[][]laberinto;
    private int filas;
    private int columnas;
    private int entradaFila;
    private int entradaColumna;
    private int salidaFila;
    private int salidaColumna;

    public String cargarLaberinto(String ruta) {

        File archivo = new File(ruta);

        if (!archivo.exists()) {
            return "El archivo no existe.";
        }

        if (!archivo.isFile()) {
            return "La ruta no corresponde a un archivo.";
        }

        if (!archivo.canRead()) {
            return "No se puede leer el archivo.";
        }

        // Leer las lineas (sin trim: se conservan los espacios).
        ArrayList<String> lineas = new ArrayList<String>();

        try {
            Scanner lector = new Scanner(archivo);

            while (lector.hasNextLine()) {
                lineas.add(lector.nextLine());
            }

            lector.close();

        } catch (FileNotFoundException e) {
            return "No se pudo leer el archivo.";
        }

        if (lineas.size() == 0) {
            return "El archivo esta vacio.";
        }

        int nuevasFilas = lineas.size();

        int nuevasColumnas = lineas.get(0).length();

        if (nuevasColumnas == 0) {
            return "Las filas deben tener al menos una columna.";
        }

        // Todas las filas con la misma longitud.
        for (int f = 0; f < nuevasFilas; f++) {

            if (lineas.get(f).length() != nuevasColumnas) {
                return "La fila " + (f + 1) + " tiene una longitud diferente.";
            }
        }

        char[][] nuevo = new char[nuevasFilas][nuevasColumnas];

        int cuentaE = 0;
        int cuentaS = 0;

        int eF = -1;
        int eC = -1;
        int sF = -1;
        int sC = -1;

        // Validar caracteres y copiar.
        for (int f = 0; f < nuevasFilas; f++) {

            for (int c = 0; c < nuevasColumnas; c++) {

                char ch = lineas.get(f).charAt(c);

                if ("+-| #ES".indexOf(ch) < 0) {
                    return "Caracter no valido en fila " + (f + 1)
                            + ", columna " + (c + 1)
                            + " (las tabulaciones no sustituyen espacios).";
                }

                nuevo[f][c] = ch;

                if (ch == 'E') {
                    cuentaE++;
                    eF = f;
                    eC = c;
                }

                if (ch == 'S') {
                    cuentaS++;
                    sF = f;
                    sC = c;
                }
            }
        }

        if (cuentaE > 0 || cuentaS > 0) {

            if (cuentaE != 1 || cuentaS != 1) {
                return "Debe haber exactamente una E y una S (hay "
                        + cuentaE + " E y " + cuentaS + " S).";
            }

        } else {


            int cuenta = 0;

            for (int f = 0; f < nuevasFilas; f++) {

                for (int c = 0; c < nuevasColumnas; c++) {

                    boolean borde = f == 0 || f == nuevasFilas - 1
                            || c == 0 || c == nuevasColumnas - 1;

                    if (borde && esTransitable(nuevo[f][c])) {

                        cuenta++;

                        if (cuenta == 1) {
                            eF = f;
                            eC = c;
                        }

                        if (cuenta == 2) {
                            sF = f;
                            sC = c;
                        }
                    }
                }
            }

            if (cuenta != 2) {
                return "Sin E y S debe haber exactamente dos posiciones "
                        + "transitables en el perimetro (hay " + cuenta + ").";
            }
        }

        laberinto = nuevo;

        filas = nuevasFilas;
        columnas = nuevasColumnas;

        entradaFila = eF;
        entradaColumna = eC;

        salidaFila = sF;
        salidaColumna = sC;

        return null;
    }

    public boolean esTransitable(char c) {
        return c == ' ' || c == '#' || c == 'E' || c == 'S';
    }

    public boolean esTransitable(int fila, int columna) {

        if (laberinto == null) {
            return false;
        }

        if (fila < 0 || fila >= filas || columna < 0 || columna >= columnas) {
            return false;
        }

        return esTransitable(laberinto[fila][columna]);
    }


    public Grafo construirGrafo() {

        if (laberinto == null) {
            return null;
        }

        Grafo grafo = new Grafo();

        int[][] ids = new int[filas][columnas];

        int siguienteId = 1;

        for (int f = 0; f < filas; f++) {

            for (int c = 0; c < columnas; c++) {

                if (esTransitable(f, c)) {

                    ids[f][c] = siguienteId;

                    Vertice vertice = new Vertice(siguienteId, f, c);

                    grafo.agregarVertice(vertice);

                    siguienteId++;
                }
            }
        }


        for (int f = 0; f < filas; f++) {

            for (int c = 0; c < columnas; c++) {

                if (ids[f][c] == 0) {
                    continue;
                }

                if (c + 1 < columnas && ids[f][c + 1] != 0) {
                    grafo.agregarArco(ids[f][c], ids[f][c + 1], 1);
                }

                if (f + 1 < filas && ids[f + 1][c] != 0) {
                    grafo.agregarArco(ids[f][c], ids[f + 1][c], 1);
                }
            }
        }

        grafo.setEntrada(ids[entradaFila][entradaColumna]);

        grafo.setSalida(ids[salidaFila][salidaColumna]);

        return grafo;
    }

    public char[][] getLaberinto() {

        if (laberinto == null) {
            return null;
        }

        char[][] copia = new char[filas][columnas];

        for (int f = 0; f < filas; f++) {

            for (int c = 0; c < columnas; c++) {
                copia[f][c] = laberinto[f][c];
            }
        }

        return copia;
    }

    public int getFilas() {
        return filas;
    }
    public int getColumnas() {
        return columnas;
    }
    public int getEntradaFila() {
        return entradaFila;
    }
    public int getEntradaColumna() {
        return entradaColumna;
    }
    public int getSalidaFila() {
        return salidaFila;
    }
    public int getSalidaColumna() {
        return salidaColumna;
    }
}
