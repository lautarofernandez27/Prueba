package CodigoIntermedio;

import AnalizadorLexico.AnalizadorLexico;

public class TercetoComparacion extends Terceto {

    public final static String CMP = "CMP";
    public static final String etiquetaIgual = "JE";
    public static final String etiquetaMenor = "JB";
    public static final String etiquetaMayor = "JG";
    public static final String etiquetaMayorIgual = "JGE";
    public static final String etiquetaMenorIgual = "JBE";

    public static final String etiquetaDistinto = "JNE";

    private String registroAux = new String();


    public TercetoComparacion(TercetoSimple izq, TercetoSimple medio, TercetoSimple der, int numeroTerceto) {
        super(izq, medio, der, numeroTerceto);
        // TODO Auto-generated constructor stub
    }


    public String getAssembler() {
        String assembler = "";
        String operador = elementos.get(0).getNombreVar();
        Terceto terceto1 = null;
        if (!elementos.get(1).esToken())
            terceto1 = controladorTercetos.getTerceto( Integer.parseInt( elementos.get(1).getNombreVar() ) );
        Terceto terceto2 = null;
        if (!elementos.get(2).esToken())
            terceto2 = controladorTercetos.getTerceto( Integer.parseInt( elementos.get(2).getNombreVar() ) );


        //caso 1: (OP, variable, variable)
        if ( ( elementos.get(1).esToken() ) && ( elementos.get(2).esToken() ) ) {

            if ( (elementos.get(1).getToken().getTipo().equals(AnalizadorLexico.variableL) ) && (elementos.get(2).getToken().getTipo().equals(AnalizadorLexico.variableL)) ){
                assembler = assembler + "MOV " + reg3Long+ "," + elementos.get(1).getNombreVar() + '\n';
                assembler = assembler + CMP + " " + reg3Long+ "," + elementos.get(2).getNombreVar() + '\n';
            }
            else
            if ( (elementos.get(1).getToken().getTipo().equals(AnalizadorLexico.variableF) ) && (elementos.get(2).getToken().getTipo().equals(AnalizadorLexico.variableF)) ){
                assembler = assembler + "FLD " + elementos.get(1).getNombreVar() + '\n';
                assembler = assembler + "FLD " + elementos.get(2).getNombreVar() + '\n';
                assembler = assembler + "FCOM" + '\n';
            }
        }
        else
        //caso 2: (OP, registro, variable)
        if ( ( !elementos.get(1).esToken() ) && ( elementos.get(2).esToken() ) ){
            if ( (elementos.get(1).getToken().getTipo().equals(AnalizadorLexico.variableL) ) && (elementos.get(2).getToken().getTipo().equals(AnalizadorLexico.variableL)) ){
                assembler = assembler + "MOV " + reg3Long +" , "+elementos.get(2).getNombreVar()+'\n';
                assembler = assembler + CMP + " " + AUX+terceto1.getNumeroTerceto()+","+ reg3Long+ '\n';
            }
            else
            if ( (elementos.get(1).getToken().getTipo().equals(AnalizadorLexico.variableF) ) && (elementos.get(2).getToken().getTipo().equals(AnalizadorLexico.variableF)) ){
                assembler = assembler + "FLD " + AUX+terceto1.getNumeroTerceto() + '\n';
                assembler = assembler + "FLD " + elementos.get(2).getNombreVar() + '\n';
                assembler = assembler + "FCOM" + '\n';
            }
        }

        else
        //caso 3: (OP, registro, registro)
        if ( ( !elementos.get(1).esToken() ) && ( !elementos.get(2).esToken() ) ){
            if ( (elementos.get(1).getToken().getTipo().equals( AnalizadorLexico.variableL) ) && (elementos.get(2).getToken().getTipo().equals(AnalizadorLexico.variableL)) ){
                assembler = assembler + "MOV " +AUX+terceto1.getNumeroTerceto()+","+elementos.get(1).getNombreVar()+'\n';
                assembler = assembler + "MOV " +AUX+terceto2.getNumeroTerceto()+","+elementos.get(2).getNombreVar()+'\n';
                assembler = assembler + CMP + " " +AUX+terceto1.getPosicionTerceto()+","+AUX+terceto2.getPosicionTerceto()+'\n';
            }
            else
            if ( (elementos.get(1).getToken().getTipo().equals( AnalizadorLexico.variableF) ) && (elementos.get(2).getToken().getTipo().equals(AnalizadorLexico.variableF)) ){
                assembler = assembler + "FLD " + AUX+terceto1.getNumeroTerceto() + '\n';
                assembler = assembler + "FLD " + AUX+terceto2.getNumeroTerceto() + '\n';
                assembler = assembler + "FCOM" + '\n';
            }
        }
        else
        //caso 4: (OP, variable, registro)
        if ( ( elementos.get(1).esToken() ) && ( !elementos.get(2).esToken() ) ){
            if ( (elementos.get(1).getToken().getTipo().equals(AnalizadorLexico.variableL) ) && (elementos.get(2).getToken().getTipo().equals(AnalizadorLexico.variableL)) ){
                assembler = assembler + "MOV " + reg3Long +" , "+elementos.get(1).getNombreVar()+'\n';
                assembler = assembler + CMP + " " + reg3Long +","+AUX+terceto2.getNumeroTerceto()+ '\n';
            }
            else
            if ( (elementos.get(1).getToken().getTipo().equals(AnalizadorLexico.variableF) ) && (elementos.get(2).getToken().getTipo().equals(AnalizadorLexico.variableF)) ){
                assembler = assembler + "FLD " + elementos.get(1).getNombreVar() + '\n';
                assembler = assembler + "FLD " + AUX+terceto2.getNumeroTerceto() + '\n';
                assembler = assembler + "FCOM" + '\n';
            }
        }
        return assembler;
    }
}
