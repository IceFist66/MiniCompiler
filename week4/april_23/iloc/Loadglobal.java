package iloc;

public class Loadglobal extends Instruction{

	public Loadglobal(String arg1, String arg2){
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.arg3 = null;
		this.text = "loadglobal " + arg1 + " => " + arg2;
		this.target = arg2;
	}

}