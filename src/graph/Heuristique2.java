package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Heuristique2 extends Heuristique{

    List<Node> nodes;
    List<Edge> edges;
    Random random;

    public String toString() {
        String s= new String();
        for(Node n:nodes) {
            s+=n.toStringWithEdge()+" ";
        }
        return s;
    }

    public Heuristique2(float redNodePercentage, float blueEdgePercentage) {
        edges = new ArrayList<>();
        nodes = new ArrayList<>();
        random = new Random();
        for(int i=0; i<100;i++) {
            nodes.add(new Node(i+"", colorNode(redNodePercentage)));
        }
        for(int i=0; i<nodes.size();i++) {
            for(int j=i+1; j<nodes.size();j++) {
                Node first= nodes.get(i);
                Node second = nodes.get(j);

                Edge firstToSecond = new Edge(colorEdge(blueEdgePercentage), first, second);
                Edge secondToFirst =  new Edge(colorEdge(blueEdgePercentage),second,first);
                //System.out.println("Edge1"+firstToSecond.isRed());
                //System.out.println("Edge2"+firstToSecond.isRed());
                first.addEdgeIn(firstToSecond);
                first.addEdgeOut(secondToFirst);
                second.addEdgeIn(secondToFirst);
                second.addEdgeOut(firstToSecond);
            }
        }
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


    List<Node> get_reds(){
        List<Node>nodesRed=new ArrayList<>();
        for(Node node :nodes)
            if(node.isRed())
                nodesRed.add(node);
        return nodesRed;
    }

    @Override
    public List<Node> red_sequence() {
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
}