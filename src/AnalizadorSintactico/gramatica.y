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
%token L_D
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
             | lista_variables ':' tipo   error  {
                                                 analizadorS.addError (new Error ( analizadorS.errorPuntoFinal,"ERROR SINTACTICO", controladorArchivo.getLinea() ));
                                                    }
             | lista_variables ':' error  '.'{
                                            analizadorS.addError (new Error ( analizadorS.errorTipo,"ERROR SINTACTICO", controladorArchivo.getLinea() ));
             }
             | lista_variables error tipo '.' {
                                             analizadorS.addError (new Error ( analizadorS.errorDosPuntos,"ERROR SINTACTICO", controladorArchivo.getLinea() ));
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


asignacion  : ID  '=' expresion'.'{                      analizadorS.addEstructura (new Error ( analizadorS.estructuraASIG,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ));

}
            | ID  '=' expresion error  {
                                           analizadorS.addError (new Error ( analizadorS.errorPuntoFinal,"ERROR SINTACTICO", controladorArchivo.getLinea() ));
                                      }
            | ID  '=' error '.' {
                                 analizadorS.addError (new Error ( analizadorS.errorAsignacion,"ERROR SINTACTICO", controladorArchivo.getLinea() ));
            }
            ;

expresion  :  expresion '+'  termino
           |  expresion '-'  termino
           |  termino
           ;

termino  :  termino '*'  factor   {
                                           Token tokenFactor= (Token) $3.obj;
                                           if ((tokenFactor.getLexema().equals("Constante long")) && (Long.parseLong(tokenFactor.getNombre())<=CeldaAS.maximoL)){
                                                 Token t=new Token(tokenFactor.getNombre(),tokenFactor.getUso());
                                                 t.setTipo(tokenFactor.getTipo());
                                                 tablaSimbolo.addSimbolo(t);
                                           }
                                           else if ((tokenFactor.getLexema().equals("Constante flotante")) && (Double.parseDouble(tokenFactor.getNombre().replace(",","."))<=CeldaAS.maximoFP) && (Double.parseDouble(tokenFactor.getNombre().replace(",","."))>=CeldaAS.minimoFP) || (Double.parseDouble(tokenFactor.getNombre().replace(",","."))==0.0)){
                                                   Token t=new Token(tokenFactor.getNombre(),tokenFactor.getUso());
                                                   t.setTipo(tokenFactor.getTipo());
                                                   tablaSimbolo.addSimbolo(t);
                                                }
                                     }
         |  termino '*' '-' factor  {
                                      Token tokenFactor= (Token) $4.obj;
                                      if ((tokenFactor.getLexema().equals("Constante long")) && (Long.parseLong("-"+tokenFactor.getNombre())>=CeldaAS.minimoL)){
                                           Token t=new Token("-"+tokenFactor.getNombre(),tokenFactor.getUso());
                                           t.setTipo(tokenFactor.getTipo());
                                           tablaSimbolo.addSimbolo(t);
                                      }
                                       else if ((tokenFactor.getLexema().equals("Constante flotante")) && (Double.parseDouble(tokenFactor.getNombre().replace(",","."))<=CeldaAS.maximoFN) && (Double.parseDouble(tokenFactor.getNombre().replace(",","."))>=CeldaAS.minimoFN) || (Double.parseDouble(tokenFactor.getNombre().replace(",","."))==0.0)){
                                           Token t=new Token("-"+tokenFactor.getNombre(),tokenFactor.getUso());
                                           t.setTipo(tokenFactor.getTipo());
                                           tablaSimbolo.addSimbolo(t);
                                         }
                                   }
         |  termino '/' factor   {
                                     Token tokenFactor= (Token) $3.obj;
                                     if ((tokenFactor.getLexema().equals("Constante long")) && (Long.parseLong(tokenFactor.getNombre())<=CeldaAS.maximoL)){
                                        Token t=new Token(tokenFactor.getNombre(),tokenFactor.getUso());
                                        t.setTipo(tokenFactor.getTipo());
                                        tablaSimbolo.addSimbolo(t);
                                     }
                                      else if ((tokenFactor.getLexema().equals("Constante flotante")) && (Double.parseDouble(tokenFactor.getNombre().replace(",","."))<=CeldaAS.maximoFP) && (Double.parseDouble(tokenFactor.getNombre().replace(",","."))>=CeldaAS.minimoFP) || (Double.parseDouble(tokenFactor.getNombre().replace(",","."))==0.0)){
                                              Token t=new Token(tokenFactor.getNombre(),tokenFactor.getUso());
                                              t.setTipo(tokenFactor.getTipo());
                                              tablaSimbolo.addSimbolo(t);
                                          }
                                 }
         |  termino '/' '-' factor  {
                                        Token tokenFactor= (Token) $4.obj;
                                        if ((tokenFactor.getLexema().equals("Constante long")) && (Long.parseLong("-"+tokenFactor.getNombre())>=CeldaAS.minimoL)){
                                             Token t=new Token("-"+tokenFactor.getNombre(),tokenFactor.getUso());
                                             t.setTipo(tokenFactor.getTipo());
                                             tablaSimbolo.addSimbolo(t);
                                        }
                                        else if ((tokenFactor.getLexema().equals("Constante flotante")) && (Double.parseDouble(tokenFactor.getNombre().replace(",","."))<=CeldaAS.maximoFN) && (Double.parseDouble(tokenFactor.getNombre().replace(",","."))>=CeldaAS.minimoFN) || (Double.parseDouble(tokenFactor.getNombre().replace(",","."))==0.0)){
                                                  Token t=new Token("-"+tokenFactor.getNombre(),tokenFactor.getUso());
                                                  t.setTipo(tokenFactor.getTipo());
                                                  tablaSimbolo.addSimbolo(t);
                                              }
                                     }
         |  factor      {
                            Token tokenFactor= (Token) $1.obj;
                            if ((tokenFactor.getLexema().equals("Constante long")) && (Long.parseLong(tokenFactor.getNombre())<=CeldaAS.maximoL)){
                                Token t=new Token(tokenFactor.getNombre(),tokenFactor.getUso());
                                t.setTipo(tokenFactor.getTipo());
                                tablaSimbolo.addSimbolo(t);
                            }
                            else if ((tokenFactor.getLexema().equals("Constante flotante")) && (Double.parseDouble(tokenFactor.getNombre().replace(",","."))<=CeldaAS.maximoFP) && (Double.parseDouble(tokenFactor.getNombre().replace(",","."))>=CeldaAS.minimoFP) || (Double.parseDouble(tokenFactor.getNombre().replace(",","."))==0.0)){
                                     Token t=new Token(tokenFactor.getNombre(),tokenFactor.getUso());
                                     t.setTipo(tokenFactor.getTipo());
                                     tablaSimbolo.addSimbolo(t);
                                 }
                            if ((tokenFactor.getLexema().equals("Constante long")) && (Long.parseLong(tokenFactor.getNombre())==CeldaAS.maximoL+1)){
                                                            analizadorL.addError(new Error(analizadorL.ErrorC,"Lexico",controladorArchivo.getLinea()));
                                                        }
                             }
         |  '-' factor {
                                                               Token tokenFactor= (Token) $2.obj;
                                                               if ((tokenFactor.getLexema().equals("Constante long")) && (Long.parseLong("-"+tokenFactor.getNombre())>=CeldaAS.minimoL)){
                                                                    Token t=new Token("-"+tokenFactor.getNombre(),tokenFactor.getUso());
                                                                    t.setTipo(tokenFactor.getTipo());
                                                                    tablaSimbolo.addSimbolo(t);
                                                               }
                                                               else if ((tokenFactor.getLexema().equals("Constante flotante")) && (Double.parseDouble(tokenFactor.getNombre().replace(",","."))<=CeldaAS.maximoFN) && (Double.parseDouble(tokenFactor.getNombre().replace(",","."))>=CeldaAS.minimoFN) || (Double.parseDouble(tokenFactor.getNombre().replace(",","."))==0.0)){
                                                                         Token t=new Token("-"+tokenFactor.getNombre(),tokenFactor.getUso());
                                                                         t.setTipo(tokenFactor.getTipo());
                                                                         tablaSimbolo.addSimbolo(t);
                                                                     }
                                                            }
         ;

factor   :  CTEF
         |  CTEL
         |  ID
         ;

out  :    OUT '('   CADENA   ')'  '.'  {
                                      analizadorS.addEstructura (new Error ( analizadorS.estructuraOUT,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ));

    }
    |   OUT '(' CADENA ')'  error{
                        analizadorS.addError (new Error ( analizadorS.errorPuntoFinal,"ERROR SINTACTICO", controladorArchivo.getLinea() ));
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
    |  error '(' CADENA ')' '.' {
                                 analizadorS.addError (new Error ( analizadorS.errorOUT2,"ERROR SINTACTICO", controladorArchivo.getLinea() ));
    }
;

let  :    LET  asignacion   {
                                  analizadorS.addEstructura (new Error ( analizadorS.estructuraLET,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ));

}

;
seleccion  :   IF '(' condicion  ')' THEN   bloque_sentencias_control  END_IF  {
                                                                             analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ));

            }
           |   IF '(' condicion  ')'   bloque_sentencias_control  END_IF  {
                                                                               analizadorS.addError (new Error ( analizadorS.faltaThen,"ERROR SINTACTICO", controladorArchivo.getLinea() ));

            }
           |   IF '(' condicion  ')' THEN   bloque_sentencias_control  ELSE bloque_sentencias_control END_IF   {
                                                                             analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ));

           }
           |   IF '(' condicion  ')'   bloque_sentencias_control  ELSE bloque_sentencias_control END_IF  {
                                                                             analizadorS.addError (new Error ( analizadorS.faltaThen,"ERROR SINTACTICO", controladorArchivo.getLinea() ));

           }
           |   IF condicion ')' THEN bloque_sentencias_control  END_IF  {
                                                                            analizadorS.addError (new Error ( analizadorS.errorParentesisA,"ERROR SINTACTICO", controladorArchivo.getLinea() ));
            }
           |   IF '(' condicion THEN bloque_sentencias_control  END_IF  {
                                                                                       analizadorS.addError (new Error ( analizadorS.errorParentesisB,"ERROR SINTACTICO", controladorArchivo.getLinea() ));
           }
           |   IF condicion ')' THEN bloque_sentencias_control  ELSE bloque_sentencias_control  END_IF   {
                                                                                       analizadorS.addError (new Error ( analizadorS.errorParentesisA,"ERROR SINTACTICO", controladorArchivo.getLinea() ));
           }
           |   IF '(' condicion THEN bloque_sentencias_control  ELSE bloque_sentencias_control  END_IF  {
                                                                                                  analizadorS.addError (new Error ( analizadorS.errorParentesisB,"ERROR SINTACTICO", controladorArchivo.getLinea() ));
           }
