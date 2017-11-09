package CodigoIntermedio;

public class TercetoEtiqueta extends Terceto {

    public TercetoEtiqueta(TercetoSimple izq, TercetoSimple medio, TercetoSimple der, int numeroTerceto) {
        super(izq, medio, der, numeroTerceto);
    }

    @Override
    public String getAssembler() {
        return "label"+numeroTerceto+":"+"\n";
    }

}
