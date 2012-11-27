package bench.operation.impl;

import bench.operation.Operation;

public class OperationDeleteGraph extends Operation {

	@Override
	protected void onInitialize(String[] args) {
	}

	@Override
	protected void onExecute() {
		getGraphDescriptor().deleteDb();
		setResult("DONE");
	}

}
