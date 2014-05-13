package iloc;

public class Storeinargument extends Instruction{

	public Storeinargument(String arg1, String arg2, String arg3){
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.arg3 = arg3;
		this.text = "storeinargument " + arg1 + " => " + arg2 + ", " + arg3;
		this.target = arg2  + " + " + arg3;
	}

}