package iloc;

public class Computeformaladdress extends Instruction{

	public Computeformaladdress(String arg1, String arg2){
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.arg3 = null;
		this.text = "computeformaladdress " + arg1 + " => " + arg2;
	}
}
