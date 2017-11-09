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

            assembler = assembler + "MOV " + reg3Long + ", " + elementos.get(1).getNombreVar() + '\n';

            assembler = assembler + "MOV " + reg1Long + ", " + elementos.get(2).getNombreVar() + '\n';


            assembler = assembler + opAssembler + " " + registroAX + ", " + registro + '\n';

            controladorTercetos.liberarRegistro(registro);
            elementos.get(0).getToken().setTipo(AnalizadorLexico.variableL);
            controladorTercetos.liberarRegistro(registroDX);
            registro = controladorTercetos.getProxRegLibre(elementos.get(0).getToken());

            this.setRegistro(registro);

            assembler =  assembler + MOV + " " + registro +", " + registroAX  + '\n';

        }
        else
//			//caso 2: (OP, registro, variable)
            if ( ( !elementos.get(1).esToken() ) && ( elementos.get(2).esToken() ) ){

                String registroDX = controladorTercetos.getReg4(terceto1.getTerceto(0).getToken());
                String registroAX = controladorTercetos.getReg3(terceto1.getTerceto(0).getToken() );
                assembler += MOV + " " + registroAX +", " + terceto1.getRegistro()  + '\n';
                controladorTercetos.liberarRegistro(terceto1.getRegistro());
                String registro = controladorTercetos.getProxRegLibre(elementos.get(2).getToken());


                if(elementos.get(2).getNombreVar().startsWith("mat")){
                    assembler = assembler + "MOV " + registro + ", " + elementos.get(2).getNombreVar() + "["+controladorTercetos.getRegMatriz(1)+"]\n";
                }else
                    assembler = assembler + "MOV " + registro + ", " + elementos.get(2).getNombreVar() + '\n';

                assembler = assembler + hacerConversiones(registroAX, registroDX, registro);

                if(this.tipo.equals(AnalizadorLexico.variableL)){
                    registroAX = registroAux1;
//					registroDX = registroAux2;
                    registro = registroAux3;
                }
                this.setRegistro(registro);

                assembler = assembler + opAssembler + " " + registroAX + ", " + registro + '\n';
                controladorTercetos.liberarRegistro(registro);
                elementos.get(0).getToken().setTipo(AnalizadorLexico.variableL);
                controladorTercetos.liberarRegistro(registroDX);

                registro = controladorTercetos.getProxRegLibre(elementos.get(0).getToken());
                this.setRegistro(registro);

                assembler =  assembler + MOV + " " + registro +", " + registroAX  + '\n';

                controladorTercetos.liberarRegistro(registroAX);
            }
        return assembler;
    }

}
