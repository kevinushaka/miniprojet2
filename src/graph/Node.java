package graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node {

    private String name;
    private List<Edge> edges;
    private Color color;

    Node(String name,Color color){
        this.name=name;
        this.color=color;
        edges=new ArrayList<>();
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void addEdge(Edge edge){edges.add(edge);}

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
