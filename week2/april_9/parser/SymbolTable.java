import java.util.HashMap;

// table_key = global or function name

public class SymbolTable {
	private HashMap<String, HashMap<String, Type>> hashmap;
	
	public SymbolTable(){
		hashmap = new HashMap<String, HashMap<String, Type>>();
	}
	
	public void addSymbol(String table_key, String value_key, Type type){
		HashMap<String, Type> value_map;

		if(!hashmap.containsKey(table_key)){
			value_map = new HashMap<String, Type>();
		}
		else{
			value_map = hashmap.get(table_key);
		}
		
		hashmap.put(table_key, value_map);
		hashmap.get(table_key).put(value_key, type);
		//System.out.println(hashmap.get(table_key).get(value_key).getMiniType().toString());
		
		
//		hashmap.put(table_key, new HashMap<String, Type>());
//		hashmap.get(table_key).put(value_key, type);
//		System.out.println(hashmap.get(table_key).get(value_key));
	}
	
	public Type getType(String table_key, String value_key){
		HashMap<String, Type> value_map;
		
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
	
//	public Type getStructType(String table_key, String dot_expression) {
//		HashMap<String, Type> value_map;
//		String[] result = dot_expression.split(".");
//		String value_key = result[result.length-2];
//		String field = result[result.length - 1];
//		if (value_key == null || field == null) {
//			System.out.println("Could not resolve dot expression!");
//			return null;
//		}
//		
//		// if variable doesn't exist under local scope, set table_key to "global"
//		
//		// if the scope is local and the scope is in the table
//		if ((!table_key.equals("global")) && (hashmap.containsKey(table_key)))
//			
//			// if the variable isn't found under the local scope
//			if (!hashmap.get(table_key).containsKey(value_key))
//				table_key = "global";
//		
//		if (hashmap.containsKey(table_key)) {
//			value_map = hashmap.get(table_key);
//			if (value_map.containsKey(value_key)) {
//				String structType = hashmap.get(table_key).get(value_key).getStructType();
//				return 
//			}
//			else {
//				System.out.println("struct value_key not in hashmap.\n");
//				return null;
//			}
//		}			
//		else {
//			System.out.println("struct table_key not in hashmap.\n");
//			return null;
//		}
//		
//	}		


}
