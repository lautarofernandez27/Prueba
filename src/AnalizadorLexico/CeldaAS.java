package AnalizadorLexico;

public class CeldaAS extends CeldaABS{

    public static final double maximoF = 2.2250738585072014E-308;
    public static final double minimoF = -1.7976931348623157E308;
    static final long maximoL = 2147483647;
    static final long minimoL = -2147483648;


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
                if ((t.getNombre().length()>15) && (t.getUso()== AnalizadorLexico.ID) ){
                    String truncar=t.getNombre() ;
                    t.setNombre( truncar.substring(0, 14) );
                    if (!tablaSimb.existe(t.getNombre() ))
                        tablaSimb.addSimbolo(t);

                    return -3;
                }

                //Se chequea el valor de la constante
                //Si esta fuera de los limites, tambien lo trunco al maximo o minimo correspondiente.

                if (t.getUso() == AnalizadorLexico.CTEF) {
                    t.setTipo("float");
                    String cadenaf = t.getNombre();
                    double valorf= Double.parseDouble(cadenaf);
                    t.setNombre(cadenaf);
                    if (valorf>maximoF){
                        t.setNombre((String)maximoF);//Coloco el mayor valor aceptado por float
                        if (!tablaSimb.existe(t.getNombre()))
                            tablaSimb.addSimbolo(t);
                        return -4;
                    }
                    if (valorf<minimoF){
                        t.setNombre((String)minimoF);//Coloco el mayor valor aceptado por float
                        if (!tablaSimb.existe(t.getNombre() ))
                            tablaSimb.addSimbolo(t);
                        return -4;
                    }
                }
                if(t.getUso() == AnalizadorLexico.CTEL) ) {
                    String cadenal = t.getNombre();
                    long valorl= Long.parseLong(cadenal);
                    t.setTipo("long");
                    t.setNombre(cadenal);//Coloco el mayor valor aceptado por long
                    if (valorl>maximoL){
                        t.setNombre((String)maximoL);
                        if (!tablaSimb.existe(t.getNombre() ))
                            tablaSimb.addSimbolo(t);
                        return -4;
                    }
                    if (valorl<minimoL){
                        t.setNombre((String)minimoL);
                        if (!tablaSimb.existe(t.getNombre() ))
                            tablaSimb.addSimbolo(t);
                        return -4;
                    }
                }

                //Si es un ID o CTE, va a la tabla de simbolos.
                if(tablaSimb.es_Agregable(t))
                    tablaSimb.addSimbolo(t);

                return -1;
            }
            return -1;
        }
        else
            return -2;
    }
}
