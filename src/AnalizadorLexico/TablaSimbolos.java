package AnalizadorLexico;

import java.util.ArrayList;
import java.util.Hashtable;

public class TablaSimbolos {


    public static final String tipoPrint = "db";
    public static final String trentaydosBits = "dd";
    private Hashtable<String, Token> tSimb;

    ArrayList<Token> prints = new ArrayList<Token>();

    public TablaSimbolos(){
        tSimb = new Hashtable<>();
    }

    public void addSimbolo( Token t){
        if (t.getUso()== AnalizadorLexico.ID){
            int indice= existe(t.getNombre())+1;
            t.setNombre(t.getNombre().toLowerCase()+"@"+indice);
            tSimb.put(t.getNombre(), t);

        }
        else
            tSimb.put(t.getNombre(), t);
    }


    //Me confirma si el token es agregable a la tabla de simbolos.
    public boolean es_Agregable( Token t){
        if(    // t.getUso() == AnalizadorLexico.ID ||
              //  t.getUso() == AnalizadorLexico.CTEF ||
                       // t.getUso() == AnalizadorLexico.CTEL ||
                        t.getUso() == AnalizadorLexico.CADENA)
            return true;
        else
            return false;
    }

    public ArrayList<Token> getTokens(){
        return new ArrayList<>(tSimb.values());
    }

    public int existe(String nombre) {
        boolean encontre= true;
        for (int i=0;encontre;i++)
            if (!tSimb.containsKey(nombre.toLowerCase()+"@"+i)) {
                return i-1;
            }

        return -1;
    }

    public Token getToken(String nombre){
        if (existe(nombre)!=-1)
            return tSimb.get(nombre.toLowerCase()+"@"+existe(nombre));
        else
            return null;
    }


    public void borrarSimbolo(String nombre) {
            if(tSimb.containsKey(nombre)){
                tSimb.remove(nombre);
            }
    };


    public ArrayList<Token> getPrints() {
        System.out.println("getPrint");
        ArrayList<Token> tokens =getTokens();
        for (Token t: tokens){
            if ( ( t.getUso() == AnalizadorLexico.CADENA) && (!estaPrint(t) ) )
                prints.add(t);
        }
        return prints;
    }

    private boolean estaPrint(Token token) {
        for (Token t : prints)
            if (t.getNombre().equals(token.getNombre()))
                return true;
        return false;
    }


    public String getAssembler (){
        ArrayList<Token> tokens = getTokens();
        String assembler = "";
        for (Token t: tokens){
            if  ( (t.getUso() != AnalizadorLexico.CTEL) || (t.getUso() != AnalizadorLexico.CTEF)){
                String tipoAssembler = getTipoAssember(t);
                if(t.getUso() != AnalizadorLexico.CADENA)
                    assembler = assembler + t.getNombre()+ " " + tipoAssembler + '\n';
            }
        }
        return assembler;
    }


    public String getTipoAssember(Token t) {
        String tipo = "";
        AnalizadorLexico analizador = new AnalizadorLexico(null, null); //es para usar las constantes

        if ( t.getTipo() == analizador.variableL || t.getTipo() == analizador.variableF){
            tipo = trentaydosBits;
        }
        else
        if( t.getUso() == analizador.CADENA){
            //Se lleva una cuenta de la posicion del print para luego
            //coordinar con los tercetos la posicion.
            tipo = "print" +String.valueOf(prints.indexOf(t)+1) +" " +tipoPrint +" " +t.nombre.replace('\'','"')  +"," ;
        }
        return tipo + " 0";//inicializo en cero todas las variables
    }

}
