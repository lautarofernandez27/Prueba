package AnalizadorLexico;

import java.util.ArrayList;
import java.util.Hashtable;

public class TablaSimbolos {

    private Hashtable<String, Token> tSimb;

    //Se almacenan los usos de cada token, p.e. indicando el uso del identificador en el programa (variable,
    //nombre de funcion, nombre de parametro, nombre de arreglo, nombre de programa, etc.).
    private Hashtable<String, String> tUsos;

    public TablaSimbolos(){
        tSimb = new Hashtable<>();
        tUsos = new Hashtable<>();
    }

    public void addSimbolo( Token t){
        tSimb.put(t.getNombre(), t);
    }

    public void addUso(String var, String uso){
        tUsos.put(var, uso);
    };


    public String getUso(String var){
        if (tUsos.contains(var))
            return tUsos.get(var);
        else
            return "";
    };

    //Me confirma si el token es agregable a la tabla de simbolos.
    public boolean es_Agregable( Token t){
        if(     t.getUso() == AnalizadorLexico.ID ||
                t.getUso() == AnalizadorLexico.CTEF ||
                        t.getUso() == AnalizadorLexico.CTEL ||
                        t.getUso() == AnalizadorLexico.CADENA)
            return true;
        else
            return false;
    }

    public ArrayList<Token> getTokens(){
        return new ArrayList<>(tSimb.values());
    }

    public boolean existe(String nombre){
        return tSimb.containsKey(nombre);
    }

    public Token getToken(String nombre){
        if (tSimb.containsKey(nombre))
            return tSimb.get(nombre);
        else
            return null;
    }

    public void borrarSimbolo(String nombre) {
        if (tSimb.remove(nombre)!= null)
            System.out.println("anda" );
    };



}
