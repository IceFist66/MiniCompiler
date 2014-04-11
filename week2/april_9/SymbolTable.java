import java.util.HashMap;


public class SymbolTable {
	private HashMap<String, HashMap<String, Type>> hashmap;
	
	public SymbolTable(){
		hashmap = new HashMap<String, HashMap<String, Type>>();
	}
	
	public void addTable(String key, HashMap<String, Type> map){
		hashmap.put(key, map);
	}
	
	public void addSymbol(String table_key, String value_key, Type type){
		HashMap<String, Type> value_map;
		//check if table exists, create if not
		if(!hashmap.containsKey(table_key)){
			//create the table
			value_map = new HashMap<String, Type>();
		}
		else{
			//Retrieve table
			value_map = hashmap.get(table_key);
		}
		//add type
		value_map.put(value_key, type);
	}
	
	public Type getType(String table_key, String value_key){
		return hashmap.get(table_key).get(value_key);
	}

	public enum ENUM{
		INTG,
		BOOLE,
		FUNC,
		STRUCT;
	}
	
	public enum Scope{
		GLOBAL,
		LOCAL,
		PARAMETER;
	}
		
	public class Type {
		private ENUM type;
		private Scope scope;
		private int num_param;
		private String[] parameters;
		
		public Type(ENUM type, Scope scope){
			this.type = type;
			this.scope = scope;
			this.num_param = -1;
			this.parameters = null;
		}
		
		public Type(Scope scope, int num_param){
			this.type = ENUM.FUNC;
			this.scope = scope;
			this.num_param = num_param;
			this.parameters = new String[num_param];
		}
		
		public void addParam(int index, String parameter){
			if(this.parameters != null && index < this.parameters.length){
				this.parameters[index] = parameter;
			}
		}
		
		public ENUM getENUM(){
			return this.type;
		}
	}

}
