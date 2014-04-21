tree grammar Control;

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
    private ArrayList<cfg.Node> functions;
    private int currentIDNum;
}

error:
    {System.out.println("NO MATCH FOUND");}
;

types:
    ^(TYPES struct*) 
;

struct:
    ^(STRUCT ID {StructDef sd = new StructDef();} (decl)*)
;

decl:
    ^(DECL ^(TYPE type) ID )
;

type:
    ^(TYPE INT)
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

funcs:
    ^(FUNCS fun*)
;

fun:
    ^(FUN id=ID{
        functions.add(new cfg.Node(cfg.NodeType.ENTRY,$id.text));
        cfg.Node last = new cfg.Node(cfg.NodeType.EXIT, null, "Exit");
    } (params) rettype decls var=stmts{
        
    })
;

params:
    ^(PARAMS decl*)
;

rettype:
    ^(RETTYPE type)
;


construct [StructTypes stypes, SymbolTable stable] 
    @init {
        g_stypes = stypes; 
        g_stable = stable; 
        functions = new ArrayList<cfg.Node>();
        currentIDNum = 0;
    }:
    ^(PROGRAM (types) decls funcs)
    { System.out.println("Successfully walked Program tree."); }
;
