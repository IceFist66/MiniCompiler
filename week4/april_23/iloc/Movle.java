package iloc;

public class Movle extends Instruction{

	public Movle(String arg1, String arg2){
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.arg3 = null;
		this.text = "movle " + arg1 + ", " + arg2;
		this.target = null;
	}

}