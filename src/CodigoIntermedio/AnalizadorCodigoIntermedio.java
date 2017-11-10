package CodigoIntermedio;
import java.util.ArrayList;

import AnalizadorLexico.Error;


public class AnalizadorCodigoIntermedio {

    public static final String errorNoExisteVariable = "Esta variable no fue declarada";
    public static final String errorFaltaL_F= "TIPOS INCOMPATIBLES";
    public static final String errorVariableControlFOR = "No se actualiza la variable de control en el for.";


    private ArrayList<Error> erroresCodigoIntermedio;

    public AnalizadorCodigoIntermedio() {
        erroresCodigoIntermedio = new ArrayList<Error>();
    }
    public void addError(Error error){
        erroresCodigoIntermedio.add(error);
    }
    public String getErroresCI(){
        String aux = "Errores Codigo Intermedio: \n";
        for (Error e:erroresCodigoIntermedio){
            aux = aux + e.Imprimir() + "\n";
        }
        if (erroresCodigoIntermedio.size() == 0)
            return "No hay errores de generacion de codigo intermedio.";
        else
            return aux;
    }

    public boolean hayErrores() {
        return (!erroresCodigoIntermedio.isEmpty());
    }
}
