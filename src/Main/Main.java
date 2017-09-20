package Main;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream.GetField;
import java.io.Reader;

import AnalizadorLexico.AnalizadorLexico;
import AnalizadorLexico.ControladorArchivo;
import AnalizadorLexico.TablaSimbolos;


public class Main {

    private static BufferedReader codigo;

    private static StringBuilder getCodigo(BufferedReader ubicacion){

        StringBuilder buffer = new StringBuilder();
        try{
            //lectura de ubicacion de archivo
//			System.out.print("Ingrese la ubicacion del archivo: ");
//			BufferedReader ubicacion = new BufferedReader(new InputStreamReader(System.in));
            codigo = new BufferedReader( new FileReader( ubicacion.readLine() ) );
            String readLine;
            //TODO: Check que el \n no arruine nada
            while ((readLine = codigo.readLine())!= null) {
                buffer.append(readLine+"\n");
            }
            buffer.append("$");
        }
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return buffer;
    }
    public static void main (String [] args){

        //String direccion = new String("C:\\Users\\lauta\\IdeaProjects\\Prueba24\\src\\TP1_7.txt");

        String direccion = new String("C:\\Users\\Facu\\IdeaProjects\\prueba\\src\\TP1_1.txt");

        InputStream is = new ByteArrayInputStream(direccion.getBytes());
        System.out.println("COMPILADORG17\n");

        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        StringBuilder codigo = null;
        codigo = new StringBuilder( getCodigo( br ) );
        ControladorArchivo archivo =new ControladorArchivo( codigo );
        TablaSimbolos ts = new TablaSimbolos();
        AnalizadorLexico analizadorLexico = new AnalizadorLexico(archivo,ts);
        analizadorLexico.consumirToken();

        System.out.print(analizadorLexico.mostrarTokens());
        System.out.println( analizadorLexico.mostrarTs() );
        System.out.println(analizadorLexico.mostrarWarning());
        System.out.println(analizadorLexico.mostrarErrorComp());
    }
}
