import java.util.ArrayList;


public class StructDef {
	private ArrayList<String> names;
	private ArrayList<MiniType> types;

	public StructDef(ArrayList<String> names, ArrayList<MiniType> types){
		this.names = names;
		this.types = types;
	}
	
	public StructDef(){
		this.names = new ArrayList<String>();
		this.types = new ArrayList<MiniType>();
	}
	
	public MiniType getFieldType(String name) {
		for (int i = 0; i < names.size(); i++) {
			if(name.equals(names.get(i)))
				return types.get(i);
		}
		System.out.println("Field name not found.");
		return null;
	}
	
	public String getName() {
		return this.names.get(0);
	}
	
	public void addField(String name, MiniType type) {
		this.names.add(name);
		this.types.add(type);
	}
	
}
