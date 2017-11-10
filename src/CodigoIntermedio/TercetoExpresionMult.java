package CodigoIntermedio;

public class TercetoExpresionMult extends TercetoExpresion {

    //private String tipo;

    public TercetoExpresionMult(TercetoSimple izq, TercetoSimple medio,	TercetoSimple der, int numeroTerceto) {
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
        String opAssembler = "IMUL";

        //caso 1: (OP, variable, variable)
        if ( ( elementos.get(1).esToken() ) && ( elementos.get(2).esToken() ) ) {

            if (elementos.get(1).getToken().getTipo().equals("long")) {

                assembler = assembler + MOV + reg3Long + "," + elementos.get(1).getNombreVar() + '\n';

                assembler = assembler + opAssembler + reg3Long + ", " + elementos.get(2).getNombreVar() + '\n';

                assembler = assembler + MOV + AUX + numeroTerceto + ", " + reg3Long + '\n';


            }
            else if (elementos.get(1).getToken().getTipo().equals("float")) {
                assembler = assembler + "FLD " + elementos.get(1).getNombreVar() + '\n';

                assembler = assembler + "FLD " + elementos.get(2).getNombreVar() + '\n';

                assembler = assembler + "FMUL" + '\n';

                assembler = assembler + "FST" + AUX + numeroTerceto + '\n';
            }
        }
        //caso 2: (OP, terceto, variable)
        if ( (!elementos.get(1).esToken() ) && ( elementos.get(2).esToken() ) ) {
            if (elementos.get(1).getToken().getTipo().equals("long")) {

                assembler = assembler + MOV + reg3Long + "," + terceto1.getNumeroTerceto() + '\n';

                assembler = assembler + opAssembler + reg3Long + ", " + elementos.get(2).getNombreVar() + '\n';

                assembler = assembler + MOV + AUX + numeroTerceto + ", " + reg3Long + '\n';
            }
        }
        return assembler;
    }

}

