package AnalizadorLexico;

import java.util.ArrayList;
import java.util.Hashtable;

public class TablaSimbolos {

    private Hashtable<String, Token> tSimb;


    public TablaSimbolos(){
        tSimb = new Hashtable<>();
    }

    public void addSimbolo( Token t){
        if (t.getUso()== AnalizadorLexico.ID){
            int indice= existe(t.getNombre())+1;
            t.setNombre(t.getNombre().toLowerCase()+"@"+indice);
            tSimb.put(t.getNombre(), t);

        }
        else
            tSimb.put(t.getNombre(), t);
    }


    //Me confirma si el token es agregable a la tabla de simbolos.
    public boolean es_Agregable( Token t){
        if(    // t.getUso() == AnalizadorLexico.ID ||
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

    public int existe(String nombre) {
        boolean encontre= true;
        for (int i=0;encontre;i++)
            if (!tSimb.containsKey(nombre.toLowerCase()+"@"+i)) {
                return i-1;
            }

        return -1;
    }

    public Token getToken(String nombre){
        if (existe(nombre)!=-1)
            return tSimb.get(nombre.toLowerCase()+"@"+existe(nombre));
        else
            return null;
    }


    public void borrarSimbolo(String nombre) {
            if(tSimb.containsKey(nombre)){
                tSimb.remove(nombre);
            }
    };



}
