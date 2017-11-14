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



    private String convertirOperador(String op){
        if (op == "+") return "ADD";
        if (op == "-") return "SUB";
        return "0";
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
        String opAssembler = convertirOperador(operador);


        //caso 1: (OP, variable, variable)
        if ( ( elementos.get(1).esToken() ) && ( elementos.get(2).esToken() ) ) {
            if (elementos.get(1).getToken().getTipo().equals("long")) {

                assembler = assembler + MOV +" "+ reg3Long + ", " + elementos.get(1).getNombreVar() + '\n';

                assembler = assembler + opAssembler +" "+ reg3Long + ", " + elementos.get(2).getNombreVar() + '\n';

                assembler = assembler + MOV+" " + AUX + numeroTerceto + ", " + reg3Long + '\n';

                assembler = assembler + getAssemblerErrorOverflow("long")+'\n';
            }
            else if (elementos.get(1).getToken().getTipo().equals("float")) {
                if (elementos.get(2).getToken().getUso()== AnalizadorLexico.CTEF)
                    assembler = assembler + "FLD " + "auxf" + elementos.get(2).getNombreVar().replace(',','a').replace('-','n') + '\n';
                else
                    assembler = assembler + "FLD " + elementos.get(2).getNombreVar() + '\n';

                if (elementos.get(1).getToken().getUso()== AnalizadorLexico.CTEF)
                    assembler = assembler + "FLD " + "auxf" + elementos.get(1).getNombreVar().replace(',','a').replace('-','n') + '\n';
                else
                    assembler = assembler + "FLD " + elementos.get(1).getNombreVar() + '\n';

                assembler = assembler + "F"+opAssembler+ " " + '\n';

                assembler = assembler + "FST " + AUX + numeroTerceto + '\n';

                assembler = assembler + getAssemblerErrorOverflow("float")+'\n';
            }
        }
        //caso 2: (OP, terceto, variable)
        if ( (!elementos.get(1).esToken() ) && ( elementos.get(2).esToken() ) ) {
            if (elementos.get(1).getToken().getTipo().equals("long")) {

                assembler = assembler + MOV + reg3Long + "," + AUX +terceto1.getNumeroTerceto() + '\n';

                assembler = assembler + opAssembler + reg3Long + ", " + elementos.get(2).getNombreVar() + '\n';

                assembler = assembler + MOV + AUX + numeroTerceto + ", " + reg3Long + '\n';

                assembler = assembler + getAssemblerErrorOverflow("long")+'\n';
            }
            else if (elementos.get(1).getToken().getTipo().equals("float")) {
                if (elementos.get(2).getToken().getUso()== AnalizadorLexico.CTEF)
                    assembler = assembler + "FLD " + "auxf" + elementos.get(2).getNombreVar().replace(',','a').replace('-','n') + '\n';
                else
                    assembler = assembler + "FLD " + elementos.get(2).getNombreVar() + '\n';

                assembler = assembler + "FLD " + AUX +terceto1.getNumeroTerceto() + '\n';

                assembler = assembler + "F"+opAssembler+ " " + '\n';

                assembler = assembler + "FST " + AUX + numeroTerceto + '\n';

                assembler = assembler + getAssemblerErrorOverflow("float")+'\n';
            }
        }
        //caso 3: (OP, variable, terceto)
        if ( (elementos.get(1).esToken() ) && ( !elementos.get(2).esToken() ) ) {
            if (elementos.get(1).getToken().getTipo().equals("long")) {

                assembler = assembler + MOV + reg3Long + "," + elementos.get(1).getNombreVar() + '\n';

                assembler = assembler + opAssembler + reg3Long + ", " + AUX +terceto2.getNumeroTerceto()+ '\n';

                assembler = assembler + MOV + AUX + numeroTerceto + ", " + reg3Long + '\n';

                assembler = assembler + getAssemblerErrorOverflow("long")+'\n';
            }
            else if (elementos.get(1).getToken().getTipo().equals("float")) {

                assembler = assembler + "FLD " + AUX +terceto2.getNumeroTerceto()  + '\n';

                if (elementos.get(1).getToken().getUso()== AnalizadorLexico.CTEF)
                    assembler = assembler + "FLD " + "auxf" + elementos.get(1).getNombreVar().replace(',','a').replace('-','n') + '\n';
                else
                    assembler = assembler + "FLD " + elementos.get(1).getNombreVar() + '\n';

                assembler = assembler + "F"+opAssembler+ " " + '\n';

                assembler = assembler + "FST " + AUX + numeroTerceto + '\n';

                assembler = assembler + getAssemblerErrorOverflow("float")+'\n';
            }
        }
        //caso 4: (OP, terceto, terceto)
        if ( (!elementos.get(1).esToken() ) && ( !elementos.get(2).esToken() ) ) {
            if (elementos.get(1).getToken().getTipo().equals("long")) {

                assembler = assembler + MOV + reg3Long + "," + AUX +terceto1.getNumeroTerceto() + '\n';

                assembler = assembler + opAssembler + reg3Long + ", " + AUX +terceto2.getNumeroTerceto() + '\n';

                assembler = assembler + MOV + AUX + numeroTerceto + ", " + reg3Long + '\n';

                assembler = assembler + getAssemblerErrorOverflow("long")+'\n';
            }
            else if (elementos.get(1).getToken().getTipo().equals("float")) {
                assembler = assembler + "FLD " + AUX +terceto1.getNumeroTerceto() + '\n';

                assembler = assembler + "FLD " + AUX +terceto2.getNumeroTerceto() + '\n';

                assembler = assembler + "F"+opAssembler+ " " + '\n';

                assembler = assembler + "FST " + AUX + numeroTerceto + '\n';
                assembler = assembler + getAssemblerErrorOverflow("float")+'\n';
            }
        }
        return assembler;
    }

    private String getAssemblerErrorOverflow(String tipo) {

        String assembler = "";
        assembler = assembler + "JO LabelOverflowSuma" + '\n';
        return assembler;
    }
}