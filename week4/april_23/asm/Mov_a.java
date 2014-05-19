package asm;

public class Mov_a{
        String arg1;
        String arg2;
        String arg3;
        String target;
        String text;

	public Mov_a(String arg1, String arg2){
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.arg3 = null;
		this.text = "movq " + arg1 + ", " + arg2;
	}

}
