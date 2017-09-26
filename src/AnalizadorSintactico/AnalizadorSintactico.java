package AnalizadorSintactico;

import java.awt.List;

import java.util.ArrayList;

import AnalizadorLexico.AnalizadorLexico;
import AnalizadorLexico.Error;
import AnalizadorLexico.TablaSimbolos;

public class AnalizadorSintactico {

    //descripcion estructura sintactica
    static final String estructuraOUT = "Sentencia para imprimir por pantalla ";
    static final String estructuraIF = "Sentencia de seleccion IF ";
    static final String estructuraWHILE = "Sentencia de iteracion WHILE ";
    static final String estructuraASIG = "Sentencia de ASIGNACION ";
    static final String estructuraCONDICION = "Sentencia de CONDICION ";
    static final String estructuraDECLARACION = "Sentencia de declaracion de variables.";
    static final String estructuraLET = "Sentencia de LET";
    static final String estructuraCONVERSION = "Sentencia de CONVERSION ";
    static final String estructuraASIGnegativo = "Sentencia de ASIGNACION negativa ";

    //descripciones errores sintacticos
    static final String errorOUT1 = "Existe un error en la sentencia OUT ";
    static final String errorOUT2 = "Se espera la palabra reservada 'OUT' al comienzo de la sentencia ";
    static final String errorParentesisA = "Falto abrir parentesis. ";
    static final String errorParentesisB = "Falto cerrar parentesis. ";
    static final String errorCondicionI = "No se reconoce el lado izquierdo de la condicion ";
    static final String errorCondicionD = "No se reconoce el lado derecho de la condicion ";
    static final String errorPuntoFinal = "Se espera un '.' al final de la sentencia ";
    static final String errorDeclaracionVar = "No se declaro correctamente las variables.";
    static final String errorAsignacion = "Error en la asignacion.";
    static final String errorTipo = "Error al declarar el tipo.";
    static final String errorSimboloAsignacion = "La asignacion es con =";
    static final String errorPalabraWHILE = "Se esperaba la palabra WHILE en mayuscula ";
    static final String errorTipo_operacion = "Los tipos de las variables son incompatibles.";
    static final String errorPalabraIF = "Se esperaba la palabra IF";
    static final String faltaThen = "Se esperaba la palabra THEN";


    ArrayList<Error> erroresSint;
    ArrayList<Error > estructuras;
    AnalizadorLexico analizadorL;
    TablaSimbolos tablaS;

    public AnalizadorSintactico() {
        erroresSint = new ArrayList<Error>();
        estructuras = new ArrayList<Error>();
    }

    public void addError( Error error ){
        erroresSint.add(error);
    }

    public void addEstructura(Error estructura){
        estructuras.add(estructura); //usamos el error tambien para almacenar la estructura para mostrarla
    }

    public String getErroresSint(){
        String aux = "Errores Sintacticos: \n";
        for (Error e:erroresSint){
            aux = aux + e.Imprimir() + "\n";
        }
        if (erroresSint.size() == 0)
            return "No hay errores sintacticos.";
        else
            return aux;
    }

    public String getEstructuras(){
        String aux = "Estructuras encontradas: \n";
        for (Error e:estructuras){
            aux = aux + e.Imprimir() + "\n";
        }
        if (estructuras.size() == 0)
            return "No se encontraron estructuras sintacticas";
        else
            return aux;
    }

    public boolean hayErrores() {
        return (!erroresSint.isEmpty());
    }
}
