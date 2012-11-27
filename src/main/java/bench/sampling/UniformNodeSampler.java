package bench.sampling;

import org.neo4j.graphdb.Node;

public class UniformNodeSampler extends NodeSampler {

	@Override
	public double evaluate(Node n) {
		return 1d;
	}
}