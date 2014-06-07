import java.util.ArrayList;


public class Bubble implements Comparable<Bubble> {
	private String id;
	private Color color;
    private int unc_count;
    private int spill;
	private boolean constrained;
	private boolean predefined;
	private ArrayList<Bubble> edges;
	
	
	public Bubble(String id) {
		this.id = id;
		if (id.charAt(0) == '%')
		   predefined = true;
		else
		   predefined = false;
		this.color = Color.UNC;
		this.constrained = false;
		this.edges = new ArrayList<Bubble>();
        this.spill = 0;
        this.unc_count = 0;
	}
	
	public boolean isPredefined() {
	   return this.predefined;
	}
	public void setPredefined(boolean predefined) {
	   this.predefined = predefined;
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
    public int getSpill(){
        return this.spill;
    }
    public void setSpill(int spill){
        this.spill = spill;
    }
    public int getUncCount(){
        return this.unc_count;
    }
    public void setUncCount(int count){
        this.unc_count = count;
    }
}

