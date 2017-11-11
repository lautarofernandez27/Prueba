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
            if (!elementos.get(2).esToken())
                terceto2 = controladorTercetos.getTerceto(Integer.parseInt(elementos.get(2).getNombreVar()));

            //caso 1: (ASIG, variable, variable)
            if ((elementos.get(1).esToken()) && (elementos.get(2).esToken())) {
                assembler = assembler + "MOV  " + elementos.get(1).getNombreVar() + ",  " + elementos.get(2).getNombreVar() + '\n';
            }
            //caso 2: (ASIG, variable, registro)
            assembler = assembler + "MOV  " + elementos.get(1).getNombreVar() + ",  " + AUX + terceto2.getNumeroTerceto() + '\n';

            return assembler;
        }
    }