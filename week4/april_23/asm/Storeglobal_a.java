package asm;

public class Storeglobal_a extends Instruction_a{

	public Storeglobal_a(String arg1, String arg2){
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.arg3 = null;
		this.text = "storeglobal_a " + arg1 + " => " + arg2;
		this.target = arg2;
	}

}