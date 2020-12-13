package graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node {

    private String name;
    private List<Edge> edgesIn;
    private List<Edge> edgesOut;
    private Color color;

    Node(String name,Color color){
        this.name=name;
        this.color=color;
        edgesIn=new ArrayList<>();
        edgesOut=new ArrayList<>();
    }

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
}
