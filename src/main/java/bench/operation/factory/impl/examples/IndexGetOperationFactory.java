package bench.operation.factory.impl.examples;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.graphdb.Node;

import bench.errors.OperationFactoryException;
import bench.operation.OperationDescriptor;
import bench.operation.factory.impl.SamplingOperationFactory;
import bench.operation.impl.examples.IndexGetOperation;
import bench.sampling.NodeSampler;

public class IndexGetOperationFactory extends SamplingOperationFactory {

	private String propertyKey = null;
	private int lookupPerOp = 0;
	private String tag = null;

	public IndexGetOperationFactory(int opCount, NodeSampler evaluator,
			String propertyKey, int lookupPerOp, String tag) {
		super(opCount, evaluator, IndexGetOperation.class);
		this.propertyKey = propertyKey;
		this.lookupPerOp = lookupPerOp;
		this.tag = tag;
	}

	@Override
	protected OperationDescriptor nextOperation()
			throws OperationFactoryException {
		List<String> propertyKeys = new ArrayList<String>();
		List<String> propertyValues = new ArrayList<String>();

		for (int i = 0; i < lookupPerOp; i++) {
			long nodeId = getNodeIdSamples().remove(0);
			Node node = getGraphDescriptor().getDb().getNodeById(nodeId);

			if (node == null)
				throw new OperationFactoryException(String.format(
						"Node[%d] not found", nodeId));

			propertyKeys.add(propertyKey);
			propertyValues.add(node.getProperty(propertyKey).toString());
		}

		String operationName = ("".equals(tag)) ? Integer.toString(lookupPerOp)
				: tag + "-" + Integer.toString(lookupPerOp);

		// args = [property key, property value]
		String[] args = new String[] {
				propertyKeys.toString().replaceAll("[\\[ \\]]", ""),
				propertyValues.toString().replaceAll("[\\[ \\]]", "") };

		return createOperationDescriptor(args, getOperationType(),
				operationName);
	}

}
