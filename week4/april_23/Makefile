FILES=Mini.java SymbolTable.java Type.java StructDef.java Scope.java StructTypes.java Variable.java Node.java NodeType.java Assembly_Factory.java


all : iloc-c asm-c Mini.class

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

iloc-c :
	make -C iloc

asm-c :
	make -C asm

clean : clean_iloc clean_asm this

this:
	\rm *generated* MiniParser.java MiniLexer.java Control.java TypeCheck.java Mini.tokens TypeCheck.tokens *.class *.dot *.il *.asm
	
clean_iloc :
	make -C iloc clean
clean_asm :
	make -C asm clean
