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

expression [Node predNode] returns [Node n = null]
   :^(AND expression[predNode] expression[predNode])
   |^(OR expression[predNode] expression[predNode])
   |^(EQ expression[predNode] expression[predNode])
   |^(LT expression[predNode] expression[predNode])
   |^(GT expression[predNode] expression[predNode])
   |^(LE expression[predNode] expression[predNode])
   |^(GE expression[predNode] expression[predNode])
   |^(PLUS expression[predNode] expression[predNode])
   |^(MINUS expression[predNode] expression[predNode])
   |^(TIMES expression[predNode] expression[predNode])
   |^(DIVIDE expression[predNode] expression[predNode])
   |^(NOT expression[predNode])
   |^(NEW ID)
   |^(DOT expression[predNode] expression[predNode])
   |^(INVOKE ID args[predNode])
   |TRUE
   |FALSE
   |INTEGER
   |ID
   |ENDL
   |NULL
   |stmts[predNode]
;

lvalue:
   ^(DOT lvalue ID)
   |ID
;

stmt [Node predNode] returns [Node n = null]
    :^(BLOCK stmts[predNode])
    |^(PRINT (expression[predNode])*)
    |^(READ lvalue)
    |^(IF expression[predNode] stmt[predNode] stmt[predNode]?)             // need to start new node
    |^(WHILE expression[predNode] stmt[predNode] expression[predNode])     // need to start new node
    |^(DELETE expression[predNode])
    |^(RETURN (expression[predNode])?)
    |^(INVOKE ID args[predNode])                       // need to start new node
    |^(ASSIGN expression[predNode] lvalue)
;

args [Node predNode] returns [Node n = null]
   :^(ARGS (expression[predNode])*)
;

stmts [Node predNode] returns [Node n = null]
   : ^(STMTS node = (stmt[predNode]
   
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
