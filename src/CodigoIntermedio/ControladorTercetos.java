package CodigoIntermedio;

import AnalizadorLexico.TablaSimbolos;
import AnalizadorLexico.Token;

import java.util.ArrayList;

public class ControladorTercetos {

    public static final String BF = "BF";
    public static final String BI = "BI";


    private ArrayList<Terceto> tercetos;
    private ArrayList<Integer> pila;
    private ArrayList<Integer> labelPendientes; // por el tema del if
    private ArrayList<Token> prints;
    private TablaSimbolos tablaSimbolos;

    private int cantPrint = 0;
    private int num_terceto_actual = 0;

    public ControladorTercetos() {
        tercetos = new ArrayList<Terceto>();
        pila = new ArrayList<Integer>();
        labelPendientes = new ArrayList<Integer>();
        tablaSimbolos = null;
        prints = new ArrayList<Token>();
    }

    public String imprimirTercetos() {
        String cadena="Tercetos: \n";
        for (Terceto t: tercetos )
            cadena= cadena + t.imprimirTerceto() + '\n';
        return cadena;
    }

    public void addTerceto(int index, Terceto t){
        tercetos.add(index, t);
    }

    public int borrarLabelPendiente() {
        int l = labelPendientes.get( labelPendientes.size()-1 );
        labelPendientes.remove( labelPendientes.size()-1 );
        return l;
    }

    public void addTerceto(Terceto t){
        tercetos.add(t);
    }

    public void removeTerceto(){
        tercetos.remove(tercetos.size());
        num_terceto_actual--;
    }

    public int getProxNumero(){
        return tercetos.size()+1;
    }

    public int getCantTercetos(){
        return tercetos.size();
    }

    public void addLabelPendiente(int labelPendiente) {
        this.labelPendientes.add( labelPendiente );
    }

    public String numeroTercetoString(){
        return String.valueOf(tercetos.size());
    }

    public void apilar(){
        pila.add(new Integer(tercetos.size()-1) );
    }

    public void apilarControl() {pila.add(new Integer(tercetos.size())); }

    public void desapilar(){
        int tercetoMod = pila.get(pila.size()-1);
        pila.remove(pila.size()-1);
        Terceto nuevo = tercetos.get(tercetoMod);
        Token t = new Token( String.valueOf(tercetos.size()+1) );
        t.setNumTerceto(false);
        TercetoSimple add = new TercetoSimple(t);
        if (nuevo.getTerceto(1) == null)
            nuevo.setElemento(1, add);
        else
            nuevo.setElemento(2, add);
        tercetos.set(tercetoMod, nuevo);
    }

    public void desapilarControl(){
        Terceto nuevo = tercetos.get(tercetos.size()-1);
        Token t = new Token( String.valueOf(pila.remove(pila.size()-1)) );
        t.setNumTerceto(false);
        TercetoSimple add = new TercetoSimple(t);
        nuevo.setElemento(1, add);
    }


    public ArrayList<Terceto> getTercetos() {
        return tercetos;
    }

    public Terceto getTerceto (int index) {
        return tercetos.get(index-1);
    }

    public String generarAssembler() {
        String assembler = "";

        num_terceto_actual = 1; //numero de terceto para colocar el label
        for ( Terceto t: tercetos ){

            t.setControladorTercetos(this);
            assembler = assembler + t.getAssembler();

            num_terceto_actual++;
            if ( (!labelPendientes.isEmpty()) && ( num_terceto_actual == labelPendientes.get(labelPendientes.size()-1) ) ){
                assembler = assembler + "Label" + String.valueOf(labelPendientes.get(labelPendientes.size()-1))+ ":" + '\n';
                borrarLabelPendiente();
            }
        }
        return assembler;
    }

    public int getNumTercetoActual(){
        return num_terceto_actual;
    }

    //Informa que se agrego un terceto de print y modifica el ultimo terceto para coordinar
    //el nombre con el .datat del ASM
    //FIJARSE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    public void addPrint(String nombre){
        prints = tablaSimbolos.getPrints();
        for (int i=0; i<prints.size(); i++)
            if ( prints.get(i).getNombre() == nombre)
                ((TercetoPrint)tercetos.get(tercetos.size()-1)).setPrint(String.valueOf(i+1));
    }


    public void setTablaSimbolos(TablaSimbolos tablaSimbolos) {
        this.tablaSimbolos = tablaSimbolos;
    }

    public String getPrintsAssembler(){
        String assembler = "";
        for (Token t:prints)
            assembler = assembler + tablaSimbolos.getTipoAssember(t) + '\n';
        return assembler;
    }





}
