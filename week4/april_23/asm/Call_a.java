package asm;

public class Call_a extends Instruction_a{

	public Call_a(String arg1){
		this.arg1 = arg1;
		this.arg2 = null;
		this.arg3 = null;
		this.text = "call_a " + arg1;
		this.target = null;
	}

}