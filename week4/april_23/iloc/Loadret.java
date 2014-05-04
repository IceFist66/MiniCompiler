package iloc;

public class Loadret extends Instruction{

	public Loadret(String arg1){
		this.arg1 = arg1;
		this.arg2 = null;
		this.arg3 = null;
		this.text = "loadret " + "=> " + arg1;
	}
}
