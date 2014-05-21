package asm;

public class Println_a extends Instruction_a{

	public Println_a(String arg1){
		this.arg1 = arg1;
		this.arg2 = null;
		this.arg3 = null;
		this.text = "println_a " + arg1;
		this.target = null;
	}

}