package AnalizadorLexico;

public abstract class CeldaABS {
    protected int proxEstado;

    public CeldaABS(int proxEstado){
        this.proxEstado = proxEstado;
    }


    public abstract int ejecutar_celda(Token t);

    public int getEstado(){
        return proxEstado;
    }
}
