package asm;

public class Call extends Instruction_a{

	public Call(String arg1){
		this.arg1 = arg1;
		this.arg2 = null;
		this.arg3 = null;
		this.text = "call " + arg1;
	}

}