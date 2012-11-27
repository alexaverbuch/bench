package bench.operation.factory.impl;

import java.util.Arrays;
import java.util.List;

import bench.StatisticsHelper;
import bench.operation.Operation;
import bench.operation.OperationFactory;
import bench.sampling.NodeSampler;

public abstract class SamplingOperationFactory extends OperationFactory {

	private List<Long> nodeIdSamples = null;
	private int opCount = -1;
	private final NodeSampler evaluator;
	private final Class<? extends Operation> operationType;

	public SamplingOperationFactory(int opCount, NodeSampler evaluator,
			Class<? extends Operation> operationType) {
		this.opCount = opCount;
		this.evaluator = evaluator;
		this.operationType = operationType;
	}

	@Override
	protected final void onInitialize() {
		nodeIdSamples = Arrays.asList(StatisticsHelper.getSampleNodeIds(
				getGraphDescriptor().getDb(), evaluator, opCount));
	}

	protected List<Long> getNodeIdSamples() {
		return nodeIdSamples;
	}

	protected final Class<? extends Operation> getOperationType() {
		return operationType;
	}

	@Override
	protected final boolean hasNextOperation() {
		return nodeIdSamples.isEmpty() == false;
	}

}
