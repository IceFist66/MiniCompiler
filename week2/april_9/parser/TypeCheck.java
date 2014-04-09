// $ANTLR 3.3 Nov 30, 2010 12:50:56 TypeCheck.g 2014-04-09 09:33:29

   import java.util.Map;
   import java.util.HashMap;
   import java.util.Vector;
   import java.util.Iterator;


import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class TypeCheck extends TreeParser {
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


        public TypeCheck(TreeNodeStream input) {
            this(input, new RecognizerSharedState());
        }
        public TypeCheck(TreeNodeStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return TypeCheck.tokenNames; }
    public String getGrammarFileName() { return "TypeCheck.g"; }



    // $ANTLR start "types"
    // TypeCheck.g:17:1: types : ^( TYPES ( struct )* ) ;
    public final void types() throws RecognitionException {
        try {
            // TypeCheck.g:17:6: ( ^( TYPES ( struct )* ) )
            // TypeCheck.g:18:5: ^( TYPES ( struct )* )
            {
            match(input,TYPES,FOLLOW_TYPES_in_types43); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // TypeCheck.g:18:13: ( struct )*
                loop1:
                do {
                    int alt1=2;
                    int LA1_0 = input.LA(1);

                    if ( (LA1_0==STRUCT) ) {
                        alt1=1;
                    }


                    switch (alt1) {
                	case 1 :
                	    // TypeCheck.g:18:13: struct
                	    {
                	    pushFollow(FOLLOW_struct_in_types45);
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
            System.out.println("Found types");

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
    // TypeCheck.g:21:1: struct : ^( STRUCT ID ( decl )* ) ;
    public final void struct() throws RecognitionException {
        try {
            // TypeCheck.g:21:7: ( ^( STRUCT ID ( decl )* ) )
            // TypeCheck.g:22:5: ^( STRUCT ID ( decl )* )
            {
            match(input,STRUCT,FOLLOW_STRUCT_in_struct62); 

            match(input, Token.DOWN, null); 
            match(input,ID,FOLLOW_ID_in_struct64); 
            // TypeCheck.g:22:17: ( decl )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==DECL) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // TypeCheck.g:22:17: decl
            	    {
            	    pushFollow(FOLLOW_decl_in_struct66);
            	    decl();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            match(input, Token.UP, null); 
            System.out.println("Found struct");

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
    // TypeCheck.g:25:1: decl : ^( DECL ^( TYPE type ) ID ) ;
    public final void decl() throws RecognitionException {
        try {
            // TypeCheck.g:25:5: ( ^( DECL ^( TYPE type ) ID ) )
            // TypeCheck.g:26:5: ^( DECL ^( TYPE type ) ID )
            {
            match(input,DECL,FOLLOW_DECL_in_decl82); 

            match(input, Token.DOWN, null); 
            match(input,TYPE,FOLLOW_TYPE_in_decl85); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_type_in_decl87);
            type();

            state._fsp--;


            match(input, Token.UP, null); 
            match(input,ID,FOLLOW_ID_in_decl90); 

            match(input, Token.UP, null); 
            System.out.println("DECL found");

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
    // TypeCheck.g:29:1: type : ( INT | BOOL | ^( STRUCT ID ) );
    public final void type() throws RecognitionException {
        try {
            // TypeCheck.g:29:5: ( INT | BOOL | ^( STRUCT ID ) )
            int alt3=3;
            switch ( input.LA(1) ) {
            case INT:
                {
                alt3=1;
                }
                break;
            case BOOL:
                {
                alt3=2;
                }
                break;
            case STRUCT:
                {
                alt3=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }

            switch (alt3) {
                case 1 :
                    // TypeCheck.g:30:5: INT
                    {
                    match(input,INT,FOLLOW_INT_in_type104); 
                    System.out.println("Int Found");

                    }
                    break;
                case 2 :
                    // TypeCheck.g:31:6: BOOL
                    {
                    match(input,BOOL,FOLLOW_BOOL_in_type113); 
                    System.out.println("Bool Found");

                    }
                    break;
                case 3 :
                    // TypeCheck.g:32:6: ^( STRUCT ID )
                    {
                    match(input,STRUCT,FOLLOW_STRUCT_in_type123); 

                    match(input, Token.DOWN, null); 
                    match(input,ID,FOLLOW_ID_in_type125); 

                    match(input, Token.UP, null); 
                    System.out.println("Struct found");

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
    // TypeCheck.g:35:1: decls : ;
    public final void decls() throws RecognitionException {
        try {
            // TypeCheck.g:35:6: ()
            // TypeCheck.g:36:5: 
            {
            System.out.println("Decls found");

            }

        }
        finally {
        }
        return ;
    }
    // $ANTLR end "decls"


    // $ANTLR start "funcs"
    // TypeCheck.g:39:1: funcs : ;
    public final void funcs() throws RecognitionException {
        try {
            // TypeCheck.g:39:6: ()
            // TypeCheck.g:40:5: 
            {
            System.out.println("Found funcs");

            }

        }
        finally {
        }
        return ;
    }
    // $ANTLR end "funcs"


    // $ANTLR start "verify"
    // TypeCheck.g:43:1: verify : ^( PROGRAM types decls funcs ) ;
    public final void verify() throws RecognitionException {
        try {
            // TypeCheck.g:43:8: ( ^( PROGRAM types decls funcs ) )
            // TypeCheck.g:44:5: ^( PROGRAM types decls funcs )
            {
            match(input,PROGRAM,FOLLOW_PROGRAM_in_verify167); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_types_in_verify169);
            types();

            state._fsp--;

            pushFollow(FOLLOW_decls_in_verify171);
            decls();

            state._fsp--;

            pushFollow(FOLLOW_funcs_in_verify173);
            funcs();

            state._fsp--;


            match(input, Token.UP, null); 
             System.out.println("Finished Verify"); 

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
    // $ANTLR end "verify"

    // Delegated rules


 

    public static final BitSet FOLLOW_TYPES_in_types43 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_struct_in_types45 = new BitSet(new long[]{0x0000000000000018L});
    public static final BitSet FOLLOW_STRUCT_in_struct62 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_struct64 = new BitSet(new long[]{0x0000000004000008L});
    public static final BitSet FOLLOW_decl_in_struct66 = new BitSet(new long[]{0x0000000004000008L});
    public static final BitSet FOLLOW_DECL_in_decl82 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_TYPE_in_decl85 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_type_in_decl87 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ID_in_decl90 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INT_in_type104 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BOOL_in_type113 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRUCT_in_type123 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_type125 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PROGRAM_in_verify167 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_types_in_verify169 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_decls_in_verify171 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_funcs_in_verify173 = new BitSet(new long[]{0x0000000000000008L});

}