package bench.operation.impl;

import bench.operation.Operation;

public class OperationOpenGraph extends Operation {

	@Override
	protected void onInitialize(String[] args) {
	}

	@Override
	protected void onExecute() {
		getGraphDescriptor().openDb();
		setResult("DONE");
	}

}
