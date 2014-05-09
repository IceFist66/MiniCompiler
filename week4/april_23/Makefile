FILES=Mini.java SymbolTable.java Type.java StructDef.java Scope.java StructTypes.java Variable.java Node.java NodeType.java


Mini.class : antlr.generated ${FILES}
	javac *.java

antlr.generated: antlr.generated.mini antlr.generated.type antlr.generated.control
	touch antlr.generated

antlr.generated.mini : Mini.g
	java org.antlr.Tool Mini.g
	touch antlr.generated.mini

antlr.generated.type : TypeCheck.g
	java org.antlr.Tool TypeCheck.g
	touch antlr.generated.type

antlr.generated.control : Control.g
	java org.antlr.Tool Control.g
	touch antlr.generated.control

clean:
	\rm *generated* MiniParser.java MiniLexer.java TypeCheck.java Mini.tokens TypeCheck.tokens *.class *.dot *.il
