package iloc;

public class Moveq extends Instruction{

	public Moveq(String arg1, String arg2){
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.arg3 = null;
		this.text = "moveq " + arg1 + ", " + arg2;
		this.target = arg2;
	}

}