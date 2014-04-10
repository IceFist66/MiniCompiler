// $ANTLR 3.3 Nov 30, 2010 12:50:56 TypeCheck.g 2014-04-09 17:07:54

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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "STRUCT", "INT", "BOOL", "FUN", "VOID", "PRINT", "ENDL", "READ", "IF", "ELSE", "WHILE", "DELETE", "RETURN", "TRUE", "FALSE", "NEW", "NULL", "PROGRAM", "TYPES", "TYPE", "DECLS", "FUNCS", "DECL", "DECLLIST", "PARAMS", "RETTYPE", "BLOCK", "STMTS", "INVOKE", "ARGS", "NEG", "LBRACE", "RBRACE", "SEMI", "COMMA", "LPAREN", "RPAREN", "ASSIGN", "DOT", "AND", "OR", "EQ", "LT", "GT", "NE", "LE", "GE", "PLUS", "MINUS", "TIMES", "DIVIDE", "NOT", "ID", "INTEGER", "WS", "COMMENT", "BOOLTERM", "CONJ", "ASSIGNMENT", "CONDITIONAL", "LOOP", "RET", "INVOCATION"
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
    public static final int BOOLTERM=60;
    public static final int CONJ=61;
    public static final int ASSIGNMENT=62;
    public static final int CONDITIONAL=63;
    public static final int LOOP=64;
    public static final int RET=65;
    public static final int INVOCATION=66;

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
    // TypeCheck.g:36:1: decls : ^( DECLS ( decllist )* ) ;
    public final void decls() throws RecognitionException {
        try {
            // TypeCheck.g:36:6: ( ^( DECLS ( decllist )* ) )
            // TypeCheck.g:37:5: ^( DECLS ( decllist )* )
            {
            match(input,DECLS,FOLLOW_DECLS_in_decls142); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // TypeCheck.g:37:13: ( decllist )*
                loop4:
                do {
                    int alt4=2;
                    int LA4_0 = input.LA(1);

                    if ( (LA4_0==DECLLIST) ) {
                        alt4=1;
                    }


                    switch (alt4) {
                	case 1 :
                	    // TypeCheck.g:37:13: decllist
                	    {
                	    pushFollow(FOLLOW_decllist_in_decls144);
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
            System.out.println("Decls found");

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
    // TypeCheck.g:41:1: decllist : ^( DECLLIST type ( ID )* ) ;
    public final void decllist() throws RecognitionException {
        try {
            // TypeCheck.g:41:9: ( ^( DECLLIST type ( ID )* ) )
            // TypeCheck.g:42:5: ^( DECLLIST type ( ID )* )
            {
            match(input,DECLLIST,FOLLOW_DECLLIST_in_decllist165); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_type_in_decllist167);
            type();

            state._fsp--;

            // TypeCheck.g:42:21: ( ID )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==ID) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // TypeCheck.g:42:21: ID
            	    {
            	    match(input,ID,FOLLOW_ID_in_decllist169); 

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);


            match(input, Token.UP, null); 
            System.out.println("Decclist found");

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


    // $ANTLR start "assignment"
    // TypeCheck.g:46:1: assignment : ^( ASSIGN lvalue expression ) ;
    public final void assignment() throws RecognitionException {
        try {
            // TypeCheck.g:46:11: ( ^( ASSIGN lvalue expression ) )
            // TypeCheck.g:47:4: ^( ASSIGN lvalue expression )
            {
            match(input,ASSIGN,FOLLOW_ASSIGN_in_assignment189); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_lvalue_in_assignment191);
            lvalue();

            state._fsp--;

            pushFollow(FOLLOW_expression_in_assignment193);
            expression();

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
    // $ANTLR end "assignment"


    // $ANTLR start "expression"
    // TypeCheck.g:50:1: expression : ( ^( BOOLTERM boolterm ) | ^( CONJ boolterm boolterm ) );
    public final void expression() throws RecognitionException {
        try {
            // TypeCheck.g:50:11: ( ^( BOOLTERM boolterm ) | ^( CONJ boolterm boolterm ) )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==BOOLTERM) ) {
                alt6=1;
            }
            else if ( (LA6_0==CONJ) ) {
                alt6=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }
            switch (alt6) {
                case 1 :
                    // TypeCheck.g:51:4: ^( BOOLTERM boolterm )
                    {
                    match(input,BOOLTERM,FOLLOW_BOOLTERM_in_expression206); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        pushFollow(FOLLOW_boolterm_in_expression208);
                        boolterm();

                        state._fsp--;


                        match(input, Token.UP, null); 
                    }

                    }
                    break;
                case 2 :
                    // TypeCheck.g:52:6: ^( CONJ boolterm boolterm )
                    {
                    match(input,CONJ,FOLLOW_CONJ_in_expression217); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        pushFollow(FOLLOW_boolterm_in_expression219);
                        boolterm();

                        state._fsp--;

                        pushFollow(FOLLOW_boolterm_in_expression221);
                        boolterm();

                        state._fsp--;


                        match(input, Token.UP, null); 
                    }

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
    // $ANTLR end "expression"


    // $ANTLR start "boolterm"
    // TypeCheck.g:55:1: boolterm : simple ;
    public final void boolterm() throws RecognitionException {
        try {
            // TypeCheck.g:55:9: ( simple )
            // TypeCheck.g:56:4: simple
            {
            pushFollow(FOLLOW_simple_in_boolterm233);
            simple();

            state._fsp--;


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
    // $ANTLR end "boolterm"


    // $ANTLR start "simple"
    // TypeCheck.g:60:1: simple : ;
    public final void simple() throws RecognitionException {
        try {
            // TypeCheck.g:60:7: ()
            // TypeCheck.g:62:1: 
            {
            }

        }
        finally {
        }
        return ;
    }
    // $ANTLR end "simple"


    // $ANTLR start "lvalue"
    // TypeCheck.g:64:1: lvalue : ( ID | ^( DOT lvalue ID ) );
    public final void lvalue() throws RecognitionException {
        try {
            // TypeCheck.g:64:7: ( ID | ^( DOT lvalue ID ) )
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==ID) ) {
                alt7=1;
            }
            else if ( (LA7_0==DOT) ) {
                alt7=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1 :
                    // TypeCheck.g:65:4: ID
                    {
                    match(input,ID,FOLLOW_ID_in_lvalue258); 

                    }
                    break;
                case 2 :
                    // TypeCheck.g:66:6: ^( DOT lvalue ID )
                    {
                    match(input,DOT,FOLLOW_DOT_in_lvalue266); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_lvalue_in_lvalue268);
                    lvalue();

                    state._fsp--;

                    match(input,ID,FOLLOW_ID_in_lvalue270); 

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
        return ;
    }
    // $ANTLR end "lvalue"


    // $ANTLR start "print"
    // TypeCheck.g:69:1: print : ;
    public final void print() throws RecognitionException {
        try {
            // TypeCheck.g:69:6: ()
            // TypeCheck.g:71:1: 
            {
            }

        }
        finally {
        }
        return ;
    }
    // $ANTLR end "print"


    // $ANTLR start "ret"
    // TypeCheck.g:73:1: ret : ;
    public final void ret() throws RecognitionException {
        try {
            // TypeCheck.g:73:4: ()
            // TypeCheck.g:75:1: 
            {
            }

        }
        finally {
        }
        return ;
    }
    // $ANTLR end "ret"


    // $ANTLR start "read"
    // TypeCheck.g:77:1: read : ;
    public final void read() throws RecognitionException {
        try {
            // TypeCheck.g:77:5: ()
            // TypeCheck.g:79:1: 
            {
            }

        }
        finally {
        }
        return ;
    }
    // $ANTLR end "read"


    // $ANTLR start "conditional"
    // TypeCheck.g:81:1: conditional : ;
    public final void conditional() throws RecognitionException {
        try {
            // TypeCheck.g:81:12: ()
            // TypeCheck.g:83:4: 
            {
            }

        }
        finally {
        }
        return ;
    }
    // $ANTLR end "conditional"


    // $ANTLR start "loop"
    // TypeCheck.g:85:1: loop : ;
    public final void loop() throws RecognitionException {
        try {
            // TypeCheck.g:85:5: ()
            // TypeCheck.g:87:4: 
            {
            }

        }
        finally {
        }
        return ;
    }
    // $ANTLR end "loop"


    // $ANTLR start "invocation"
    // TypeCheck.g:89:1: invocation : ;
    public final void invocation() throws RecognitionException {
        try {
            // TypeCheck.g:89:11: ()
            // TypeCheck.g:91:1: 
            {
            }

        }
        finally {
        }
        return ;
    }
    // $ANTLR end "invocation"


    // $ANTLR start "delete"
    // TypeCheck.g:93:1: delete : ;
    public final void delete() throws RecognitionException {
        try {
            // TypeCheck.g:93:7: ()
            // TypeCheck.g:95:1: 
            {
            }

        }
        finally {
        }
        return ;
    }
    // $ANTLR end "delete"


    // $ANTLR start "stmt"
    // TypeCheck.g:97:1: stmt : ( ^( STMTS ^( BLOCK stmtlist ) ) | ^( STMTS ^( ASSIGNMENT assignment ) ) | ^( STMTS ^( PRINT print ) ) | ^( STMTS ^( READ read ) ) | ^( STMTS ^( CONDITIONAL conditional ) ) | ^( STMTS ^( LOOP loop ) ) | ^( STMTS ^( DELETE delete ) ) | ^( STMTS ^( RET ret ) ) | ^( STMTS ^( INVOCATION invocation ) ) );
    public final void stmt() throws RecognitionException {
        try {
            // TypeCheck.g:97:5: ( ^( STMTS ^( BLOCK stmtlist ) ) | ^( STMTS ^( ASSIGNMENT assignment ) ) | ^( STMTS ^( PRINT print ) ) | ^( STMTS ^( READ read ) ) | ^( STMTS ^( CONDITIONAL conditional ) ) | ^( STMTS ^( LOOP loop ) ) | ^( STMTS ^( DELETE delete ) ) | ^( STMTS ^( RET ret ) ) | ^( STMTS ^( INVOCATION invocation ) ) )
            int alt8=9;
            alt8 = dfa8.predict(input);
            switch (alt8) {
                case 1 :
                    // TypeCheck.g:98:5: ^( STMTS ^( BLOCK stmtlist ) )
                    {
                    match(input,STMTS,FOLLOW_STMTS_in_stmt366); 

                    match(input, Token.DOWN, null); 
                    match(input,BLOCK,FOLLOW_BLOCK_in_stmt369); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_stmtlist_in_stmt371);
                    stmtlist();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // TypeCheck.g:99:6: ^( STMTS ^( ASSIGNMENT assignment ) )
                    {
                    match(input,STMTS,FOLLOW_STMTS_in_stmt381); 

                    match(input, Token.DOWN, null); 
                    match(input,ASSIGNMENT,FOLLOW_ASSIGNMENT_in_stmt384); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_assignment_in_stmt386);
                    assignment();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // TypeCheck.g:100:6: ^( STMTS ^( PRINT print ) )
                    {
                    match(input,STMTS,FOLLOW_STMTS_in_stmt396); 

                    match(input, Token.DOWN, null); 
                    match(input,PRINT,FOLLOW_PRINT_in_stmt399); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        pushFollow(FOLLOW_print_in_stmt401);
                        print();

                        state._fsp--;


                        match(input, Token.UP, null); 
                    }

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // TypeCheck.g:101:6: ^( STMTS ^( READ read ) )
                    {
                    match(input,STMTS,FOLLOW_STMTS_in_stmt411); 

                    match(input, Token.DOWN, null); 
                    match(input,READ,FOLLOW_READ_in_stmt414); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        pushFollow(FOLLOW_read_in_stmt416);
                        read();

                        state._fsp--;


                        match(input, Token.UP, null); 
                    }

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // TypeCheck.g:102:6: ^( STMTS ^( CONDITIONAL conditional ) )
                    {
                    match(input,STMTS,FOLLOW_STMTS_in_stmt426); 

                    match(input, Token.DOWN, null); 
                    match(input,CONDITIONAL,FOLLOW_CONDITIONAL_in_stmt429); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        pushFollow(FOLLOW_conditional_in_stmt431);
                        conditional();

                        state._fsp--;


                        match(input, Token.UP, null); 
                    }

                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // TypeCheck.g:103:6: ^( STMTS ^( LOOP loop ) )
                    {
                    match(input,STMTS,FOLLOW_STMTS_in_stmt441); 

                    match(input, Token.DOWN, null); 
                    match(input,LOOP,FOLLOW_LOOP_in_stmt444); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        pushFollow(FOLLOW_loop_in_stmt446);
                        loop();

                        state._fsp--;


                        match(input, Token.UP, null); 
                    }

                    match(input, Token.UP, null); 

                    }
                    break;
                case 7 :
                    // TypeCheck.g:104:6: ^( STMTS ^( DELETE delete ) )
                    {
                    match(input,STMTS,FOLLOW_STMTS_in_stmt456); 

                    match(input, Token.DOWN, null); 
                    match(input,DELETE,FOLLOW_DELETE_in_stmt459); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        pushFollow(FOLLOW_delete_in_stmt461);
                        delete();

                        state._fsp--;


                        match(input, Token.UP, null); 
                    }

                    match(input, Token.UP, null); 

                    }
                    break;
                case 8 :
                    // TypeCheck.g:105:6: ^( STMTS ^( RET ret ) )
                    {
                    match(input,STMTS,FOLLOW_STMTS_in_stmt471); 

                    match(input, Token.DOWN, null); 
                    match(input,RET,FOLLOW_RET_in_stmt474); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        pushFollow(FOLLOW_ret_in_stmt476);
                        ret();

                        state._fsp--;


                        match(input, Token.UP, null); 
                    }

                    match(input, Token.UP, null); 

                    }
                    break;
                case 9 :
                    // TypeCheck.g:106:6: ^( STMTS ^( INVOCATION invocation ) )
                    {
                    match(input,STMTS,FOLLOW_STMTS_in_stmt486); 

                    match(input, Token.DOWN, null); 
                    match(input,INVOCATION,FOLLOW_INVOCATION_in_stmt489); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        pushFollow(FOLLOW_invocation_in_stmt491);
                        invocation();

                        state._fsp--;


                        match(input, Token.UP, null); 
                    }

                    match(input, Token.UP, null); 
                    System.out.println("stmts found");

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
    // $ANTLR end "stmt"


    // $ANTLR start "stmtlist"
    // TypeCheck.g:110:1: stmtlist : ^( STMTS ( stmt )* ) ;
    public final void stmtlist() throws RecognitionException {
        try {
            // TypeCheck.g:110:9: ( ^( STMTS ( stmt )* ) )
            // TypeCheck.g:111:5: ^( STMTS ( stmt )* )
            {
            match(input,STMTS,FOLLOW_STMTS_in_stmtlist512); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // TypeCheck.g:111:13: ( stmt )*
                loop9:
                do {
                    int alt9=2;
                    int LA9_0 = input.LA(1);

                    if ( (LA9_0==STMTS) ) {
                        alt9=1;
                    }


                    switch (alt9) {
                	case 1 :
                	    // TypeCheck.g:111:13: stmt
                	    {
                	    pushFollow(FOLLOW_stmt_in_stmtlist514);
                	    stmt();

                	    state._fsp--;


                	    }
                	    break;

                	default :
                	    break loop9;
                    }
                } while (true);


                match(input, Token.UP, null); 
            }
            System.out.println("found stmtlist");

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
    // $ANTLR end "stmtlist"


    // $ANTLR start "funcs"
    // TypeCheck.g:115:1: funcs : ^( FUNCS ( fun )* ) ;
    public final void funcs() throws RecognitionException {
        try {
            // TypeCheck.g:115:6: ( ^( FUNCS ( fun )* ) )
            // TypeCheck.g:116:5: ^( FUNCS ( fun )* )
            {
            match(input,FUNCS,FOLLOW_FUNCS_in_funcs535); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // TypeCheck.g:116:13: ( fun )*
                loop10:
                do {
                    int alt10=2;
                    int LA10_0 = input.LA(1);

                    if ( (LA10_0==FUN) ) {
                        alt10=1;
                    }


                    switch (alt10) {
                	case 1 :
                	    // TypeCheck.g:116:13: fun
                	    {
                	    pushFollow(FOLLOW_fun_in_funcs537);
                	    fun();

                	    state._fsp--;


                	    }
                	    break;

                	default :
                	    break loop10;
                    }
                } while (true);


                match(input, Token.UP, null); 
            }
            System.out.println("Found funcs");

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
    // TypeCheck.g:120:1: fun : ^( FUN ID params rettype decls stmtlist ) ;
    public final void fun() throws RecognitionException {
        try {
            // TypeCheck.g:120:4: ( ^( FUN ID params rettype decls stmtlist ) )
            // TypeCheck.g:121:5: ^( FUN ID params rettype decls stmtlist )
            {
            match(input,FUN,FOLLOW_FUN_in_fun558); 

            match(input, Token.DOWN, null); 
            match(input,ID,FOLLOW_ID_in_fun560); 
            pushFollow(FOLLOW_params_in_fun562);
            params();

            state._fsp--;

            pushFollow(FOLLOW_rettype_in_fun564);
            rettype();

            state._fsp--;

            pushFollow(FOLLOW_decls_in_fun566);
            decls();

            state._fsp--;

            pushFollow(FOLLOW_stmtlist_in_fun568);
            stmtlist();

            state._fsp--;


            match(input, Token.UP, null); 
            System.out.println("Found fun");

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
    // TypeCheck.g:125:1: params : ^( PARAMS ( decl )* ) ;
    public final void params() throws RecognitionException {
        try {
            // TypeCheck.g:125:7: ( ^( PARAMS ( decl )* ) )
            // TypeCheck.g:126:5: ^( PARAMS ( decl )* )
            {
            match(input,PARAMS,FOLLOW_PARAMS_in_params588); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // TypeCheck.g:126:14: ( decl )*
                loop11:
                do {
                    int alt11=2;
                    int LA11_0 = input.LA(1);

                    if ( (LA11_0==DECL) ) {
                        alt11=1;
                    }


                    switch (alt11) {
                	case 1 :
                	    // TypeCheck.g:126:14: decl
                	    {
                	    pushFollow(FOLLOW_decl_in_params590);
                	    decl();

                	    state._fsp--;


                	    }
                	    break;

                	default :
                	    break loop11;
                    }
                } while (true);


                match(input, Token.UP, null); 
            }
            System.out.println("Found params");

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
    // TypeCheck.g:130:1: rettype : ^( RETTYPE ID ) ;
    public final void rettype() throws RecognitionException {
        try {
            // TypeCheck.g:130:8: ( ^( RETTYPE ID ) )
            // TypeCheck.g:131:5: ^( RETTYPE ID )
            {
            match(input,RETTYPE,FOLLOW_RETTYPE_in_rettype611); 

            match(input, Token.DOWN, null); 
            match(input,ID,FOLLOW_ID_in_rettype613); 

            match(input, Token.UP, null); 
            System.out.println("Found rettype");

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


    // $ANTLR start "verify"
    // TypeCheck.g:137:1: verify : ^( PROGRAM types decls funcs ) ;
    public final void verify() throws RecognitionException {
        try {
            // TypeCheck.g:137:8: ( ^( PROGRAM types decls funcs ) )
            // TypeCheck.g:138:5: ^( PROGRAM types decls funcs )
            {
            match(input,PROGRAM,FOLLOW_PROGRAM_in_verify637); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_types_in_verify639);
            types();

            state._fsp--;

            pushFollow(FOLLOW_decls_in_verify641);
            decls();

            state._fsp--;

            pushFollow(FOLLOW_funcs_in_verify643);
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


    protected DFA8 dfa8 = new DFA8(this);
    static final String DFA8_eotS =
        "\14\uffff";
    static final String DFA8_eofS =
        "\14\uffff";
    static final String DFA8_minS =
        "\1\37\1\2\1\11\11\uffff";
    static final String DFA8_maxS =
        "\1\37\1\2\1\102\11\uffff";
    static final String DFA8_acceptS =
        "\3\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11";
    static final String DFA8_specialS =
        "\14\uffff}>";
    static final String[] DFA8_transitionS = {
            "\1\1",
            "\1\2",
            "\1\5\1\uffff\1\6\3\uffff\1\11\16\uffff\1\3\37\uffff\1\4\1\7"+
            "\1\10\1\12\1\13",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA8_eot = DFA.unpackEncodedString(DFA8_eotS);
    static final short[] DFA8_eof = DFA.unpackEncodedString(DFA8_eofS);
    static final char[] DFA8_min = DFA.unpackEncodedStringToUnsignedChars(DFA8_minS);
    static final char[] DFA8_max = DFA.unpackEncodedStringToUnsignedChars(DFA8_maxS);
    static final short[] DFA8_accept = DFA.unpackEncodedString(DFA8_acceptS);
    static final short[] DFA8_special = DFA.unpackEncodedString(DFA8_specialS);
    static final short[][] DFA8_transition;

    static {
        int numStates = DFA8_transitionS.length;
        DFA8_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA8_transition[i] = DFA.unpackEncodedString(DFA8_transitionS[i]);
        }
    }

    class DFA8 extends DFA {

        public DFA8(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 8;
            this.eot = DFA8_eot;
            this.eof = DFA8_eof;
            this.min = DFA8_min;
            this.max = DFA8_max;
            this.accept = DFA8_accept;
            this.special = DFA8_special;
            this.transition = DFA8_transition;
        }
        public String getDescription() {
            return "97:1: stmt : ( ^( STMTS ^( BLOCK stmtlist ) ) | ^( STMTS ^( ASSIGNMENT assignment ) ) | ^( STMTS ^( PRINT print ) ) | ^( STMTS ^( READ read ) ) | ^( STMTS ^( CONDITIONAL conditional ) ) | ^( STMTS ^( LOOP loop ) ) | ^( STMTS ^( DELETE delete ) ) | ^( STMTS ^( RET ret ) ) | ^( STMTS ^( INVOCATION invocation ) ) );";
        }
    }
 

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
    public static final BitSet FOLLOW_DECLS_in_decls142 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_decllist_in_decls144 = new BitSet(new long[]{0x0000000008000008L});
    public static final BitSet FOLLOW_DECLLIST_in_decllist165 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_type_in_decllist167 = new BitSet(new long[]{0x0100000000000008L});
    public static final BitSet FOLLOW_ID_in_decllist169 = new BitSet(new long[]{0x0100000000000008L});
    public static final BitSet FOLLOW_ASSIGN_in_assignment189 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_lvalue_in_assignment191 = new BitSet(new long[]{0x3000000000000000L});
    public static final BitSet FOLLOW_expression_in_assignment193 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BOOLTERM_in_expression206 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_boolterm_in_expression208 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CONJ_in_expression217 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_boolterm_in_expression219 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_boolterm_in_expression221 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_simple_in_boolterm233 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_lvalue258 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOT_in_lvalue266 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_lvalue_in_lvalue268 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_ID_in_lvalue270 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_STMTS_in_stmt366 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_BLOCK_in_stmt369 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_stmtlist_in_stmt371 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_STMTS_in_stmt381 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ASSIGNMENT_in_stmt384 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_assignment_in_stmt386 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_STMTS_in_stmt396 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_PRINT_in_stmt399 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_print_in_stmt401 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_STMTS_in_stmt411 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_READ_in_stmt414 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_read_in_stmt416 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_STMTS_in_stmt426 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_CONDITIONAL_in_stmt429 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_conditional_in_stmt431 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_STMTS_in_stmt441 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_LOOP_in_stmt444 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_loop_in_stmt446 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_STMTS_in_stmt456 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DELETE_in_stmt459 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_delete_in_stmt461 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_STMTS_in_stmt471 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_RET_in_stmt474 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ret_in_stmt476 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_STMTS_in_stmt486 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_INVOCATION_in_stmt489 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_invocation_in_stmt491 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_STMTS_in_stmtlist512 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_stmt_in_stmtlist514 = new BitSet(new long[]{0x0000000080000008L});
    public static final BitSet FOLLOW_FUNCS_in_funcs535 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_fun_in_funcs537 = new BitSet(new long[]{0x0000000000000088L});
    public static final BitSet FOLLOW_FUN_in_fun558 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_fun560 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_params_in_fun562 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_rettype_in_fun564 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_decls_in_fun566 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_stmtlist_in_fun568 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PARAMS_in_params588 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_decl_in_params590 = new BitSet(new long[]{0x0000000004000008L});
    public static final BitSet FOLLOW_RETTYPE_in_rettype611 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_rettype613 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PROGRAM_in_verify637 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_types_in_verify639 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_decls_in_verify641 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_funcs_in_verify643 = new BitSet(new long[]{0x0000000000000008L});

}