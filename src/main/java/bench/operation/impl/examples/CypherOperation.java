package bench.operation.impl.examples;

import java.util.Map;

import bench.operation.Operation;
import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.helpers.collection.IteratorUtil;

public class CypherOperation extends Operation {

	// private String cypherQuery = null;
	private ExecutionEngine engine = null;
	private ExecutionResult result = null;

	// args
	// -> 0 cypherQuery
	@Override
	protected void onInitialize(String[] args) {
		// cypherQuery = args[0];
		engine = new ExecutionEngine(getGraphDescriptor().getDb());
		result = engine.execute(args[0]);
	}

	@Override
	protected void onExecute() {
		Integer resultCount = 0;
		for (Map<String, Object> row : result)
			resultCount += IteratorUtil.count(row.entrySet());
		setResult(resultCount);
	}

}
