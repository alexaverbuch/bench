package bench.operation.impl.examples;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.Index;

import bench.errors.OperationException;
import bench.operation.Operation;

public class IndexGetOperation extends Operation {

	private String[] propertyKeys = null;
	private String[] propertyValues = null;

	// args
	// -> 0 property keys
	// -> 1 property values
	@Override
	protected void onInitialize(String[] args) {
		this.propertyKeys = args[0].split(",");
		this.propertyValues = args[1].split(",");
	}

	@Override
	protected void onExecute() throws OperationException {
		Index<Node> nodeIndex = getGraphDescriptor().getDb().index()
				.forNodes("nodes");
		List<Node> nodes = new ArrayList<Node>();

		for (int i = 0; i < propertyKeys.length; i++)
			for (Node node : nodeIndex.get(propertyKeys[i], propertyValues[i]))
				nodes.add(node);

		setResult(nodes);

	}
}
