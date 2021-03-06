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

types [StructTypes stypes]:
    ^(TYPES struct[stypes, "global"]*) 
;

struct [StructTypes stypes, String scope]:
    ^(STRUCT ID {StructDef sd = new StructDef();} (decl[stypes, scope])*)
;

decl[StructTypes stypes, String scope]:
    ^(DECL ^(TYPE type) ID )
;

type //[StructTypes stypes] returns {Type t = null]
    : ^(TYPE INT)
    | ^(TYPE BOOL)
    | ^(TYPE ^(STRUCT type))
    | INT //{$t = new Type(MiniType.INT, "global");}
    | BOOL
    | ^(STRUCT type)
    | ID
;


decls:
    ^(DECLS decllist*)
;

decllist:
    ^(DECLLIST type ID*)
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
;

lvalue:
   ^(DOT lvalue ID)
   |ID
;

stmt:
    ^(BLOCK stmts)
    |^(PRINT expression*)
    |^(READ lvalue)
    |^(IF expression stmt stmt?)
    |^(WHILE expression stmt expression)
    |^(DELETE expression)
    |^(RETURN expression?)
    |^(INVOKE ID args)
    |^(ASSIGN expression lvalue)
;

args:
    ^(ARGS expression*)
;

stmts:
    ^(STMTS stmt*)
;

funcs[StructTypes stypes, SymbolTable stable]:
    ^(FUNCS fun[stypes, stable]*)
;

fun[StructTypes stypes, SymbolTable stable]:
    ^(FUN id=ID (params[stypes, stable, $id.text]) rettype decls stmts)
;

params[StructTypes stypes, SymbolTable stable, String id]:
    ^(PARAMS decl[stypes, id]*)
;

rettype:
    ^(RETTYPE type)
;


verify [StructTypes stypes, SymbolTable stable]: 
    ^(PROGRAM (types[stypes]) decls funcs[stypes, stable])
    { System.out.println("Successfully walked Program tree."); }
;
