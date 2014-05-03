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
    boolean printMini;
    Node last;
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

expression [Node predNode] returns [Node n = predNode]
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
   |^(INVOKE id=ID args[predNode])
   
   {
      if (printNodeAdds)
         System.out.println("invoke " + $id.text);
   }
   
   |TRUE
   |FALSE
   |INTEGER
   |id=ID
   |ENDL
   |NULL
   |stmts[predNode]
;

lvalue:
   ^(DOT lvalue id=ID) 
   |id=ID
;

stmt [Node predNode] returns [Node n = predNode]
    :^(BLOCK stmts[predNode])
    
          {
            
            if (printNodeAdds)
               System.out.println("block");
            
          }
    
    |^(PRINT (current=expression[predNode]
    
          {
            
            if (printNodeAdds)
               System.out.println("print");
            
          }
    
    
    )*)
    
    |^(READ lvalue)
    
          {
            
            if (printNodeAdds)
               System.out.println("read");
            
          }
    
    |^(IF 
          
          {
            if (printNodeAdds)
               System.out.println("if (expression)");
          }
    
    e=expression[predNode] 
    
          {
            Node thenBlock = new Node(NodeType.THEN, (currentIDNum++), "THEN");
            Node ifJoin = new Node(NodeType.IF_JOIN, (currentIDNum++), "IF_JOIN");
            e.getSuccNodes().add(thenBlock);
            thenBlock.getPredNodes().add(e);
            if (printNodeAdds) {
               System.out.println("true: jump from L" + e.getId() + " to L" + thenBlock.getId()); 
               System.out.println("L" + thenBlock.getId() + " THEN");
            }      
          }
    
    th=stmt[thenBlock]
    
          {
            //insert explicit jump to if_join block here
            th.getSuccNodes().add(ifJoin);
            ifJoin.getPredNodes().add(th);
            if (printNodeAdds) {
              System.out.println("jump from L" + thenBlock.getId() + " to L" 
               + ifJoin.getId() + " (ifJoin)"); 
           }   
          }
    
    (
      
          {
           Node elseBlock = new Node(NodeType.ELSE, (currentIDNum++), "ELSE");
           if (printNodeAdds) {
              System.out.println("false: jump from L" + e.getId() + " to L" + elseBlock.getId()); 
              System.out.println("L" + elseBlock.getId() + " ELSE");
           }  
          }
    
    el=stmt[elseBlock]
    
          {
            
           predNode.getSuccNodes().add(elseBlock);
           elseBlock.getPredNodes().add(e1);
           // insert explicit jump to if_join block here
           elseBlock.getSuccNodes().add(ifJoin);
           ifJoin.getPredNodes().add(elseBlock);
           if (printNodeAdds) {
              System.out.println("jump to L" + ifJoin.getId() + " (ifJoin)");
              System.out.println("L" + ifJoin.getId() + " IF_JOIN");
           }    
          }    
    
    )?)
    
          {
            n = ifJoin;  
            
          }
    

    
    |^(WHILE 
    
         {
            if (printNodeAdds)
               System.out.println("while exp1");
        
         }
    
    e1=expression[predNode]
    
         {
            if (printNodeAdds)
               System.out.println("L" + e1.getId() + " WHILE_BODY exp2");
            Node whileBlock = new Node(NodeType.WHILE_BODY, (currentIDNum++), "WHILE_BODY");
            Node whileJoin = new Node(NodeType.WHILE_JOIN, (currentIDNum++), "WHILE_JOIN");
            e1.getSuccNodes().add(whileBlock);
            e1.getSuccNodes().add(whileJoin);
            whileBlock.getPredNodes().add(e1);
            whileBlock.getSuccNodes().add(whileBlock);
            Node after = e1;
         }
    
    w=stmt[after] 
    
         {
            if (printNodeAdds)
               System.out.println("L" + w.getId() + " WHILE_JOIN"); 
            whileBlock.getSuccNodes().add(whileJoin);
            whileJoin.getPredNodes().add(whileBlock);  
            whileJoin.getPredNodes().add(predNode);        
         }
    
    e2=expression[predNode])
    
    
    |^(DELETE current=expression[predNode]
    
    {
      
      if (printNodeAdds)
         System.out.println("delete");
      
    }
    
    )
    |^(RETURN (current=expression[predNode]
    
    {
      
      if (printNodeAdds)
         System.out.println("RETURN: jump to L" + last.getId());
      current.getSuccNodes().add(last);
      last.getPredNodes().add(current);
      n = last; 
      // allow function to use default return (= predNode)    
    }
    
    )?)                   
    |^(INVOKE id=ID current=args[predNode])                                          
    
    {      
      if (printNodeAdds)
         System.out.println("invoke " + $id.text);
      
    }
    
    |^(ASSIGN current=expression[predNode] 
    
    {      
      if (printNodeAdds)
         System.out.println("assign");
      
    }
    
     lvalue)
;

args [Node predNode] returns [Node n = predNode]
   :^(ARGS (expression[predNode])*)
;

stmts [Node predNode] returns [Node n = predNode]
   :^(STMTS 
    
   {  
      if (printNodeAdds)
         System.out.println("stmts");

   }
    
   (newNode = stmt[predNode]
   
   {
      predNode = newNode;     
   
   }
   
   )*)
   
   {
      n = predNode;
   }
;

funcs:
    ^(FUNCS fun*)
;

fun:
    ^(FUN id=ID
      
      {
        Node head = new Node(NodeType.ENTRY, (currentIDNum++), $id.text);
        functions.add(head);
        Node firstBlock = new Node(NodeType.BLOCK, (currentIDNum++), "Block");
        head.getSuccNodes().add(firstBlock);
        firstBlock.getPredNodes().add(head);
        last = new Node(NodeType.EXIT, (currentIDNum++), "Exit");
        if (printNodeAdds)
           System.out.println("\nL" + head.getId() + " HEAD Node for function " + $id.text);
           System.out.println("jump from L" + head.getId() + " to L" + firstBlock.getId()); 
           System.out.println("L" + firstBlock.getId() + " start new Block ");        
      } 
      
      params rettype decls current=stmts[firstBlock]
    
      {   
        
        if (current != null) {
            current.getSuccNodes().add(last);
            last.getPredNodes().add(current);
        } else {
            System.out.println("Null return from stmts");
        }
        
        if (printNodeAdds)
           System.out.println("L" + last.getId() + " EXIT Node for function " + $id.text);
           
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
        printMini = false;
    }
   : ^(PROGRAM (types) decls funcs)
   { System.out.println("Successfully completed Control.g."); }
;
