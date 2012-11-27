package bench.sampling;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.helpers.collection.IteratorUtil;

public class InDegreeNodeSampler extends NodeSampler {

	@Override
	public double evaluate(Node n) {
		return IteratorUtil.count(n.getRelationships(Direction.INCOMING));
	}
}