;

bloque_sentencias_control  :   BEGIN   sentencias_control   END '.'
                           |   BEGIN   sentencias_control  '.' {
                                                                    analizadorS.addError (new Error ( analizadorS.errorEND,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ));

                           }
                           |   sentencias_control  END  '.' {
                                                                    analizadorS.addError (new Error ( analizadorS.errorBEGIN,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ));

                           }
                           |  ejecucion
                           ;



sentencias_control  :  sentencias_control ejecucion
                    |  ejecucion
                    ;




control    :   WHILE '(' condicion ')' DO bloque_sentencias_control '.' {
                                                                   analizadorS.addEstructura (new Error ( analizadorS.estructuraWHILE,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ));

            }
            | WHILE '(' condicion DO bloque_sentencias_control  '.' {
                                                                         analizadorS.addError (new Error ( analizadorS.errorParentesisB,"ERROR SINTACTICO", controladorArchivo.getLinea() ));

            }
            | WHILE condicion ')' DO bloque_sentencias_control  '.' {
                                                                         analizadorS.addError (new Error ( analizadorS.errorParentesisA,"ERROR SINTACTICO", controladorArchivo.getLinea() ));

            }
            | WHILE '(' condicion ')' bloque_sentencias_control  '.' {
                                                                         analizadorS.addError (new Error ( analizadorS.faltaDO,"ERROR SINTACTICO", controladorArchivo.getLinea() ));

            }
           ;


condicion   :   expresion   comparador   expresion     {
                                                        analizadorS.addEstructura (new Error ( analizadorS.estructuraCONDICION,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ));

            }
            |   expresion   comparador     {
                                            analizadorS.addError (new Error ( analizadorS.errorCondicionD,"ERROR SINTACTICO", controladorArchivo.getLinea() ));

            }
            |  comparador  expresion   {
                                        analizadorS.addError (new Error ( analizadorS.errorCondicionI,"ERROR SINTACTICO", controladorArchivo.getLinea() ));

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