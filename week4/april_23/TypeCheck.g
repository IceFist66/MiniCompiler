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

@members {
    private StructTypes g_stypes;
    private SymbolTable g_stable;
}

error:
    {System.out.println("NO MATCH FOUND");}
;

types :
    ^(TYPES struct*) 
;

struct :
    ^(STRUCT ID {StructDef sd = new StructDef();} (decl)*)
;

decl:
    ^(DECL ^(TYPE type["empty"]) ID )
;

type [String scope] returns [Variable t = null]
    : ^(TYPE INT)
    | ^(TYPE BOOL)
    | ^(TYPE ^(STRUCT type["empty"]))
    | INT {$t = new Variable(Type.INT, scope);}
    | BOOL
    | ^(STRUCT type["empty"])
    | ID
    | VOID
;


decls [String scope]:
    ^(DECLS (decllist[scope])*)
    //{System.out.println(scope);}
;

decllist[String scope]:
    ^(DECLLIST type[scope] ID*)
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

funcs:
    ^(FUNCS fun*)
;

fun:
    ^(FUN id=ID (params) rettype decls[$id.text] stmts)
;

params:
    ^(PARAMS decl*)
;

rettype:
    ^(RETTYPE type["empty"])
;


verify [StructTypes stypes, SymbolTable stable] @init {g_stypes = stypes; g_stable = stable; }:
    ^(PROGRAM (types) decls["global"] funcs)
    { System.out.println("Successfully walked Program tree."); }
;
