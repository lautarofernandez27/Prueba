package CodigoIntermedio;

import AnalizadorLexico.AnalizadorLexico;
import AnalizadorLexico.Token;

public class TercetoExpresion extends Terceto {

    public final static String MOV = "MOV";
    public final static String ADD = "ADD";
    public final static String SUB = "SUB";
    public final static String MULT = "IMUL";
    public final static String DIV = "DIV";



    public TercetoExpresion(TercetoSimple izq, TercetoSimple medio,	TercetoSimple der, int numeroTerceto) {
        super(izq, medio, der,  numeroTerceto);
    }

    @Override
    public String getAssembler() {
        return null;
    }


    private String convertirOperador(String op){
        if (op == "+") return "ADD";
        if (op == "-") return "SUB";
        if (op == "/") return "DIV";
        return "IMUL";
    }

}