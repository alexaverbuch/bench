package bench.sampling;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.tooling.GlobalGraphOperations;

public abstract class NodeSampler {

	private double total = -1d;

	public double evaluateTotal(GraphDatabaseService db) {
		if (total != -1d)
			return total;

		total = 0d;
		for (Node n : GlobalGraphOperations.at(db).getAllNodes())
			total += evaluate(n);

		return total;
	}

	public abstract double evaluate(Node n);
}
