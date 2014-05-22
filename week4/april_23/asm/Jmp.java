package asm;

public class Jmp extends Instruction_a{

	public Jmp(String arg1){
		this.arg1 = arg1;
		this.arg2 = null;
		this.arg3 = null;
		this.text = "jmp " + arg1;
	}

}