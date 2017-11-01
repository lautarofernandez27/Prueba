package CodigoIntermedio;
import java.util.ArrayList;

public abstract class Terceto {

    protected ArrayList<TercetoSimple> elementos;
    protected int numeroTerceto;
    protected ControladorTercetos controladorTercetos;
    int posicion;
    int varAux;

    public Terceto(TercetoSimple izq, TercetoSimple medio, TercetoSimple der, int aux,  int numeroTerceto) {
        elementos = new ArrayList<TercetoSimple>();
        elementos.add(izq);
        elementos.add(medio);
        elementos.add(der);
        varAux=aux;
        this.numeroTerceto = numeroTerceto;

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

