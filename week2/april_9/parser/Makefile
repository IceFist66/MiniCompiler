FILES=Mini.java
TOOL = -jar ../../../antlr-3.3-complete.jar
ORG = org.antlr.Tool


Mini.class : antlr.generated ${FILES}
	javac *.java

antlr.generated: antlr.generated.mini antlr.generated.type
	touch antlr.generated

antlr.generated.mini : Mini.g
	java ${ORG} Mini.g
	touch antlr.generated.mini

antlr.generated.type : TypeCheck.g
	java ${ORG} TypeCheck.g
	touch antlr.generated.type

clean:
	\rm *generated* MiniParser.java MiniLexer.java TypeCheck.java Mini.tokens TypeCheck.tokens *.class
