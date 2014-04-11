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

types:
    ^(TYPES struct*) {System.out.println("Found types");}
;

struct:
    ^(STRUCT ID decl*){System.out.println("Found struct");}
;

decl:
    ^(DECL ^(TYPE type) ID){System.out.println("DECL found");}
;

type:
    INT {System.out.println("Int Found");}
    |BOOL {System.out.println("Bool Found");}
    |^(STRUCT ID) {System.out.println("Struct found");}
;


decls:
    ^(DECLS decllist*)
    {System.out.println("Decls found");}
;

decllist:
    ^(DECLLIST type ID*)
    {System.out.println("Decclist found");}
;

assignment:
   ^(ASSIGN lvalue expression)
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
   |INT
   |stmt
   |BOOL
   |ID
   |ENDL
;

lvalue:
   ^(DOT lvalue ID)
   |ID
;
/*
boolterm:
   ^(COMPAR simple simple)
   //|ID
   //|NOT ID
   //|simple
;
  
simple:
  // term
   //| ^(BOP term term)
;

term:
   unary
   |^(TM unary unary)
;

unary:
    NOT odd_not
    |MINUS odd_neg
    |selector
;

odd_not:
    //NOT even_not
    //|^(NOT selector)
;

even_not:
    NOT odd_not
    |selector
;

odd_neg:
    MINUS even_neg
    |^(NEG selector)
;

even_neg:
    MINUS odd_neg
    |selector
;

selector:
    factor
    |^(DOT ID)
;

factor:
    expression
    |^(INVOKE ID arglist)
    |ID
    |INTEGER
    |TRUE
    |FALSE
    |^(NEW ID)
    |NULL
;

arglist:

;

print:
   expression
;

ret:
   (expression)?
;

read:
   // to do
;

guard:
   exp
;

loop:
   // to do
   ;   
invocation:
   
;

delete:
   // to do
;*/

stmt:
    ^(STMT ^(BLOCK stmtlist))
    |^(STMT ^(ASSIGNMENT assignment))
    |^(STMT ^(PRINT expression))
    |^(STMT ^(READ lvalue))
    |^(STMT ^(IF expression stmt stmt))
    |^(STMT ^(LOOP expression stmt expression))
    |^(STMT ^(DELETE expression))
    |^(STMT ^(RET expression))
    |^(STMT ^(INVOCATION ID expression*))
    {System.out.println("stmts found");}
;

stmtlist:
    ^(STMTLIST stmt*)
    {System.out.println("found stmtlist");}
;

funcs:
    ^(FUNCS fun*)
    {System.out.println("Found funcs");}
;

fun:
    ^(FUN ID params rettype decls stmtlist)
    {System.out.println("Found fun");}
;

params:
    ^(PARAMS decl*)
    {System.out.println("Found params");}
;

rettype:
    ^(RETTYPE ID)
    {System.out.println("Found rettype");}
;

verify : 
    ^(PROGRAM types decls funcs)
   { System.out.println("Finished Verify"); }
;
