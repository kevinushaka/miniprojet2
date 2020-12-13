package graph;

import java.util.Comparator;

public class NodeScoreComparator implements Comparator<Node>{



	@Override
	public int compare(Node o1, Node o2) {
		// TODO Auto-generated method stub
		return o1.score2()-o2.score2();
	}

}
