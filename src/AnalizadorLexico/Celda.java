package AnalizadorLexico;

public class Celda extends CeldaABS{
    public Celda(int proxEstado){
        super(proxEstado);
    }

    public int ejecutar_celda(Token t) {
        return proxEstado;
    }
}
