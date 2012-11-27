package bench.operation.factory.impl.examples;

import bench.operation.OperationDescriptor;
import bench.operation.factory.impl.SamplingOperationFactory;
import bench.operation.impl.examples.CypherOperation;
import bench.sampling.NodeSampler;

public class CypherOperationFactory extends SamplingOperationFactory {

	private String tag = null;
	// Cypher Query Closure
	// E.g. "start n=node(%s) return n"
	private String cypherStringMinusStartNode = null;

	public CypherOperationFactory(int opCount, NodeSampler evaluator, String tag,
			String cypherQueryStringMinusStartNode) {
		super(opCount, evaluator, CypherOperation.class);
		this.tag = tag;
		this.cypherStringMinusStartNode = cypherQueryStringMinusStartNode;
	}

	@Override
	protected OperationDescriptor nextOperation() {
		long nodeId = getNodeIdSamples().remove(0);
		String cypherQuery = String.format(cypherStringMinusStartNode, nodeId);

		String operationName = ("".equals(tag)) ? cypherQuery : tag + "-"
				+ cypherQuery;

		// args
		// -> 0 cypherQuery
		String[] args = new String[] { cypherQuery };

		return createOperationDescriptor(args, getOperationType(),
				operationName);
	}

}
