package CodigoIntermedio;

public class TercetoConversion extends Terceto {

    public TercetoConversion(TercetoSimple izq, TercetoSimple medio,	TercetoSimple der, int numeroTerceto) {
        super(izq, medio, der,  numeroTerceto);
    }

    @Override
    public String getAssembler() {
        String assembler = "";
//        Terceto terceto1 = null;
//        if (!elementos.get(1).esToken()) {
//            terceto1 = controladorTercetos.getTerceto(Integer.parseInt(elementos.get(1).getNombreVar()));
//            assembler = assembler + "FLD  " + AUX + terceto1.getNumeroTerceto() + '\n';
//        }
        //assembler = assembler + "CVTLF " +'\n';
        return assembler;
    }


}
