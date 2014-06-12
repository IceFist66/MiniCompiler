/*
Note: Look at LiveNow.Remove(target) for correctness.
*/

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
    private int spillSpace;
	
	public IGraph(ArrayList<Node> nodes) {
		this.bubbles = new ArrayList<Bubble>();
		this.colors = createColorSet();
		this.nodes = nodes;
        this.spillSpace = 0;
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

    public void setSpillSpace(){
        if(spillSpace == 0){        
            for(Bubble b: bubbles){
                if(b.getColor() == Color.UNC){
                    spillSpace++;
                    b.setSpill(spillSpace);
                }
            }
        }
    }

    public int getSpillSpace(){
        return this.spillSpace;
    }

    public void createGraph(){
		ArrayList<Instruction_a> reverse = new ArrayList<Instruction_a>();
		ArrayList<String> liveNow = new ArrayList<String>();
		ArrayList<String> targets = new ArrayList<String>();
		ArrayList<String> sources = new ArrayList<String>();
		int numColors = colors.size();
		
        //System.out.println("Size of nodes: " + nodes.size());
		for(Node n : nodes){
            reverse.clear(); //make sure to clean reverse from previous Node
			reverse.addAll(n.getAsmInstructions());
			Collections.reverse(reverse);
			liveNow.clear(); //make sure to clean LiveNow from previous Node
			liveNow.addAll(n.getLiveOut()); //get LiveOut and save to LiveNow
			for(Instruction_a a : reverse){
				targets = a.getTargets();
                //System.out.println(a.toString() + " Instruciton target size: " + a.getTargets().size());
                for(String target : targets){
				    liveNow.remove(target);
                    if(target != null && (target.charAt(0) == 'r' || target.charAt(0) == '%')){
				        addBubble(target);
                    }
                }
		        sources = a.getSources();
		        for(String source : sources){
                    if(source != null && (source.charAt(0) == 'r' || source.charAt(0) == '%')){
                        //liveNow.add(source); // move down for loop target : targeta THIS IS WRONG!!!
			            addBubble(source);
                        //System.out.println("Added a bubble: " + source);
                    }
                    
		        }
                for(String target : targets){
		            Bubble btarget = getBubble(target);
		            if(btarget != null){ //some instructions have no targets and return null or are not registers
			            for(String s : liveNow){
				            Bubble bsource = getBubble(s);
				            if(bsource != null){ //some instruction have no sources and return null or are not registers
					            if(!btarget.getEdges().contains(bsource) && !btarget.getId().equals(bsource.getId())) {
						            btarget.getEdges().add(bsource);
						            if (btarget.getEdges().size() >= numColors){
                                        if (btarget.isConstrained() == false){
                                            btarget.setConstrained(true);
                                        }
                                    }
						        }
					            if(!bsource.getEdges().contains(btarget) && !bsource.getId().equals(btarget.getId())) {
						            bsource.getEdges().add(btarget);
						            if (bsource.getEdges().size() >= numColors){
                                        if (bsource.isConstrained() == false){
                                            bsource.setConstrained(true);
                                        }
                                    }
						        }
				            }
				            else{
					            //System.out.println("BSource is null: " + s);
				            }
			            }
		            }
		            else{
			            //System.out.println("BTarget is null!!: "+ target);
		            }
                }
                 // loop over all sources in the instruction and add to LiveNow
               for(String source : sources){
                 liveNow.add(source);
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
	
	   int numColors = colors.size() - 5; // save %r14 and %r15 for temporary use and spills, don't use %rbp or %rsp, and Color.UNC is not a valid coloring
	   Stack<Bubble> stackOfBubbles = new Stack<Bubble>();
	   ArrayList<Bubble> copy = new ArrayList<Bubble>(bubbles); //makes an unaltering copy
	          
	      // color the predefined registers
	      for (Bubble b : bubbles) {
	         if (b.isPredefined() == true) {
	            for (Color c : colors)
	               if (b.getId() == c.text())
	                  b.setColor(c);
	         }
	      }
	   
	      // push the unconstrained, unpredefined bubbles first
	      for (Bubble b : bubbles) {
	         if (b.isConstrained() == false && b.isPredefined() == false) {
	            copy.remove(b);
               stackOfBubbles.push(b);            
            }
	      }
	      
	      // now sort the constrained bubbles in descending order by degree
	      Collections.sort(copy);
	      
	      // push the now ordered set of constrained, unpredefined bubbles
	      for (Bubble c : copy) {
              if (c.isPredefined() == false){
                  //DO NOT REMOVE FROM COPY (won't compile)
                  stackOfBubbles.push(c);
              }
	      }
	      
	      // push the now ordered set of constrained and/or predefined bubbles
	      for (Bubble p : copy) {
              if (p.isPredefined() == true){
                  //DO NOT REMOVE FROM COPY (won't compile)
                  stackOfBubbles.push(p);
              }
	      }
	      
	      // empty copy (will be used to store results of coloring)
	      copy.clear();
	      
	      // now add the bubbles back to the graph and try to color
	      // assign Color.UNC to bubbles that can't be colored
	      boolean done, conflict;
	      int count;
	      
	      while (!stackOfBubbles.empty()) {
	         done = false;        // attempted coloring of one bubble
	         conflict = false;    // conflict for a particular color for a particular bubble
	         count = 0;           // color number
            int uncolored_count = 0;
	         Bubble b = stackOfBubbles.pop();
	         Color color;
	         if (b.getColor() == Color.UNC) { // if the bubble is already colored, don't attempt to color
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
                 if (conflict == true){
                     b.setColor(Color.UNC);
                     b.setUncCount(++uncolored_count);
                 }
	         }
	         // the bubble is colored and can now be added back to the graph
	         copy.add(b);
	      }
	      
	      // move the copy with colored bubbles back into bubbles
	      bubbles.clear();
	      bubbles.addAll(copy);
          setSpillSpace();
	      	      
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

