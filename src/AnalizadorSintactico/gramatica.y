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
%}


programa  :   bloque_sentencias
           ;


bloque_sentencias  :   bloque_sentencias sentencia
                    |  sentencia
                    ;


sentencia   : declaracion '.'
            | ejecucion  '.'
            ;


declaracion  :  lista_variables ':' tipo
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



asignacion  :  ID  '=' expresion
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

out  :    OUT '('   CADENA   ')'
     ;

let  :    LET  asignacion  '.'


seleccion  :   IF '(' condicion  ')' THEN   bloque_sentencias_if  END_IF
           |   IF '(' condicion  ')' THEN   bloque_sentencias_if  ELSE bloque_sentencias_if  END_IF


bloque_sentencias_if  :   BEGIN   bloque_sentencias     END
                      |  sentencia
                      ;

bloque_sentencias_do  :  BEGIN sentencias_do END
                      | ejecucion
                      ;

sentencias_do   :   sentencias_do ejecucion
                |  ejecucion
                ;



control    :   WHILE '(' condicion ')' DO bloque_sentencias_do
           ;



condicion   :   expresion   comparador   expresion
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