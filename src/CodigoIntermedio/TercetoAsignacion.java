package CodigoIntermedio;

import AnalizadorLexico.AnalizadorLexico;

    public class TercetoAsignacion extends Terceto {


        public TercetoAsignacion(TercetoSimple izq, TercetoSimple medio, TercetoSimple der, int numeroTerceto) {
            super(izq, medio, der, numeroTerceto);
            // TODO Auto-generated constructor stub
        }

        @Override
        public String getAssembler() {

            String assembler = "";
            Terceto terceto2 = null;
            if(elementos.size()>2){
                if (!elementos.get(2).esToken())
                    terceto2 = controladorTercetos.getTerceto(Integer.parseInt(elementos.get(2).getNombreVar()));

                //caso 1: (ASIG, variable, variable)
                if ((elementos.get(1).esToken()) && (elementos.get(2).esToken())) {

                    if (elementos.get(2).getToken().getUso()==AnalizadorLexico.CTEF) {
                        assembler = assembler + "FLD auxf" + elementos.get(2).getNombreVar().replace(',', 'a').replace('-', 'n') + '\n';
                        assembler = assembler + "FST "+ elementos.get(1).getNombreVar() +'\n';

                    }else{
                        assembler = assembler + "MOV " + reg1Long + ", " + elementos.get(2).getNombreVar() + '\n';
                        assembler = assembler + "MOV " + elementos.get(1).getNombreVar() + ", " + reg1Long + '\n';

                    }
                }
                else
                    //caso 2: (ASIG, variable, registro)
                    if (elementos.get(2).getToken().getTipo()==AnalizadorLexico.variableF) {
                        if (elementos.get(2).getToken().isConvertido()) {
                            assembler = assembler + "FILD " + AUX + terceto2.getNumeroTerceto() + '\n';
                            assembler = assembler + "FST " + elementos.get(1).getNombreVar() + '\n';
                        }
                        else{
                            assembler = assembler + "FLD " + AUX + terceto2.getNumeroTerceto() + '\n';
                            assembler = assembler + "FST " + elementos.get(1).getNombreVar() + '\n';
                        }
                    }else {
                        assembler = assembler + "MOV " + reg2Long + ", " + AUX+terceto2.getNumeroTerceto() + '\n';
                        assembler = assembler + "MOV  " + elementos.get(1).getNombreVar() + ", " + reg2Long + '\n';
                    }
            }
            return assembler;
        }
    }