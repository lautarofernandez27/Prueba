package AnalizadorLexico;

import java.util.ArrayList;
import java.util.Hashtable;

public class AnalizadorLexico {

    static final int F = -1;  //F Es la constante que indica un estado final !
    static final int E = -2;  //E Es la constante que indica un estado de error !
    static final int WI = -3; //WI Es la constante que indica un Warning de identificador
    static final int WC = -4; //WC Es la constante que indica un Warning de constante

    public static final String constanteF = "constante flotante";
    public static final String variableF = "float";

    static final String identificador = "I";

    public static final String constanteL = "constante long";
    public static final String variableL = "long";

    //Constantes para YACC
    public static final int ID = 257;
    public static final int CTEL = 258;
    public static final int S_IGUAL_IGUAL = 259;
    public static final int S_MAYOR_IGUAL = 260;
    public static final int S_MENOR_IGUAL = 261;
    static final int S_DESIGUAL = 262;	//<>
    public static final int CTEF = 263;
    static final int COMENTARIO = 264;

    //PALABRA RESERVADA
    static final int IF = 265;
    static final int THEN = 266;
    static final int ELSE = 267;
    static final int END_IF = 268;
    public static final int OUT = 269;
    static final int BEGIN = 270;
    static final int END = 271;
    static final int WHILE = 272;
    static final int DO = 273;
    static final int LET = 274;
    static final int LONGINT = 275;

    static final String warningI = "Warning: Identificador truncado por superar 15 caracteres.";
    static final String warningC = "Warning: Constante fuera de rango. La constante toma el valor minimo o maximo dependiendo su signo";

    //Los tokens que se van identificando se almacenan en una lista
    private ArrayList<Token> tokens = new ArrayList<>();

    //Se almacenan los valores con los cual se reconocen a los tokens en el YACC.
    //Por ejemplo, un ID se identifica con el 257, CTEL 258...
    Hashtable<Integer, Integer> id_tokens = new Hashtable<>();

    //Los errores que se van identificando se almacenan en una lista
    private ArrayList<Error> erroresComp;
    private ArrayList<Error> erroresWarning;
    private boolean errorEncontrado;

    //Estado actual. El estado inicial es siempre 0.
    //El estadoAnterior se utiliza para reconocer el token una vez que se llega al estado Final.
    private int estadoActual;
    private int estadoAnterior;
    private int estadoAux;

    //Buffer del token, voy almacenando lo que leo.
    //Una vez que reconozco el tipo de token, lo vacio y vuelvo a empezar.
    private StringBuilder token_buffer;

    //Tiene toda la funcionalidad del archivo
    private ControladorArchivo archivo;

    //Matriz transicion de estados. Hice la clase por toda la inicializacion pero lo vemos
    private MatrizTE matTrans;

    //
    private CeldaAbs celdaActual;

    public AnalizadorLexico( ControladorArchivo archivo, TablaSimbolos ts ) {
        //Contiene los estados de transicion
        matTrans = new MatrizTE(ts);
        //Se encarga de manejar el archivo
        this.archivo = archivo;
        //Estado actual. El estado inicial es siempre 0.
        estadoActual = 0;
        //Almacena el token que se esta formando
        token_buffer = new StringBuilder();
        erroresComp = new ArrayList<>();
        erroresWarning = new ArrayList<Error>();

        agregarTokens();

    }


    //Se encarga de agregar los valores correspondientes de cada token para ser identificados por el YACC
    public void agregarTokens(){

        id_tokens.put(1, ID);
        id_tokens.put(14, COMENTARIO);	//COMENTARIO VACIO
        id_tokens.put(17, COMENTARIO);
        id_tokens.put(21, CTEF);
        id_tokens.put(22, CTEL);
    }

    public int sigToken(){

        if (token_buffer.length() != 0)
            token_buffer.delete(0, token_buffer.length());
        char ultimoChar;
        int lineaActual=0;

        estadoActual = 0;
        estadoAnterior = 0;

        //Para que no almacene en el buffer los espacios vacios
        while ( (archivo.getActual() == ' ' ) || (archivo.getActual() == '	'  ) || (archivo.getActual() == '\n' ))
            archivo.avanzar();

        if(archivo.finArchivo())
            return estadoAnterior;

        while  (estadoActual != F && estadoActual != E)  {
            //Almaceno el ultimo caracter que leo
            ultimoChar = archivo.getActual();

            token_buffer.append(ultimoChar);
            lineaActual = archivo.getLinea();

            //Ejecuto el proximo estado
            celdaActual = matTrans.getCelda(estadoActual, matTrans.getColumna(new Character (ultimoChar) ) );

            estadoAux = estadoAnterior;
            estadoAnterior = estadoActual;

            estadoActual = celdaActual.ejecutar_celda(null);


            if (estadoActual == E){
                //Hay error.
                //El error se genera si hay un problema ejecutando la accion semantica.
                Error errorCelda = ((CeldaAS) celdaActual).getError();
                erroresComp.add(new Error(errorCelda.getDescripcion(),errorCelda.getTipo(), lineaActual) );
                errorEncontrado = true;
            }
            archivo.avanzar();
        }
        archivo.retroceder();
        token_buffer.deleteCharAt(token_buffer.length()-1);
        estadoActual = celdaActual.ejecutar_celda(new Token( token_buffer.toString(), calcularUso(estadoAnterior,token_buffer.toString()) ) );
        if (estadoActual == WI)
            erroresWarning.add( new Error(warningI, "WI", lineaActual) );
        if (estadoActual == WC)
            erroresWarning.add( new Error(warningC, "WC", lineaActual) );
        return estadoAnterior;
    }


