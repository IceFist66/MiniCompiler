import java.util.ArrayList;
import java.util.Collections;

import iloc.*;
import asm.*;


public class IGraph {
	private ArrayList<Bubble> bubbles;
	private ArrayList<Integer> colors;
	private ArrayList<Node> nodes;
	
	public IGraph(ArrayList<Node> nodes) {
		this.bubbles = new ArrayList<Bubble>();
		this.colors = new ArrayList<Integer>();
		this.nodes = nodes;
        createGraph();
	}
	
	public ArrayList<Bubble> getBubbles() {
		return bubbles;
	}
	public void setBubbles(ArrayList<Bubble> bubbles) {
		this.bubbles = bubbles;
	}
	public ArrayList<Integer> getColors() {
		return colors;
	}
	public void setColors(ArrayList<Integer> colors) {
		this.colors = colors;
	}
	
	public void createGraph(){
		ArrayList<Instruction_a> reverse = new ArrayList<Instruction_a>();
		ArrayList<String> liveNow = new ArrayList<String>();
		String target;
		ArrayList<String> sources = new ArrayList<String>();
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
							    if(!btarget.getEdges().contains(bsource))
								    btarget.getEdges().add(bsource);
							    if(!bsource.getEdges().contains(btarget))
								    bsource.getEdges().add(btarget);
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
	
	public void printBubbles(){
		for(Bubble b: bubbles){
			System.out.print("Bubble " + b.getId()+": ");
			for(Bubble sb : b.getEdges()){
				System.out.print(sb.getId() + " ");
			}
			System.out.println();
		}
	}
}

