package CodigoIntermedio;
import java.util.ArrayList;

public abstract class Terceto {

    public static final String AUX = "auxiliar";
    public static final String reg2Long = "EBX";
    public static final String reg1Long = "ECX";
    public static final String reg3Long = "EAX";
    public static final String reg4Long = "EDX";
    public static final String ST = "ST";
    public static final String ST1 = "ST(1)";
    public static final String ST2 = "ST(2)";
    public static final String ST3 = "ST(3)";
    public static final String ST4 = "ST(4)";
    public static final String ST5 = "ST(5)";
    public static final String ST6 = "ST(6)";
    public static final String ST7 = "ST(7)";



    protected ArrayList<TercetoSimple> elementos;
    protected int numeroTerceto;
    protected ControladorTercetos controladorTercetos;
    int posicion;



    public Terceto(TercetoSimple izq, TercetoSimple medio, TercetoSimple der, int numeroTerceto) {
        elementos = new ArrayList<TercetoSimple>();
        elementos.add(izq);
        elementos.add(medio);
        elementos.add(der);
        this.numeroTerceto = numeroTerceto;

    }
    public int getNumeroTerceto (){
        return numeroTerceto;
    }

    public void setPosicionTerceto(int pos){
        posicion = pos;
    }

    public int getPosicionTerceto(){
        return posicion;
    }

    public String imprimirTerceto(){
        String terceto = numeroTerceto + "  (";
        for (int i = 0 ; i< elementos.size(); i++){
            if (elementos.get(i) != null)
                terceto = terceto + elementos.get(i).imprimirTerceto();
            else
                terceto = terceto + " - ";
            if (i != elementos.size()-1)
                terceto = terceto + ", ";
            else
                terceto = terceto + ")";
        }
        return terceto;
    }

    public void setElemento(int index, TercetoSimple t){
        elementos.set(index, t);
    }

    public TercetoSimple getTerceto(int index){
        if (elementos.get(index)==null)
            return null;
        else
            return elementos.get(index);
    }

    public void setControladorTercetos(ControladorTercetos controladorTercetos) {
        this.controladorTercetos = controladorTercetos;
    }

    public abstract String getAssembler();
}

