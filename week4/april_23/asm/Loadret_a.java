package asm;

public class Loadret_a extends Instruction_a{

	public Loadret_a(String arg1){
		this.arg1 = arg1;
		this.arg2 = null;
		this.arg3 = null;
		this.text = "loadret_a " + "=> " + arg1;
		this.target = arg1;
	}

}