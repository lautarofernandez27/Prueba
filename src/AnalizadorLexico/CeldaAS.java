package AnalizadorLexico;

public class CeldaAS extends CeldaABS{

    public static final double maximoFP = 3.40282347E38;
    public static final double maximoFN = -1.17549435E-38;
    public static final double minimoFP = 1.17549435E-38;
    public static final double minimoFN = -3.40282347E38;
    public static final long maximoL = 2147483647;
    public static final long minimoL = -2147483648;


    private TablaSimbolos tablaSimb;
    private Error error;


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

                    return -3;
                }

                //Se chequea el valor de la constante
                //Si esta fuera de los limites, devuelvo error

                if (t.getUso() == AnalizadorLexico.CTEF) {
                    t.setTipo("float");
                    String cadenaf = t.getNombre();
                    double valorf= Double.parseDouble(cadenaf.replace(",","."));
                    t.setNombre(cadenaf);
                    if (valorf>maximoFP){
                        return -4;
                    }
                    if ((valorf<minimoFP)&&  (valorf != 0.0)){
                        return -4;
                    }
                }
                if(t.getUso() == AnalizadorLexico.CTEL) {
                    String cadenal = t.getNombre();
                    long valorl= Long.parseLong(cadenal);
                    t.setTipo("long");
                    t.setNombre(cadenal);
                    if (valorl>maximoL+1){
                        return -4;
                    }
                    if (valorl<minimoL){
                        return -4;
                    }
                }

                //Si es una Cadena va a la tabla de simbolos
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
