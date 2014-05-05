package iloc;

public class Storet extends Instruction{

	public Storet(String arg1){
		this.arg1 = arg1;
		this.arg2 = null;
		this.arg3 = null;
		this.text = "storet " + arg1;
	}

}