package graph;

import java.util.*;

public class Graph {
    List<Node> nodes;
    List<Edge> edges;
    Random random;

    Graph(){
        edges=new ArrayList<>();
        nodes=new ArrayList<>();
        random=new Random();
    }


    public String printListNode(List<Node> nodes){
        if(nodes.isEmpty())
            return "[]";
        String tab="";
        tab+="[";
        int n=nodes.size();
        for(int i=0;i<n;i++){
            tab+=nodes.get(i).getName();
            if(n-1==i){
                tab+="]";
            }else{
                tab+=", ";
            }
        }
        return tab;
    }

    /*
    Example :
      A B C D
    A   R B R
    B     R R
    C   R   R
    D R R R
    */
    final static String tree[][]= new String[][]{
            {"AR","BR","CR","DB","EB","FR","GB","HR"},
            {"","B","","","","","","B"},
            {"R","","B","","","","B",""},
            {"","","","","B","","R",""},
            {"","","R","","B","R","",""},
            {"","","B","","","","",""},
            {"","","","","R","","B",""},
            {"","","","","","","",""},
            {"","","","","","","R",""},
    };
    public void read(String[][] adjacency_matrix){
        int n=adjacency_matrix[0].length;

            for(int j=0;j<n;j++){
                StringBuilder name=new StringBuilder();
                StringBuilder color=new StringBuilder();
                name.append(adjacency_matrix[0][j].charAt(0));
                color.append(adjacency_matrix[0][j].charAt(1));
                nodes.add(new Node(name.toString(),get_color(color.toString())));
            }
            for ( int i=1;i<n+1;i++){
                for(int j=0;j<n;j++){
                    if(!adjacency_matrix[i][j].isEmpty()){
                        Node in =nodes.get(i-1);
                        Node out=nodes.get(j);
                        Color color= get_color(adjacency_matrix[i][j]);
                        Edge edge=new Edge(color,in,out);
                        edges.add(edge);
                        in.addEdgeOut(edge);
                        out.addEdgeIn(edge);
                    }
                }
            }
    }
    public Color get_color(String str){
        if(str.equals("R"))
            return  Color.RED;
        return Color.BLUE;
    }

    public List<Node> red_sequence(){

        List<Node> nodeList=new ArrayList<>();
        for(Node node:nodes)
            nodeList.add(node);
        List<Node> red_sequence= new ArrayList<>();
        int nbReds=getNbRed();
        List<Node> nodeRemoved=new ArrayList<>();
        List<Node> nodesColored=new ArrayList<>();
        while(nbReds!=0){
            Node max_score=null;
            int best_score=Integer.MIN_VALUE;
            for(Node node :get_reds()){
                int score = score(null,node,new ArrayList<>(),new ArrayList<>(),nodeRemoved);
                if(score>best_score){
                    best_score=score;
                    max_score=node;
                }
            }
            if(max_score!=null){
                nodes.remove(max_score);
                nodeRemoved.add(max_score);
                
                red_sequence.add(max_score);
            }
            nbReds=getNbRed();
        }
        nodes=nodeList;
        for(Node nodeColored: nodesColored){
            if(nodeColored.isRed())
                nodeColored.setColor(Color.BLUE);
            else
                nodeColored.setColor(Color.RED);
        }
        return red_sequence;
    }
    public int getNbRed(){
        int n=0;
        for(Node node :nodes)
            if(node.isRed())
                n++;
        return n;
    }

    List<Node> get_reds(){
        List<Node>nodesRed=new ArrayList<>();
        for(Node node :nodes)
            if(node.isRed())
                nodesRed.add(node);
        return nodesRed;
    }


    public int score(Node from,Node to, List<Edge> edgesMarked, List<Node>nodesMarked,List<Node> nodesRemoved){
        nodesMarked.add(to);
        for(Edge edge : to.getEdgesOut()){
            if(!edgesMarked.contains(edge) && !nodesRemoved.contains(edge.getOut())) {
                if (edge.isRed() && edge.getOut().isBlue() &&!nodesMarked.contains(edge.getOut())) {
                    edgesMarked.add(edge);
                    return 1 + score(to,edge.getOut(),edgesMarked,nodesMarked,nodesRemoved);
                }
                if (edge.isBlue() && edge.getOut().isRed()&&!nodesMarked.contains(edge.getOut())) {
                    edgesMarked.add(edge);
                    return score(to,edge.getOut(),edgesMarked,nodesMarked,nodesRemoved) - 1;
                }
            }
        }
        return 0;
    }

    public void construct_with_prob(double p, double q) {
        nodes=new ArrayList<>();
        edges=new ArrayList<>();
        int nNodes = 100;
        for (int j = 0; j < nNodes; j++) {
            Color color = null;
            if (random.nextInt(100) < (int)(p * 100.0)) {
                color = Color.RED;
            } else {
                color = Color.BLUE;
            }

            Node node = new Node(String.valueOf(j), color);
            nodes.add(node);
        }

        for (int i = 1; i < nNodes + 1; i++) {
            for (int j = 0; j < nNodes; j++) {
                Node in = nodes.get(i - 1);
                Node out = nodes.get(j);
                Color color = null;
                if(random.nextInt(100)<(int)(q*100.0)) {
                    color=Color.BLUE;
                }else {
                    color=Color.RED;
                }
                Edge edge = new Edge(color, in, out);
                edges.add(edge);
                in.addEdgeOut(edge);
                out.addEdgeIn(edge);
            }
        }

    }
    
    public List<Node> red_sequence2() {
    	List<Node> redSequence= new ArrayList<>();
	    while(get_reds().size()!=0) {	
	    	Collections.sort(nodes,new NodeScoreComparator());
	    	Node bestNode = nodes.get(0);
	    	if(bestNode.isRed()) {
	    		removeNode(bestNode);
	    		redSequence.add(bestNode);
	    	}else {
	    		List<Node> reds =get_reds();
	    		Collections.sort(reds,new NodeScoreComparator());
	    		boolean remove = false;
	    		for(Node redNode:reds) {
	    			if(redNode.canChangeToRed(bestNode)) {
	    				removeNode(redNode);
	    				redSequence.add(redNode);
	    				remove=true;
	    				break;
	    			}
	    		}
	    		if(!remove) {
	    			redSequence.add(get_reds().get(0));
	    			removeNode(get_reds().get(0));
	    		}
	    	}
	    }
    	return redSequence;
    	
    }
    
    public void removeNode(Node n){
    	 nodes.remove(n);
         for(Edge edge : n.getEdgesOut()){
             if(edge.isRed()) {
                 edge.getIn().setColor(Color.RED);
             }else {
                 edge.getIn().setColor(Color.BLUE);
             }
         }
    }
    
    public String toString() {
    	String s= new String();
    	for(Node n:nodes) {
    		s+=n.toStringWithEdge()+" ";
    	}
    	return s;		
    }
}
