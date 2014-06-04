import java.util.ArrayList;


public class Bubble {
	private String id;
	private int color;
	private ArrayList<Bubble> edges;
	
	
	public Bubble(String id) {
		this.id = id;
		this.color = -1;
		this.edges = new ArrayList<Bubble>();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	public ArrayList<Bubble> getEdges() {
		return edges;
	}
	public void setEdges(ArrayList<Bubble> edges) {
		this.edges = edges;
	}
}

