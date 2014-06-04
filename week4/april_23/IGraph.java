import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Stack;

import iloc.*;
import asm.*;


public class IGraph {
	private ArrayList<Bubble> bubbles;
	private ArrayList<Color> colors;
	private ArrayList<Node> nodes;
	
	public IGraph(ArrayList<Node> nodes) {
		this.bubbles = new ArrayList<Bubble>();
		this.colors = createColorSet();
		this.nodes = nodes;
        createGraph();
	}
	
	public ArrayList<Bubble> getBubbles() {
		return bubbles;
	}
	
	public void setBubbles(ArrayList<Bubble> bubbles) {
		this.bubbles = bubbles;
	}
	
	public ArrayList<Color> getColors() {
		return colors;
	}
	
	public void setColors(ArrayList<Color> colors) {
		this.colors = colors;
	}
		
	public ArrayList<Color> createColorSet() {
	   ArrayList<Color> col = new ArrayList<Color>(EnumSet.allOf(Color.class));
	   col.remove(Color.UNC);
	   return col;
	}
	
	public void createGraph(){
		ArrayList<Instruction_a> reverse = new ArrayList<Instruction_a>();
		ArrayList<String> liveNow = new ArrayList<String>();
		String target;
		ArrayList<String> sources = new ArrayList<String>();
		int numColors = colors.size();
		
		for(Node n : nodes){
			reverse.addAll(n.getAsmInstructions());
			Collections.reverse(reverse);
			liveNow.clear();
			liveNow.addAll(n.getLiveOut());
			for(Instruction_a a : reverse){
				target = a.getTarget();
				liveNow.remove(target);
                if(target != null  && (target.charAt(0) == 'r' || target.charAt(0) == '%')){
				    addBubble(target);
				    sources = a.getSources();
				    for(String source : sources){
                        if(source != null && (source.charAt(0) == 'r' || source.charAt(0) == '%'))
					        addBubble(source);
				    }
				    Bubble btarget = getBubble(target);
				    if(btarget != null){
					    for(String s : liveNow){
						    Bubble bsource = getBubble(s);
						    if(bsource != null){
							    if(!btarget.getEdges().contains(bsource)) {
								    btarget.getEdges().add(bsource);
								    if (btarget.getEdges().size() >= numColors)
								      if (btarget.isConstrained() == false)
								         btarget.setConstrained(true);
								 }
							    if(!bsource.getEdges().contains(btarget)) {
								    bsource.getEdges().add(btarget);
								    if (bsource.getEdges().size() >= numColors)
								      if (bsource.isConstrained() == false)
								         bsource.setConstrained(true);
								 }
						    }
						    else{
							    System.out.println("BSource is null");
						    }
					    }
				    }
				    else{
					    System.out.println("BTarget is null!!");
				    }
                }
			}
		}
	}
	
	public void addBubble(String s){
		boolean found = false;
		for(Bubble b : bubbles){
			if(b.getId().equals(s)){
				found = true;
			}
		}
		if(!found){
			bubbles.add(new Bubble(s));
		}
	}
	
	public Bubble getBubble(String s){
		for(Bubble b : bubbles){
			if(b.getId().equals(s)){
				return b;
			}
		}
		return null;
	}
	
	public void colorGraph() {
	
	   int numColors = colors.size();
	   Stack<Bubble> stackOfBubbles = new Stack<Bubble>();
	   ArrayList<Bubble> copy = new ArrayList<Bubble>(bubbles);
	   ArrayList<Bubble> predefined = new ArrayList<Bubble>(bubbles);
	   
	      // remove non-predefined registers from predefined
	      for (Bubble b : bubbles) {
	         if (b.getId().charAt(0) == 'r')
	            predefined.remove(b);
	      }
	      
	      // now remove predefined registers from bubbles
	      for (Bubble p : predefined) {
	         bubbles.remove(p);
	      }
	            
	      // color the predefined registers
	      for (Bubble p : predefined) {
	         for (Color c : colors)
	            if (p.getId() == c.text())
	               p.setColor(c);
	      }
	   
	      // push the unconstrained bubbles first
	      for (Bubble b : bubbles) {
	         if (b.isConstrained() == false) {
	            copy.remove(b);
               stackOfBubbles.push(b);            
            }
	      }
	      
	      // now sort the constrained bubbles in descending order by degree
	      Collections.sort(copy);
	      
	      // push the now ordered set of constrained bubbles
	      for (Bubble c : copy) {
            stackOfBubbles.push(c);            
	      }
	      
	      // now add the predefined registers
	      for (Bubble p : predefined) {
	         stackOfBubbles.push(p);
	      }
	      
	      // empty copy (will be used to storre results of coloring)
	      copy.clear();
	      
	      // now add the bubbles back to the graph and try to color
	      // assign Color.UNC to bubbles that can't be colored
	      boolean done, conflict;
	      int count;
	      
	      while (!stackOfBubbles.empty()) {
	         done = false;        // attempted coloring of one bubble
	         conflict = false;    // conflict for a particular color for a particular bubble
	         count = 0;           // color number
	         Bubble b = stackOfBubbles.pop();
	         Color color;
	         if (b.getColor() == Color.UNC) {
	            while (done == false && count < numColors) {
	               color = colors.get(count++);
	               
	               // compare current color with each of the bubble's neighbors
	               // and set conflict to true if there is a clash
	               for (Bubble n : b.getEdges()) {
                     if (copy.contains(n) && n.getColor() == color)
                        conflict = true;   	
                  }
                  
                  // if no conflict was found, apply the color to the bubble and
                  // flag the coloring for this bubble as done
                  if (conflict == false) {
                     b.setColor(color);
                     done = true;
                  }
                  // if a conflict was found, reset the flag to check for the next color 
                  else {
                     conflict = false;
                  }
	            }
	            // if after trying all colors there is still a conflict
	            // then color the bubble as uncolorable
	            if (conflict == true)
	               b.setColor(Color.UNC);
	         }
	         // the bubble is colored and can now be added back to the graph
	         copy.add(b);
	      }
	      
	      // move the copy with colored bubbles back into bubbles
	      bubbles.clear();
	      bubbles.addAll(copy);
	      	      
	}
	
	public void printBubbleColors() {
	   System.out.println("\n***Bubble COLORS***");
	   for (Bubble b : bubbles) {
	      System.out.print("Bubble " + b.getId() + " (" + b.getColor().text() + "):\t");
	      for(Bubble n : b.getEdges()){
				System.out.print(n.getId() + " (" + n.getColor().text() + "), ");
			}
			System.out.println();
	   }
	   System.out.println();
	}
	
	public void printBubbles(){
	   System.out.println("\n***Bubble EDGES***");
		for(Bubble b: bubbles){
			System.out.print("Bubble " + b.getId()+":\t");
			for(Bubble sb : b.getEdges()){
				System.out.print(sb.getId() + " ");
			}
			System.out.println();
		}
	}
}

