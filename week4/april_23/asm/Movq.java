package asm;

public class Movq extends Instruction_a{

	public Movq(String arg1, String arg2){
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.arg3 = null;
		this.text = "movq " + arg1 + " , " + arg2;;
	}

}