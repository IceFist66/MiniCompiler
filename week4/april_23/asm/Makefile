FILES= Addq.java Cmoveq.java Driver_a.java Jle.java Orq.java Andq.java Cmovgeq.java Idivq.java Jl.java Ret.java Cmovgq.java Imulq.java Jmp.java Shrq.java Cmovleq.java Jne.java Subq.java Cmovlq.java Je.java Xorq.java Cmovneq.java Jge.java Call.java Cmp.java Jg.java Movq.java

all : python Driver.class

Driver.class : ${FILES}
	javac *.java

python : 
	python asm_creation.py asm_template

clean:
	\rm *.class
