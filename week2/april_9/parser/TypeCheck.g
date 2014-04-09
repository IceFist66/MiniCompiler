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
    {System.out.println("stmts found")}
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
    ^(FUN ID params rettype decls stmst)
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
