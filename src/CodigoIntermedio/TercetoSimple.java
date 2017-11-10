package CodigoIntermedio;
import java.io.ObjectInputStream.GetField;

import AnalizadorLexico.AnalizadorLexico;
import AnalizadorLexico.Token;

public class TercetoSimple {
    Token t;
    boolean esToken;


    public TercetoSimple(Token t) {
        this.t= t;
    }

    public String imprimirTerceto() {
        if (t.isNumTerceto())
            return "[" + t.getNombre() + "]";
        else
            return t.getNombre();
    }

    public Token getToken() {
        return t;
    }

    public boolean esToken(){
        return (!t.isNumTerceto() );
    }

    public int getNumeroTerceto(){
        return ( Integer.parseInt( t.getNombre() ) );

    }

    public String getNombreVar(){
            return t.getNombre();
    }
}
