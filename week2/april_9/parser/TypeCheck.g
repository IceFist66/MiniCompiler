tree grammar TypeCheck;

options
{
   tokenVocab=Mini;
   ASTLabelType=CommonTree;
}

@header
{
   import java.util.Map;
   import java.util.HashMap;
   import java.util.Vector;
   import java.util.Iterator;
}

error:
    {System.out.println("NO MATCH FOUND");}
;

types:
    {System.out.println("Found types");}
    ^(TYPES struct*) 
    {System.out.println("Done with types");}
    //|{System.out.println("NO MATCH FOUND FOR TYPES");}
;

struct:
    {System.out.println("Found struct!");}^(STRUCT ID decl*){System.out.println("out of struct");}
    //|{System.out.println("NO MATCH FOUND FOR STRUCT");}
;

decl:
    ^(DECL ^(TYPE type) {System.out.println("after type");} ID {System.out.println("ID FOUND!\nout of DECL\n");} )//{System.out.println("DECL found");}
    //|{System.out.println("NO MATCH FOUND FOR DECLS");}
;

type:
    ^(TYPE INT)
    |^(TYPE BOOL)
    |^(TYPE ^(STRUCT type))
    |INT {System.out.println("Int Found");}
    |BOOL {System.out.println("Bool Found");}
    |^(STRUCT type)
    |ID
;


decls:
    {System.out.println("Decls found");}
    ^(DECLS decllist*)
    {System.out.println("out of decls");}
;

decllist:
    {System.out.println("Decclist found");}
    ^(DECLLIST type ID*)
    {System.out.println("out of Decclist");}
;

expression:
   ^(AND expression expression)
   |^(OR expression expression)
   |^(EQ expression expression)
   |^(LT expression expression)
   |^(GT expression expression)
   |^(LE expression expression)
   |^(GE expression expression)
   |^(PLUS expression expression)
   |^(MINUS expression expression)
   |^(TIMES expression expression)
   |^(DIVIDE expression expression)
   |^(NOT expression)
   |^(NEW ID)
   |^(DOT expression expression)
   |^(INVOKE ID args)
   |TRUE
   |FALSE
   |INTEGER
   |ID
   |ENDL
   |NULL
   |stmts
  // {System.out.println("Expression found")}
;

lvalue:
   ^(DOT lvalue ID)
   |ID
;

stmt:
    ^(BLOCK stmts)
    |^(PRINT expression*)
    |^(READ lvalue)
    |^(IF {System.out.println("Inside IF");}expression stmt stmt?) {System.out.println("OUT of IF");}
    |^(WHILE expression stmt expression)
    |^(DELETE expression)
    |^(RETURN expression?)
    |^(INVOKE ID args)
    |^(ASSIGN expression lvalue) {System.out.println("OUT of ASSIGN");}
    //{System.out.println("stmts found");}
;

args:
    ^(ARGS expression*)
;

stmts:
    ^(STMTS stmt*)
   // {System.out.println("found stmts");}
;

funcs:
    {System.out.println("Found funcs");}
    ^(FUNCS fun*)
    {System.out.println("out of funcs");}
;

fun:
    {System.out.println("--Found FUN");}
    ^(FUN ID params rettype decls stmts)
    {System.out.println("**OUT of fun");}
;

params:
    ^(PARAMS decl*)
    //{System.out.println("Found params");}
;

rettype:
    ^(RETTYPE type) {System.out.println("Finished rettype");}
;


verify : 
    ^(PROGRAM types decls funcs)
   //{ System.out.println("Finished Verify"); }
;
