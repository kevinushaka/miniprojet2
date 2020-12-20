package graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node {

    private String name;
    private List<Edge> edgesIn;
    private List<Edge> edgesOut;
    private Color color;
    private  boolean marked=false;

    public Node(String name, Color color){
        this.name=name;
        this.color=color;
        edgesIn=new ArrayList<>();
        edgesOut=new ArrayList<>();
    }

    public  void mark(){this.marked=true;}

    public boolean is_marked(){return  marked;}

    public List<Edge> getEdgesIn() {
        return edgesIn;
    }

    public List<Edge> getEdgesOut() {
        return edgesOut;
    }

    public void addEdgeIn(Edge edge){edgesIn.add(edge);}

    public void addEdgeOut(Edge edge){edgesOut.add(edge);}

    public String getName() {
        return name;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(name, node.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public boolean isRed(){return color==Color.RED;}
    public boolean isBlue(){return color==Color.BLUE;}
    
    public String toString() {
    	return name;
    }
    
    public String toStringWithEdge() {
    	String s= new String();
    	for(Edge e:edgesIn) {
    		s+= color.name() +" "+ e.getColor().name()+" "+e.getOut().color.name()+" \n";
    		s+= name+" <- "+e.getOut()+" \n";
    	}
    	for(Edge e:edgesOut) {
    		s+= color.name() +" "+ e.getColor().name()+" "+e.getIn().color.name()+" \n";
    		s+= name+"->"+e.getIn()+" \n";
    	}
    	return s;
    }
    
    public int score2() {
    	int score =0;
    		for(Edge e:edgesOut) {
    			if(e.isRed()) {
    				if(e.getOut().isBlue()) {
    					score+=2;
    				}else {
    					score++;
    				}
    			}else if(e.isBlue() && e.getOut().isRed()) {
    				score--;
    			}
    		}
    	return score;
    }
    
   public boolean canChangeToRed(Node n2){
	   for(Edge e:edgesOut) {
		   if(e.isRed() && e.getOut().name.equals(n2.name)) {
			   return true;
		   }
	   }
	   return false;
   }
}
