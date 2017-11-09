package CodigoIntermedio;

public class TercetoConversion extends Terceto {

    public TercetoConversion(TercetoSimple izq, TercetoSimple medio,	TercetoSimple der, int numeroTerceto) {
        super(izq, medio, der,  numeroTerceto);
    }

    @Override
    public String getAssembler() {
        return null;
    }


}
