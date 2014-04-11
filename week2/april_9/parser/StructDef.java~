
public class StructDef {
	private String[] names;
	private MiniType[] types;

	public StructDef(String[] names, MiniType[] types){
		this.names = names;
		this.types = types;
	}
	
	public MiniType getFieldType(String name) {
		for (int i = 0; i < names.length; i++) {
			if(name.equals(names[i]))
				return types[i];
		}
		System.out.println("Field name not found.");
		return null;
	}
	
	public String getName() {
		return this.names[0];
	}
	
}
