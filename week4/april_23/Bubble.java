import java.util.ArrayList;


public class Bubble implements Comparable<Bubble> {
	private String id;
	private Color color;
	private boolean constrained;
	private ArrayList<Bubble> edges;
	
	
	public Bubble(String id) {
		this.id = id;
		this.color = Color.UNC;
		this.constrained = false;
		this.edges = new ArrayList<Bubble>();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public void setConstrained(boolean constrained) {
	   this.constrained = constrained;
	}
	public boolean isConstrained() {
	   return this.constrained;
	}
	public ArrayList<Bubble> getEdges() {
		return edges;
	}
	public void setEdges(ArrayList<Bubble> edges) {
		this.edges = edges;
	}
	public int compareTo(Bubble compareBubble) {
	   int compareDegree = ((Bubble) compareBubble).edges.size();
	   return compareDegree - this.edges.size();
	}
}

