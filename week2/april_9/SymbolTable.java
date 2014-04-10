import java.util.HashMap;


public class SymbolTable {
	private HashMap hashmap;
	
	public SymbolTable(){
		hashmap = new HashMap<String, Type>();
	}
	
	public void addSymbol(String key, Type type){
		hashmap.put(key, type);
	}
	
	public Type getType(String key){
		return ((Type) hashmap.get(key));
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
