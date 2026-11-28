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

    public String cargarLaberinto(String ruta){
        File archivo = new File(ruta);
        if (!archivo.exists()){
            return "El archivo no existe";
        }
        if (!archivo.isFile()){
            return "El archivo esta vacio";
        }
        if (!archivo.canRead()){
            return "No se puede leer el archivo";
        }
        ArrayList<String> lienas = new ArrayList<>();
        try {
            Scanner lector = new Scanner(archivo);
            while (lector.hasNextLine()){
                lienas.add(lector.nextLine());
            }
            lector.close();
        }catch (FileNotFoundException e){
            return "No se puede leer el archivo";
        }
        if (lienas.size()==0){
            return "El archivo esta vacio";
        }
        int nuevasFilas = lienas.size();
        int nuevasColumnas = lienas.get(0).length();

        if (nuevasColumnas==0){
            return "las filas deben tener almenos una columna";
        }

    }
}
