%token ID
%token CTEL
%token S_IGUAL_IGUAL
%token S_MAYOR_IGUAL
%token S_MENOR_IGUAL
%token S_DESIGUAL
%token CTEF
%token COMENTARIO
%token CADENA

%token IF
%token THEN
%token ELSE
%token END_IF
%token OUT
%token BEGIN
%token END
%token WHILE
%token DO
%token LET
%token FLOAT
%token LONG
%%

%{
package AnalizadorSintactico;
import AnalizadorLexico.*;
import AnalizadorSintactico.*;
import AnalizadorLexico.Error;
%}


programa  :   bloque_sentencias
           ;


bloque_sentencias  :   bloque_sentencias sentencia
                    |  sentencia
                    ;


sentencia   : declaracion
            | ejecucion
            ;


declaracion  :  lista_variables ':' tipo '.'   {
                                               analizadorS.addEstructura (new Error ( analizadorS.estructuraDECLARACION,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  ));

}
             | lista_variables ':' error  '.'{
                                            analizadorS.addError (new Error ( analizadorS.errorTipo,"ERROR SINTACTICO", controladorArchivo.getLinea() ));
             }
             ;


lista_variables  :  lista_variables ',' ID
                 |  ID
                 ;


tipo :  LONG
     |  FLOAT
     ;



ejecucion : control
          | seleccion
          | asignacion
          | out
          | let
          ;



asignacion  :  ID  '=' expresion '.'{
                                  analizadorS.addEstructura (new Error ( analizadorS.estructuraASIG,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ));


}
            ;



expresion  :  expresion '+'   termino
           |  expresion '-'   termino
           |  termino
           ;

termino  :  termino '*'  factor
         |  termino '/'  factor
         |  factor
         ;

factor   :  CTEF
         |  CTEL
         |  ID
         ;

out  :    OUT '('   CADENA   ')'  '.'  {
                                      analizadorS.addEstructura (new Error ( analizadorS.estructuraOUT,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ));

    }
    |   OUT CADENA ')'  '.'{
                        analizadorS.addError (new Error ( analizadorS.errorOUT1,"ERROR SINTACTICO", controladorArchivo.getLinea() ));
    }
    |   OUT '(' CADENA '.' {
                        analizadorS.addError (new Error ( analizadorS.errorOUT1,"ERROR SINTACTICO", controladorArchivo.getLinea() ));
    }
    |   OUT CADENA '.' {
                     analizadorS.addError (new Error ( analizadorS.errorOUT1,"ERROR SINTACTICO", controladorArchivo.getLinea() ));
    }
    |   '(' CADENA ')'  '.'{
                        analizadorS.addError (new Error ( analizadorS.errorOUT2,"ERROR SINTACTICO", controladorArchivo.getLinea() ));
    }
;

let  :    LET  asignacion   {
                                  analizadorS.addEstructura (new Error ( analizadorS.estructuraLET,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ));

}

;
seleccion  :   IF '(' condicion  ')' THEN   bloque_sentencias_control  END_IF  '.' {
                                                                             analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ));

            }
           |   IF '(' condicion  ')'   bloque_sentencias_control  END_IF   '.'{
                                                                                         analizadorS.addError (new Error ( analizadorS.faltaThen,"ERROR SINTACTICO", controladorArchivo.getLinea() ));

            }
           |   IF '(' condicion  ')' THEN   bloque_sentencias_control  ELSE bloque_sentencias_control END_IF    '.'{
                                                                             analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ));

           }
           |   IF '(' condicion  ')'   bloque_sentencias_control  ELSE bloque_sentencias_control END_IF    '.'{
                                                                             analizadorS.addError (new Error ( analizadorS.faltaThen,"ERROR SINTACTICO", controladorArchivo.getLinea() ));

           }
           |   IF condicion ')' THEN bloque_sentencias_control  END_IF  '.' {
                                                                            analizadorS.addError (new Error ( analizadorS.errorParentesisA,"ERROR SINTACTICO", controladorArchivo.getLinea() ));
            }
           |   IF '(' condicion THEN bloque_sentencias_control  END_IF  '.' {
                                                                                       analizadorS.addError (new Error ( analizadorS.errorParentesisB,"ERROR SINTACTICO", controladorArchivo.getLinea() ));
           }
           |   IF condicion ')' THEN bloque_sentencias_control  ELSE bloque_sentencias_control  END_IF  '.' {
                                                                                       analizadorS.addError (new Error ( analizadorS.errorParentesisA,"ERROR SINTACTICO", controladorArchivo.getLinea() ));
           }
           |   IF '(' condicion THEN bloque_sentencias_control  ELSE bloque_sentencias_control  END_IF  '.' {
                                                                                                  analizadorS.addError (new Error ( analizadorS.errorParentesisB,"ERROR SINTACTICO", controladorArchivo.getLinea() ));
           }
;

bloque_sentencias_control  :   BEGIN   sentencias_control   END '.'
                           |  ejecucion
                           ;



sentencias_control  :  sentencias_control ejecucion
                    |  ejecucion
                    ;




control    :   WHILE '(' condicion ')' DO bloque_sentencias_control  '.' {
                                                                   analizadorS.addEstructura (new Error ( analizadorS.estructuraWHILE,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ));

}
           ;


condicion   :   expresion   comparador   expresion     {
                                                        analizadorS.addEstructura (new Error ( analizadorS.estructuraCONDICION,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ));

}
            ;


comparador   :   '<'
             |   '>'
             |   S_IGUAL_IGUAL
             |   S_MAYOR_IGUAL
             |   S_MENOR_IGUAL
             |   S_DESIGUAL
             ;


%%
AnalizadorLexico analizadorL;
AnalizadorSintactico analizadorS;
TablaSimbolos tablaSimbolo;
ControladorArchivo controladorArchivo;

public void setLexico(AnalizadorLexico al) {
       analizadorL = al;
}
public void setSintactico (AnalizadorSintactico as){
	analizadorS = as;
}


public void setControladorArchivo ( ControladorArchivo ca){
	controladorArchivo = ca;
}

public void setTS (TablaSimbolos ts){
	tablaSimbolo = ts;
}

int yylex()
{
	Token token = ((Token)(analizadorL).yylex());
   	int val = token.getUso();
   	yylval = new ParserVal(token);
    return val;
}


void yyerror(String s) {
	if(s.contains("under"))
		System.out.println("par:"+s);
}