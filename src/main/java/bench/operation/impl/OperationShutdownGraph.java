package bench.operation.impl;

import bench.operation.Operation;

public class OperationShutdownGraph extends Operation {

	@Override
	protected void onInitialize(String[] args) {
	}

	@Override
	protected void onExecute() {
		getGraphDescriptor().shutdownDb();
		setResult("DONE");
	}

}
