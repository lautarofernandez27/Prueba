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
%token L_F
%%

%{
package AnalizadorSintactico;
import java.util.ArrayList;
import AnalizadorLexico.*;
import AnalizadorSintactico.*;
import AnalizadorLexico.Error;
import CodigoIntermedio.*;
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
                                               	String tipo = ((Token) $3.obj).getNombre();

                                               	for(Token t : (ArrayList<Token>)$1.obj ){
                                               		Token t1 = new Token(t.getNombre(), t.getUso() );
                                               	    if ((t1.getNombre().length()>15) && (t1.getUso()== AnalizadorLexico.ID) ){
                                                                    String truncar=t1.getNombre() ;
                                                                    t1.setNombre( truncar.substring(0, 14) );
                                                    }

                                               				t1.setTipo(tipo);
                                               				tablaSimbolo.addSimbolo(t1);


                                               }
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


lista_variables  : lista_variables ',' ID  {	ArrayList<Token> lista = (ArrayList<Token>) $1.obj;
                                           		lista.add((Token)$3.obj);
                                           		$$ = new ParserVal(lista);
                                           		}
                 |  ID {	ArrayList<Token> lista = new ArrayList<>();
                            lista.add((Token)$1.obj);
                            $$ = new ParserVal(lista); }
                 ;


tipo :  LONG  {  $$ = new ParserVal(  new Token( analizadorL.variableL ) ); }
     |  FLOAT {  $$ = new ParserVal(  new Token( analizadorL.variableF ) ); }
     ;



ejecucion : control
          | seleccion
          | asignacion
          | out
          | let
          ;


asignacion  : ID  '=' expresion'.'{  analizadorS.addEstructura (new Error ( analizadorS.estructuraASIG,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ));
                                     Token t = tablaSimbolo.getToken( ( (Token) $1.obj).getNombre() );
                                     if  ( t == null )
                                     		analizadorCI.addError (new Error ( analizadorCI.errorNoExisteVariable,"ERROR DE GENERACION DE CODIGO INTERMEDIO", controladorArchivo.getLinea()  ));

                                     String valor ="=";
									 Token t1 = (Token) $1.obj;
									Token t2 = (Token) $3.obj;
									if ( (t1 != null) && (t2 != null) ){
										if(!tipoCompatible(t1,t2))
											analizadorCI.addError (new Error ( analizadorCI.errorFaltaAllow,"ERROR DE GENERACION DE CODIGO INTERMEDIO", controladorArchivo.getLinea()  ));
									}
                                    TercetoAsignacion terceto = new TercetoAsignacion ( new TercetoSimple( new Token("=",(int)valor.charAt(0) ) ),new TercetoSimple(t),  new TercetoSimple( (Token)$3.obj ), controladorTercetos.getProxNumero() );
                                    controladorTercetos.addTerceto (terceto);

}
            | ID  '=' expresion error  {
                                           analizadorS.addError (new Error ( analizadorS.errorPuntoFinal,"ERROR SINTACTICO", controladorArchivo.getLinea() ));
                                      }
            | ID  '=' error '.' {
                                 analizadorS.addError (new Error ( analizadorS.errorAsignacion,"ERROR SINTACTICO", controladorArchivo.getLinea() ));
            }
            ;

expresion  :  expresion '+'  termino{	String valor ="+";
                                        if (tipoCompatible ((Token)$1.obj,(Token)$3.obj)){
                                            TercetoExpresion terceto = new TercetoExpresion ( new TercetoSimple( new Token("+",(int) valor.charAt(0) ) ),new TercetoSimple( (Token)$1.obj ), new TercetoSimple( (Token)$3.obj ), controladorTercetos.getProxNumero() );
                                            controladorTercetos.addTerceto (terceto);
                                            Token nuevo = new Token( controladorTercetos.numeroTercetoString());
                                            nuevo.setTipo(tipo);
                                            $$ = new ParserVal(nuevo);
                                        }
                                        else
                                                                                        analizadorS.addError (new Error ( analizadorS.errorTiposCompatibles,"ERROR SINTACTICO", controladorArchivo.getLinea() ));

                                                                        									}
           |  expresion '-'  termino{	String valor ="-";
                                       if (tipoCompatible ((Token)$1.obj,(Token)$3.obj)){
                                    	    TercetoExpresion terceto = new TercetoExpresion ( new TercetoSimple( new Token("-",(int) valor.charAt(0) ) ),new TercetoSimple( (Token)$1.obj ), new TercetoSimple( (Token)$3.obj ), controladorTercetos.getProxNumero() );
                                    	    controladorTercetos.addTerceto (terceto);
                                    	    Token nuevo = new Token( controladorTercetos.numeroTercetoString());
                                    	    nuevo.setTipo(tipo);
                                    	    $$ = new ParserVal(nuevo);
                                    	}
                                    	else
                                                                                         analizadorS.addError (new Error ( analizadorS.errorTiposCompatibles,"ERROR SINTACTICO", controladorArchivo.getLinea() ));

                                    									}
           |  L_F '(' expresion ')' {   TercetoConversion terceto = new TercetoConversion (new TercetoSimple (new Token ("ltof",analizadorL.L_F)),new TercetoSimple((Token)$3.obj),null,controladorTercetos.getProxNumero());
                                        controladorTercetos.addTerceto (terceto);
                                        Token nuevo = new Token( controladorTercetos.numeroTercetoString());
                                       	nuevo.setTipo("float");
                                        $$ = new ParserVal(nuevo);


                                        analizadorS.addEstructura (new Error ( analizadorS.estructuraCONVERSION,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ));}
           |  termino           { $$ = new ParserVal((Token)$1.obj); }
           ;

termino  :  termino '*'  factor   {
                                           Token tokenFactor= (Token) $3.obj;
                                           if ((tokenFactor.getLexema().equals("Constante long")) && (Long.parseLong(tokenFactor.getNombre())<=CeldaAS.maximoL)){
                                                 Token t=new Token(tokenFactor.getNombre(),tokenFactor.getUso());
                                                 t.setTipo(tokenFactor.getTipo());
                                                 tablaSimbolo.addSimbolo(t);
                                           }
                                           else if (tokenFactor.getLexema().equals("Constante flotante"))
                                                    if ((Double.parseDouble(tokenFactor.getNombre().replace(",","."))<=CeldaAS.maximoFP) && (Double.parseDouble(tokenFactor.getNombre().replace(",","."))>=CeldaAS.minimoFP) || (Double.parseDouble(tokenFactor.getNombre().replace(",","."))==0.0)){
                                                   Token t=new Token(tokenFactor.getNombre(),tokenFactor.getUso());
                                                   t.setTipo(tokenFactor.getTipo());
                                                   tablaSimbolo.addSimbolo(t);
                                                }
                                                 if ((tokenFactor.getLexema().equals("Constante long")) && (Long.parseLong(tokenFactor.getNombre())==CeldaAS.maximoL+1)){
                                                                                                            analizadorL.addError(new Error(analizadorL.ErrorC,"Lexico",controladorArchivo.getLinea()));
                                                                                                        }
                                           if (tipoCompatible ((Token)$1.obj,(Token)$3.obj)){
                                                String simb= "*";
                                                TercetoExpresionMult  terceto = new TercetoExpresionMult (new TercetoSimple (new Token (simb, (int)simb.charAt(0))), new TercetoSimple ((Token)$1.obj),new TercetoSimple ((Token)$3.obj),controladorTercetos.getProxNumero());
                                                controladorTercetos.addTerceto (terceto);
                                                Token nuevo = new Token (controladorTercetos.numeroTercetoString());
                                                nuevo.setTipo(((Token)$1.obj).getTipo());
                                                $$ = new ParserVal(nuevo);
                                           }
                                           else
                                                analizadorS.addError (new Error ( analizadorS.errorTiposCompatibles,"ERROR SINTACTICO", controladorArchivo.getLinea() ));


                                     }
         |  termino '*' '-' factor  {
                                      Token tokenFactor= (Token) $4.obj;
                                      if ((tokenFactor.getLexema().equals("Constante long")) && (Long.parseLong("-"+tokenFactor.getNombre())>=CeldaAS.minimoL)){
                                           Token t=new Token("-"+tokenFactor.getNombre(),tokenFactor.getUso());
                                           t.setTipo(tokenFactor.getTipo());
                                           tablaSimbolo.addSimbolo(t);
                                      }
                                       else if (tokenFactor.getLexema().equals("Constante flotante"))
                                                if  ((Double.parseDouble("-"+tokenFactor.getNombre().replace(",","."))<=CeldaAS.maximoFN) && (Double.parseDouble("-"+tokenFactor.getNombre().replace(",","."))>=CeldaAS.minimoFN) || (Double.parseDouble(tokenFactor.getNombre().replace(",","."))==0.0)){
                                           Token t=new Token("-"+tokenFactor.getNombre(),tokenFactor.getUso());
                                           t.setTipo(tokenFactor.getTipo());
                                           tablaSimbolo.addSimbolo(t);
                                         }

                                       if (tipoCompatible ((Token)$1.obj,(Token)$4.obj)){
                                         String simb= "*";
                                         Token negativo = (Token)$4.obj;
                                        negativo.setNegativo();
                                        TercetoExpresionMult  terceto = new TercetoExpresionMult (new TercetoSimple (new Token (simb, (int)simb.charAt(0))), new TercetoSimple ((Token)$1.obj),new TercetoSimple (negativo),controladorTercetos.getProxNumero());
                                         controladorTercetos.addTerceto (terceto);
                                          Token nuevo = new Token (controladorTercetos.numeroTercetoString());
                                         nuevo.setTipo(((Token)$1.obj).getTipo());
                                          $$ = new ParserVal(nuevo);
                                       }
                                       else
                                                                                       analizadorS.addError (new Error ( analizadorS.errorTiposCompatibles,"ERROR SINTACTICO", controladorArchivo.getLinea() ));


                                   }
         |  termino '/' factor   {
                                     Token tokenFactor= (Token) $3.obj;
                                     if ((tokenFactor.getLexema().equals("Constante long")) && (Long.parseLong(tokenFactor.getNombre())<=CeldaAS.maximoL)){
                                        Token t=new Token(tokenFactor.getNombre(),tokenFactor.getUso());
                                        t.setTipo(tokenFactor.getTipo());
                                        tablaSimbolo.addSimbolo(t);
                                     }
                                      else if (tokenFactor.getLexema().equals("Constante flotante"))
                                             if ((Double.parseDouble(tokenFactor.getNombre().replace(",","."))<=CeldaAS.maximoFP) && (Double.parseDouble(tokenFactor.getNombre().replace(",","."))>=CeldaAS.minimoFP) || (Double.parseDouble(tokenFactor.getNombre().replace(",","."))==0.0)){
                                              Token t=new Token(tokenFactor.getNombre(),tokenFactor.getUso());
                                              t.setTipo(tokenFactor.getTipo());
                                              tablaSimbolo.addSimbolo(t);
                                          }

                                       if ((tokenFactor.getLexema().equals("Constante long")) && (Long.parseLong(tokenFactor.getNombre())==CeldaAS.maximoL+1)){
                                                                                                  analizadorL.addError(new Error(analizadorL.ErrorC,"Lexico",controladorArchivo.getLinea()));
                                                                                              }
                                      if (tipoCompatible ((Token)$1.obj,(Token)$3.obj)){
                                         String simb= "/";
                                        TercetoExpresionDiv  terceto = new TercetoExpresionDiv (new TercetoSimple (new Token (simb, (int)simb.charAt(0))), new TercetoSimple ((Token)$1.obj),new TercetoSimple ((Token)$3.obj),controladorTercetos.getProxNumero());
                                         controladorTercetos.addTerceto (terceto);
                                         Token nuevo = new Token (controladorTercetos.numeroTercetoString());
                                         nuevo.setTipo(((Token)$1.obj).getTipo());
                                         $$ = new ParserVal(nuevo);
                                      }
                                      else
                                                                                      analizadorS.addError (new Error ( analizadorS.errorTiposCompatibles,"ERROR SINTACTICO", controladorArchivo.getLinea() ));

                                 }
         |  termino '/' '-' factor  {
                                        Token tokenFactor= (Token) $4.obj;
                                        if ((tokenFactor.getLexema().equals("Constante long")) && (Long.parseLong("-"+tokenFactor.getNombre())>=CeldaAS.minimoL)){
                                             Token t=new Token("-"+tokenFactor.getNombre(),tokenFactor.getUso());
                                             t.setTipo(tokenFactor.getTipo());
                                             tablaSimbolo.addSimbolo(t);
                                        }
                                        else if (tokenFactor.getLexema().equals("Constante flotante"))
                                                if (((Double.parseDouble("-"+tokenFactor.getNombre().replace(",","."))<=CeldaAS.maximoFN) && (Double.parseDouble("-"+tokenFactor.getNombre().replace(",","."))>=CeldaAS.minimoFN) || (Double.parseDouble(tokenFactor.getNombre().replace(",","."))==0.0))){
                                                  Token t=new Token("-"+tokenFactor.getNombre(),tokenFactor.getUso());
                                                  t.setTipo(tokenFactor.getTipo());
                                                  tablaSimbolo.addSimbolo(t);
                                              }

                                        if (tipoCompatible ((Token)$1.obj,(Token)$4.obj)){
                                             String simb= "/";
                                                Token negativo = (Token)$4.obj;
                                            negativo.setNegativo();
                                             TercetoExpresionDiv  terceto = new TercetoExpresionDiv (new TercetoSimple (new Token (simb, (int)simb.charAt(0))), new TercetoSimple ((Token)$1.obj),new TercetoSimple (negativo),controladorTercetos.getProxNumero());
                                              controladorTercetos.addTerceto (terceto);
                                              Token nuevo = new Token (controladorTercetos.numeroTercetoString());
                                             nuevo.setTipo(((Token)$1.obj).getTipo());
                                              $$ = new ParserVal(nuevo);
                                        }
                                        else
                                                                                        analizadorS.addError (new Error ( analizadorS.errorTiposCompatibles,"ERROR SINTACTICO", controladorArchivo.getLinea() ));

                                     }
         |  factor      {
                            Token tokenFactor= (Token) $1.obj;
                            if ((tokenFactor.getLexema().equals("Constante long")) && (Long.parseLong(tokenFactor.getNombre())<=CeldaAS.maximoL)){
                                Token t=new Token(tokenFactor.getNombre(),tokenFactor.getUso());
                                t.setTipo(tokenFactor.getTipo());
                                tablaSimbolo.addSimbolo(t);
                            }
                            else if ((tokenFactor.getLexema().equals("Constante flotante")))
                                   if ((Double.parseDouble(tokenFactor.getNombre().replace(",","."))<=CeldaAS.maximoFP) && (Double.parseDouble(tokenFactor.getNombre().replace(",","."))>=CeldaAS.minimoFP) || (Double.parseDouble(tokenFactor.getNombre().replace(",","."))==0.0)){
                                     Token t=new Token(tokenFactor.getNombre(),tokenFactor.getUso());
                                     t.setTipo(tokenFactor.getTipo());
                                     tablaSimbolo.addSimbolo(t);
                                 }
                            if ((tokenFactor.getLexema().equals("Constante long")) && (Long.parseLong(tokenFactor.getNombre())==CeldaAS.maximoL+1)){
                                                            analizadorL.addError(new Error(analizadorL.ErrorC,"Lexico",controladorArchivo.getLinea()));
                                                        }


                             $$ = new ParserVal((Token)$1.obj);
                             }
         |  '-' factor {
                           Token tokenFactor= (Token) $2.obj;
                           if ((tokenFactor.getLexema().equals("Constante long")) && (Long.parseLong("-"+tokenFactor.getNombre())>=CeldaAS.minimoL)){
                                Token t=new Token("-"+tokenFactor.getNombre(),tokenFactor.getUso());
                                t.setTipo(tokenFactor.getTipo());
                                tablaSimbolo.addSimbolo(t);
                           }
                           else if (tokenFactor.getLexema().equals("Constante flotante"))
                                   if (((Double.parseDouble("-"+tokenFactor.getNombre().replace(",","."))<=CeldaAS.maximoFN) && (Double.parseDouble("-"+tokenFactor.getNombre().replace(",","."))>=CeldaAS.minimoFN)) || (Double.parseDouble(tokenFactor.getNombre().replace(",","."))==0.0)){
                                         Token t=new Token("-"+tokenFactor.getNombre(),tokenFactor.getUso());
                                         t.setTipo(tokenFactor.getTipo());
                                         tablaSimbolo.addSimbolo(t);
                             }

                             Token nuevo = (Token)$2.obj;
                             nuevo.setNegativo();
                             $$ = new ParserVal(nuevo);
                        }
         ;

factor   :  CTEF{  Token t= (Token) $1.obj;
                   t.setTipo(analizadorL.variableF);
                   $$ = new ParserVal( (Token)t );
                }
         |  CTEL{  Token t= (Token) $1.obj;
                   t.setTipo(analizadorL.variableL);
                   $$ = new ParserVal( (Token)t );
            }
         |  ID{
               	 Token t1 = tablaSimbolo.getToken(((Token) $1.obj).getNombre());
                 if (t1 == null)
       			    analizadorCI.addError (new Error ( analizadorCI.errorNoExisteVariable,"ERROR DE GENERACION DE CODIGO INTERMEDIO", controladorArchivo.getLinea()  ));
                 else
  				     $$ = new ParserVal( t1 );
       			 }

         ;

out  :    OUT '('   CADENA   ')'  '.'  {TercetoPrint terceto = new TercetoPrint ( new TercetoSimple( (Token)$1.obj ),new TercetoSimple( (Token)$3.obj ), null, controladorTercetos.getProxNumero() );
                                        controladorTercetos.addTerceto (terceto);
                                        controladorTercetos.addPrint( ((Token)$3.obj).getNombre() );
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


if_condicion : IF  '(' condicion ')' THEN  {	TercetoIf terceto = new TercetoIf ( new TercetoSimple( (new Token( controladorTercetos.BF) ) ), new TercetoSimple(new Token( controladorTercetos.numeroTercetoString() ) ), null, controladorTercetos.getProxNumero() );
                                           		//terceto.setTipoSalto(((Token)$3.obj).getNombre());
                                           		controladorTercetos.addTerceto (terceto);
                                           		controladorTercetos.apilar();
                                           	}


seleccion  :  if_condicion   bloque_sentencias_control  END_IF  {            controladorTercetos.desapilar();
                                                                             analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ));

            }

           |   if_condicion  bloque_sentencias_control  ELSE {	TercetoIf terceto = new TercetoIf ( new TercetoSimple( new Token( controladorTercetos.BI)  ), null, null, controladorTercetos.getProxNumero() );
                                                             	controladorTercetos.addTerceto (terceto);
                                                             	controladorTercetos.desapilar();
                                                             	controladorTercetos.apilar();
                                                             										}
                bloque_sentencias_control END_IF   {  controladorTercetos.desapilar();
                                                      analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ));

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
                           |  declaracion {
                                            analizadorS.addError( new Error(analizadorS.errorDeclaracionDentroDeControl,"ESTRUCTURA SINTACTICA",controladorArchivo.getLinea() ));
                           }
                           ;



