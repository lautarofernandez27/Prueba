package AnalizadorLexico;

public class CeldaAS extends CeldaABS{

    private static final double maximoF = 3.40282347E38;
    private static final double minimoF = 1.17549435E-38;
    private static final long maximoL = 2147483647;
    private static final long minimoL = -2147483648;


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

        if  ( error == null ) {
            if (t != null){
                //Se chequea si es un ID, si el nombre es >15 lo trunco.
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
                    double valorf= Double.parseDouble(cadenaf.replace(",","."));
                    t.setNombre(cadenaf);
                    if (valorf>maximoF){
                        return -4;
                    }
                    if (valorf<minimoF){
                        return -4;
                    }
                }
                if(t.getUso() == AnalizadorLexico.CTEL) {
                    String cadenal = t.getNombre();
                    long valorl= Long.parseLong(cadenal);
                    t.setTipo("long");
                    t.setNombre(cadenal);//Coloco el mayor valor aceptado por long
                    if (valorl>maximoL){
                        return -4;
                    }
                    if (valorl<minimoL){
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
