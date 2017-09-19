package AnalizadorLexico;

public class MatrizTE {

    static final String error1 = "Error: caracter invalido";
    static final String error2  = "Error al declarar un identificador. Los caracteres validos son letras, digitos y _";

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



    }
    public void fila2(){



    }
    public void fila3(){



    }
    public void fila4(){



    }
    public void fila5(){



    }
    public void fila6(){



    }
    public void fila7(){



    }
    public void fila8(){



    }
    public void fila9(){



    }
    public void fila10(){



    }
    public void fila11(){



    }
    public void fila12(){



    }
    public void fila13(){



    }
    public void fila14(){



    }

}
