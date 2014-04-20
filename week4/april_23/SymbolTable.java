import java.util.HashMap;

// table_key = global or function name

public class SymbolTable {
	private HashMap<String, HashMap<String, Variable>> hashmap;
	
	public SymbolTable(){
		hashmap = new HashMap<String, HashMap<String, Variable>>();
	}
	
	public void addSymbol(String table_key, String value_key, Variable type){
		HashMap<String, Variable> value_map;

		if(!hashmap.containsKey(table_key)){
			value_map = new HashMap<String, Variable>();
		}
		else{
			value_map = hashmap.get(table_key);
		}
		
		hashmap.put(table_key, value_map);
		hashmap.get(table_key).put(value_key, type);

	}
	
	public Variable getVariable(String table_key, String value_key){
		HashMap<String, Variable> value_map;
		
		if (value_key.contains("."))
			value_key = getStructVariable(value_key);
		
		// if variable doesn't exist under local scope, set table_key to "global"
		
		// if the scope is local and the scope is in the table
		if ((!table_key.equals("global")) && (hashmap.containsKey(table_key)))
			
			// if the variable isn't found under the local scope
			if (!hashmap.get(table_key).containsKey(value_key))
				table_key = "global";
		
		// if the specified local scope doesn't exist in the table, set to global
		if ((!table_key.equals("global")) && (!hashmap.containsKey(table_key))) {
			table_key = "global";
			System.out.println("Specified local scope not in table - setting to global.");
		}
		
		if (hashmap.containsKey(table_key)) {
			value_map = hashmap.get(table_key);
			if (value_map.containsKey(value_key)) {
				return hashmap.get(table_key).get(value_key);
				//return value_map.get(value_key);
			}
			else {
				System.out.println("value_key not in hashmap.\n");
				return null;
			}
		}			
		else {
			System.out.println("table_key not in hashmap.\n");
			return null;
		}
	}
	
	public String getStructVariable(String dot_expression) {
		String[] result = dot_expression.split("\\.");
		System.out.println("result length = " + result.length);
		return result[result.length-2];
	}

}