sentencias_control  :  sentencias_control ejecucion
                    |  ejecucion
                    | sentencias_control declaracion  {
                                                                              analizadorS.addError( new Error(analizadorS.errorDeclaracionDentroDeControl,"ESTRUCTURA SINTACTICA",controladorArchivo.getLinea() ));
                                                             }
                    ;

while_do  :  WHILE {
                   	TercetoEtiqueta tercetoEtiqueta = new TercetoEtiqueta (null,null,null,controladorTercetos.getProxNumero());
                   	controladorTercetos.addTerceto(tercetoEtiqueta);
                   	controladorTercetos.apilarControl();
             }
              '(' condicion ')' DO  {
                                    	TercetoWhile terceto = new TercetoWhile ( new TercetoSimple( (new Token( controladorTercetos.BF) ) ), new TercetoSimple(new Token( controladorTercetos.numeroTercetoString() ) ), null, controladorTercetos.getProxNumero() );
                                    	//terceto.setTipoSalto(((Token)$5.obj).getNombre());
                                    	controladorTercetos.addTerceto(terceto);
                                    	controladorTercetos.apilar();
                }



control    :  while_do  bloque_sentencias_control '.' {             TercetoWhile terceto = new TercetoWhile ( new TercetoSimple( new Token( controladorTercetos.BI)  ), null, null, controladorTercetos.getProxNumero() );
                                                       				controladorTercetos.addTerceto (terceto);
                                                       				controladorTercetos.desapilar();
                                                       				controladorTercetos.desapilarControl();
                                                                   analizadorS.addEstructura (new Error ( analizadorS.estructuraWHILE,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ));

            }

           ;


