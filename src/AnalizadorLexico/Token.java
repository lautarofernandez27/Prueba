package AnalizadorLexico;

import java.util.Hashtable;

public class Token {
    //Nombre del token dentro del codigo
    String nombre;

    //Valor del token, p.e. _i453; valor = 453.
    long valorLong;

    //integer, longint
    String tipo="";

    //ID, CTE
    String lexema;

    //Es el valor ASCII o el numero de token
    int uso;

    Hashtable<String, Integer> tablaTokens = new Hashtable<>();
    AnalizadorLexico analizador = new AnalizadorLexico(null,null);

    public Token (String nombre, int uso){
        this.nombre = nombre;
        this.uso = uso;

        if ( (uso>=analizador.IF) && (uso<=analizador.LONGINT) )
            lexema = "Palabra reservada";
        else
        if (uso==analizador.ID)
            lexema = "Identificador";
        else
        if (uso==analizador.MULTI_LINEA)
            lexema = "Cadena de caracteres";
        else
        if (uso==analizador.CTEL)
            lexema = "Constante long";
        else
        if (uso==analizador.S_ASIGNACION)
            lexema = "Asignacion";
        else
        if (uso==analizador.S_MAYOR_IGUAL)
            lexema = "Simbolo Mayor igual";
        else
        if (uso==analizador.S_MENOR_IGUAL)
            lexema = "Simbolo Menor igual";
        else
        if (uso==analizador.S_EXCLAMACION_IGUAL)
            lexema = "Simbolo distinto";
        else
        if (uso==analizador.CTEI)
            lexema = "Constante entera";
        else
        if (uso==analizador.S_RESTA_RESTA)
            lexema = "Simbolo --";
        else
        if (uso==analizador.CTEI)
            lexema = "Constante entera";
        else
        if (uso==analizador.COMENTARIO)
            lexema = "Comentario";
        else
        if (uso==analizador.ANOTACIONF)
            lexema = "Anotacion por filas";
        else
        if (uso==analizador.ANOTACIONC)
            lexema = "Anotacion por columnas";
        else
            lexema = "Simbolo";

    }

    public Token(String numeroTercetoString) {
        // Usado para los tercetos
        nombre = numeroTercetoString;
    }

    public long getValor(){
        return valorLong;
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

    public void setValor(long valor) {
        this.valorLong = valor;
    }

    public String imprimirToken(){
        String imprimir = lexema + ": " + nombre +" " + tipo + " " + valorLong + " [" +uso + "] ";
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

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
