package AnalizadorLexico;

import java.util.ArrayList;
import java.util.Hashtable;

public class TablaSimbolos {

    private Hashtable<String, Token> tSimb;


    public TablaSimbolos(){
        tSimb = new Hashtable<>();
    }

    public void addSimbolo( Token t){
        tSimb.put(t.getNombre().toLowerCase(), t);
        System.out.print("Agregue "+t.getNombre());
    }


    //Me confirma si el token es agregable a la tabla de simbolos.
    public boolean es_Agregable( Token t){
        if(     t.getUso() == AnalizadorLexico.ID ||
              //  t.getUso() == AnalizadorLexico.CTEF ||
                       // t.getUso() == AnalizadorLexico.CTEL ||
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
            if(tSimb.containsKey(nombre)){
                tSimb.remove(nombre);
            }
    };



}
