package AnalizadorLexico;

public class CeldaAS extends CeldaABS{
    static final String constanteI = "CI";
    static final String identificador = "I";
    static final String constanteL = "CL";

    public static final long maximo = 32767;
    public static final long minimo = -32768;
    static final long maximoL = 2147483647;
    static final long minimoL = -2147483648;

    static final String Smaximo = "_i32767";
    static final String Sminimo = "_i-32768";
    static final String SmaximoL = "_l2147483647";
    static final String SminimoL = "_l-2147483648";


    private TablaSimbolos tablaSimb;
    private Error error;// ver si esta bien aca


    public CeldaAS(int proxEstado, TablaSimbolos tablaSimb, Error error){
        super(proxEstado);
        this.tablaSimb = tablaSimb;
        this.error = error;
    }

    public CeldaAS(int proxEstado, TablaSimbolos tablaSimb){
        super(proxEstado);
        this.tablaSimb = tablaSimb;

    }

    public Error getError() {
        return error;
    }

    public int ejecutar_celda(Token t) {
        //si error es null es un estado final. Devuelvo -1 si es Final o -2 si es Error.
        int warning;

        if  ( error == null ) {
            if (t != null){
                //Se chequea si es un ID, si el nombre es >20 lo trunco.
                //TODO: Deberia tener un warning o alfinal no?
                if ((t.getNombre().length()>20) && (t.getUso()== AnalizadorLexico.ID) ){
                    String truncar=t.getNombre() ;
                    t.setNombre( truncar.substring(0, 19) );
                    if (!tablaSimb.existe(t.getNombre() ))
                        tablaSimb.addSimbolo(t);

                    return -3;
                }

                //Se chequea el valor de la constante
                //Si esta fuera de los limites, tambien lo trunco al maximo o minimo correspondiente.
                //TODO: Si lo trunco no lo deberia agregar tmb a la tabla de simb. ?

                if ( (t.getUso() == AnalizadorLexico.CTEI) || (t.getUso() == AnalizadorLexico.CTEL) ) {
                    String cadena = t.getNombre();

                    if(t.getUso() == AnalizadorLexico.CTEI)
                        t.setTipo("integer");
                    else
                        t.setTipo("long");

                    //TODO:
                    //Integer.parseInt() soporta hasta numeros de diez digitos, si se le da un string mas grande
                    //se rompe !

                    //int valor;
                    //if(cadena.length()<=10)
                    long valor = Long.parseLong(cadena.substring(2)); //Se extrae el valor despues de _i o _l
                    t.setValor(valor);
                    //else
                    //valor = Integer.parseInt(cadena.substring(0,9));

                    if ( (valor>maximo) && (t.getUso()== AnalizadorLexico.CTEI) ){
                        t.setNombre(Smaximo);
                        t.setValor(maximo);
                        if (!tablaSimb.existe(t.getNombre() ))
                            tablaSimb.addSimbolo(t);
                        return -4;
                    }
                    if ( (valor<minimo) && (t.getUso()== AnalizadorLexico.CTEI) ){
                        t.setNombre(Sminimo);
                        t.setValor(minimo);
                        if (!tablaSimb.existe(t.getNombre() ))
                            tablaSimb.addSimbolo(t);
                        return -4;
                    }
                    if ( (valor>maximoL) && (t.getUso()== AnalizadorLexico.CTEL) ){
                        t.setNombre(SmaximoL);
                        t.setValor(maximoL);
                        if (!tablaSimb.existe(t.getNombre() ))
                            tablaSimb.addSimbolo(t);
                        return -4;
                    }
                    if ( (valor<minimoL) && (t.getUso()== AnalizadorLexico.CTEL) ){
                        t.setNombre(SminimoL);
                        t.setValor(minimoL);
                        if (!tablaSimb.existe(t.getNombre() ))
                            tablaSimb.addSimbolo(t);
                        return -4;
                    }
                }

                //Si es un ID o CTE, va a la tabla de simbolos.
                if(tablaSimb.es_Agregable(t))
                    tablaSimb.addSimbolo(t);


                //Si es una palabra reservada la tengo que devolver de alguna tabla
                return -1;
            }
            return -1;
        }
        else
            return -2;
    }
}
