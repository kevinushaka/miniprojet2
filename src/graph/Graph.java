package graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Graph {
    List<Node> nodes;
    List<Edge> edges;

    Graph(){
        edges=new ArrayList<>();
        nodes=new ArrayList<>();
    }

    public static void main(String[] args) {
        Graph graph=new Graph();
        graph.read(tree);
    }

    /*
    Example :
      A B C D
    A B R B R
    B   R R R
    C   R B R
    D R R R R
    */
    final static String tree[][]= new String[][]{
            {"AR","BR","CB","DB"},
            {"B","R","B","R"},
            {"","R","R","R"},
            {"B","R","B","R"},
            {"","R","R","R"},
            {"R","R","B","B"}
    };
    public void read(String[][] adjacency_matrix){
        int n=adjacency_matrix[0].length;

            for(int j=0;j<n;j++){
                StringBuilder name=new StringBuilder();
                StringBuilder color=new StringBuilder();
                name.append(adjacency_matrix[0][1].charAt(0));
                color.append(adjacency_matrix[0][1].charAt(0));
                nodes.add(new Node(adjacency_matrix[0][j],get_color(color.toString())));
            }
            for ( int i=1;i<n;i++){
                for(int j=0;j<n;j++){
                    if(!adjacency_matrix[i][j].isEmpty()){
                        Node in =nodes.get(i);
                        Node out=nodes.get(j);
                        Color color= get_color(adjacency_matrix[i][j]);
                        Edge edge=new Edge(color,in,out);
                        edges.add(edge);
                        in.addEdge(edge);
                    }
                }
            }
    }
    public Color get_color(String str){
        if(str.equals("R"))
            return  Color.RED;
        return Color.BLUE;
    }

    public List<Node>red_sequence(){
        int best_score=0;
        List<Node> nodeList=new ArrayList<>();
        for(Node node:nodes)
            nodeList.add(node);
        List<Node> red_sequence= new ArrayList<>();
        while(getNbRed()!=red_sequence.size()){
            for(Node node :nodes){
                
            }
        }
        nodes=nodeList;
        return new ArrayList<>();
    }
    public int getNbRed(){
        int n=0;
        for(Node node :nodes)
            if(node.isRed())
                n++;
        return n;
    }


    public int score(Node node, Set<Edge> marked){
        for(Edge edge : node.getEdges()){
            if(!marked.contains(edge)) {
                if (edge.isRed() && edge.getOut().isBlue()) {
                    marked.add(edge);
                    return 1 + score(edge.getOut(),marked);
                }
                if (edge.isBlue() && edge.getOut().isRed()) {
                    marked.add(edge);
                    return score(edge.getOut(),marked) - 1;
                }
            }
        }
        return 0;
    }

}
