package asm;

public class Orgq extends Instruction_a{

	public Orgq(String arg1, String arg2){
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.arg3 = null;
		this.text = "orgq " + arg2 + " , " + arg1;
	}

}