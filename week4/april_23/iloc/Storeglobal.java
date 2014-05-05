package iloc;

public class Storeglobal extends Instruction{

	public Storeglobal(String arg1, String arg2){
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.arg3 = null;
		this.text = "storeglobal " + arg1 + " => " + arg2;
	}

}