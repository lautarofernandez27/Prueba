package CodigoIntermedio;

public class TercetoWhile extends Terceto {
    String tipoSalto;
    public static final String etiquetaSaltoIncondicional = "JMP";

    public TercetoWhile (TercetoSimple izq,TercetoSimple medio, TercetoSimple der,int numeroTerceto){
        super(izq,medio,der,numeroTerceto);
    }

    public void setTipoSalto(String tipoSalto){
        if(tipoSalto== "<=")
            this.tipoSalto = "JG";
        else
        if(tipoSalto.equals("=="))
            this.tipoSalto = "JNE";
        else
        if(tipoSalto.equals(">="))
            this.tipoSalto = "JL";
        else
        if(tipoSalto.equals(">"))
            this.tipoSalto = "JLE";
        else
        if(tipoSalto.equals("<"))
            this.tipoSalto = "JGE";
        else
        if(tipoSalto.equals("<>"))
            this.tipoSalto = "JE";
    };


    public String getAssembler() {
        String assembler = "";
        String operador = elementos.get(0).getNombreVar();
        if (operador == controladorTercetos.BF){
            assembler = assembler + tipoSalto + " Label" + elementos.get(2).getNombreVar() + '\n';
            controladorTercetos.addLabelPendiente( Integer.parseInt(elementos.get(2).getNombreVar() ) );
        }
        else{
            //BI
            assembler ="JMP Label" + elementos.get(1).getNombreVar() + '\n';
        }
        return assembler;
    }

}
