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
import AnalizadorSintactico.AnalizadorSintactico;
import AnalizadorSintactico.Parser;
import CodigoIntermedio.AnalizadorCodigoIntermedio;
import CodigoIntermedio.ControladorTercetos;
import CodigoIntermedio.ConvertidorAssembler;


public class Main {

    private static BufferedReader codigo;

    private static StringBuilder getCodigo(BufferedReader ubicacion){

        StringBuilder buffer = new StringBuilder();
        try{
            codigo = new BufferedReader( new FileReader( ubicacion.readLine() ) );
            String readLine;
            while ((readLine = codigo.readLine())!= null) {
                buffer.append(readLine+"\n");
            }
            buffer.append("$");
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return buffer;
    }
    public static void main (String [] args) throws IOException {
       // String direccion = new String("C:\\Users\\lauta\\IdeaProjects\\Prueba24\\src\\codigo.txt");

        String direccion = new String("C:\\Users\\Facu\\IdeaProjects\\prueba\\src\\codigo.txt");

       // String direccion = new String(args[0]);

        InputStream is = new ByteArrayInputStream(direccion.getBytes());
        System.out.println("COMPILADORG7\n");

        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        StringBuilder codigo = null;
        codigo = new StringBuilder( getCodigo( br ) );
        ControladorArchivo archivo =new ControladorArchivo( codigo );
        TablaSimbolos ts = new TablaSimbolos();
        AnalizadorLexico analizadorLexico = new AnalizadorLexico(archivo,ts);
        AnalizadorSintactico analizadorSintactico = new AnalizadorSintactico();
        AnalizadorCodigoIntermedio analizadorCodigoIntermedio = new AnalizadorCodigoIntermedio();
        ControladorTercetos controladorTercetos = new ControladorTercetos();
        ConvertidorAssembler convertidorAssembler = new ConvertidorAssembler(controladorTercetos);
        convertidorAssembler.setTablaSimb(ts);

        Parser parser;
        parser = new Parser();
        parser.setLexico(analizadorLexico);
        parser.setCodigoIntermedio(analizadorCodigoIntermedio);
        parser.setControladorTercetos(controladorTercetos);
        parser.setTS(ts);
        parser.setSintactico(analizadorSintactico);
        parser.setControladorArchivo(archivo);
        parser.run();


        System.out.print(analizadorLexico.mostrarTokens());
        System.out.println( analizadorLexico.mostrarTs() );
        System.out.println(analizadorLexico.mostrarWarning());
        System.out.println(analizadorLexico.mostrarErrorComp());
        System.out.println(analizadorSintactico.getErroresSint());
        System.out.println(analizadorSintactico.getEstructuras());
        System.out.println(analizadorCodigoIntermedio.getErroresCI());
        if ( analizadorCodigoIntermedio.hayErrores() || analizadorLexico.hayErrores() || analizadorSintactico.hayErrores() )
            System.out.println( "No se genera codigo intermedio por errores en el codigo" );
        else{
            System.out.println( controladorTercetos.imprimirTercetos() );
            convertidorAssembler.generarAssembler();
            System.out.println( convertidorAssembler.generarArchivo() );
        }
    }
}
