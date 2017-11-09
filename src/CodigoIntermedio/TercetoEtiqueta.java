package CodigoIntermedio;

public class TercetoEtiqueta extends Terceto {

    public TercetoEtiqueta(TercetoSimple izq, TercetoSimple medio, TercetoSimple der, int numeroTerceto) {
        super(izq, medio, der, numeroTerceto);
    }

    public String getAsembler(){
        return "label"+numeroTerceto+":"+"\n";
    }
}
