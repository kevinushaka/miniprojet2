package graph;

public class Edge {

    private Color color;
    private Node in;
    private Node out;
    public Edge(Color color, Node in, Node out){
        this.color=color;
        this.in=in;
        this.out=out;
    }

    public boolean isRed(){return color==Color.RED;}
    public boolean isBlue(){return color==Color.BLUE;}

    public Node getOut() {
        return out;
    }
    public Node getIn() {
    	return in;
    }
    public Color getColor() {
    	return color;
    }
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
