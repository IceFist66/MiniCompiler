package iloc;

public class Xori extends Instruction{

	public Xori(String arg1, String arg2, String arg3){
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.arg3 = arg3;
		this.text = "xori " + arg1 + ", " + arg2 + " => " + arg3;
	}

}