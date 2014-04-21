import java.util.ArrayList;


public class StructDef {
    private ArrayList<String> struct_types;
	private ArrayList<String> names;
	private ArrayList<Type> types;

	public StructDef(ArrayList<String> struct_types, ArrayList<String> names, ArrayList<Type> types){
		this.struct_types = struct_types;
                this.names = names;
		this.types = types;
	}
	
	public StructDef(){
                this.struct_types = new ArrayList<String>();
		this.names = new ArrayList<String>();
		this.types = new ArrayList<Type>();
	}
	
	public Type getFieldType(String name) {
		for (int i = 0; i < names.size(); i++) {
			if(name.equals(names.get(i)))
				return types.get(i);
		}
		System.out.println("Field name not found.");
		return null;
	}

	public String getStructType(String name) {
		for (int i = 0; i < names.size(); i++) {
			if(name.equals(names.get(i)))
				return struct_types.get(i);
		}
		System.out.println("Field name not found.");
		return null;
	}
	
	public String getName() {
		return this.names.get(0);
	}
	
	public void addField(String struct_type, String name, Type type) {
		this.struct_types.add(struct_type);
        this.names.add(name);
		this.types.add(type);
	}
	
}