condicion   :   expresion   comparador   expresion    {	TercetoComparacion terceto = new TercetoComparacion ( new TercetoSimple( (Token)$2.obj ) ,new TercetoSimple( (Token)$1.obj ), new TercetoSimple( (Token)$3.obj ), controladorTercetos.getProxNumero() );
                                                      	controladorTercetos.addTerceto (terceto);
                                                      	String tipo;
                                                      	if ((((Token)$1.obj).getTipo().equals("float")) || (((Token)$3.obj).getTipo().equals("float")))
                                                      		tipo = "float";
                                                      	else
                                                      		tipo= "long";
                                                      	Token nuevo = new Token( controladorTercetos.numeroTercetoString() );
                                                      	nuevo.setTipo(tipo);
                                                      	nuevo.setNombre(((Token) $2.obj).getNombre());
                                                      	$$ = new ParserVal(nuevo);
                                                        analizadorS.addEstructura (new Error ( analizadorS.estructuraCONDICION,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ));

            }
            |   expresion   comparador   error  {
                                            analizadorS.addError (new Error ( analizadorS.errorCondicionD,"ERROR SINTACTICO", controladorArchivo.getLinea() ));

            }
            |  error comparador  expresion   {
                                        analizadorS.addError (new Error ( analizadorS.errorCondicionI,"ERROR SINTACTICO", controladorArchivo.getLinea() ));

            }
            ;


