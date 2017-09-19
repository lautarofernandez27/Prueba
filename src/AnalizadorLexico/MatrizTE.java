package AnalizadorLexico;

public class MatrizTE {

    static final String error1 = "Error: caracter invalido";
    static final String error2  = "Error al declarar un identificador. Los caracteres validos son letras, digitos y _";
    static final String error3 = "Error: olvido poner numero de exponente";
    static final String error4 = "Se esperaba ' para terminar la cadena";

    static final int col=17;
    static final String compilacion= "Error de compilacion";
    static final String warning= "Warning";

    TablaSimbolos tablaSimb;
    CeldaABS matTrans[][];


    //   0   1   2    3   4    5  6   7   8  9   10        11   12    13     14       15        16
    //   d   l   ,    -   _    >  <   *   =  +   /.():     E    $    '\n'    otro      '      espacio



    public int getColumna(Character c){
        if ( c.isDigit(c) )
            return 0;
        if ( c.charValue()== '_')
            return 4;
        if ( c.charValue()== '+')
            return 9;
        if (  ( c.charValue()== '/') || ( c.charValue()== ':') ||
                ( c.charValue()== '(') || ( c.charValue()== ')') || ( c.charValue()== '.') )
            return 10;
        if (( c.charValue()== 'E') || ( c.charValue()== 'e'))
            return 11;
        if ( c.charValue()== '=')
            return 8;
        if ( c.charValue()== '-')
            return 3;
        if ( c.charValue()== '*')
            return 7;
        if ( c.charValue()== '>')
            return 5;
        if ( c.charValue()== '<')
            return 6;
        if ( c.charValue()== ',')
            return 2;
        if ( c.charValue()== '\n')
            return 13;
        if ( c.charValue()== '\'')
            return 15;
        if ( ( c.charValue()== ' ' ) || (c.charValue() ==  '	' ) )
            return 16;
        if ( c.charValue()== '$') //ver como es fin de archivo
            return 12;
        if ( c.isLetter(c) ) //que pregunte primero las letras particulares
            return 1;
        return 14;// si no entra a ninguno es caracter "OTRO"
    }


    public MatrizTE(TablaSimbolos ts) {
        // TODO Auto-generated constructor stub
        matTrans = new CeldaABS[col][col];
        tablaSimb = ts;
        fila0();
        fila1();
        fila2();
        fila3();
        fila4();
        fila5();
        fila6();
        fila7();
        fila8();
        fila9();
        fila10();
        fila11();
        fila12();
        fila13();
        fila14();
    }

    public CeldaABS getCelda(int fila, int col) {
        return matTrans[fila][col];
    }

    public TablaSimbolos getTablaSimbolos(){
        return tablaSimb;
    }

    public void fila0(){
        //Estado inicial

        matTrans[0][0]= new Celda(2);
        matTrans[0][1]= new Celda(1);
        matTrans[0][2]= new Celda(3);
        matTrans[0][3]= new Celda(14);
        matTrans[0][4]= new CeldaAS(-2,tablaSimb,new Error(error1, compilacion));
        matTrans[0][5]= new Celda(13);
        matTrans[0][6]= new Celda(12);
        matTrans[0][7]= new Celda(8);
        matTrans[0][8]= new Celda(11);
        matTrans[0][9]= new Celda(14);
        matTrans[0][10]= new Celda(14);
        matTrans[0][11]= new Celda(1);
        matTrans[0][12]= new CeldaAS(-1,tablaSimb);
        matTrans[0][13]= new Celda(0);
        matTrans[0][14]= new CeldaAS(-2,tablaSimb,new Error(error1,compilacion));
        matTrans[0][15]= new Celda(10);
        matTrans[0][16]= new Celda(0);
    }
    public void fila1(){

        matTrans[1][0]= new Celda(1);
        matTrans[1][1]= new Celda(1);
        for(int i = 2; i<col; i++)
            if ((i!= 4) && (i!= 11))
                matTrans[1][i] = new CeldaAS(-1,tablaSimb);
            else
                matTrans[1][i] = new Celda(1);
    }
    public void fila2(){
        matTrans[2][0]= new Celda(2);
        matTrans[2][1] = new CeldaAS(-1,tablaSimb);
        matTrans[2][2]= new Celda(6);
        for(int i = 3; i<col; i++)
            matTrans[2][i] = new CeldaAS(-1,tablaSimb);
    }
    public void fila3(){
        matTrans[3][0]= new Celda(6);
        for(int i = 1; i<col; i++)
            if (i!= 11)
                matTrans[3][i] = new CeldaAS(-1,tablaSimb);
            else
                matTrans[3][i] = new Celda(4);
    }
    public void fila4(){
        matTrans[4][0]= new Celda(7);
        for(int i = 1; i<col; i++)
            if ((i!= 3) && (i!= 9))
                matTrans[4][i] = new CeldaAS(-2,tablaSimb,new Error(error3,compilacion));
            else
                matTrans[4][i] = new Celda(5);
    }
    public void fila5(){
        matTrans[5][0]= new Celda(7);
        for(int i = 1; i<col; i++)
            matTrans[5][i] = new CeldaAS(-2,tablaSimb,new Error(error3,compilacion));
    }
    public void fila6(){
        matTrans[6][0]= new Celda(6);
        for(int i = 1; i<col; i++)
            if (i!= 11)
                matTrans[6][i] = new CeldaAS(-1,tablaSimb);
            else
                matTrans[6][i] = new Celda(4);
    }
    public void fila7(){
        matTrans[7][0]= new Celda(7);
        for(int i = 1; i<col; i++)
            matTrans[7][i] = new CeldaAS(-1,tablaSimb);

    }
    public void fila8(){

        for(int i = 0; i<col; i++)
            if (i!= 7)
                matTrans[8][i] = new CeldaAS(-1,tablaSimb);
            else
                matTrans[8][i] = new Celda(9);

    }
    public void fila9(){
        for(int i = 0; i<col; i++)
            if ((i!= 12)&&(i!=13))
                matTrans[9][i] = new Celda(9);
        matTrans[9][12] = new CeldaAS(-1,tablaSimb);
        matTrans[9][13] = new Celda(0);
    }
    public void fila10(){
        for(int i = 0; i<col; i++)
            if ((i!= 12)&&(i!=13)&&(i!=15))
                matTrans[10][i] = new Celda(10);
        matTrans[10][12] = new CeldaAS(-2,tablaSimb,new Error(error4,compilacion));
        matTrans[10][13] = new CeldaAS(-2,tablaSimb,new Error(error4,compilacion));
        matTrans[10][15] = new Celda(14);
    }
    public void fila11(){
        for(int i = 0; i<col; i++)
            if (i!= 8)
                matTrans[11][i] = new CeldaAS(-1,tablaSimb);
            else
                matTrans[11][i] = new Celda(14);
    }
    public void fila12(){
        for(int i = 0; i<col; i++)
            if ((i!= 8)&&(i!=5))
                matTrans[12][i] = new CeldaAS(-1,tablaSimb);
            else
                matTrans[12][i] = new Celda(14);
    }
    public void fila13(){

        for(int i = 0; i<col; i++)
            if (i!= 8)
                matTrans[13][i] = new CeldaAS(-1,tablaSimb);
            else
                matTrans[13][i] = new Celda(14);
    }
    public void fila14(){
        for(int i = 0; i<col; i++)
                matTrans[14][i] = new CeldaAS(-1,tablaSimb);
    }

}
