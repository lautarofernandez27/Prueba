package CodigoIntermedio;

public class TercetoComparacion extends Terceto {

    public final static String CMP = "CMP";
    public static final String etiquetaIgual = "JE";
    public static final String etiquetaDistinto = "JNE";
    private String registroAux = new String();


    public TercetoComparacion(TercetoSimple izq, TercetoSimple medio, TercetoSimple der, int numeroTerceto) {
        super(izq, medio, der, numeroTerceto);
        // TODO Auto-generated constructor stub
    }

/*
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
        if ( ( elementos.get(1).esToken() ) && ( elementos.get(2).esToken() ) ){
            String registro1 = controladorTercetos.getProxRegLibre( elementos.get(1).getToken() );
            String registro2 = controladorTercetos.getProxRegLibre( elementos.get(2).getToken() );

            assembler = assembler + "MOV" + " " +  registro1 + ", " + elementos.get(1).getNombreVar()+ '\n';
            assembler = assembler + "MOV" + " " +  registro2 + ", " + elementos.get(2).getNombreVar()+ '\n';

            if ( (elementos.get(1).getToken().getTipo().equals( AnalizadorLexico.variableL) ) && (elementos.get(2).getToken().getTipo().equals(AnalizadorLexico.variableL)) ){
                assembler = assembler + crearAssemblerConversionVar(registro1,1);
                registro1=registroAux;
            }
            else
            if ( (elementos.get(1).getToken().getTipo().equals( AnalizadorLexico.variableL) ) && (elementos.get(2).getToken().getTipo().equals(AnalizadorLexico.variableI)) ){
                assembler = assembler + crearAssemblerConversionVar(registro2,2);
                registro2=registroAux;
            }

            assembler = assembler + CMP + " " +  registro1 + ", " + registro2 + '\n';
            controladorTercetos.liberarRegistro(registro1);
            controladorTercetos.liberarRegistro(registro2);
        }
        else
            //caso 2: (OP, registro, variable)
            if ( ( !elementos.get(1).esToken() ) && ( elementos.get(2).esToken() ) ){
                String registro2 = controladorTercetos.getProxRegLibre( elementos.get(2).getToken() );

                if(elementos.get(2).getNombreVar().startsWith("mat@")){
                    String regMatrizAux = controladorTercetos.getRegMatriz(2);
                    if(regMatrizAux==null)
                        assembler = assembler + "MOV" + " " +  registro2 + ", " + elementos.get(2).getNombreVar()+"[" +controladorTercetos.getRegMatriz(1) +"]" +'\n';
                    else
                        assembler = assembler + "MOV" + " " +  registro2 + ", " + elementos.get(2).getNombreVar()+"[" +regMatrizAux +"]" +'\n';
                }
                else
                    assembler = assembler + "MOV" + " " +  registro2 + ", " + elementos.get(2).getNombreVar()+ '\n';

                if ( (elementos.get(1).getToken().getTipo().equals( AnalizadorLexico.variableI) ) && (elementos.get(2).getToken().getTipo().equals(AnalizadorLexico.variableL)) ){
                    assembler = assembler + crearAssemblerConversion(terceto1,1);
                }
                else
                if ( (elementos.get(1).getToken().getTipo().equals( AnalizadorLexico.variableL) ) && (elementos.get(2).getToken().getTipo().equals(AnalizadorLexico.variableI)) ){
                    assembler = assembler + crearAssemblerConversionVar(registro2,2);
                    registro2=registroAux;
                }

                assembler = assembler + CMP + " "  + terceto1.getRegistro() +", " + registro2 + '\n';
                controladorTercetos.liberarRegistro(terceto1.getRegistro());
                controladorTercetos.liberarRegistro(registro2);
            }
            else
                //caso 3: (OP, registro, registro)
                if ( ( !elementos.get(1).esToken() ) && ( !elementos.get(2).esToken() ) ){
                    if ( (elementos.get(1).getToken().getTipo().equals( AnalizadorLexico.variableI) ) && (elementos.get(2).getToken().getTipo().equals(AnalizadorLexico.variableL)) )
                        assembler = assembler + crearAssemblerConversion(terceto1,1);
                    else
                    if ( (elementos.get(1).getToken().getTipo().equals( AnalizadorLexico.variableL) ) && (elementos.get(2).getToken().getTipo().equals(AnalizadorLexico.variableI)) )
                        assembler = assembler + crearAssemblerConversion(terceto2,2);
                    assembler = assembler + CMP  + " " + terceto1.getRegistro() + ", " + terceto2.getRegistro() + '\n';
                    controladorTercetos.liberarRegistro(terceto1.getRegistro());
                    controladorTercetos.liberarRegistro(terceto2.getRegistro());
                }
                else
                    //caso 4: (OP, variable, registro)
                    if ( ( elementos.get(1).esToken() ) && ( !elementos.get(2).esToken() ) ){
                        String registro1 = controladorTercetos.getProxRegLibre( elementos.get(1).getToken() );

                        assembler = assembler + "MOV" + " " +  registro1 + ", " + elementos.get(1).getNombreVar()+ '\n';

                        if ( (elementos.get(1).getToken().getTipo().equals( AnalizadorLexico.variableI) ) && (elementos.get(2).getToken().getTipo().equals(AnalizadorLexico.variableL)) ){
                            assembler = assembler + crearAssemblerConversionVar(registro1,1);
                            registro1=registroAux;
                        }
                        else
                        if ( (elementos.get(1).getToken().getTipo().equals( AnalizadorLexico.variableL) ) && (elementos.get(2).getToken().getTipo().equals(AnalizadorLexico.variableI)) )
                            assembler = assembler + crearAssemblerConversion(terceto2,2);

                        assembler =assembler + CMP + " " + registro1 + ", " + terceto2.getRegistro() + '\n';
                        controladorTercetos.liberarRegistro(registro1);
                        controladorTercetos.liberarRegistro(terceto2.getRegistro());
                    }
        return assembler;
    }

    private String crearAssemblerConversion(Terceto terceto,int pos){
        String assembler = "";
        assembler = assembler + "MOV"  + " " + "AX" + ", " + terceto.getRegistro() + '\n';
        assembler = assembler + "CWDE" + '\n';
        controladorTercetos.liberarRegistro(terceto.getRegistro());
        elementos.get(pos).getToken().setTipo(AnalizadorLexico.variableL);
        String registro = controladorTercetos.getProxRegLibre(elementos.get(1).getToken());
        terceto.setRegistro(registro);
        assembler = assembler + "MOV"  + " " + terceto.getRegistro() + ", " + "EAX" + '\n';
        elementos.get(pos).getToken().setTipo(AnalizadorLexico.variableI);
        return assembler;
    }

    private String crearAssemblerConversionVar(String registro, int pos){
        String assembler = "";
        assembler = assembler + "MOV"  + " " + "AX" + ", " + registro + '\n';
        assembler = assembler + "CWDE" + '\n';
        controladorTercetos.liberarRegistro(registro);
        elementos.get(pos).getToken().setTipo(AnalizadorLexico.variableL);
        registro = controladorTercetos.getProxRegLibre(elementos.get(1).getToken());
        registroAux = registro;
        assembler = assembler + "MOV"  + " " + registro + ", " + "EAX" + '\n';
        elementos.get(pos).getToken().setTipo(AnalizadorLexico.variableI);
        return assembler;
    }*/
}
