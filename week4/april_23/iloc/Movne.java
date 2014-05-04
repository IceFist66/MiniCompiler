package iloc;

public class Movne extends Instruction{

	public Movne(String arg1, String arg2){
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.arg3 = null;
		this.text = "movne " + arg1 + ", " + arg2;
	}
}
