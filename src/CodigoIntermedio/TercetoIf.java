package CodigoIntermedio;

import com.sun.org.apache.xpath.internal.SourceTree;

public class TercetoIf extends Terceto {

    private String tipoSalto;
    public static final String etiquetaSaltoIncondicional = "JMP";

    public TercetoIf(TercetoSimple izq, TercetoSimple medio,	TercetoSimple der, int numeroTerceto) {
        super(izq, medio, der, numeroTerceto);
        // TODO Auto-generated constructor stub
    }

    public void setTipoSalto(String tipoSalto){
        if(tipoSalto== "<=")
            this.tipoSalto = "JA";
        else
        if(tipoSalto.equals("=="))
            this.tipoSalto = "JNE";
        else
        if(tipoSalto.equals(">="))
            this.tipoSalto = "JB";
        else
        if(tipoSalto.equals(">"))
            this.tipoSalto = "JBE";
        else
        if(tipoSalto.equals("<"))
            this.tipoSalto = "JAE";
        else
        if(tipoSalto.equals("<>"))
            this.tipoSalto = "JE";
    };


    public String getAssembler() {
        String assembler = "";
        String operador = elementos.get(0).getNombreVar();
        if (operador == controladorTercetos.BF){
            assembler = tipoSalto + " Label" + elementos.get(2).getNombreVar() + '\n';
            controladorTercetos.addLabelPendiente( Integer.parseInt(elementos.get(2).getNombreVar() ) );
        }
        else{
            assembler = etiquetaSaltoIncondicional + " Label" + elementos.get(1).getNombreVar() + '\n';
            assembler = assembler + "Label" + String.valueOf( controladorTercetos.borrarLabelPendiente() ) +":" + '\n';
            controladorTercetos.addLabelPendiente( Integer.parseInt( elementos.get(1).getNombreVar() ) );
        }
        return assembler;
    }
}
