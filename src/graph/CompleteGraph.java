package graph;

public class CompleteGraph extends Graph{
	
	public CompleteGraph(float redNodePercentage,float blueEdgePercentage) {
		super();
		for(int i=0; i<5;i++) {
			nodes.add(new Node(i+"", colorNode(redNodePercentage)));
		}
		for(int i=0; i<nodes.size();i++) {
			for(int j=i+1; j<nodes.size();j++) {
				Node first= nodes.get(i);
				Node second = nodes.get(j);
				
				Edge firstToSecond = new Edge(colorEdge(blueEdgePercentage), first, second);
				Edge secondToFirst =  new Edge(colorEdge(blueEdgePercentage),second,first);
				first.addEdgeIn(firstToSecond);
				first.addEdgeOut(secondToFirst);
				second.addEdgeIn(secondToFirst);
				second.addEdgeOut(firstToSecond);
			}	
		}
	}
	
	@Override
	public void read(String[][] adjacency_matrix) {
	}
	
	public Color colorNode(float redNodePercentage) {
		if(Math.random()<redNodePercentage) {
			return Color.RED;
		}
		return Color.BLUE;
	}
	public Color colorEdge(float blueEdgePercentage) {
		if(Math.random()<blueEdgePercentage) {
			return Color.BLUE;
		}
		return Color.RED;
	}
}
