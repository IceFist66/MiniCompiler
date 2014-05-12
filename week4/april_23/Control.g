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
   import java.io.IOException;
   import iloc.*;
}

@members {
    private StructTypes g_stypes;
    private SymbolTable g_stable;
    private ArrayList<Node> functions;
    private ArrayList<String> funcNames;
    private int currentIDNum;
    boolean printNodeAdds;
    boolean printMini;
    Node last;
    HashMap<String, String> globalRegisterMap; // this is set once at the start
    HashMap<String, String> localRegisterMap; // this is set anew for every function
    private int registerCounter = 0;
    
    public HashMap<String, String> buildRegisterMap(ArrayList<String> variableNames) {
      HashMap<String, String> registerMap = new HashMap<String, String>();
      for(String variableName : variableNames) {
         registerMap.put(variableName, "r" + registerCounter);
         System.out.println("Assigned variable " + variableName + " to register " + "r" + registerCounter);
         registerCounter++;
      }
      return registerMap;
    }
    
    public String getRegister(String variable) {
      if (localRegisterMap.containsKey(variable))
         return localRegisterMap.get(variable);
      else if (globalRegisterMap.containsKey(variable))
         return globalRegisterMap.get(variable);
      else
         return "ERROR";
    }
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
   |^(PLUS expression[predNode] {int reg1 = registerCounter-1;} expression[predNode] 
    {
        int numI = $n.getInstructions().size();
        if ($n.getInstructions().get(numI - 1) instanceof Mov) {
            System.out.println("Instruction #" + (numI - 1) + " = Mov");
            // get arg1 of this Mov and if it's an id, get its register in the registerMap
            }
        if ($n.getInstructions().get(numI - 2) instanceof Mov) {
            System.out.println("Instruction #" + (numI - 2) + " = Mov");
            // get arg2 of this Mov and if it's an id, get its register in the registerMap
            }
        // right now this newAdd is incorrect - use the information from the previous two Mov
        // instructions to build the correct Add instruction
        Add newAdd = new Add("r"+reg1,"r"+(registerCounter-1),"r"+registerCounter++);
        $n.getInstructions().add(newAdd);
    })
   |^(MINUS expression[predNode] expression[predNode])
   |^(TIMES expression[predNode] {int reg1 = registerCounter-1;} expression[predNode]
    {
        Mult newMult = new Mult("r"+reg1,"r"+(registerCounter-1),"r"+registerCounter++);
        $n.getInstructions().add(newMult);
    })
   |^(DIVIDE expression[predNode] expression[predNode])
   |^(NOT expression[predNode])
   |^(NEW id=ID)
    {
        New newNew = new New($id.text, "r"+registerCounter++);
        $n.getInstructions().add(newNew);
    }
   |^(DOT expression[predNode] expression[predNode])
   |^(INVOKE id=ID
      {
          if (printNodeAdds)
             System.out.println("invoke " + $id.text);
      }
     args[predNode])
   
   
   
   |tr=TRUE
    {
        Mov newMov = new Mov($tr.text, "r"+registerCounter++);
        $n.getInstructions().add(newMov);
    }
   |fa=FALSE
    {
        Mov newMov = new Mov($fa.text, "r"+registerCounter++);
        $n.getInstructions().add(newMov);
    }
   |inte=INTEGER
    {
        Loadi newLoadi = new Loadi($inte.text, "r" + registerCounter++);
        $n.getInstructions().add(newLoadi);
    }
   |id=ID
    {
        Mov newMov = new Mov(getRegister($id.text), "r" + registerCounter++);
        $n.getInstructions().add(newMov);
    }
   |en=ENDL
    {
        Mov newMov = new Mov("endl", "r"+registerCounter++);
        $n.getInstructions().add(newMov);
    }
   |NULL
    {
        Mov newMov = new Mov("null", "r"+registerCounter++);
        $n.getInstructions().add(newMov);
    }
   |node = stmts[predNode]
    {
        $n = node;
        //System.out.println("In EXPRESSION, n type: " + n.getNodeType());
    }
;

lvalue [Node predNode] returns [Node n = predNode]
   : ^(DOT lvalue[predNode] id=ID) 
   | id=ID
      {
         // need ability to get target of an instruction
         Mov newMov = new Mov(getRegister($id.text), "r" + registerCounter++);
         $n.getInstructions().add(newMov);
      }
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

    lvalue[predNode])
    
          
    
    |^(IF 
          
          {
            if (printNodeAdds)
               System.out.println("if (expression)");
            predNode.setNodeType(NodeType.IF); // this line makes the graph print out accurately
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
                Jumpi newJumpi = new Jumpi("L"+ifJoin.getId());
                th.getInstructions().add(newJumpi);
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
            
            e.getSuccNodes().add(elseBlock);
            elseBlock.getPredNodes().add(e);
            // insert explicit jump to if_join block here
            if(el.getNodeType() != NodeType.EXIT){
                el.getSuccNodes().add(ifJoin);
                ifJoin.getPredNodes().add(el);
                Jumpi newJumpi = new Jumpi("L"+ifJoin.getId());
                el.getInstructions().add(newJumpi);
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
            else
               n = last;
            
          }
    

    
    |^(WHILE 
    
         {
            if (printNodeAdds)
               System.out.println("while exp1");
            predNode.setNodeType(NodeType.WHILE); // this line makes the graph print out accurately
         }
    
    e=expression[predNode]
    
         {
            if (printNodeAdds)
               System.out.println("L" + e.getId() + " WHILE_BODY exp2");
            Node whileBodyStart = new Node(NodeType.WHILE_BODY, (currentIDNum++), "WHILE_BODY");
            Node whileJoin = new Node(NodeType.WHILE_JOIN, (currentIDNum++), "WHILE_JOIN");
            e.getSuccNodes().add(whileBodyStart);
            e.getSuccNodes().add(whileJoin);
            whileBodyStart.getPredNodes().add(e);
            //whileBodyStart.getSuccNodes().add(whileJoin);
            if (printNodeAdds) {
                System.out.println("jump from L" + e.getId() + " to L" 
                   + whileBodyStart.getId()); 
                System.out.println("L" + whileJoin.getId() + " WHILE_JOIN");
            }   
         }
    
    whileBodyAfter=stmt[whileBodyStart] 
    
         {//This is the whileJoin block
            if (printNodeAdds)
               System.out.println("L" + whileJoin.getId() + " while_after"); 
            whileJoin.getPredNodes().add(whileBodyAfter);  
            whileJoin.getPredNodes().add(e);
            whileBodyAfter.setBackEdgeTarget(whileBodyStart);
            whileBodyAfter.getSuccNodes().add(whileJoin);
            n = whileJoin;
         }
    
    e=expression[whileBodyAfter] {n = whileJoin;})
    
      {System.out.println("while-join node type = " + n.getNodeType());}
    
    
    |^(DELETE 
        {
          
          if (printNodeAdds)
             System.out.println("delete");
          
        }
    
        current=expression[predNode]
    
    
    )

    |^(RETURN {current = null;} (current=expression[predNode])?)
    
        {
          Node newPredNode;

          if(current == null){
              newPredNode = predNode;
          }
          else{
              newPredNode = current;
          }
          if (printNodeAdds){
             System.out.println("RETURN: jump to L" + last.getId());
          }

          Storeret newStoreret = new Storeret("r"+(registerCounter-1));
          newPredNode.getInstructions().add(newStoreret);

          Jumpi newJumpi = new Jumpi("L"+last.getId());
          newPredNode.getInstructions().add(newJumpi);

          newPredNode.getSuccNodes().add(last);
          last.getPredNodes().add(newPredNode);
          Ret newRet = new Ret();
          last.getInstructions().add(newRet);
          $n = last; 
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
    current=expression[predNode] lv=lvalue[current]
        {
          $n = lv;
        }
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
        Node head = new Node(NodeType.ENTRY, (currentIDNum++), "Entry");
        functions.add(head);
        head.setLocals(g_stable.gatherVariablesInScope($id.text));
        head.setRegisterMap(buildRegisterMap(head.getLocals()));
        localRegisterMap = head.getRegisterMap(); 
        System.out.println("After mapping vars for function " + $id.text + ", the reg count is " + registerCounter);
        funcNames.add($id.text);
        Node firstBlock = new Node(NodeType.BLOCK, (currentIDNum++), "Block");
        head.getSuccNodes().add(firstBlock);
        firstBlock.getPredNodes().add(head);
        Jumpi newJumpi = new Jumpi("L"+firstBlock.getId());
        head.getInstructions().add(newJumpi);
        last = new Node(NodeType.EXIT, (currentIDNum++), "Exit");
        if (printNodeAdds)
           System.out.println("\nL" + head.getId() + " HEAD Node for function " + $id.text);
           System.out.println("jump from L" + head.getId() + " to L" + firstBlock.getId()); 
           System.out.println("L" + firstBlock.getId() + " start new Block ");        
      } 
      
      params 
      
      {
         Variable f = g_stable.getVariable("global", $id.text);
         int numP = f.getNumParam();
         int count = 0;
         System.out.println("numP = " + numP);
         for (String p : f.getParams()) {
            System.out.println(p);
            Loadinargument lia = new Loadinargument(p, count + "", getRegister(p));
            firstBlock.getInstructions().add(lia);
            count++;
         }
      }
      
      rettype decls current=stmts[firstBlock]
    
      {   
        System.out.println("current node type = " + current.getNodeType());
        if (current.getNodeType() != NodeType.EXIT) {
           current.getSuccNodes().add(last);
           last.getPredNodes().add(current);
           Ret newRet = new Ret();
           last.getInstructions().add(newRet);
        }
        
        
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
        funcNames = new ArrayList<String>();
        currentIDNum = 0;
        printNodeAdds = false;
        printMini = false;
        ArrayList<String> globals = g_stable.gatherVariablesInScope("global");
        globalRegisterMap = buildRegisterMap(globals);
        System.out.println("After mapping global vars, the reg count is " + registerCounter);
    }
   : ^(PROGRAM (types) decls funcs)
   { System.out.println("Successfully completed Control.g.");
      int count = 0;
      for (Node n : functions) { 
         try {        
            String name = funcNames.get(count++);
            //System.out.println("started " + name);
            n.printCFGtoDotFile(name); 
            n.printCFGtoFile(name);
            //System.out.println("finished " + name);
         } catch (Exception e) {
           System.out.println("Unable to complete DOT file");
         }        
      }
    }
;
