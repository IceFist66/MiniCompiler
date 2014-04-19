FILES=Mini.java SymbolTable.java MiniType.java StructDef.java Scope.java StructTypes.java Type.java


Mini.class : antlr.generated ${FILES}
	javac *.java

antlr.generated: antlr.generated.mini antlr.generated.type
	touch antlr.generated

antlr.generated.mini : Mini.g
	java org.antlr.Tool Mini.g
	touch antlr.generated.mini

antlr.generated.type : TypeCheck.g
	java org.antlr.Tool TypeCheck.g
	touch antlr.generated.type

clean:
	\rm *generated* MiniParser.java MiniLexer.java TypeCheck.java Mini.tokens TypeCheck.tokens *.class
