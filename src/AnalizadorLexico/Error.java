package AnalizadorLexico;

public class Error {
    String descripcion;
    //Warning o error de compilacion
    String tipo;
    int linea;

    public Error(String descripcion, String tipo, int linea){
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.linea = linea;
    }

    public Error(String descripcion, String tipo){
        this.descripcion = descripcion;
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public String Imprimir(){
        String l= "";
        l= l + "Linea "+ String.valueOf(linea)+": ";
        return l + descripcion ;
    }

}
