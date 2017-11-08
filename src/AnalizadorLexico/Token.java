package AnalizadorLexico;

import java.util.Hashtable;

public class Token {
    //Nombre del token dentro del codigo
    String nombre;

    //LONG, FLOAT
    String tipo="";

    //id que identifica el numero de repeticion de una variable
    int id_shadow= 0;

    //booleano que indica si el token es un numero de terceto o realmente un token
    boolean numTerceto=false;

    //ID, CTE
    String lexema;

    //Es el valor ASCII o el numero de token
    int uso;

    AnalizadorLexico analizador = new AnalizadorLexico(null,null);


    public Token (String nombre, int uso){
        this.nombre = nombre;
        this.uso = uso;

        if ( (uso>=analizador.IF) && (uso<=analizador.L_F) )
            lexema = "Palabra reservada";
        else
        if (uso==analizador.ID)
            lexema = "Identificador";
        else
        if (uso==analizador.CADENA)
            lexema = "Cadena de caracteres";
        else
        if (uso==analizador.CTEL)
            lexema = "Constante long";
        else
        if (uso==analizador.S_MAYOR_IGUAL)
            lexema = "Simbolo Mayor igual";
        else
        if (uso==analizador.S_MENOR_IGUAL)
            lexema = "Simbolo Menor igual";
        else
        if (uso==analizador.S_DESIGUAL)
            lexema = "Simbolo distinto";
        else
        if (uso==analizador.CTEF)
            lexema = "Constante flotante";
        else
        if (uso==analizador.S_IGUAL_IGUAL)
            lexema = "Simbolo ==";
        else
        if (uso==analizador.COMENTARIO)
            lexema = "Comentario";
        else
            lexema = "Simbolo";
    }
    public boolean isNumTerceto(){
        return numTerceto;
    }

    public void setNumTerceto(boolean numTerceto){
        this.numTerceto= numTerceto;
    }

    public Token(String numeroTercetoString) {
        // Usado para los tercetos
        nombre = numeroTercetoString;
        numTerceto=true;
    }


    public int getUso() {
        return uso;
    }

    public String getTipo(){
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String imprimirToken(){
        String imprimir = lexema + ": " + nombre +" " + tipo + " [" +uso + "] ";
        return imprimir;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNegativo(){
        nombre= "-"+nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
