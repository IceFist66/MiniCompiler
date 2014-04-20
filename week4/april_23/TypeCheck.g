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

types [String scope]
   : ^(TYPES (struct[scope])*) 
;

struct [String scope]:
    ^(STRUCT id=ID {StructDef sd = new StructDef();} (decl[scope])*) {g_stypes.addStruct(scope, $id.text, sd);}
;

decl [String scope] returns [Variable v = null]
   : ^(DECL ^(TYPE var=type[scope]) id=ID ) //{$v = new Variable }
;

type [String scope] returns [Variable v = null]
    : ^(TYPE INT) {$v = new Variable(Type.INT, scope);}
    | ^(TYPE BOOL) {$v = new Variable(Type.BOOL, scope);}
    | ^(TYPE ^(STRUCT type["empty"]))
    | INT //{$v = new Variable(Type.INT, scope);}
    | BOOL //{$v = new Variable(Type.BOOL, scope);}
    | ^(STRUCT type["empty"])
    | ID
    | VOID
;


decls [String scope]:
    ^(DECLS (decllist[scope])*)
    //{System.out.println(scope);}
;

decllist[String scope]:
    ^(DECLLIST v=type[scope] (id=ID 
    
    {    
      System.out.println(scope); 
      if (v!=null) {
         g_stable.addSymbol(scope, $id.text, v); 
         System.out.println("Added a new symbol to the table: " + $id.text);
    	   Variable tt1 = g_stable.getVariable(scope, $id.text);
		   if (tt1 == null)
			   System.out.println("t1 not in table.");
		   else
			   System.out.println($id.text + " = " + tt1.getType().toString());
      }
      else {
         System.out.println("v WAS NULL");
      }
    }
    
    )*)
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

funcs [String scope]:
    ^(FUNCS (fun[scope])*)
;

fun [String scope]:
    ^(FUN id=ID (params[scope]) rettype decls[$id.text] stmts)
;

params [String scope]:
    ^(PARAMS (decl[scope])*)
;

rettype:
    ^(RETTYPE type["empty"])
;


verify [StructTypes stypes, SymbolTable stable] @init {g_stypes = stypes; g_stable = stable; }:
    ^(PROGRAM types["global"] decls["global"] funcs["global"])
    { System.out.println("Successfully walked Program tree."); }
;
