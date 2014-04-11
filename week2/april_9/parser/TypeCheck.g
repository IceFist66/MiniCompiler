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
    //error
    //|{System.out.println("NO MATCH FOUND FOR TYPES");}
;

struct:
    {System.out.println("Found struct!");}^(STRUCT ID decl*){System.out.println("out of struct");}
    //|error
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
    //|error//{System.out.println("NO MATCH FOUND FOR TYPE");}
;


decls:
    {System.out.println("Decls found");}
    ^(DECLS decllist*)
    {System.out.println("out of decls");}
    //|error//{System.out.println("NO MATCH FOUND FOR DECLS");}
;

decllist:
    {System.out.println("Decclist found");}
    ^(DECLLIST type ID*)
    {System.out.println("out of Decclist");}
    //|error//{System.out.println("NO MATCH FOUND FOR DECLLIST");}
;

/*assignment:
   ^(ASSIGN lvalue expression)
   //{System.out.println("Assignment found")}
    //|error//{System.out.println("NO MATCH FOUND FOR ASSIGNMENT");}
;*/

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
   // |error//{System.out.println("NO MATCH FOUND FOR EXPRESSION");}
;

lvalue:
   ^(DOT lvalue ID)
   |ID
    //|error//{System.out.println("NO MATCH FOUND FOR LVALUE");}
;

/*stmt:
    ^(STMT ^(BLOCK stmts))
    |^(STMT ^(ASSIGNMENT assignment))
    |^(STMT ^(PRINT expression))
    |^(STMT ^(READ lvalue))
    |^(STMT ^(IF expression stmt stmt))
    |^(STMT ^(LOOP expression stmt expression))
    |^(STMT ^(DELETE expression))
    |^(STMT ^(RET expression))
    |^(STMT ^(INVOCATION ID expression*))
    {System.out.println("stmts found");}
;*/


stmt:
    ^(BLOCK stmts)
    |^(PRINT expression*)
    |^(READ lvalue)
    |^(IF {System.out.println("Inside IF");}expression stmt stmt?) {System.out.println("OUT of IF");}
    |^(WHILE expression stmt expression)
    |^(DELETE expression)
    |^(RETURN expression)
    |^(INVOKE ID args)
    |^(ASSIGN expression lvalue) {System.out.println("OUT of ASSIGN");}
    //{System.out.println("stmts found");}
    //|error//{System.out.println("NO MATCH FOUND FOR STMT");}
;

args:
    ^(ARGS expression*)
    //|error//{System.out.println("NO MATCH FOUND FOR ARGS");}
;

stmts:
    ^(STMTS stmt*)
   // {System.out.println("found stmts");}
    //|error//{System.out.println("NO MATCH FOUND FOR STMTS");}
;

funcs:
    {System.out.println("Found funcs");}
    ^(FUNCS fun*)
    {System.out.println("out of funcs");}
    ///|error//{System.out.println("NO MATCH FOUND FOR FUNCS");}
;

fun:
    {System.out.println("--Found FUN");}
    ^(FUN ID params rettype decls stmts)
    {System.out.println("**OUT of fun");}
    //|error//{System.out.println("NO MATCH FOUND FOR FUN");}
;

params:
    ^(PARAMS decl*)
    //{System.out.println("Found params");}
    //|error//{System.out.println("NO MATCH FOUND FOR PARAMS");}
;

rettype:
    ^(RETTYPE type) {System.out.println("Finished rettype");}
    //|error//{System.out.println("NO MATCH FOUND FOR RETTYPE");}
;


verify : 
    ^(PROGRAM types decls funcs)
   //{ System.out.println("Finished Verify"); }
    //|error//{System.out.println("NO MATCH FOUND FOR VERIFY");}
;
