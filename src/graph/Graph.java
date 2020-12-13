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
        List<Node> nodeList=graph.red_sequence();
        System.out.println(nodeList);
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

    public List<Node>red_sequence(){

        List<Node> nodeList=new ArrayList<>();
        for(Node node:nodes)
            nodeList.add(node);
        List<Node> red_sequence= new ArrayList<>();
        int nbReds=getNbRed();
        while(nbReds!=red_sequence.size()){
            Node max_score=null;
            int best_score=Integer.MIN_VALUE;
            for(Node node :get_reds()){
                int score = score(null,node,new ArrayList<>(),new ArrayList<>());
                if(score>best_score){
                    best_score=score;
                    max_score=node;
                }
            }
            if(max_score!=null){
                nodes.remove(max_score);
                red_sequence.add(max_score);
            }
        }
        nodes=nodeList;
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


    public int score(Node from,Node to, List<Edge> edgesMarked, List<Node>nodesMarked){
        nodesMarked.add(to);
        for(Edge edge : to.getEdgesOut()){
            if(!edgesMarked.contains(edge)) {
                if (edge.isRed() && edge.getOut().isBlue() &&!nodesMarked.contains(edge.getOut())) {
                    edgesMarked.add(edge);
                    return 1 + score(to,edge.getOut(),edgesMarked,nodesMarked);
                }
                if (edge.isBlue() && edge.getOut().isRed()&&!nodesMarked.contains(edge.getOut())) {
                    edgesMarked.add(edge);
                    return score(to,edge.getOut(),edgesMarked,nodesMarked) - 1;
                }
            }
        }
        return 0;
    }

}
