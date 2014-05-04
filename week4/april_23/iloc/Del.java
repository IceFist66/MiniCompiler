package iloc;

public class Del extends Instruction{

	public Del(String arg1){
		this.arg1 = arg1;
		this.arg2 = null;
		this.arg3 = null;
		this.text = "del " + arg1;
	}
}
