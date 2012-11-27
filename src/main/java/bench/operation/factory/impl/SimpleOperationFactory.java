package bench.operation.factory.impl;

import bench.operation.Operation;
import bench.operation.OperationDescriptor;
import bench.operation.OperationFactory;

public class SimpleOperationFactory extends OperationFactory {

	private Class<? extends Operation> operationType = null;
	private int opCount = -1;
	private String[] args = null;
	private String name = null;

	public SimpleOperationFactory(Class<? extends Operation> operationType) {
		this(operationType, -1, new String[] {}, "nameless");
	}

	// opCount = -1 -> infinite
	// opCount > -1 -> finite
	public SimpleOperationFactory(Class<? extends Operation> operationType,
			int opCount, String[] args, String name) {
		this.operationType = operationType;
		this.opCount = opCount;
		this.args = args;
		this.name = name;
	}

	@Override
	public void onInitialize() {
	}

	@Override
	protected OperationDescriptor nextOperation() {
		if (-1 != opCount)
			opCount--;
		return createOperationDescriptor(args, operationType, name);
	}

	@Override
	protected boolean hasNextOperation() {
		return (-1 == opCount) ? true : (opCount > 0);
	}
}
