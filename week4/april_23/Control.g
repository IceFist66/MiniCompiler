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
   |^(INVOKE id=ID
      {
          if (printNodeAdds)
             System.out.println("invoke " + $id.text);
      }
     args[predNode])
   
   
   
   |TRUE
   |FALSE
   |INTEGER
   |id=ID
   |ENDL
   |NULL
   |node = stmts[predNode]
    {
        $n = node;
        //System.out.println("In EXPRESSION, n type: " + n.getNodeType());
    }
;

lvalue:
   ^(DOT lvalue id=ID) 
   |id=ID
;

stmt [Node predNode] returns [Node n = predNode]
    :^(BLOCK 
        {
            
            if (printNodeAdds)
               System.out.println("block");
            
        }
    node = stmts[predNode]
        {$n = node;}
    )
    
          
    
    |^(PRINT 
          {
            
            if (printNodeAdds)
               System.out.println("print");
            
          }
    (current=expression[predNode]
    
          
    
    
    )*)
    
    |^(READ 
          {
            
            if (printNodeAdds)
               System.out.println("read");
            
          }

    lvalue)
    
          
    
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
            //System.out.println("In THEN, th type is: " + th.getNodeType());
            //System.out.println("In THEN, last type is: " + last.getNodeType());
            if(th.getNodeType() != NodeType.EXIT){
                th.getSuccNodes().add(ifJoin);
                ifJoin.getPredNodes().add(th);
                if (printNodeAdds) {
                  System.out.println("jump from L" + thenBlock.getId() + " to L" 
                   + ifJoin.getId() + " (ifJoin)"); 
               }   
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
    
    {el = null;} el=stmt[elseBlock]
    
          {
            
            predNode.getSuccNodes().add(elseBlock);
            elseBlock.getPredNodes().add(e1);
            // insert explicit jump to if_join block here
            if(el.getNodeType() != NodeType.EXIT){
                elseBlock.getSuccNodes().add(ifJoin);
                ifJoin.getPredNodes().add(elseBlock);
                if (printNodeAdds) {
                    System.out.println("jump to L" + ifJoin.getId() + " (ifJoin)");
                    System.out.println("L" + ifJoin.getId() + " IF_JOIN");
                }    
            }
          }    
    
    )?)
    
          {
            if((th != null && th.getNodeType() != NodeType.EXIT) || (el != null && el.getNodeType() != NodeType.EXIT))
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
            whileBlock.getSuccNodes().add(whileJoin);
            if (printNodeAdds) {
                System.out.println("jump from L" + e1.getId() + " to L" 
                   + whileBlock.getId()); 
                System.out.println("L" + whileJoin.getId() + " WHILE_JOIN");
            }   
            Node after = e1;
         }
    
    w=stmt[after] 
    
         {//This is the whileJoin block
            if (printNodeAdds)
               System.out.println("L" + w.getId() + " while_after"); 
            whileJoin.getPredNodes().add(whileBlock);  
            whileJoin.getPredNodes().add(e1);
            whileJoin.getSuccNodes().add(w);
            w.getPredNodes().add(whileJoin);
            $n=w;
         }
    
    e1=expression[w])
    
    
    |^(DELETE 
        {
          
          if (printNodeAdds)
             System.out.println("delete");
          
        }
    
        current=expression[predNode]
    
    
    )

    |^(RETURN {/*System.out.println ("return");*/ current = null;} (current=expression[predNode])?)
    
        {
          //System.out.println("In RETURN after expression");
          Node newPredNode;
          if(current == null){
              //System.out.println("current is null");
              newPredNode = predNode;
          }
          else{
              //System.out.println("current is NOT null");
              newPredNode = current;
          }
          //System.out.println("In RETURN, predNode type is: " + newPredNode.getNodeType());
          //System.out.println("In RETURN, last is: " + last.getNodeType());
          if (printNodeAdds){
             System.out.println("RETURN: jump to L" + last.getId());
          }
          newPredNode.getSuccNodes().add(last);
          last.getPredNodes().add(newPredNode);
          //System.out.println("IN RETURN, n is: " + n.getNodeType());
          $n = last; 
          //System.out.println("In RETURN, n is: " + n.getNodeType());
          // allow function to use default return (= predNode)    
        }
                   
    |^(INVOKE id=ID 
        {      
          if (printNodeAdds)
             System.out.println("invoke " + $id.text);
          
        }

    current=args[predNode])                                          
    
    
    
    |^(ASSIGN 
        {      
          if (printNodeAdds)
             System.out.println("assign");
          
        }
    current=expression[predNode] lvalue
    )
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
      //System.out.println("In STMTS newNode type: " + newNode.getNodeType());
      predNode = newNode;     
   
   }
   
   )*)
   
   {
      $n = predNode;
      //System.out.println("In STMTS n type: " + n.getNodeType());
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
        
        current.getSuccNodes().add(last);
        last.getPredNodes().add(current);
        
        
        if (printNodeAdds){
            if(current.getNodeType() != NodeType.EXIT)
                System.out.println("jump from L" + current.getId() + " to L" + last.getId()); 
            System.out.println("L" + last.getId() + " EXIT Node for function " + $id.text);      
        }
           
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
