// $ANTLR 3.3 Nov 30, 2010 12:50:56 Control.g 2014-05-29 21:24:28

   import java.util.Map;
   import java.util.HashMap;
   import java.util.Vector;
   import java.util.Iterator;
   import java.util.Set;
   import java.io.IOException;
   import iloc.*;


import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class Control extends TreeParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "STRUCT", "INT", "BOOL", "FUN", "VOID", "PRINT", "ENDL", "READ", "IF", "ELSE", "WHILE", "DELETE", "RETURN", "TRUE", "FALSE", "NEW", "NULL", "PROGRAM", "TYPES", "TYPE", "DECLS", "FUNCS", "DECL", "DECLLIST", "PARAMS", "RETTYPE", "BLOCK", "STMTS", "INVOKE", "ARGS", "NEG", "LBRACE", "RBRACE", "SEMI", "COMMA", "LPAREN", "RPAREN", "ASSIGN", "DOT", "AND", "OR", "EQ", "LT", "GT", "NE", "LE", "GE", "PLUS", "MINUS", "TIMES", "DIVIDE", "NOT", "ID", "INTEGER", "WS", "COMMENT"
    };
    public static final int EOF=-1;
    public static final int STRUCT=4;
    public static final int INT=5;
    public static final int BOOL=6;
    public static final int FUN=7;
    public static final int VOID=8;
    public static final int PRINT=9;
    public static final int ENDL=10;
    public static final int READ=11;
    public static final int IF=12;
    public static final int ELSE=13;
    public static final int WHILE=14;
    public static final int DELETE=15;
    public static final int RETURN=16;
    public static final int TRUE=17;
    public static final int FALSE=18;
    public static final int NEW=19;
    public static final int NULL=20;
    public static final int PROGRAM=21;
    public static final int TYPES=22;
    public static final int TYPE=23;
    public static final int DECLS=24;
    public static final int FUNCS=25;
    public static final int DECL=26;
    public static final int DECLLIST=27;
    public static final int PARAMS=28;
    public static final int RETTYPE=29;
    public static final int BLOCK=30;
    public static final int STMTS=31;
    public static final int INVOKE=32;
    public static final int ARGS=33;
    public static final int NEG=34;
    public static final int LBRACE=35;
    public static final int RBRACE=36;
    public static final int SEMI=37;
    public static final int COMMA=38;
    public static final int LPAREN=39;
    public static final int RPAREN=40;
    public static final int ASSIGN=41;
    public static final int DOT=42;
    public static final int AND=43;
    public static final int OR=44;
    public static final int EQ=45;
    public static final int LT=46;
    public static final int GT=47;
    public static final int NE=48;
    public static final int LE=49;
    public static final int GE=50;
    public static final int PLUS=51;
    public static final int MINUS=52;
    public static final int TIMES=53;
    public static final int DIVIDE=54;
    public static final int NOT=55;
    public static final int ID=56;
    public static final int INTEGER=57;
    public static final int WS=58;
    public static final int COMMENT=59;

    // delegates
    // delegators


        public Control(TreeNodeStream input) {
            this(input, new RecognizerSharedState());
        }
        public Control(TreeNodeStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return Control.tokenNames; }
    public String getGrammarFileName() { return "Control.g"; }


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
        private String currentScope = "global";
        
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
             if (i instanceof Mov && i.getArg1().equals(i.getTarget()))
                n.getInstructions().remove(numC - 1);
             return i.getTarget();
          }
          else {
             System.out.println("no instruction!");
             return "no instruction!";
          }
        }



    // $ANTLR start "error"
    // Control.g:91:1: error : ;
    public final void error() throws RecognitionException {
        try {
            // Control.g:91:6: ()
            // Control.g:92:5: 
            {
            System.out.println("NO MATCH FOUND");

            }

        }
        finally {
        }
        return ;
    }
    // $ANTLR end "error"


    // $ANTLR start "types"
    // Control.g:95:1: types : ^( TYPES ( struct )* ) ;
    public final void types() throws RecognitionException {
        try {
            // Control.g:95:6: ( ^( TYPES ( struct )* ) )
            // Control.g:96:5: ^( TYPES ( struct )* )
            {
            match(input,TYPES,FOLLOW_TYPES_in_types61); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // Control.g:96:13: ( struct )*
                loop1:
                do {
                    int alt1=2;
                    int LA1_0 = input.LA(1);

                    if ( (LA1_0==STRUCT) ) {
                        alt1=1;
                    }


                    switch (alt1) {
                	case 1 :
                	    // Control.g:96:13: struct
                	    {
                	    pushFollow(FOLLOW_struct_in_types63);
                	    struct();

                	    state._fsp--;


                	    }
                	    break;

                	default :
                	    break loop1;
                    }
                } while (true);


                match(input, Token.UP, null); 
            }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "types"


    // $ANTLR start "struct"
    // Control.g:99:1: struct : ^( STRUCT ID ( decl )* ) ;
    public final void struct() throws RecognitionException {
        try {
            // Control.g:99:7: ( ^( STRUCT ID ( decl )* ) )
            // Control.g:100:5: ^( STRUCT ID ( decl )* )
            {
            match(input,STRUCT,FOLLOW_STRUCT_in_struct79); 

            match(input, Token.DOWN, null); 
            match(input,ID,FOLLOW_ID_in_struct81); 
            // Control.g:100:17: ( decl )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==DECL) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // Control.g:100:18: decl
            	    {
            	    pushFollow(FOLLOW_decl_in_struct84);
            	    decl();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            match(input, Token.UP, null); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "struct"


    // $ANTLR start "decl"
    // Control.g:103:1: decl : ^( DECL ^( TYPE type ) ID ) ;
    public final void decl() throws RecognitionException {
        try {
            // Control.g:103:5: ( ^( DECL ^( TYPE type ) ID ) )
            // Control.g:104:5: ^( DECL ^( TYPE type ) ID )
            {
            match(input,DECL,FOLLOW_DECL_in_decl100); 

            match(input, Token.DOWN, null); 
            match(input,TYPE,FOLLOW_TYPE_in_decl103); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_type_in_decl105);
            type();

            state._fsp--;


            match(input, Token.UP, null); 
            match(input,ID,FOLLOW_ID_in_decl108); 

            match(input, Token.UP, null); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "decl"


    // $ANTLR start "type"
    // Control.g:107:1: type : ( ^( TYPE INT ) | ^( TYPE BOOL ) | ^( TYPE ^( STRUCT type ) ) | INT | BOOL | ^( STRUCT type ) | ID | VOID );
    public final void type() throws RecognitionException {
        try {
            // Control.g:107:5: ( ^( TYPE INT ) | ^( TYPE BOOL ) | ^( TYPE ^( STRUCT type ) ) | INT | BOOL | ^( STRUCT type ) | ID | VOID )
            int alt3=8;
            alt3 = dfa3.predict(input);
            switch (alt3) {
                case 1 :
                    // Control.g:108:5: ^( TYPE INT )
                    {
                    match(input,TYPE,FOLLOW_TYPE_in_type123); 

                    match(input, Token.DOWN, null); 
                    match(input,INT,FOLLOW_INT_in_type125); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // Control.g:109:7: ^( TYPE BOOL )
                    {
                    match(input,TYPE,FOLLOW_TYPE_in_type135); 

                    match(input, Token.DOWN, null); 
                    match(input,BOOL,FOLLOW_BOOL_in_type137); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // Control.g:110:7: ^( TYPE ^( STRUCT type ) )
                    {
                    match(input,TYPE,FOLLOW_TYPE_in_type147); 

                    match(input, Token.DOWN, null); 
                    match(input,STRUCT,FOLLOW_STRUCT_in_type150); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_type_in_type152);
                    type();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // Control.g:111:7: INT
                    {
                    match(input,INT,FOLLOW_INT_in_type162); 

                    }
                    break;
                case 5 :
                    // Control.g:112:7: BOOL
                    {
                    match(input,BOOL,FOLLOW_BOOL_in_type170); 

                    }
                    break;
                case 6 :
                    // Control.g:113:7: ^( STRUCT type )
                    {
                    match(input,STRUCT,FOLLOW_STRUCT_in_type179); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_type_in_type181);
                    type();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 7 :
                    // Control.g:114:7: ID
                    {
                    match(input,ID,FOLLOW_ID_in_type190); 

                    }
                    break;
                case 8 :
                    // Control.g:115:7: VOID
                    {
                    match(input,VOID,FOLLOW_VOID_in_type199); 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "type"


    // $ANTLR start "decls"
    // Control.g:118:1: decls : ^( DECLS ( decllist )* ) ;
    public final void decls() throws RecognitionException {
        try {
            // Control.g:118:6: ( ^( DECLS ( decllist )* ) )
            // Control.g:119:5: ^( DECLS ( decllist )* )
            {
            match(input,DECLS,FOLLOW_DECLS_in_decls212); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // Control.g:119:13: ( decllist )*
                loop4:
                do {
                    int alt4=2;
                    int LA4_0 = input.LA(1);

                    if ( (LA4_0==DECLLIST) ) {
                        alt4=1;
                    }


                    switch (alt4) {
                	case 1 :
                	    // Control.g:119:13: decllist
                	    {
                	    pushFollow(FOLLOW_decllist_in_decls214);
                	    decllist();

                	    state._fsp--;


                	    }
                	    break;

                	default :
                	    break loop4;
                    }
                } while (true);


                match(input, Token.UP, null); 
            }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "decls"


    // $ANTLR start "decllist"
    // Control.g:122:1: decllist : ^( DECLLIST type ( ID )* ) ;
    public final void decllist() throws RecognitionException {
        try {
            // Control.g:122:9: ( ^( DECLLIST type ( ID )* ) )
            // Control.g:123:5: ^( DECLLIST type ( ID )* )
            {
            match(input,DECLLIST,FOLLOW_DECLLIST_in_decllist229); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_type_in_decllist231);
            type();

            state._fsp--;

            // Control.g:123:21: ( ID )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==ID) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // Control.g:123:21: ID
            	    {
            	    match(input,ID,FOLLOW_ID_in_decllist233); 

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);


            match(input, Token.UP, null); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "decllist"


    // $ANTLR start "expression"
    // Control.g:125:1: expression[Node predNode] returns [Node n = predNode] : ( ^( AND n1= expression[predNode] n2= expression[n1] ) | ^( OR n1= expression[predNode] n2= expression[n1] ) | ^( EQ n1= expression[predNode] n2= expression[n1] ) | ^( NE n1= expression[predNode] n2= expression[n1] ) | ^( LT n1= expression[predNode] n2= expression[n1] ) | ^( GT n1= expression[predNode] n2= expression[n1] ) | ^( LE n1= expression[predNode] n2= expression[n1] ) | ^( GE n1= expression[predNode] n2= expression[n1] ) | ^( PLUS n1= expression[predNode] n2= expression[n1] ) | ^( MINUS n1= expression[predNode] n2= expression[predNode] ) | ^( TIMES n1= expression[predNode] n2= expression[predNode] ) | ^( DIVIDE n1= expression[predNode] n2= expression[predNode] ) | ^( NOT expression[predNode] ) | ^( NEW id= ID ) | ^( DOT expression[predNode] expression[predNode] ) | ^( INVOKE id= ID current= args[predNode] ) | tr= TRUE | fa= FALSE | inte= INTEGER | id= ID | en= ENDL | NULL | node= stmts[predNode] );
    public final Node expression(Node predNode) throws RecognitionException {
        Node n =  predNode;

        CommonTree id=null;
        CommonTree tr=null;
        CommonTree fa=null;
        CommonTree inte=null;
        CommonTree en=null;
        Node n1 = null;

        Node n2 = null;

        Node current = null;

        Node node = null;


        try {
            // Control.g:126:4: ( ^( AND n1= expression[predNode] n2= expression[n1] ) | ^( OR n1= expression[predNode] n2= expression[n1] ) | ^( EQ n1= expression[predNode] n2= expression[n1] ) | ^( NE n1= expression[predNode] n2= expression[n1] ) | ^( LT n1= expression[predNode] n2= expression[n1] ) | ^( GT n1= expression[predNode] n2= expression[n1] ) | ^( LE n1= expression[predNode] n2= expression[n1] ) | ^( GE n1= expression[predNode] n2= expression[n1] ) | ^( PLUS n1= expression[predNode] n2= expression[n1] ) | ^( MINUS n1= expression[predNode] n2= expression[predNode] ) | ^( TIMES n1= expression[predNode] n2= expression[predNode] ) | ^( DIVIDE n1= expression[predNode] n2= expression[predNode] ) | ^( NOT expression[predNode] ) | ^( NEW id= ID ) | ^( DOT expression[predNode] expression[predNode] ) | ^( INVOKE id= ID current= args[predNode] ) | tr= TRUE | fa= FALSE | inte= INTEGER | id= ID | en= ENDL | NULL | node= stmts[predNode] )
            int alt6=23;
            switch ( input.LA(1) ) {
            case AND:
                {
                alt6=1;
                }
                break;
            case OR:
                {
                alt6=2;
                }
                break;
            case EQ:
                {
                alt6=3;
                }
                break;
            case NE:
                {
                alt6=4;
                }
                break;
            case LT:
                {
                alt6=5;
                }
                break;
            case GT:
                {
                alt6=6;
                }
                break;
            case LE:
                {
                alt6=7;
                }
                break;
            case GE:
                {
                alt6=8;
                }
                break;
            case PLUS:
                {
                alt6=9;
                }
                break;
            case MINUS:
                {
                alt6=10;
                }
                break;
            case TIMES:
                {
                alt6=11;
                }
                break;
            case DIVIDE:
                {
                alt6=12;
                }
                break;
            case NOT:
                {
                alt6=13;
                }
                break;
            case NEW:
                {
                alt6=14;
                }
                break;
            case DOT:
                {
                alt6=15;
                }
                break;
            case INVOKE:
                {
                alt6=16;
                }
                break;
            case TRUE:
                {
                alt6=17;
                }
                break;
            case FALSE:
                {
                alt6=18;
                }
                break;
            case INTEGER:
                {
                alt6=19;
                }
                break;
            case ID:
                {
                alt6=20;
                }
                break;
            case ENDL:
                {
                alt6=21;
                }
                break;
            case NULL:
                {
                alt6=22;
                }
                break;
            case STMTS:
                {
                alt6=23;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }

            switch (alt6) {
                case 1 :
                    // Control.g:126:5: ^( AND n1= expression[predNode] n2= expression[n1] )
                    {
                    match(input,AND,FOLLOW_AND_in_expression252); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_expression256);
                    n1=expression(predNode);

                    state._fsp--;


                             String p1 = getLastTarget(n1);
                          
                    pushFollow(FOLLOW_expression_in_expression277);
                    n2=expression(n1);

                    state._fsp--;


                             String p2 = getLastTarget(n2);
                             And newAnd = new And(p1, p2, "r" + registerCounter++);
                             n2.getInstructions().add(newAnd);
                             n = n2;
                          

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // Control.g:140:5: ^( OR n1= expression[predNode] n2= expression[n1] )
                    {
                    match(input,OR,FOLLOW_OR_in_expression308); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_expression312);
                    n1=expression(predNode);

                    state._fsp--;


                             String p1 = getLastTarget(n1);
                          
                    pushFollow(FOLLOW_expression_in_expression338);
                    n2=expression(n1);

                    state._fsp--;


                             String p2 = getLastTarget(n2);
                             Or newOr = new Or(p1, p2, "r" + registerCounter++);
                             n2.getInstructions().add(newOr);
                             n = n2;
                          

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // Control.g:153:5: ^( EQ n1= expression[predNode] n2= expression[n1] )
                    {
                    match(input,EQ,FOLLOW_EQ_in_expression361); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_expression365);
                    n1=expression(predNode);

                    state._fsp--;


                             String p1 = getLastTarget(n1);
                          
                    pushFollow(FOLLOW_expression_in_expression381);
                    n2=expression(n1);

                    state._fsp--;


                             String compResultRegister = "r" + registerCounter++;
                             Loadi li = new Loadi("0", compResultRegister);
                             n2.getInstructions().add(li);
                             String p2 = getLastTarget(n2);
                             Comp c = new Comp(p1, p2, "ccr");
                             n2.getInstructions().add(c);
                             //Cbrne cn = new Cbrne("ccr", "L*", "L*");
                             //n2.getInstructions().add(cn);
                             Moveq m = new Moveq("ccr", compResultRegister);
                             n2.getInstructions().add(m);
                             n = n2;
                          

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // Control.g:172:5: ^( NE n1= expression[predNode] n2= expression[n1] )
                    {
                    match(input,NE,FOLLOW_NE_in_expression404); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_expression408);
                    n1=expression(predNode);

                    state._fsp--;


                             String p1 = getLastTarget(n1);
                          
                    pushFollow(FOLLOW_expression_in_expression424);
                    n2=expression(n1);

                    state._fsp--;


                             String compResultRegister = "r" + registerCounter++;
                             Loadi li = new Loadi("0", compResultRegister);
                             n2.getInstructions().add(li);
                             String p2 = getLastTarget(n2);
                             Comp c = new Comp(p1, p2, "ccr");
                             n2.getInstructions().add(c);
                             //Cbrne cn = new Cbrne("ccr", "L*", "L*");
                             //n2.getInstructions().add(cn);
                             Movne m = new Movne("ccr", compResultRegister);
                             n2.getInstructions().add(m);
                             n = n2;
                          

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // Control.g:191:5: ^( LT n1= expression[predNode] n2= expression[n1] )
                    {
                    match(input,LT,FOLLOW_LT_in_expression447); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_expression451);
                    n1=expression(predNode);

                    state._fsp--;


                             String p1 = getLastTarget(n1);
                          
                    pushFollow(FOLLOW_expression_in_expression467);
                    n2=expression(n1);

                    state._fsp--;


                             String compResultRegister = "r" + registerCounter++;
                             Loadi li = new Loadi("0", compResultRegister);
                             n2.getInstructions().add(li);
                             String p2 = getLastTarget(n2);
                             Comp c = new Comp(p1, p2, "ccr");
                             n2.getInstructions().add(c);
                             //Cbrge cge = new Cbrge("ccr", "L*", "L*");
                             //n2.getInstructions().add(cge);
                             Movlt m = new Movlt("ccr", compResultRegister);
                             n2.getInstructions().add(m);
                             n = n2;
                          

                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // Control.g:210:5: ^( GT n1= expression[predNode] n2= expression[n1] )
                    {
                    match(input,GT,FOLLOW_GT_in_expression491); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_expression495);
                    n1=expression(predNode);

                    state._fsp--;


                             String p1 = getLastTarget(n1);
                          
                    pushFollow(FOLLOW_expression_in_expression511);
                    n2=expression(n1);

                    state._fsp--;


                             String compResultRegister = "r" + registerCounter++;
                             Loadi li = new Loadi("0", compResultRegister);
                             n2.getInstructions().add(li);
                             String p2 = getLastTarget(n2);
                             Comp c = new Comp(p1, p2, "ccr");
                             n2.getInstructions().add(c);
                             //Cbrle cle = new Cbrle("ccr", "L*", "L*");
                             //n2.getInstructions().add(cle);
                             Movgt m = new Movgt("ccr", compResultRegister);
                             n2.getInstructions().add(m);
                             n = n2;
                          

                    match(input, Token.UP, null); 

                    }
                    break;
                case 7 :
                    // Control.g:229:5: ^( LE n1= expression[predNode] n2= expression[n1] )
                    {
                    match(input,LE,FOLLOW_LE_in_expression535); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_expression539);
                    n1=expression(predNode);

                    state._fsp--;


                             String p1 = getLastTarget(n1);
                          
                    pushFollow(FOLLOW_expression_in_expression555);
                    n2=expression(n1);

                    state._fsp--;


                             String compResultRegister = "r" + registerCounter++;
                             Loadi li = new Loadi("0", compResultRegister);
                             n2.getInstructions().add(li);
                             String p2 = getLastTarget(n2);
                             Comp c = new Comp(p1, p2, "ccr");
                             n2.getInstructions().add(c);
                             //Cbrgt cgt = new Cbrgt("ccr", "L*", "L*");
                             //n2.getInstructions().add(cgt);
                             Movle m = new Movle("ccr", compResultRegister);
                             n2.getInstructions().add(m);
                             n = n2;
                          

                    match(input, Token.UP, null); 

                    }
                    break;
                case 8 :
                    // Control.g:248:5: ^( GE n1= expression[predNode] n2= expression[n1] )
                    {
                    match(input,GE,FOLLOW_GE_in_expression579); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_expression583);
                    n1=expression(predNode);

                    state._fsp--;


                             String p1 = getLastTarget(n1);
                          
                    pushFollow(FOLLOW_expression_in_expression599);
                    n2=expression(n1);

                    state._fsp--;


                             String compResultRegister = "r" + registerCounter++;
                             Loadi li = new Loadi("0", compResultRegister);
                             n2.getInstructions().add(li);
                             String p2 = getLastTarget(n2);
                             Comp c = new Comp(p1, p2, "ccr");
                             n2.getInstructions().add(c);
                             //Cbrlt clt = new Cbrlt("ccr", "L*", "L*");
                             //n2.getInstructions().add(clt);
                             Movge m = new Movge("ccr", compResultRegister);
                             n2.getInstructions().add(m);
                             n = n2;
                          

                    match(input, Token.UP, null); 

                    }
                    break;
                case 9 :
                    // Control.g:268:5: ^( PLUS n1= expression[predNode] n2= expression[n1] )
                    {
                    match(input,PLUS,FOLLOW_PLUS_in_expression627); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_expression631);
                    n1=expression(predNode);

                    state._fsp--;


                             String p1 = getLastTarget(n1);
                          
                    pushFollow(FOLLOW_expression_in_expression659);
                    n2=expression(n1);

                    state._fsp--;


                             String p2 = getLastTarget(n2);
                             Add newAdd = new Add(p1, p2, "r" + registerCounter++);
                             n2.getInstructions().add(newAdd);
                             n = n2;
                          

                    match(input, Token.UP, null); 

                    }
                    break;
                case 10 :
                    // Control.g:285:5: ^( MINUS n1= expression[predNode] n2= expression[predNode] )
                    {
                    match(input,MINUS,FOLLOW_MINUS_in_expression699); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_expression703);
                    n1=expression(predNode);

                    state._fsp--;


                             String m1 = getLastTarget(n1);      
                          
                    pushFollow(FOLLOW_expression_in_expression729);
                    n2=expression(predNode);

                    state._fsp--;


                             String m2 = getLastTarget(n2);
                             Sub newSub = new Sub(m1, m2, "r" + registerCounter++);
                             n2.getInstructions().add(newSub);
                             n = n2;
                          

                    match(input, Token.UP, null); 

                    }
                    break;
                case 11 :
                    // Control.g:302:5: ^( TIMES n1= expression[predNode] n2= expression[predNode] )
                    {
                    match(input,TIMES,FOLLOW_TIMES_in_expression762); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_expression774);
                    n1=expression(predNode);

                    state._fsp--;


                            String m1 = getLastTarget(n1);
                          
                    pushFollow(FOLLOW_expression_in_expression800);
                    n2=expression(predNode);

                    state._fsp--;


                             String m2 = getLastTarget(n2);
                             Mult newMult = new Mult(m1, m2, "r" + registerCounter++);
                             n2.getInstructions().add(newMult);
                             n = n2;
                          

                    match(input, Token.UP, null); 

                    }
                    break;
                case 12 :
                    // Control.g:321:5: ^( DIVIDE n1= expression[predNode] n2= expression[predNode] )
                    {
                    match(input,DIVIDE,FOLLOW_DIVIDE_in_expression834); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_expression846);
                    n1=expression(predNode);

                    state._fsp--;


                            String d1 = getLastTarget(n1);
                          
                    pushFollow(FOLLOW_expression_in_expression871);
                    n2=expression(predNode);

                    state._fsp--;


                             String d2 = getLastTarget(n2);
                             Div newDiv = new Div(d1, d2, "r" + registerCounter++);
                             n2.getInstructions().add(newDiv);
                             n = n2;
                          

                    match(input, Token.UP, null); 

                    }
                    break;
                case 13 :
                    // Control.g:340:5: ^( NOT expression[predNode] )
                    {
                    match(input,NOT,FOLLOW_NOT_in_expression904); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_expression906);
                    expression(predNode);

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 14 :
                    // Control.g:341:5: ^( NEW id= ID )
                    {
                    match(input,NEW,FOLLOW_NEW_in_expression915); 

                    match(input, Token.DOWN, null); 
                    id=(CommonTree)match(input,ID,FOLLOW_ID_in_expression919); 

                    match(input, Token.UP, null); 

                            New newNew = new New((id!=null?id.getText():null), "r"+registerCounter++);
                            n.getInstructions().add(newNew);
                        

                    }
                    break;
                case 15 :
                    // Control.g:346:5: ^( DOT expression[predNode] expression[predNode] )
                    {
                    match(input,DOT,FOLLOW_DOT_in_expression933); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_expression935);
                    expression(predNode);

                    state._fsp--;

                    pushFollow(FOLLOW_expression_in_expression938);
                    expression(predNode);

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 16 :
                    // Control.g:347:5: ^( INVOKE id= ID current= args[predNode] )
                    {
                    match(input,INVOKE,FOLLOW_INVOKE_in_expression947); 

                    match(input, Token.DOWN, null); 
                    id=(CommonTree)match(input,ID,FOLLOW_ID_in_expression951); 

                              if (printNodeAdds)
                                 System.out.println("invoke " + (id!=null?id.getText():null));
                          
                    pushFollow(FOLLOW_args_in_expression968);
                    current=args(predNode);

                    state._fsp--;

                      
                             Variable f = g_stable.getVariable(currentScope, (id!=null?id.getText():null));
                             int numP = f.getNumParam();
                             Call newCall = new Call((id!=null?id.getText():null), "" + numP);
                             current.getInstructions().add(newCall);
                             Loadret lr = new Loadret("r" + registerCounter++);
                             current.getInstructions().add(lr);
                          

                    match(input, Token.UP, null); 

                    }
                    break;
                case 17 :
                    // Control.g:367:5: tr= TRUE
                    {
                    tr=(CommonTree)match(input,TRUE,FOLLOW_TRUE_in_expression1025); 

                            Mov newMov = new Mov((tr!=null?tr.getText():null), "r"+registerCounter++);
                            n.getInstructions().add(newMov);
                        

                    }
                    break;
                case 18 :
                    // Control.g:372:5: fa= FALSE
                    {
                    fa=(CommonTree)match(input,FALSE,FOLLOW_FALSE_in_expression1039); 

                            Mov newMov = new Mov((fa!=null?fa.getText():null), "r"+registerCounter++);
                            n.getInstructions().add(newMov);
                        

                    }
                    break;
                case 19 :
                    // Control.g:377:5: inte= INTEGER
                    {
                    inte=(CommonTree)match(input,INTEGER,FOLLOW_INTEGER_in_expression1053); 

                            Loadi newLoadi = new Loadi((inte!=null?inte.getText():null), "r" + registerCounter++);
                            n.getInstructions().add(newLoadi);
                        

                    }
                    break;
                case 20 :
                    // Control.g:382:5: id= ID
                    {
                    id=(CommonTree)match(input,ID,FOLLOW_ID_in_expression1067); 

                            Mov newMov = new Mov(getRegister((id!=null?id.getText():null)), "r" + registerCounter++);
                            //Mov newMov = new Mov(getRegister((id!=null?id.getText():null)), getRegister((id!=null?id.getText():null)));
                            n.getInstructions().add(newMov);
                        

                    }
                    break;
                case 21 :
                    // Control.g:388:5: en= ENDL
                    {
                    en=(CommonTree)match(input,ENDL,FOLLOW_ENDL_in_expression1081); 

                            Mov newMov = new Mov("endl", "r"+registerCounter++);
                            n.getInstructions().add(newMov);
                        

                    }
                    break;
                case 22 :
                    // Control.g:393:5: NULL
                    {
                    match(input,NULL,FOLLOW_NULL_in_expression1093); 

                            Mov newMov = new Mov("null", "r"+registerCounter++);
                            n.getInstructions().add(newMov);
                        

                    }
                    break;
                case 23 :
                    // Control.g:398:5: node= stmts[predNode]
                    {
                    pushFollow(FOLLOW_stmts_in_expression1109);
                    node=stmts(predNode);

                    state._fsp--;


                            n = node;
                            //System.out.println("In EXPRESSION, n type: " + n.getNodeType());
                        

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "expression"


    // $ANTLR start "lvalue"
    // Control.g:405:1: lvalue[Node predNode] returns [Node n = predNode] : ( ^( DOT lvalue[predNode] id= ID ) | id= ID );
    public final Node lvalue(Node predNode) throws RecognitionException {
        Node n =  predNode;

        CommonTree id=null;

        try {
            // Control.g:406:4: ( ^( DOT lvalue[predNode] id= ID ) | id= ID )
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==DOT) ) {
                alt7=1;
            }
            else if ( (LA7_0==ID) ) {
                alt7=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1 :
                    // Control.g:406:6: ^( DOT lvalue[predNode] id= ID )
                    {
                    match(input,DOT,FOLLOW_DOT_in_lvalue1135); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_lvalue_in_lvalue1137);
                    lvalue(predNode);

                    state._fsp--;

                    id=(CommonTree)match(input,ID,FOLLOW_ID_in_lvalue1142); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // Control.g:407:6: id= ID
                    {
                    id=(CommonTree)match(input,ID,FOLLOW_ID_in_lvalue1153); 

                             Mov newMov = new Mov(getRegister((id!=null?id.getText():null)), getRegister((id!=null?id.getText():null)));
                             n.getInstructions().add(newMov);
                          

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "lvalue"


    // $ANTLR start "stmt"
    // Control.g:414:1: stmt[Node predNode] returns [Node n = predNode] : ( ^( BLOCK node= stmts[predNode] ) | ^( PRINT (current= expression[predNode] )* ) | ^( READ current= lvalue[predNode] ) | ^( IF e= expression[predNode] th= stmt[thenBlock] (el= stmt[elseBlock] )? ) | ^( WHILE e= expression[predNode] whileBodyAfter= stmt[whileBodyStart] e= expression[whileBodyAfter] ) | ^( DELETE current= expression[predNode] ) | ^( RETURN (current= expression[predNode] )? ) | ^( INVOKE id= ID current= args[predNode] ) | ^( ASSIGN current= expression[predNode] lv= lvalue[current] ) );
    public final Node stmt(Node predNode) throws RecognitionException {
        Node n =  predNode;

        CommonTree id=null;
        Node node = null;

        Node current = null;

        Node e = null;

        Node th = null;

        Node el = null;

        Node whileBodyAfter = null;

        Node lv = null;


        try {
            // Control.g:415:5: ( ^( BLOCK node= stmts[predNode] ) | ^( PRINT (current= expression[predNode] )* ) | ^( READ current= lvalue[predNode] ) | ^( IF e= expression[predNode] th= stmt[thenBlock] (el= stmt[elseBlock] )? ) | ^( WHILE e= expression[predNode] whileBodyAfter= stmt[whileBodyStart] e= expression[whileBodyAfter] ) | ^( DELETE current= expression[predNode] ) | ^( RETURN (current= expression[predNode] )? ) | ^( INVOKE id= ID current= args[predNode] ) | ^( ASSIGN current= expression[predNode] lv= lvalue[current] ) )
            int alt11=9;
            switch ( input.LA(1) ) {
            case BLOCK:
                {
                alt11=1;
                }
                break;
            case PRINT:
                {
                alt11=2;
                }
                break;
            case READ:
                {
                alt11=3;
                }
                break;
            case IF:
                {
                alt11=4;
                }
                break;
            case WHILE:
                {
                alt11=5;
                }
                break;
            case DELETE:
                {
                alt11=6;
                }
                break;
            case RETURN:
                {
                alt11=7;
                }
                break;
            case INVOKE:
                {
                alt11=8;
                }
                break;
            case ASSIGN:
                {
                alt11=9;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;
            }

            switch (alt11) {
                case 1 :
                    // Control.g:415:6: ^( BLOCK node= stmts[predNode] )
                    {
                    match(input,BLOCK,FOLLOW_BLOCK_in_stmt1180); 


                                
                                if (printNodeAdds)
                                   System.out.println("block");
                                
                            

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_stmts_in_stmt1201);
                    node=stmts(predNode);

                    state._fsp--;

                    n = node;

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // Control.g:428:6: ^( PRINT (current= expression[predNode] )* )
                    {
                    match(input,PRINT,FOLLOW_PRINT_in_stmt1247); 


                                
                                if (printNodeAdds)
                                   System.out.println("print");
                                
                              

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // Control.g:435:5: (current= expression[predNode] )*
                        loop8:
                        do {
                            int alt8=2;
                            int LA8_0 = input.LA(1);

                            if ( (LA8_0==ENDL||(LA8_0>=TRUE && LA8_0<=NULL)||(LA8_0>=STMTS && LA8_0<=INVOKE)||(LA8_0>=DOT && LA8_0<=INTEGER)) ) {
                                alt8=1;
                            }


                            switch (alt8) {
                        	case 1 :
                        	    // Control.g:435:6: current= expression[predNode]
                        	    {
                        	    pushFollow(FOLLOW_expression_in_stmt1269);
                        	    current=expression(predNode);

                        	    state._fsp--;


                        	        
                        	                Print newPrint = new Print(getLastTarget(current));
                        	                current.getInstructions().add(newPrint);
                        	              
                        	              

                        	    }
                        	    break;

                        	default :
                        	    break loop8;
                            }
                        } while (true);


                                 // this block of code detects whether a Println is needed
                                 Instruction i, j, k;
                                 int numC = current.getInstructions().size();
                                 int counter = numC - 3;
                                 boolean found = false;
                                 if (numC > 2) {
                                    i = current.getInstructions().get(numC - 2);
                                    k = current.getInstructions().get(numC - 1);
                                    if (i.getArg1().equals("endl") && k instanceof Print) {
                                       while (!found && counter >= 0) {
                                          j = current.getInstructions().get(counter);
                                          if (j instanceof Print) {
                                             Println pl = new Println(j.getTarget());
                                             current.getInstructions().remove(numC - 1);
                                             current.getInstructions().remove(numC - 2);
                                             current.getInstructions().remove(counter);
                                             current.getInstructions().add(pl);
                                          }
                                          counter--;           
                                       }            
                                    }         
                                 }
                                 n = current;
                              

                        match(input, Token.UP, null); 
                    }

                    }
                    break;
                case 3 :
                    // Control.g:474:6: ^( READ current= lvalue[predNode] )
                    {
                    match(input,READ,FOLLOW_READ_in_stmt1342); 


                                
                                if (printNodeAdds)
                                   System.out.println("read");
                                
                              

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_lvalue_in_stmt1364);
                    current=lvalue(predNode);

                    state._fsp--;


                             String name = getVariableNameFromRegister(getLastTarget(current));
                             Addi newAddi = new Addi("rarp", name, "r" + registerCounter++);
                             current.getInstructions().add(newAddi);
                             String readTarget = getLastTarget(current);
                             Read newRead = new Read(readTarget);
                             current.getInstructions().add(newRead);
                             Loadai lai = new Loadai("rarp", name, getRegister(name));
                             current.getInstructions().add(lai);
                             n = current;
                          

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // Control.g:500:6: ^( IF e= expression[predNode] th= stmt[thenBlock] (el= stmt[elseBlock] )? )
                    {
                    match(input,IF,FOLLOW_IF_in_stmt1422); 


                                if (printNodeAdds)
                                   System.out.println("if (expression)");
                                predNode.setNodeType(NodeType.IF); // this line makes the graph print out accurately
                              

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_stmt1459);
                    e=expression(predNode);

                    state._fsp--;


                                String ifGuardResult = getLastTarget(e);
                                int thenBlockID = currentIDNum++;
                                int elseBlockID = currentIDNum++;
                                int ifJoinID = currentIDNum++;
                                Node thenBlock = new Node(NodeType.THEN, (thenBlockID), "THEN");
                                Node elseBlock = new Node(NodeType.ELSE, (elseBlockID), "ELSE");
                                Node ifJoin = new Node(NodeType.IF_JOIN, (ifJoinID), "IF_JOIN");
                                
                                Brnz b = new Brnz(ifGuardResult, "L" + thenBlockID, "L" + elseBlockID);
                                e.getInstructions().add(b); 
                                
                                e.getSuccNodes().add(thenBlock);
                                thenBlock.getPredNodes().add(e);
                                if (printNodeAdds) {
                                   System.out.println("true: jump from L" + e.getId() + " to L" + thenBlock.getId()); 
                                   System.out.println("L" + thenBlock.getId() + " THEN");
                                }      
                              
                    pushFollow(FOLLOW_stmt_in_stmt1491);
                    th=stmt(thenBlock);

                    state._fsp--;


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
                              

                               //Node elseBlock = new Node(NodeType.ELSE, (currentIDNum++), "ELSE");
                                 e.getSuccNodes().add(elseBlock);
                                 elseBlock.getPredNodes().add(e);
                               if (printNodeAdds) {
                                  System.out.println("false: jump from L" + e.getId() + " to L" + elseBlock.getId()); 
                                  System.out.println("L" + elseBlock.getId() + " ELSE");
                               }  
                               
                               {el = null;} 
                              
                    // Control.g:562:5: (el= stmt[elseBlock] )?
                    int alt9=2;
                    int LA9_0 = input.LA(1);

                    if ( (LA9_0==PRINT||(LA9_0>=READ && LA9_0<=IF)||(LA9_0>=WHILE && LA9_0<=RETURN)||LA9_0==BLOCK||LA9_0==INVOKE||LA9_0==ASSIGN) ) {
                        alt9=1;
                    }
                    switch (alt9) {
                        case 1 :
                            // Control.g:562:6: el= stmt[elseBlock]
                            {
                            pushFollow(FOLLOW_stmt_in_stmt1558);
                            el=stmt(elseBlock);

                            state._fsp--;


                            }
                            break;

                    }


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
                                
                               
                              

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // Control.g:602:6: ^( WHILE e= expression[predNode] whileBodyAfter= stmt[whileBodyStart] e= expression[whileBodyAfter] )
                    {
                    match(input,WHILE,FOLLOW_WHILE_in_stmt1618); 


                                if (printNodeAdds)
                                   System.out.println("while exp1");
                                predNode.setNodeType(NodeType.WHILE); // this line makes the graph print out accurately
                             

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_stmt1648);
                    e=expression(predNode);

                    state._fsp--;


                                String guardResult = getLastTarget(e);


                                if (printNodeAdds)
                                   System.out.println("L" + e.getId() + " WHILE_BODY exp2");
                                int whileBodyStartID = currentIDNum++;
                                int whileJoinID = currentIDNum++;
                                Node whileBodyStart = new Node(NodeType.WHILE_BODY, (whileBodyStartID), "WHILE_BODY");
                                Node whileJoin = new Node(NodeType.WHILE_JOIN, (whileJoinID), "WHILE_JOIN");
                                
                                Brnz b = new Brnz(guardResult, "L" + whileBodyStartID, "L" + whileJoinID);
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
                             
                    pushFollow(FOLLOW_stmt_in_stmt1678);
                    whileBodyAfter=stmt(whileBodyStart);

                    state._fsp--;

                    //This is the whileJoin block
                                if (printNodeAdds)
                                   System.out.println("L" + whileJoin.getId() + " while_after"); 
                                whileJoin.getPredNodes().add(whileBodyAfter);  
                                whileJoin.getPredNodes().add(e);
                                whileBodyAfter.setBackEdgeTarget(whileBodyStart);
                                whileBodyAfter.getSuccNodes().add(whileJoin);
                                n = whileJoin;
                             
                    pushFollow(FOLLOW_expression_in_stmt1709);
                    e=expression(whileBodyAfter);

                    state._fsp--;


                             guardResult = getLastTarget(e);
                             Brnz bn = new Brnz(guardResult, "L" + whileBodyStartID, "L" + whileJoinID);
                             e.getInstructions().add(bn);
                             n = whileJoin;
                          

                    match(input, Token.UP, null); 
                    System.out.println("while-join node type = " + n.getNodeType());

                    }
                    break;
                case 6 :
                    // Control.g:664:6: ^( DELETE current= expression[predNode] )
                    {
                    match(input,DELETE,FOLLOW_DELETE_in_stmt1775); 


                              
                              if (printNodeAdds)
                                 System.out.println("delete");
                              
                            

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_stmt1803);
                    current=expression(predNode);

                    state._fsp--;


                               String reg = getLastTarget(current);
                               Del newDel = new Del(reg);
                               current.getInstructions().add(newDel);
                               n = current;
                            

                    match(input, Token.UP, null); 

                    }
                    break;
                case 7 :
                    // Control.g:683:6: ^( RETURN (current= expression[predNode] )? )
                    {
                    match(input,RETURN,FOLLOW_RETURN_in_stmt1844); 

                    current = null;

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // Control.g:683:33: (current= expression[predNode] )?
                        int alt10=2;
                        int LA10_0 = input.LA(1);

                        if ( (LA10_0==ENDL||(LA10_0>=TRUE && LA10_0<=NULL)||(LA10_0>=STMTS && LA10_0<=INVOKE)||(LA10_0>=DOT && LA10_0<=INTEGER)) ) {
                            alt10=1;
                        }
                        switch (alt10) {
                            case 1 :
                                // Control.g:683:34: current= expression[predNode]
                                {
                                pushFollow(FOLLOW_expression_in_stmt1851);
                                current=expression(predNode);

                                state._fsp--;


                                }
                                break;

                        }


                        match(input, Token.UP, null); 
                    }

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
                              n = last; 
                            

                    }
                    break;
                case 8 :
                    // Control.g:715:6: ^( INVOKE id= ID current= args[predNode] )
                    {
                    match(input,INVOKE,FOLLOW_INVOKE_in_stmt1898); 

                    match(input, Token.DOWN, null); 
                    id=(CommonTree)match(input,ID,FOLLOW_ID_in_stmt1902); 
                          
                              if (printNodeAdds)
                                 System.out.println("invoke " + (id!=null?id.getText():null));
                              
                            
                    pushFollow(FOLLOW_args_in_stmt1922);
                    current=args(predNode);

                    state._fsp--;

                      
                             Variable f = g_stable.getVariable(currentScope, (id!=null?id.getText():null));
                             int numP = f.getNumParam();
                             Call newCall = new Call((id!=null?id.getText():null), "" + numP);
                             current.getInstructions().add(newCall);
                             Loadret lr = new Loadret("r" + registerCounter++);
                             current.getInstructions().add(lr);
                          

                    match(input, Token.UP, null); 

                    }
                    break;
                case 9 :
                    // Control.g:737:6: ^( ASSIGN current= expression[predNode] lv= lvalue[current] )
                    {
                    match(input,ASSIGN,FOLLOW_ASSIGN_in_stmt2012); 

                          
                              if (printNodeAdds)
                                 System.out.println("assign");
                              System.out.println("assign");
                            

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expression_in_stmt2031);
                    current=expression(predNode);

                    state._fsp--;


                             String r = getLastTarget(current);
                             System.out.println("rValue = " + r);
                            
                    pushFollow(FOLLOW_lvalue_in_stmt2055);
                    lv=lvalue(current);

                    state._fsp--;


                              String l = getLastTarget(lv);
                              System.out.println("lValue = " + l);
                              n = lv;
                              Mov newMov = new Mov(r, l);
                              n.getInstructions().add(newMov);
                            

                    match(input, Token.UP, null); 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "stmt"


    // $ANTLR start "args"
    // Control.g:760:1: args[Node predNode] returns [Node n = predNode] : ^( ARGS (aNode= expression[predNode] )* ) ;
    public final Node args(Node predNode) throws RecognitionException {
        Node n =  predNode;

        Node aNode = null;


        try {
            // Control.g:762:4: ( ^( ARGS (aNode= expression[predNode] )* ) )
            // Control.g:762:5: ^( ARGS (aNode= expression[predNode] )* )
            {
            match(input,ARGS,FOLLOW_ARGS_in_args2091); 

             int argCounter = 0;

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // Control.g:766:4: (aNode= expression[predNode] )*
                loop12:
                do {
                    int alt12=2;
                    int LA12_0 = input.LA(1);

                    if ( (LA12_0==ENDL||(LA12_0>=TRUE && LA12_0<=NULL)||(LA12_0>=STMTS && LA12_0<=INVOKE)||(LA12_0>=DOT && LA12_0<=INTEGER)) ) {
                        alt12=1;
                    }


                    switch (alt12) {
                	case 1 :
                	    // Control.g:766:5: aNode= expression[predNode]
                	    {
                	    pushFollow(FOLLOW_expression_in_args2118);
                	    aNode=expression(predNode);

                	    state._fsp--;


                	            Mov newMov = new Mov(getLastTarget(aNode), "r" + registerCounter++);
                	            aNode.getInstructions().add(newMov);
                	            Storeoutargument soa = new Storeoutargument(getLastTarget(aNode), "" + argCounter++);
                	            aNode.getInstructions().add(soa);
                	            n = aNode;
                	          

                	    }
                	    break;

                	default :
                	    break loop12;
                    }
                } while (true);


                match(input, Token.UP, null); 
            }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "args"


    // $ANTLR start "stmts"
    // Control.g:779:1: stmts[Node predNode] returns [Node n = predNode] : ^( STMTS (newNode= stmt[predNode] )* ) ;
    public final Node stmts(Node predNode) throws RecognitionException {
        Node n =  predNode;

        Node newNode = null;


        try {
            // Control.g:780:4: ( ^( STMTS (newNode= stmt[predNode] )* ) )
            // Control.g:780:5: ^( STMTS (newNode= stmt[predNode] )* )
            {
            match(input,STMTS,FOLLOW_STMTS_in_stmts2163); 

              
                  if (printNodeAdds)
                     System.out.println("stmts");

               

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // Control.g:788:4: (newNode= stmt[predNode] )*
                loop13:
                do {
                    int alt13=2;
                    int LA13_0 = input.LA(1);

                    if ( (LA13_0==PRINT||(LA13_0>=READ && LA13_0<=IF)||(LA13_0>=WHILE && LA13_0<=RETURN)||LA13_0==BLOCK||LA13_0==INVOKE||LA13_0==ASSIGN) ) {
                        alt13=1;
                    }


                    switch (alt13) {
                	case 1 :
                	    // Control.g:788:5: newNode= stmt[predNode]
                	    {
                	    pushFollow(FOLLOW_stmt_in_stmts2189);
                	    newNode=stmt(predNode);

                	    state._fsp--;


                	          //System.out.println("In STMTS newNode type: " + newNode.getNodeType());
                	          predNode = newNode;     
                	       
                	       

                	    }
                	    break;

                	default :
                	    break loop13;
                    }
                } while (true);


                match(input, Token.UP, null); 
            }

                  n = predNode;
                  //System.out.println("In STMTS n type: " + n.getNodeType());
               

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "stmts"


    // $ANTLR start "funcs"
    // Control.g:804:1: funcs : ^( FUNCS ( fun )* ) ;
    public final void funcs() throws RecognitionException {
        try {
            // Control.g:804:6: ( ^( FUNCS ( fun )* ) )
            // Control.g:805:5: ^( FUNCS ( fun )* )
            {
            match(input,FUNCS,FOLLOW_FUNCS_in_funcs2232); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // Control.g:805:13: ( fun )*
                loop14:
                do {
                    int alt14=2;
                    int LA14_0 = input.LA(1);

                    if ( (LA14_0==FUN) ) {
                        alt14=1;
                    }


                    switch (alt14) {
                	case 1 :
                	    // Control.g:805:13: fun
                	    {
                	    pushFollow(FOLLOW_fun_in_funcs2234);
                	    fun();

                	    state._fsp--;


                	    }
                	    break;

                	default :
                	    break loop14;
                    }
                } while (true);


                match(input, Token.UP, null); 
            }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "funcs"


    // $ANTLR start "fun"
    // Control.g:808:1: fun : ^( FUN id= ID params rettype decls current= stmts[head] ) ;
    public final void fun() throws RecognitionException {
        CommonTree id=null;
        Node current = null;


        try {
            // Control.g:808:4: ( ^( FUN id= ID params rettype decls current= stmts[head] ) )
            // Control.g:809:5: ^( FUN id= ID params rettype decls current= stmts[head] )
            {
            match(input,FUN,FOLLOW_FUN_in_fun2249); 

            match(input, Token.DOWN, null); 
            id=(CommonTree)match(input,ID,FOLLOW_ID_in_fun2253); 

                    currentScope = (id!=null?id.getText():null);
                    registerCounter = 0;
                    Node head = new Node(NodeType.ENTRY, (currentIDNum++), "Entry");
                    head.setFunctionName((id!=null?id.getText():null));
                    functions.add(head);
                    head.setLocals(g_stable.gatherVariablesInScope((id!=null?id.getText():null)));
                    head.setRegisterMap(buildRegisterMap(head.getLocals()));
                    localRegisterMap = head.getRegisterMap(); 
                    System.out.println("After mapping vars for function " + (id!=null?id.getText():null) + ", the reg count is " + registerCounter);
                    funcNames.add((id!=null?id.getText():null));
                    //Node firstBlock = new Node(NodeType.BLOCK, (currentIDNum++), "Block");
                    //head.getSuccNodes().add(firstBlock);
                    //firstBlock.getPredNodes().add(head);
                    //Jumpi newJumpi = new Jumpi("L"+firstBlock.getId());
                    //head.getInstructions().add(newJumpi);
                    last = new Node(NodeType.EXIT, (currentIDNum++), "Exit");
              /*      if (printNodeAdds)
                       System.out.println("\nL" + head.getId() + " HEAD Node for function " + (id!=null?id.getText():null));
                       System.out.println("jump from L" + head.getId() + " to L" + firstBlock.getId()); 
                       System.out.println("L" + firstBlock.getId() + " start new Block ");   */     
                  
            pushFollow(FOLLOW_params_in_fun2284);
            params();

            state._fsp--;


                     Variable f = g_stable.getVariable("global", (id!=null?id.getText():null));
                     int numP = f.getNumParam();
                     int count = 0;
                     System.out.println("numP = " + numP);
                     for (String p : f.getParams()) {
                        System.out.println(p);
                        Loadinargument lia = new Loadinargument(p, count + "", getRegister(p));
                      //  firstBlock.getInstructions().add(lia);
                        head.getInstructions().add(lia);
                        count++;
                     }
                  
            pushFollow(FOLLOW_rettype_in_fun2322);
            rettype();

            state._fsp--;

            pushFollow(FOLLOW_decls_in_fun2324);
            decls();

            state._fsp--;

            pushFollow(FOLLOW_stmts_in_fun2328);
            current=stmts(head);

            state._fsp--;

               
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
                    
                    
                    if (printNodeAdds){
                        if(current.getNodeType() != NodeType.EXIT)
                            System.out.println("jump from L" + current.getId() + " to L" + last.getId()); 
                        System.out.println("L" + last.getId() + " EXIT Node for function " + (id!=null?id.getText():null));      
                    }
                  

            match(input, Token.UP, null); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "fun"


    // $ANTLR start "params"
    // Control.g:880:1: params : ^( PARAMS ( decl )* ) ;
    public final void params() throws RecognitionException {
        try {
            // Control.g:880:7: ( ^( PARAMS ( decl )* ) )
            // Control.g:881:5: ^( PARAMS ( decl )* )
            {
            match(input,PARAMS,FOLLOW_PARAMS_in_params2372); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // Control.g:881:14: ( decl )*
                loop15:
                do {
                    int alt15=2;
                    int LA15_0 = input.LA(1);

                    if ( (LA15_0==DECL) ) {
                        alt15=1;
                    }


                    switch (alt15) {
                	case 1 :
                	    // Control.g:881:14: decl
                	    {
                	    pushFollow(FOLLOW_decl_in_params2374);
                	    decl();

                	    state._fsp--;


                	    }
                	    break;

                	default :
                	    break loop15;
                    }
                } while (true);


                match(input, Token.UP, null); 
            }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "params"


    // $ANTLR start "rettype"
    // Control.g:884:1: rettype : ^( RETTYPE type ) ;
    public final void rettype() throws RecognitionException {
        try {
            // Control.g:884:8: ( ^( RETTYPE type ) )
            // Control.g:885:5: ^( RETTYPE type )
            {
            match(input,RETTYPE,FOLLOW_RETTYPE_in_rettype2389); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_type_in_rettype2391);
            type();

            state._fsp--;


            match(input, Token.UP, null); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "rettype"


    // $ANTLR start "construct"
    // Control.g:889:1: construct[StructTypes stypes, SymbolTable stable] returns [ArrayList<Node> f = null] : ^( PROGRAM ( types ) decls funcs ) ;
    public final ArrayList<Node> construct(StructTypes stypes, SymbolTable stable) throws RecognitionException {
        ArrayList<Node> f =  null;


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
            
        try {
            // Control.g:902:4: ( ^( PROGRAM ( types ) decls funcs ) )
            // Control.g:902:6: ^( PROGRAM ( types ) decls funcs )
            {
            match(input,PROGRAM,FOLLOW_PROGRAM_in_construct2421); 

            match(input, Token.DOWN, null); 
            // Control.g:902:16: ( types )
            // Control.g:902:17: types
            {
            pushFollow(FOLLOW_types_in_construct2424);
            types();

            state._fsp--;


            }

            pushFollow(FOLLOW_decls_in_construct2427);
            decls();

            state._fsp--;

            pushFollow(FOLLOW_funcs_in_construct2429);
            funcs();

            state._fsp--;


            match(input, Token.UP, null); 
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

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return f;
    }
    // $ANTLR end "construct"

    // Delegated rules


    protected DFA3 dfa3 = new DFA3(this);
    static final String DFA3_eotS =
        "\13\uffff";
    static final String DFA3_eofS =
        "\13\uffff";
    static final String DFA3_minS =
        "\1\4\1\2\5\uffff\1\4\3\uffff";
    static final String DFA3_maxS =
        "\1\70\1\2\5\uffff\1\6\3\uffff";
    static final String DFA3_acceptS =
        "\2\uffff\1\4\1\5\1\6\1\7\1\10\1\uffff\1\1\1\2\1\3";
    static final String DFA3_specialS =
        "\13\uffff}>";
    static final String[] DFA3_transitionS = {
            "\1\4\1\2\1\3\1\uffff\1\6\16\uffff\1\1\40\uffff\1\5",
            "\1\7",
            "",
            "",
            "",
            "",
            "",
            "\1\12\1\10\1\11",
            "",
            "",
            ""
    };

    static final short[] DFA3_eot = DFA.unpackEncodedString(DFA3_eotS);
    static final short[] DFA3_eof = DFA.unpackEncodedString(DFA3_eofS);
    static final char[] DFA3_min = DFA.unpackEncodedStringToUnsignedChars(DFA3_minS);
    static final char[] DFA3_max = DFA.unpackEncodedStringToUnsignedChars(DFA3_maxS);
    static final short[] DFA3_accept = DFA.unpackEncodedString(DFA3_acceptS);
    static final short[] DFA3_special = DFA.unpackEncodedString(DFA3_specialS);
    static final short[][] DFA3_transition;

    static {
        int numStates = DFA3_transitionS.length;
        DFA3_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA3_transition[i] = DFA.unpackEncodedString(DFA3_transitionS[i]);
        }
    }

    class DFA3 extends DFA {

        public DFA3(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 3;
            this.eot = DFA3_eot;
            this.eof = DFA3_eof;
            this.min = DFA3_min;
            this.max = DFA3_max;
            this.accept = DFA3_accept;
            this.special = DFA3_special;
            this.transition = DFA3_transition;
        }
        public String getDescription() {
            return "107:1: type : ( ^( TYPE INT ) | ^( TYPE BOOL ) | ^( TYPE ^( STRUCT type ) ) | INT | BOOL | ^( STRUCT type ) | ID | VOID );";
        }
    }
 

    public static final BitSet FOLLOW_TYPES_in_types61 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_struct_in_types63 = new BitSet(new long[]{0x0000000000000018L});
    public static final BitSet FOLLOW_STRUCT_in_struct79 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_struct81 = new BitSet(new long[]{0x0000000004000008L});
    public static final BitSet FOLLOW_decl_in_struct84 = new BitSet(new long[]{0x0000000004000008L});
    public static final BitSet FOLLOW_DECL_in_decl100 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_TYPE_in_decl103 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_type_in_decl105 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ID_in_decl108 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_TYPE_in_type123 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_INT_in_type125 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_TYPE_in_type135 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_BOOL_in_type137 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_TYPE_in_type147 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_STRUCT_in_type150 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_type_in_type152 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INT_in_type162 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BOOL_in_type170 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRUCT_in_type179 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_type_in_type181 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ID_in_type190 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VOID_in_type199 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DECLS_in_decls212 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_decllist_in_decls214 = new BitSet(new long[]{0x0000000008000008L});
    public static final BitSet FOLLOW_DECLLIST_in_decllist229 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_type_in_decllist231 = new BitSet(new long[]{0x0100000000000008L});
    public static final BitSet FOLLOW_ID_in_decllist233 = new BitSet(new long[]{0x0100000000000008L});
    public static final BitSet FOLLOW_AND_in_expression252 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression256 = new BitSet(new long[]{0x03FFFC01801E0400L});
    public static final BitSet FOLLOW_expression_in_expression277 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_OR_in_expression308 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression312 = new BitSet(new long[]{0x03FFFC01801E0400L});
    public static final BitSet FOLLOW_expression_in_expression338 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EQ_in_expression361 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression365 = new BitSet(new long[]{0x03FFFC01801E0400L});
    public static final BitSet FOLLOW_expression_in_expression381 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NE_in_expression404 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression408 = new BitSet(new long[]{0x03FFFC01801E0400L});
    public static final BitSet FOLLOW_expression_in_expression424 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LT_in_expression447 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression451 = new BitSet(new long[]{0x03FFFC01801E0400L});
    public static final BitSet FOLLOW_expression_in_expression467 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GT_in_expression491 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression495 = new BitSet(new long[]{0x03FFFC01801E0400L});
    public static final BitSet FOLLOW_expression_in_expression511 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LE_in_expression535 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression539 = new BitSet(new long[]{0x03FFFC01801E0400L});
    public static final BitSet FOLLOW_expression_in_expression555 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GE_in_expression579 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression583 = new BitSet(new long[]{0x03FFFC01801E0400L});
    public static final BitSet FOLLOW_expression_in_expression599 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PLUS_in_expression627 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression631 = new BitSet(new long[]{0x03FFFC01801E0400L});
    public static final BitSet FOLLOW_expression_in_expression659 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MINUS_in_expression699 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression703 = new BitSet(new long[]{0x03FFFC01801E0400L});
    public static final BitSet FOLLOW_expression_in_expression729 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_TIMES_in_expression762 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression774 = new BitSet(new long[]{0x03FFFC01801E0400L});
    public static final BitSet FOLLOW_expression_in_expression800 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DIVIDE_in_expression834 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression846 = new BitSet(new long[]{0x03FFFC01801E0400L});
    public static final BitSet FOLLOW_expression_in_expression871 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_in_expression904 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression906 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NEW_in_expression915 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_expression919 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DOT_in_expression933 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression935 = new BitSet(new long[]{0x03FFFC01801E0400L});
    public static final BitSet FOLLOW_expression_in_expression938 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INVOKE_in_expression947 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_expression951 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_args_in_expression968 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_TRUE_in_expression1025 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FALSE_in_expression1039 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTEGER_in_expression1053 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_expression1067 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ENDL_in_expression1081 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NULL_in_expression1093 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_stmts_in_expression1109 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOT_in_lvalue1135 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_lvalue_in_lvalue1137 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_ID_in_lvalue1142 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ID_in_lvalue1153 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BLOCK_in_stmt1180 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_stmts_in_stmt1201 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PRINT_in_stmt1247 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_stmt1269 = new BitSet(new long[]{0x03FFFC01801E0408L});
    public static final BitSet FOLLOW_READ_in_stmt1342 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_lvalue_in_stmt1364 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IF_in_stmt1422 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_stmt1459 = new BitSet(new long[]{0x000002014001DA00L});
    public static final BitSet FOLLOW_stmt_in_stmt1491 = new BitSet(new long[]{0x000002014001DA08L});
    public static final BitSet FOLLOW_stmt_in_stmt1558 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WHILE_in_stmt1618 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_stmt1648 = new BitSet(new long[]{0x000002014001DA00L});
    public static final BitSet FOLLOW_stmt_in_stmt1678 = new BitSet(new long[]{0x03FFFC01801E0400L});
    public static final BitSet FOLLOW_expression_in_stmt1709 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DELETE_in_stmt1775 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_stmt1803 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_RETURN_in_stmt1844 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_stmt1851 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INVOKE_in_stmt1898 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_stmt1902 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_args_in_stmt1922 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ASSIGN_in_stmt2012 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_stmt2031 = new BitSet(new long[]{0x0100040000000000L});
    public static final BitSet FOLLOW_lvalue_in_stmt2055 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ARGS_in_args2091 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_args2118 = new BitSet(new long[]{0x03FFFC01801E0408L});
    public static final BitSet FOLLOW_STMTS_in_stmts2163 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_stmt_in_stmts2189 = new BitSet(new long[]{0x000002014001DA08L});
    public static final BitSet FOLLOW_FUNCS_in_funcs2232 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_fun_in_funcs2234 = new BitSet(new long[]{0x0000000000000088L});
    public static final BitSet FOLLOW_FUN_in_fun2249 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_fun2253 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_params_in_fun2284 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_rettype_in_fun2322 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_decls_in_fun2324 = new BitSet(new long[]{0x03FFFC01801E0400L});
    public static final BitSet FOLLOW_stmts_in_fun2328 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PARAMS_in_params2372 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_decl_in_params2374 = new BitSet(new long[]{0x0000000004000008L});
    public static final BitSet FOLLOW_RETTYPE_in_rettype2389 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_type_in_rettype2391 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PROGRAM_in_construct2421 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_types_in_construct2424 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_decls_in_construct2427 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_funcs_in_construct2429 = new BitSet(new long[]{0x0000000000000008L});

}