    public Token yylex(){

        sigToken();

        if (archivo.finArchivo()){
            Token token = new Token ("Fin de archivo", 0);
            return token;
        }

        if(errorEncontrado==false){
            Token token = new Token(token_buffer.toString(), calcularUso(estadoAnterior, token_buffer.toString()));
            celdaActual.ejecutar_celda(token);
            tokens.add(token);

            if (token.getUso()== ANOTACIONF || token.getUso() == ANOTACIONC ) {
                //1- Puedo o hacer un yylex() y tirar a la basura el token que sigue,
                //pero si tengo &&@F por filas. &&@F = Anotacion, por = ID (Se descarta), filas = ID (No se descarta)
                //2- O puedo agregar && y hacer que lo que siga sea un comentario.
                //Por lo tanto del estado 15 y 16 se pasa a esdtado FINAL.
                //3- Tambien se podria hacer que se lea todo como comentario y que el sintactico se encargue de leer
                // @F o @C y hacer las modificaciones correspondientes.

                if (archivo.getActual()!='\n') //para que si la anotacion no tiene comentario no agregue uno vacio.
                    archivo.add("&&");
            }

            if (token.getUso() == COMENTARIO)
                return yylex();
            else
                return token;
        }else{
            errorEncontrado = false;
            Token token = new Token(token_buffer.toString(), -2);
            tokens.add(token);
            celdaActual.ejecutar_celda(token);
            archivo.avanzar();

            return yylex(); //token.calcularToken();
        }
    }

    private Integer calcularUso(int estado, String valor){
        if (estado == 1){
            switch (valor){
                case "if": return IF;
                case "else": return ELSE;
                case "endif": return ENDIF;
                case "print": return PRINT;
                case "for": return FOR;
                case "matrix": return MATRIX;
                case "allow": return ALLOW;
                case "to": return TO;
                case "integer": return INTEGER;
                case "longint": return LONGINT;
                default: return ID;
            }
        }

        if(estado == 20){
            if(valor.length()>1){
                if(valor.equals("--"))
                    return AnalizadorLexico.S_RESTA_RESTA;
                else
                if(valor.equals(":="))
                    return AnalizadorLexico.S_ASIGNACION;
                else
                if(valor.equals(">="))
                    return AnalizadorLexico.S_MAYOR_IGUAL;
                else
                if(valor.equals("<="))
                    return AnalizadorLexico.S_MENOR_IGUAL;
                else
                if(valor.equals("!="))
                    return AnalizadorLexico.S_EXCLAMACION_IGUAL;
                else
                if(valor.charAt(0)=='"' && valor.charAt(valor.length()-1)=='"')	//Un comentario
                    return AnalizadorLexico.MULTI_LINEA;
            }
        }
        if (id_tokens.containsKey(estado))
            return id_tokens.get(estado);
        else
        if(estadoActual!=-2)
            return  (int) valor.charAt(0);  //Devuelvo el valor ASCII
        else
            return -2;
    }

    public ArrayList<Error> getErrores(){
        return erroresComp;
    }

    public ArrayList<Error> getWarnings(){
        return erroresWarning;
    }

    public String mostrarTokens(){

        String cadena = "Tokens: \n";
        for ( Token t: tokens)
            cadena =cadena + t.imprimirToken() + "\n";
        cadena = cadena + "\n";
        return cadena;
    }

    public String mostrarWarning(){
        String errores="Warning: \n";
        for (Error e: erroresWarning)
            errores=errores + e.Imprimir() + "\n";
        errores=errores + "\n";
        return errores;
    }

    public String mostrarErrorComp(){
        String errores="Errores Compilacion: \n";
        for (Error e: erroresComp)
            errores=errores + e.Imprimir() + "\n";
        errores=errores + "\n";
        return errores;
    }


    public String mostrarTs(){

        ArrayList<Token> tokens = matTrans.getTablaSimbolos().getTokens();
        String cadena ="Tabla de Simbolos: \n";
        for ( Token t: tokens)
            cadena =cadena + t.imprimirToken() + "\n";
        cadena = cadena + "\n";
        return cadena;

    }

    public boolean hayErrores() {
        return (!erroresComp.isEmpty());

    }
}
