package iloc;

public class Movge extends Instruction{

	public Movge(String arg1, String arg2){
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.arg3 = null;
		this.text = "movge " + arg1 + ", " + arg2;
	}

}