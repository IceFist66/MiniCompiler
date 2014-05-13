package iloc;

public class Read extends Instruction{

	public Read(String arg1){
		this.arg1 = arg1;
		this.arg2 = null;
		this.arg3 = null;
		this.text = "read " + arg1;
		this.target = null;
	}

}