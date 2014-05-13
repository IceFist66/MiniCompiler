package iloc;

public class Loadi extends Instruction{

	public Loadi(String arg1, String arg2){
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.arg3 = null;
		this.text = "loadi " + arg1 + " => " + arg2;
		this.target = arg2;
	}

}