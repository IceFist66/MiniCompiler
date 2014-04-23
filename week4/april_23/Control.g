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
    private ArrayList<Node> functions;
    private int currentIDNum;
    boolean printNodeAdds;
}

error:
    {System.out.println("NO MATCH FOUND");}
;

types:
    ^(TYPES struct*) 
;

struct:
    ^(STRUCT ID (decl)*)
;

decl:
    ^(DECL ^(TYPE type) ID )
;

type:
    ^(TYPE INT)
    | ^(TYPE BOOL)
    | ^(TYPE ^(STRUCT type))
    | INT
    | BOOL
    | ^(STRUCT type)
    | ID 
    | VOID
;

decls:
    ^(DECLS decllist*)
;

decllist:
    ^(DECLLIST type ID*)
;

expression :
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
   |stmts[null]
;

lvalue:
   ^(DOT lvalue ID)
   |ID
;

stmt returns [Node n = null]
    :^(BLOCK stmts[null])
    |^(PRINT expression*)
    |^(READ lvalue)
    |^(IF expression stmt stmt?)             // need to start new node
    |^(WHILE expression stmt expression)     // need to start new node
    |^(DELETE expression)
    |^(RETURN expression?)
    |^(INVOKE ID args)                       // need to start new node
    |^(ASSIGN expression lvalue)
;

args:
    ^(ARGS expression*)
;

stmts [Node predNode] returns [Node n = null]
   : ^(STMTS node = (stmt
   
   {
   
      
   
   }
   
   )*)
;

funcs:
    ^(FUNCS fun*)
;

fun:
    ^(FUN id=ID
      
      {
        Node head = new Node(NodeType.ENTRY, (currentIDNum++), $id.text);
        functions.add(head);
        if (printNodeAdds)
           System.out.println("HEAD Node for function " + $id.text);
        
      } 
      
      params rettype decls n=stmts[head]
    
      {
        Node last = new Node(NodeType.EXIT, (currentIDNum++), "Exit");
        if (printNodeAdds)
           System.out.println("EXIT Node for function " + $id.text);
           
      }
          
    )
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
        functions = new ArrayList<Node>();
        currentIDNum = 0;
        printNodeAdds = true;
    }
   : ^(PROGRAM (types) decls funcs)
   { System.out.println("Successfully completed Control.g."); }
;
