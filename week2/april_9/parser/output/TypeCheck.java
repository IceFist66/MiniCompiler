// $ANTLR 3.3 Nov 30, 2010 12:45:30 /home/david/github/MiniCompiler/week2/april_9/parser/TypeCheck.g 2014-04-07 09:41:16

   import java.util.Map;
   import java.util.HashMap;
   import java.util.Vector;
   import java.util.Iterator;


import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

import org.antlr.runtime.debug.*;
import java.io.IOException;
public class TypeCheck extends DebugTreeParser {
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

    public static final String[] ruleNames = new String[] {
        "invalidRule", "verify"
    };
    public static final boolean[] decisionCanBacktrack = new boolean[] {
        false, // invalid decision
    };

     
        public int ruleLevel = 0;
        public int getRuleLevel() { return ruleLevel; }
        public void incRuleLevel() { ruleLevel++; }
        public void decRuleLevel() { ruleLevel--; }
        public TypeCheck(TreeNodeStream input) {
            this(input, DebugEventSocketProxy.DEFAULT_DEBUGGER_PORT, new RecognizerSharedState());
        }
        public TypeCheck(TreeNodeStream input, int port, RecognizerSharedState state) {
            super(input, state);
            DebugEventSocketProxy proxy =
                new DebugEventSocketProxy(this, port, input.getTreeAdaptor());
            setDebugListener(proxy);
            try {
                proxy.handshake();
            }
            catch (IOException ioe) {
                reportError(ioe);
            }
        }
    public TypeCheck(TreeNodeStream input, DebugEventListener dbg) {
        super(input, dbg, new RecognizerSharedState());

    }
    protected boolean evalPredicate(boolean result, String predicate) {
        dbg.semanticPredicate(result, predicate);
        return result;
    }


    public String[] getTokenNames() { return TypeCheck.tokenNames; }
    public String getGrammarFileName() { return "/home/david/github/MiniCompiler/week2/april_9/parser/TypeCheck.g"; }



    // $ANTLR start "verify"
    // /home/david/github/MiniCompiler/week2/april_9/parser/TypeCheck.g:17:1: verify : ;
    public final void verify() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "verify");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(17, 1);

        try {
            // /home/david/github/MiniCompiler/week2/april_9/parser/TypeCheck.g:17:8: ()
            dbg.enterAlt(1);

            // /home/david/github/MiniCompiler/week2/april_9/parser/TypeCheck.g:18:4: 
            {
            dbg.location(18,4);
             System.out.println("Entered tree walker"); 

            }

        }
        finally {
        }
        dbg.location(19, 1);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "verify");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "verify"

    // Delegated rules


 

}