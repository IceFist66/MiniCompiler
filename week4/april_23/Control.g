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
   import java.util.Set;
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
    ArrayList<String> globals; // sent to AsseemblyFactory to add .comm entries
    private int registerCounter = 0;
    private int localRegisterReset = 0; // if storing globals, this won't be needed
    private String currentScope = "global";
    private boolean assignLVisField = false; // used in processiing ASSIGN statements
    private boolean assignRisField = false;
    private String assignLVisFieldName = "---";
    private String dotFieldName = "---";
    private String lvDotFieldName = "---";
    private int maxNumParams = 0;
    private ArrayList<String> stringDirectives;
    private ArrayList<String> lsDotFieldNames; // used to determine fields in calls to lvalue which need to be loaded
    private ArrayList<String> rsDotFieldNames; // used to determine fields in calls to DOT expressions which need to be loaded
    private String stringConstants;
    private int stringCounter = 0;
    private int lsDotCounter = 0;
    private String filename = "";
    private ArrayList<ArrayList<String>> g_orderedParams;
    
    // remove the boolean argument once figure out how to load and store globals
    public HashMap<String, String> buildRegisterMap(ArrayList<String> variableNames, boolean isGlobalTable) {
      HashMap<String, String> registerMap = new HashMap<String, String>();
      for(String variableName : variableNames) {
         registerMap.put(variableName, "r" + registerCounter);
         System.out.println("Assigned variable " + variableName + " to register " + "r" + registerCounter);
         registerCounter++;
      }
      if (isGlobalTable) // should not be needed when storing globals
         localRegisterReset = registerCounter; // currently unused, if needed when reset reg count for each funtion, set to this instead of to 0
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
    
    public boolean isGlobal(String variable) {
      if (localRegisterMap.containsKey(variable))
         return false;
      else if (globalRegisterMap.containsKey(variable))
         return true;
      else
         return false;
    }
    
    public String getVariableNameFromRegister(String register) {
      boolean found = false;
      String name = "ERROR";
      Set<Map.Entry<String, String>> localSetOfEntries = localRegisterMap.entrySet();
      for(Map.Entry<String, String> entry : localSetOfEntries) {
         if(entry.getValue().equals(register)) {
            name = entry.getKey(); 
            found = true;
         }
      }
      if (!found) {
         Set<Map.Entry<String, String>> globalSetOfEntries = globalRegisterMap.entrySet();
         for(Map.Entry<String, String> entry : globalSetOfEntries) {
            if(entry.getValue().equals(register)) {
               name = entry.getKey(); 
               found = true;
            }
         }    
      }
      return name;
    }
    
    public String getLastTarget(Node n) {
      Instruction i;
      int numC = n.getInstructions().size();
      if (numC > 0) {
         i = n.getInstructions().get(numC - 1);
         if (i instanceof Mov && i.getArg1().equals(i.getTarget())) {
            n.getInstructions().remove(numC - 1);
            //registerCounter--;
         }
         return i.getTarget();
      }
      else {
         System.out.println("no instruction!");
         return "no instruction!";
      }
    }
    
    public String getLastMovTarget(Node n) {
      Instruction i;
      boolean done = false;
      int numC = n.getInstructions().size();
      int counter = numC - 1;
      String tar = "LastMovTargetError";
      while (counter >= 0 && !done) {
         i = n.getInstructions().get(counter);
         if (i instanceof Mov && i.getArg1().equals(i.getTarget())) {
            n.getInstructions().remove(numC - 1);
            //registerCounter--;
         }
         if (i instanceof Mov) {
            tar = i.getTarget();
            done = true;
         }
      }
      return tar;
    }
    
    public ArrayList<String> getFuncNames() {
      return this.funcNames;
    }
    
    public int getMaxNumParams() {
      return maxNumParams;
    }
    
    public String getStringConstants() {
      return stringConstants;
    }
    
    public ArrayList<String> getStringDirectives() {
      return stringDirectives;
    }
    
    public ArrayList<String> getGlobals() {
      return globals;
    }
    
    public String getFilename() {
      return filename;
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
   :^(AND n1=expression[predNode]
      {
         String p1 = getLastTarget(n1);
      } 
   
   n2=expression[n1]
      {
         String p2 = getLastTarget(n2);
         And newAnd = new And(p1, p2, "r" + registerCounter++);
         n2.getInstructions().add(newAnd);
         
         //Temporary
         Println print1 = new Println(p1);
         n2.getInstructions().add(print1);
         /*
         Println print2 = new Println(p2);
         n2.getInstructions().add(print2);
         */

         $n = n2;
      }      
   )
   
   |^(OR n1=expression[predNode]
      {
         String p1 = getLastTarget(n1);
      }
         
   n2=expression[n1]
      {
         String p2 = getLastTarget(n2);
         Or newOr = new Or(p1, p2, "r" + registerCounter++);
         n2.getInstructions().add(newOr);
         $n = n2;
      }  
   )
   |^(EQ n1=expression[predNode]
      {
         String p1 = getLastTarget(n1);
      }
   n2=expression[n1]
      {
         String compResultRegister = "r" + registerCounter++;
         Loadi li = new Loadi("0", compResultRegister);
         String p2 = getLastTarget(n2);
         n2.getInstructions().add(li);
         Comp c = new Comp(p1, p2, "ccr");
         n2.getInstructions().add(c);
         //Cbrne cn = new Cbrne("ccr", "L*", "L*");
         //n2.getInstructions().add(cn);
         Moveq m = new Moveq("ccr", compResultRegister);
         n2.getInstructions().add(m);
         $n = n2;
      }  
   )
   |^(NE n1=expression[predNode]
      {
         String p1 = getLastTarget(n1);
      }
   n2=expression[n1]
      {
         String compResultRegister = "r" + registerCounter++;
         Loadi li = new Loadi("0", compResultRegister);
         String p2 = getLastTarget(n2);
         n2.getInstructions().add(li);
         Comp c = new Comp(p1, p2, "ccr");
         n2.getInstructions().add(c);
         //Cbrne cn = new Cbrne("ccr", "L*", "L*");
         //n2.getInstructions().add(cn);
         Movne m = new Movne("ccr", compResultRegister);
         n2.getInstructions().add(m);
         $n = n2;
      }  
   )
   |^(LT n1=expression[predNode]
      {
         String p1 = getLastTarget(n1);
      }
   n2=expression[n1]
      {
         String compResultRegister = "r" + registerCounter++;
         Loadi li = new Loadi("0", compResultRegister);
         String p2 = getLastTarget(n2);
         n2.getInstructions().add(li);
         Comp c = new Comp(p1, p2, "ccr");
         n2.getInstructions().add(c);
         //Cbrge cge = new Cbrge("ccr", "L*", "L*");
         //n2.getInstructions().add(cge);
         Movlt m = new Movlt("ccr", compResultRegister);
         n2.getInstructions().add(m);
         $n = n2;
      }   
   )
   |^(GT n1=expression[predNode]
      {
         String p1 = getLastTarget(n1);
      }
   n2=expression[n1]
      {
         String compResultRegister = "r" + registerCounter++;
         Loadi li = new Loadi("0", compResultRegister);
         String p2 = getLastTarget(n2);
         n2.getInstructions().add(li);
         Comp c = new Comp(p1, p2, "ccr");
         n2.getInstructions().add(c);
         //Cbrle cle = new Cbrle("ccr", "L*", "L*");
         //n2.getInstructions().add(cle);
         Movgt m = new Movgt("ccr", compResultRegister);
         n2.getInstructions().add(m);
         $n = n2;
      }   
   )
   |^(LE n1=expression[predNode]
      {
         String p1 = getLastTarget(n1);
      }
   n2=expression[n1]   
      {
         String compResultRegister = "r" + registerCounter++;
         Loadi li = new Loadi("0", compResultRegister);
         String p2 = getLastTarget(n2);
         n2.getInstructions().add(li);
         Comp c = new Comp(p1, p2, "ccr");
         n2.getInstructions().add(c);
         //Cbrgt cgt = new Cbrgt("ccr", "L*", "L*");
         //n2.getInstructions().add(cgt);
         Movle m = new Movle("ccr", compResultRegister);
         n2.getInstructions().add(m);
         $n = n2;
      }
   )
   |^(GE n1=expression[predNode]
      {
         String p1 = getLastTarget(n1);
      }
   n2=expression[n1]
      {
         String compResultRegister = "r" + registerCounter++;
         Loadi li = new Loadi("0", compResultRegister);
         String p2 = getLastTarget(n2);
         n2.getInstructions().add(li);
         Comp c = new Comp(p1, p2, "ccr");
         n2.getInstructions().add(c);
         //Cbrlt clt = new Cbrlt("ccr", "L*", "L*");
         //n2.getInstructions().add(clt);
         Movge m = new Movge("ccr", compResultRegister);
         n2.getInstructions().add(m);
         $n = n2;
      }   
   )
   
   |^(PLUS n1=expression[predNode] 

      {
         String p1 = getLastTarget(n1);
      } 
     
    n2 = expression[n1] 
    
      {
         String p2 = getLastTarget(n2);
         Add newAdd = new Add(p1, p2, "r" + registerCounter++);
         n2.getInstructions().add(newAdd);
         $n = n2;
      }
      
    )
    
   |^(MINUS n1=expression[predNode] 
   
      {
         String m1 = getLastTarget(n1);      
      } 
   
   n2=expression[predNode]
   
      {
         String m2 = getLastTarget(n2);
         Sub newSub = new Sub(m1, m2, "r" + registerCounter++);
         n2.getInstructions().add(newSub);
         $n = n2;
      }
   
   )
   
   |^(TIMES 
   
   n1=expression[predNode]
  
      {
        String m1 = getLastTarget(n1);
      }
      
   n2=expression[predNode]
   
      {
         String m2 = getLastTarget(n2);
         Mult newMult = new Mult(m1, m2, "r" + registerCounter++);
         n2.getInstructions().add(newMult);
         $n = n2;
      }
    
   )
   
   |^(DIVIDE 
   
   n1=expression[predNode] 
   
      {
        String d1 = getLastTarget(n1);
      }
   
   n2=expression[predNode]
   
      {
         String d2 = getLastTarget(n2);
         Div newDiv = new Div(d1, d2, "r" + registerCounter++);
         n2.getInstructions().add(newDiv);
         $n = n2;
      }
   
   )
   
   |^(NOT n1=expression[predNode]
      {
         int size = n1.getInstructions().size();
         if (size > 0) {
            Instruction i = n1.getInstructions().get(size - 1);
            String a1 = "Error in NOT (a1)";
            String a2 = "Error in NOT (a2)";
            if (i != null && i.getArg1() != null)
               a1 = i.getArg1();
            if (i != null && i.getArg2() != null)
               a2 = i.getArg2();
            if (i instanceof Moveq) {
               n1.getInstructions().remove(i);
               Movne m = new Movne(a1, a2);
               n1.getInstructions().add(m);
            }
            else if (i instanceof Movne) {
               n1.getInstructions().remove(i);
               Moveq m = new Moveq(a1, a2);
               n1.getInstructions().add(m);
            }
            else if (i instanceof Movge) {
               n1.getInstructions().remove(i);
               Movlt m = new Movlt(a1, a2);
               n1.getInstructions().add(m);
            }
            else if (i instanceof Movgt) {
               n1.getInstructions().remove(i);
               Movle m = new Movle(a1, a2);
               n1.getInstructions().add(m);
            }
            else if (i instanceof Movle) {
               n1.getInstructions().remove(i);
               Movgt m = new Movgt(a1, a2);
               n1.getInstructions().add(m);
            }
            else if (i instanceof Movlt) {
               n1.getInstructions().remove(i);
               Movge m = new Movge(a1, a2);
               n1.getInstructions().add(m);
            }
         }
         else
            System.out.println("Error in NOT expression: no preceding move to flip!");
         $n = n1;
      }   
   )   
   |^(NEW id=ID)
    {
        ArrayList<String> names = g_stypes.getFieldNames("global", $id.text);
        String fields = "[";
        for (String s : names)
         fields += s + ", ";
        fields = fields.substring(0, fields.length() - 2);
        fields += "]";
        New newNew = new New($id.text, fields, "r"+registerCounter++);
        $n.getInstructions().add(newNew);
    }
   |^(DOT n1 = expression[predNode]
      {
         String reg = getLastTarget(n1);
         assignRisField = true;
      }
   
   n2 = expression[n1]
      {
         Loadai lai2 = new Loadai(reg, dotFieldName, "r" + registerCounter++);
         n2.getInstructions().add(lai2);
         $n = n2;
         assignRisField = false;
      }
   )
   |^(INVOKE id=ID
      {
             System.out.println("invoke " + $id.text);
      }
     current=args[predNode]     
         
      {  
         Variable f = g_stable.getVariable(currentScope, $id.text);
         int numP = f.getNumParam();
         if (numP > maxNumParams)
            maxNumParams = numP;
         Call newCall = new Call($id.text, "" + numP);
         current.getInstructions().add(newCall);
         Loadret lr = new Loadret("r" + registerCounter++);
         current.getInstructions().add(lr);
      }
     
     )
   
   
   
   |tr=TRUE
    {
        Loadi newLoadi = new Loadi("1", "r" + registerCounter++);
        $n.getInstructions().add(newLoadi);
        //Mov newMov = new Mov($tr.text, "r"+registerCounter++);
        //$n.getInstructions().add(newMov);
    }
   |fa=FALSE
    {
        Loadi newLoadi = new Loadi("0", "r" + registerCounter++);
        $n.getInstructions().add(newLoadi);
        //Mov newMov = new Mov($fa.text, "r"+registerCounter++);
        //$n.getInstructions().add(newMov);
    }
   |inte=INTEGER
    {
        Loadi newLoadi = new Loadi($inte.text, "r" + registerCounter++);
        $n.getInstructions().add(newLoadi);
    }
   |id=ID
    {
      if (!assignRisField) {
         //if(isGlobal($id.text)) {
            /*Variable v = g_stable.getVariable("global", $id.text);
            if(v.getType() == Type.STRUCT) {
               Computeglobaladdress cga = new Computeglobaladdress($id.text, "r" + registerCounter++);
               $n.getInstructions().add(cga);
            } else {
               Loadglobal lg = new Loadglobal($id.text, "r" + registerCounter++);
               $n.getInstructions().add(lg);
            }*/
         //} else {
           Mov newMov = new Mov(getRegister($id.text), "r" + registerCounter++);
           $n.getInstructions().add(newMov);           
           rsDotFieldNames.add($id.text);
           System.out.println("Added " + $id.text + " to rsDotFieldNames");
           //}
        }
        dotFieldName = $id.text; // this WORKS but is this correct?
    }
   |en=ENDL
    {
        Mov newMov = new Mov("endl", "r"+registerCounter++);
        $n.getInstructions().add(newMov);
    }
   |NULL
    {
        Loadi newLoadi = new Loadi("0", "r" + registerCounter++);
        $n.getInstructions().add(newLoadi);
        //Mov newMov = new Mov("null", "r"+registerCounter++);
        //$n.getInstructions().add(newMov);
    }
   |node = stmts[predNode]
    {
        $n = node;
        //System.out.println("In EXPRESSION, n type: " + n.getNodeType());
    }
;

lvalue [Node predNode] returns [Node n = predNode]
   : ^(DOT lvalue[predNode] id=ID 
   
      {
         lsDotCounter++;
         assignLVisField = true;
         assignLVisFieldName = $id.text; 
         //System.out.println("-----DOT EXPR: " + $id.text);
         lsDotFieldNames.add($id.text);
         //System.out.println("The field " + $id.text + " was added to lsDotFieldNames.");
         //System.out.println("lsDotCounter = " + lsDotCounter);
         //System.out.println("lsDotFieldNames.size() = " + lsDotFieldNames.size());
      }      
      
   ) 
   | id=ID
      {
            //{System.out.println("-----DOT EXPR ID: " + $id.text);}
            Mov newMov = new Mov(getRegister($id.text), getRegister($id.text));
            predNode.getInstructions().add(newMov);            
            $n = predNode;
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
    current=expression[predNode] 
    
    {
         String tar = getLastTarget(current);
         System.out.println(tar);
         boolean ln = false; 
    }
    
    (en=expression[current])?
    
         {
            //String stringLabel = ".LC" + (stringCounter) + ":\n"; // this is incremented for the next function before leaving FUN (see below)
            //String entry = "\t.string ";
            
            if (en != null) {    // && getLastTarget(en).equals("endl")) {
               ln = true;
               Println pl = new Println(tar);
               en.getInstructions().remove(en.getInstructions().size() - 1);
               en.getInstructions().add(pl);
               //stringConstants += (stringLabel + entry + "\"\%ld\\n\"\n");
               n = en;        
            } else {
               Print newPrint = new Print(tar);
               current.getInstructions().add(newPrint);
               //stringConstants += (stringLabel + entry + "\"\%ld \"\n");
               n = current;       
            }
         }
         
    )
    
    |^(READ 
          {
            
            if (printNodeAdds)
               System.out.println("read");
            
          }

    current=lvalue[predNode]
      
      {
         //String name = getVariableNameFromRegister(getLastTarget(current));
         //Addi newAddi = new Addi("rarp", name, "r" + registerCounter++);
         //current.getInstructions().add(newAddi);
         String readTarget = getLastTarget(current);
         Read newRead = new Read(readTarget);
         current.getInstructions().add(newRead);
         //Loadai lai = new Loadai("rarp", name, getRegister(name));
         //current.getInstructions().add(lai);
         n = current;
      }
      
    )
    
          
    
    |^(IF 
          
          {
            if (printNodeAdds)
               System.out.println("if (expression)");
            predNode.setNodeType(NodeType.IF); // this line makes the graph print out accurately
          }
    
    e=expression[predNode] 
    
          {
            String ifGuardResult = getLastTarget(e);
            int thenBlockID = currentIDNum++;
            int elseBlockID = currentIDNum++;
            int ifJoinID = currentIDNum++;
            Node thenBlock = new Node(NodeType.THEN, (thenBlockID), "THEN");
            Node elseBlock = new Node(NodeType.ELSE, (elseBlockID), "ELSE");
            Node ifJoin = new Node(NodeType.IF_JOIN, (ifJoinID), "IF_JOIN");
            
            Brz b = new Brz(ifGuardResult, "L" + thenBlockID, "L" + elseBlockID);
            e.getInstructions().add(b); 
            
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
            if(th.getNodeType() != NodeType.EXIT) {
                th.getSuccNodes().add(ifJoin);
                ifJoin.getPredNodes().add(th);
                Jumpi newJumpii = new Jumpi("L"+ifJoin.getId());
                th.getInstructions().add(newJumpii);
                if (printNodeAdds) {
                  System.out.println("jump from L" + thenBlock.getId() + " to L" 
                   + ifJoin.getId() + " (ifJoin)"); 
                }   
            }
          }
          
          {
           //Node elseBlock = new Node(NodeType.ELSE, (currentIDNum++), "ELSE");
             e.getSuccNodes().add(elseBlock);
             elseBlock.getPredNodes().add(e);
           if (printNodeAdds) {
              System.out.println("false: jump from L" + e.getId() + " to L" + elseBlock.getId()); 
              System.out.println("L" + elseBlock.getId() + " ELSE");
           }  
           
           {el = null;} 
          }
          

    
    (el=stmt[elseBlock])?   

          {
            if (el == null) {
               el = elseBlock;
                el.getSuccNodes().add(ifJoin);
                ifJoin.getPredNodes().add(el);
                Jumpi newJumpi = new Jumpi("L"+ifJoin.getId());
                el.getInstructions().add(newJumpi);
            }
            // insert explicit jump to if_join block here
            else if(el.getNodeType() != NodeType.EXIT && el != null){
                el.getSuccNodes().add(ifJoin);
                ifJoin.getPredNodes().add(el);
                Jumpi newJumpi = new Jumpi("L"+ifJoin.getId());
                el.getInstructions().add(newJumpi);
                if (printNodeAdds) {
                    System.out.println("jump to L" + ifJoin.getId() + " (ifJoin)");
                    System.out.println("L" + ifJoin.getId() + " IF_JOIN");
                }    
            }

    
          
          
            if((th != null && th.getNodeType() != NodeType.EXIT) || (el != null && el.getNodeType() != NodeType.EXIT))
                n = ifJoin;
            else
                n = last;
            
           
          }
    
    
    )
    

    

    
    |^(WHILE 
    
         {
            if (printNodeAdds)
               System.out.println("while exp1");
            predNode.setNodeType(NodeType.WHILE); // this line makes the graph print out accurately
         }
    
    e=expression[predNode]
    
         {
            String guardResult = getLastTarget(e);


            if (printNodeAdds)
               System.out.println("L" + e.getId() + " WHILE_BODY exp2");
            int whileBodyStartID = currentIDNum++;
            int whileJoinID = currentIDNum++;
            Node whileBodyStart = new Node(NodeType.WHILE_BODY, (whileBodyStartID), "WHILE_BODY");
            Node whileJoin = new Node(NodeType.WHILE_JOIN, (whileJoinID), "WHILE_JOIN");
            
            Brz b = new Brz(guardResult, "L" + whileBodyStartID, "L" + whileJoinID);
            e.getInstructions().add(b);
            
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
    
    e=expression[whileBodyAfter] 
    
      {
         guardResult = getLastTarget(e);
         Brz bn = new Brz(guardResult, "L" + whileBodyStartID, "L" + whileJoinID);
         e.getInstructions().add(bn);
         n = whileJoin;
      }
      
      
    )
    
      {System.out.println("while-join node type = " + n.getNodeType());}
    
    
    |^(DELETE 
        {
          
          if (printNodeAdds)
             System.out.println("delete");
          
        }
    
        current=expression[predNode]
         
        {
           String reg = getLastTarget(current);
           Del newDel = new Del(reg);
           current.getInstructions().add(newDel);
           n = current;
        }
    
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
          Storeret newStoreret = new Storeret(getLastTarget(newPredNode));
          newPredNode.getInstructions().add(newStoreret);

          Jumpi newJumpi = new Jumpi("L"+last.getId());
          newPredNode.getInstructions().add(newJumpi);

          newPredNode.getSuccNodes().add(last);
          last.getPredNodes().add(newPredNode);
          int numL = last.getInstructions().size();
          if (numL > 0 && last.getInstructions().get(numL - 1) instanceof Ret) {
            // do nothing
          } else {
            Ret newRet = new Ret();
            last.getInstructions().add(newRet);          
          }
          $n = last; 
        }
                   
    |^(INVOKE id=ID 
        {      
          if (printNodeAdds)
             System.out.println("invoke " + $id.text);
          
        }

    current=args[predNode]
    
      {  
         Variable f = g_stable.getVariable(currentScope, $id.text);
         int numP = f.getNumParam();
         if (numP > maxNumParams)
            maxNumParams = numP;
         Call newCall = new Call($id.text, "" + numP);
         current.getInstructions().add(newCall);
         Loadret lr = new Loadret("r" + registerCounter++);
         current.getInstructions().add(lr);
      }
    
    )                                          
    
    
    
    |^(ASSIGN 
        {      
          if (printNodeAdds)
             System.out.println("assign");
          System.out.println("assign");
        }
    current=expression[predNode]
        {
         String r = getLastTarget(current);
         System.out.println("rValue = " + r);
         assignRisField = false;
        }
    
    lv=lvalue[current]
        {
          String l = getLastTarget(lv);
          System.out.println("lValue = " + l);
          $n = lv;
          if(assignLVisField) {      
            int tarReg = registerCounter++;         
            Mov newMov = new Mov(l, "r" + tarReg);
            lv.getInstructions().add(newMov);
            if (lsDotFieldNames.size() > 1) {
               lsDotFieldNames.remove(lsDotFieldNames.size()-1); // this removes the last id in the dot expression which does not need to be loaded
               int countdown = lsDotFieldNames.size() - 1; // this is needed to determine when encountering the last loadai, since it won't be followed by a move
               for (String s : lsDotFieldNames) {
                  Loadai lai = new Loadai(getLastTarget(lv), s, "r" + registerCounter++);
                  lv.getInstructions().add(lai);
                  if (countdown > 0) {       // this means we're going to have to do another load, so move the result from the last load into a register
                     Mov anotherMov = new Mov(getLastTarget(lv), "r" + registerCounter++);
                     lv.getInstructions().add(anotherMov);
                     countdown--;
                  }
               }            
            }
            Storeai sai = new Storeai(r, getLastTarget(lv), assignLVisFieldName);
            $n.getInstructions().add(sai);
          } else {
             Mov newMov = new Mov(r, l);
             $n.getInstructions().add(newMov);
          }
          assignLVisField = false;
          assignLVisFieldName = "---";
          lsDotFieldNames.clear();
          lsDotCounter = 0;
        }
    )
;

args [Node predNode] returns [Node n = predNode]

   :^(ARGS 
   
      { int argCounter = 0;}
   
   (aNode = expression[predNode]
   
      {
        Mov newMov = new Mov(getLastTarget(aNode), "r" + registerCounter++);
        aNode.getInstructions().add(newMov);
        Storeoutargument soa = new Storeoutargument(getLastTarget(aNode), "" + argCounter++);
        aNode.getInstructions().add(soa);
        n = aNode;
      }
      
   )*)
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
        currentScope = $id.text;
        //registerCounter = 0;
        registerCounter = localRegisterReset;
        stringConstants += "\t.section .rodata\n";
        Node head = new Node(NodeType.ENTRY, (currentIDNum++), "Entry");
        head.setFunctionName($id.text);
        if($id.text.equals("main")){
            head.setIsMainHead(true);
            System.out.println("*******FOUND THE MAIN******");
        }
        functions.add(head);
        head.setLocals(g_stable.gatherVariablesInScope($id.text));
        head.setRegisterMap(buildRegisterMap(head.getLocals(), false));
        localRegisterMap = head.getRegisterMap(); 
        System.out.println("After mapping vars for function " + $id.text + ", the reg count is " + registerCounter);
        funcNames.add($id.text);
        //Node firstBlock = new Node(NodeType.BLOCK, (currentIDNum++), "Block");
        //head.getSuccNodes().add(firstBlock);
        //firstBlock.getPredNodes().add(head);
        //Jumpi newJumpi = new Jumpi("L"+firstBlock.getId());
        //head.getInstructions().add(newJumpi);
        last = new Node(NodeType.EXIT, (currentIDNum++), "Exit");
  /*      if (printNodeAdds)
           System.out.println("\nL" + head.getId() + " HEAD Node for function " + $id.text);
           System.out.println("jump from L" + head.getId() + " to L" + firstBlock.getId()); 
           System.out.println("L" + firstBlock.getId() + " start new Block ");   */     
      } 
      
      params 
      
      {
         Variable f = g_stable.getVariable("global", $id.text);
         int numP = f.getNumParam();
         if (numP > maxNumParams)
            maxNumParams = numP;
         int count = 0;
         System.out.println("numP = " + numP);
         
         // find function's parameter list
         ArrayList<String> target;
         for(ArrayList<String> a : g_orderedParams) {
            if (a.get(0).equals($id.text)) {
                target = a;
                target.remove(0);
                for (String p : target) {
                    System.out.println(p);
                    Loadinargument lia = new Loadinargument(p, count + "", getRegister(p));
                    head.getInstructions().add(lia);
                    count++;
                }
                
            }
         }

         /*for (String p : f.getParams()) {
            System.out.println(p);
            Loadinargument lia = new Loadinargument(p, count + "", getRegister(p));
          //  firstBlock.getInstructions().add(lia);
            head.getInstructions().add(lia);
            count++;
         }*/
      }
      
      //rettype decls current=stmts[firstBlock]
      rettype decls current=stmts[head]
    
      {   
        System.out.println("current node type = " + current.getNodeType());
        if (current.getNodeType() != NodeType.EXIT) {
           current.getSuccNodes().add(last);
           last.getPredNodes().add(current);
           
          int numL = last.getInstructions().size();
          if (numL > 0 && last.getInstructions().get(numL - 1) instanceof Ret) {
            // do nothing
          } else {
            Ret newRet = new Ret();
            last.getInstructions().add(newRet);          
          }

        }
        
        String stringLabel = ".LL" + (stringCounter) + ":\n"; // this is incremented for the next function before leaving FUN (see below)
        String entry = "\t.string ";
        stringConstants += (stringLabel + entry + "\"\%ld\\n\"\n");
        String stringLabel2 = ".LC" + (stringCounter) + ":\n";
        String entry2 = "\t.string ";
        stringConstants += (stringLabel2 + entry2 + "\"\%ld \"\n");
        
        String newString = stringConstants.substring(0, stringConstants.length());
        newString += (".LS" + stringCounter + ":\n\t.string \"\%ld\"\n");
        stringCounter++; // this increments the count to produce labels for the next function (.LC0, .LS0, ..., .LC1, .LS1, ...)
        stringDirectives.add(newString);
        stringConstants = "";
        
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


construct [StructTypes stypes, SymbolTable stable, String filename, ArrayList<ArrayList<String>> orderedParams] returns [ArrayList<Node> f = null]
    @init {
        g_stypes = stypes; 
        g_stable = stable;
        g_orderedParams = orderedParams;
        functions = new ArrayList<Node>();
        funcNames = new ArrayList<String>();
        stringDirectives = new ArrayList<String>();
        lsDotFieldNames = new ArrayList<String>();
        rsDotFieldNames = new ArrayList<String>();
        globals = new ArrayList<String>();
        stringConstants = "";
        filename = filename;
        System.out.println("*****" + filename + "*****\n");
        currentIDNum = 0;
        printNodeAdds = false;
        printMini = false;
        ArrayList<String> globals = g_stable.gatherVariablesInScope("global");
        System.out.println("GLOBAL REGISTER MAP");
        for (String s : globals) {
         System.out.println("Added " + s + "to the globals register");        
        }
        globalRegisterMap = buildRegisterMap(globals, true);
        System.out.println("After mapping global vars, the reg count is " + registerCounter);
    }
   : ^(PROGRAM (types) decls funcs)
   { 
      int ct = 0;
      System.out.println("-*-*-stringDirectives contains:");
      for (String c : stringDirectives) {
         System.out.println("string # " + ct + ": " + c);
         ct++;
      }
   
   
      System.out.println("Successfully completed Control.g.");
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
      f = functions;
    }
;
