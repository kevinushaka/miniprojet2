package graph;

public class Main {

    public static void main(String[] args) {

		Graph g= new CompleteGraph(0.5f, 0.5f);
		System.out.println(g);
        for(float p=0; p<1;p+=0.1f) {
			for(float q=0; q<1;q+=0.1f) {
				Graph g2= new CompleteGraph(p, q);
				System.out.print(g2.red_sequence2().size()+" ");
			}	
			System.out.println();
        }
        //graph.construct_with_prob(0.1,0.4);
        //List<Node> nodeList=graph.red_sequence();
        //System.out.println(graph.printListNode(nodeList));
    }

    
}
