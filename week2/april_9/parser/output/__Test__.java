import java.io.*;
import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;
import org.antlr.runtime.debug.DebugEventSocketProxy;


public class __Test__ {

    public static void main(String args[]) throws Exception {
        MiniLexer lex = new MiniLexer(new ANTLRFileStream("/home/david/github/MiniCompiler/week2/april_9/parser/output/__Test___input.txt", "UTF8"));
        CommonTokenStream tokens = new CommonTokenStream(lex);


        MiniParser parser = new MiniParser(tokens);
        MiniParser.verify_return r = parser.verify();
        CommonTreeNodeStream nodes = new CommonTreeNodeStream(r.getTree());


        TypeCheck walker = new TypeCheck(nodes);
        try {
            walker.verify();
        } catch (RecognitionException e) {
            e.printStackTrace();
        }

    }

}