comparador   :   '<'{ String valor = "<";
                        $$ = new ParserVal(  new Token("<",(int) valor.charAt(0) ) ); }
             |   '>'{ String valor = ">";
                    	$$ = new ParserVal(  new Token(">",(int) valor.charAt(0) ) ); }
             |   S_IGUAL_IGUAL { $$ = new ParserVal(  new Token("==",analizadorL.S_IGUAL_IGUAL ) ); }
             |   S_MAYOR_IGUAL { $$ = new ParserVal(  new Token(">=",analizadorL.S_MAYOR_IGUAL) ); }
             |   S_MENOR_IGUAL { $$ = new ParserVal(  new Token("<=",analizadorL.S_MENOR_IGUAL ) ); }
             |   S_DESIGUAL   { $$ = new ParserVal(  new Token("<>",analizadorL.S_DESIGUAL ) ); }
             ;

%%
AnalizadorLexico analizadorL;
AnalizadorSintactico analizadorS;
TablaSimbolos tablaSimbolo;
ControladorArchivo controladorArchivo;
ControladorTercetos controladorTercetos;
AnalizadorCodigoIntermedio analizadorCI;

public void setLexico(AnalizadorLexico al) {
       analizadorL = al;
}
public void setSintactico (AnalizadorSintactico as){
	analizadorS = as;
}

public void setCodigoIntermedio(AnalizadorCodigoIntermedio aci){
	analizadorCI = aci;
}

public void setControladorTercetos ( ControladorTercetos ct){
	controladorTercetos = ct;
}
public void setControladorArchivo ( ControladorArchivo ca){
	controladorArchivo = ca;
}



public boolean tipoCompatible(Token t1, Token t2){

    if(t1.getTipo()!=null && t2.getTipo()!=null){
            if(t1.getTipo().equals(t2.getTipo()))
                    return true;
            return false;
    }
            return false;
}
public void setTS (TablaSimbolos ts){
	tablaSimbolo = ts;
}

public String getTipoCompatibleSuma (Token t1,Token t2){
	if ((t1.getTipo().equals("float")) || (t2.getTipo().equals("float")))
		return "float";
	else
		return "long";
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