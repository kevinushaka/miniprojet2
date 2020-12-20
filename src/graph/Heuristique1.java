package graph;

import java.util.*;
import java.util.function.Supplier;

public class Heuristique1 extends Heuristique {
    List<Node> nodes_red;
    List<Node> nodes_blue;
    List<Node> nodes;
    int nbRedEdges=0;
    int nbBLueEdges=0;

    Heuristique1(float p, float q){
        nodes_red=new ArrayList<>();
        nodes_blue=new ArrayList<>();
        nodes=new ArrayList<>();
        construct_with_prob(p,q);
    }



    @Override
    public List<Node> red_sequence(){

        List<Node> red_sequence= new ArrayList<>();
        while(nodes_red.size()!=0){
            Node max_score_node=null;
            int best_score=Integer.MIN_VALUE;
            for(Node node :nodes_red){
                Score score = score(node);
                if(score.score()>=best_score){
                    best_score=score.score();
                    max_score_node=node;
                };
            }
            if(max_score_node!=null){
            removeNode(max_score_node);
            red_sequence.add(max_score_node);
            }

        }
        return red_sequence;
    }
    public void removeNode(Node node){
        node.mark();
        nodes_red.remove(node);
        for(Edge edge : node.getEdgesOut()){
            Node otherNode= edge.getOut();
            if(!otherNode.is_marked()) {
                if (edge.isRed() && otherNode.isBlue()) {
                    otherNode.setColor(Color.RED);
                    nodes_red.add(otherNode);
                    nodes_blue.remove(otherNode);
                } else if (edge.isBlue() && otherNode.isRed()) {
                    otherNode.setColor(Color.BLUE);
                    nodes_blue.add(otherNode);
                    nodes_red.remove(otherNode);
                }
            }

        }
    }


    public Score score(Node node){
        Score score =new Score();
        for(Edge edge : node.getEdgesOut()){
            Node otherNode=edge.getOut();
            if(!otherNode.is_marked()) {
                if (edge.isRed() && otherNode.isBlue()) {
                    score.increase_transform_red();
                }
                if (edge.isBlue() && otherNode.isRed()) {
                    score.increase_transform_blue();
                }
            }

        }
        return score;
    }
    class Score{
        int transform_red=0;
        int transform_blue=0;
        void increase_transform_red(){transform_red++;}
        void increase_transform_blue(){transform_blue++;}
        int score(){return transform_red-transform_blue;}
    }

    public void construct_with_prob(double p, double q) {
        nodes=new ArrayList<>();
        nodes_blue=new ArrayList<>();
        nodes_red=new ArrayList<>();
        nbBLueEdges=0;
        nbRedEdges=0;
        int nNodes = 100;
        for (int i = 0; i < nNodes; i++) {
            Node node;
            Color color =withProbability(()->Color.RED,()->Color.BLUE,p);
            if(color==Color.BLUE) {
                node=new Node(String.valueOf(i), Color.BLUE);
                nodes_blue.add(node);
            } else {
                node=new Node(String.valueOf(i), Color.RED);
                nodes_red.add(node);}


                nodes.add(node);
        }

        for (int i = 0; i < nNodes ; i++) {
            for (int j = 0; j < nNodes; j++) {
                if(i!=j){
                    Node in = nodes.get(i );
                    Node out = nodes.get(j);
                    Color color =withProbability(()->Color.BLUE,()->Color.RED,q);
                    if(color==Color.BLUE){
                        nbBLueEdges++;
                    }else{nbRedEdges++;}
                    Edge edge = new Edge(color, in, out);
                    in.addEdgeOut(edge);
                    out.addEdgeIn(edge);
                }
            }
        }

    }


    List<Node> get_reds(){
        List<Node>nodesRed=new ArrayList<>();
        for(Node node :nodes)
            if(node.isRed())
                nodesRed.add(node);
        return nodesRed;
    }


    public String toString() {
    	String s= new String();
    	for(Node n:nodes) {
    		s+=n.toStringWithEdge()+" ";
    	}
    	return s;		
    }

    SplittableRandom random=new SplittableRandom();
    public Color withProbability(Supplier<Color> positiveCase, Supplier<Color> negativeCase, double probability) {
        if (random.nextInt(1, 101) <= probability*100.0f) {
            return positiveCase.get();
        } else {
            return negativeCase.get();
        }
    }
}
