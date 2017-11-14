package CodigoIntermedio;

import AnalizadorLexico.*;

public class TercetoExpresionDiv extends TercetoExpresion {
    public TercetoExpresionDiv(TercetoSimple izq, TercetoSimple medio,	TercetoSimple der, int numeroTerceto) {
        super(izq, medio, der, numeroTerceto);
        // TODO Auto-generated constructor stub
    }

    public String getAssembler() {

        String assembler = "";
        Terceto terceto1 = null;
        if (!elementos.get(1).esToken())
            terceto1 = controladorTercetos.getTerceto( Integer.parseInt( elementos.get(1).getNombreVar() ) );
        Terceto terceto2 = null;
        if (!elementos.get(2).esToken())
            terceto2 = controladorTercetos.getTerceto( Integer.parseInt( elementos.get(2).getNombreVar() ) );
        String opAssembler = "IDIV";

        //caso 1: (OP, variable, variable)
        if ( ( elementos.get(1).esToken() ) && ( elementos.get(2).esToken() ) ) {

            if (elementos.get(1).getToken().getTipo().equals("long")) {

                assembler = assembler + MOV + reg3Long + "," + elementos.get(1).getNombreVar() + '\n';

                assembler = assembler + getAssemblerErrorDivCero("long");

                assembler = assembler + opAssembler + reg3Long + ", " + elementos.get(2).getNombreVar() + '\n';

                assembler = assembler + MOV + AUX + numeroTerceto + ", " + reg3Long + '\n';


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

                assembler = assembler + getAssemblerErrorDivCero("float");

                assembler = assembler + "FDIV" + '\n';

                assembler = assembler + "FST " + AUX + numeroTerceto + '\n';
            }
        }
        //caso 2: (OP, terceto, variable)
        if ( (!elementos.get(1).esToken() ) && ( elementos.get(2).esToken() ) ) {
            if (elementos.get(2).getToken().getTipo().equals("long")) {

                assembler = assembler + MOV + reg3Long + "," + AUX +terceto1.getNumeroTerceto() + '\n';

                assembler = assembler + getAssemblerErrorDivCero("long");

                assembler = assembler + opAssembler + reg3Long + ", " + elementos.get(2).getNombreVar() + '\n';

                assembler = assembler + MOV + AUX + numeroTerceto + ", " + reg3Long + '\n';
            }
            else if (elementos.get(2).getToken().getTipo().equals("float")) {

                if (elementos.get(2).getToken().getUso()== AnalizadorLexico.CTEF)
                    assembler = assembler + "FLD " + "auxf" + elementos.get(2).getNombreVar().replace(',','a').replace('-','n') + '\n';
                else
                    assembler = assembler + "FLD " + elementos.get(2).getNombreVar() + '\n';

                assembler = assembler + "FLD " + AUX +terceto1.getNumeroTerceto() + '\n';

                assembler = assembler + getAssemblerErrorDivCero("float");

                assembler = assembler + "FDIV" + '\n';

                assembler = assembler + "FST " + AUX + numeroTerceto + '\n';
            }
        }
        //caso 3: (OP, variable, terceto)
        if ( (elementos.get(1).esToken() ) && ( !elementos.get(2).esToken() ) ) {
            if (elementos.get(1).getToken().getTipo().equals("long")) {

                assembler = assembler + MOV + reg3Long + "," + AUX +terceto2.getNumeroTerceto() + '\n';

                assembler = assembler + getAssemblerErrorDivCero("long");

                assembler = assembler + opAssembler + reg3Long + ", " + elementos.get(1).getNombreVar() + '\n';

                assembler = assembler + MOV + AUX + numeroTerceto + ", " + reg3Long + '\n';
            }
            else if (elementos.get(1).getToken().getTipo().equals("float")) {
                assembler = assembler + "FLD " + AUX +terceto2.getNumeroTerceto() + '\n';

                if (elementos.get(1).getToken().getUso()== AnalizadorLexico.CTEF)
                    assembler = assembler + "FLD " + "auxf" + elementos.get(1).getNombreVar().replace(',','a').replace('-','n') + '\n';
                else
                    assembler = assembler + "FLD " + elementos.get(1).getNombreVar() + '\n';

                assembler = assembler + getAssemblerErrorDivCero("float");

                assembler = assembler + "FDIV" + '\n';

                assembler = assembler + "FST " + AUX + numeroTerceto + '\n';
            }
        }
        //caso 4: (OP, terceto, terceto)
        if ( (!elementos.get(1).esToken() ) && ( !elementos.get(2).esToken() ) ) {
            if (elementos.get(1).getToken().getTipo().equals("long")) {

                assembler = assembler + MOV + reg3Long + "," + AUX +terceto1.getNumeroTerceto() + '\n';

                assembler = assembler + getAssemblerErrorDivCero("long");

                assembler = assembler + opAssembler + reg3Long + ", " + AUX +terceto2.getNumeroTerceto() + '\n';

                assembler = assembler + MOV + AUX + numeroTerceto + ", " + reg3Long + '\n';
            }
            else if (elementos.get(1).getToken().getTipo().equals("float")) {
                assembler = assembler + "FLD " + AUX +terceto1.getNumeroTerceto() + '\n';

                assembler = assembler + "FLD " + AUX +terceto2.getNumeroTerceto() + '\n';

                assembler = assembler + getAssemblerErrorDivCero("float");

                assembler = assembler + "FDIV" + '\n';

                assembler = assembler + "FST " + AUX + numeroTerceto + '\n';
            }
        }
        return assembler;
    }

    private String getAssemblerErrorDivCero(String tipo) {
        String assembler = "";
        if (tipo == "float"){
            if (elementos.get(2).getToken().getUso()==AnalizadorLexico.CTEF)
                assembler = assembler + "FLD " + "auxf" +elementos.get(2).getNombreVar().replace(',','a').replace('-','n') + '\n';
            else
                assembler = assembler + "FLD " + elementos.get(2).getNombreVar() + '\n';
            assembler = assembler + "FLDZ"+'\n';
            assembler = assembler + "FCOM" + '\n';
            assembler = assembler + "FSTSW AX" + '\n';
            assembler = assembler + "SAHF" + '\n';

        }
        else{
            assembler = assembler + "CMP " + elementos.get(2).getNombreVar() + ", 0" + '\n';
        }
        assembler = assembler + "JE LabelDividirCero" + '\n';
        return assembler;
    }

}
