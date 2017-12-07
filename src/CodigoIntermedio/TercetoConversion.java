package CodigoIntermedio;

public class TercetoConversion extends Terceto {

    public TercetoConversion(TercetoSimple izq, TercetoSimple medio,	TercetoSimple der, int numeroTerceto) {
        super(izq, medio, der,  numeroTerceto);
    }

    @Override
    public String getAssembler() {
        String assembler = "";
        Terceto terceto1 = null;
        if (!elementos.get(1).esToken()) {
            terceto1 = controladorTercetos.getTerceto(Integer.parseInt(elementos.get(1).getNombreVar()));
            assembler = assembler + "MOV " + reg3Long + ", " + AUX + terceto1.getNumeroTerceto() + '\n';
        }
        else
            assembler = assembler + "MOV " + reg3Long + ", " + elementos.get(1).getNombreVar() + '\n';

        assembler = assembler + "MOV  " + AUX + numeroTerceto + ", " + reg3Long + '\n';
        return assembler;
    }


}
