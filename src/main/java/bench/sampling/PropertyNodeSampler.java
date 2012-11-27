package bench.sampling;

import org.neo4j.graphdb.Node;

public class PropertyNodeSampler extends NodeSampler {

	private String propertyName = null;

	public PropertyNodeSampler(String propertyName) {
		this.propertyName = propertyName;
	}

	@Override
	public double evaluate(Node n) {
		return (Double) n.getProperty(propertyName);
	}
}