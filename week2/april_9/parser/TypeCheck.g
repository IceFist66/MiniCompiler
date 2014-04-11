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
   |BOOL
   |ID
;

lvalue:
   ^(DOT lvalue ID)
   |ID
;

print:
   // to do
;

ret:
   // to do
;

read:
   // to do
;

conditional:
   // to do
   ;
   
loop:
   // to do
   ;
   
invocation:
   // to do
;

delete:
   // to do
;

stmt:
    ^(STMTS ^(BLOCK stmtlist))
    |^(STMTS ^(ASSIGNMENT assignment))
    |^(STMTS ^(PRINT print))
    |^(STMTS ^(READ read))
    |^(STMTS ^(CONDITIONAL conditional))
    |^(STMTS ^(LOOP loop))
    |^(STMTS ^(DELETE delete))
    |^(STMTS ^(RET ret))
    |^(STMTS ^(INVOCATION invocation))
    {System.out.println("stmts found");}
;

stmtlist:
    ^(STMTS stmt*)
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
