package bench.operation.factory.impl;

import java.io.File;
import java.util.Iterator;

import bench.LogUtils;
import bench.log.OperationLogEntry;
import bench.operation.OperationDescriptor;
import bench.operation.OperationFactory;

public final class LogOperationFactory extends OperationFactory {

	private Iterator<OperationLogEntry> operationLogIterator = null;

	public LogOperationFactory(File file) {
		operationLogIterator = LogUtils.getOperationLogReader(file).iterator();
	}

	@Override
	protected void onInitialize() {
	}

	@Override
	protected OperationDescriptor nextOperation() {
		return operationLogIterator.next();
	}

	@Override
	protected boolean hasNextOperation() {
		return operationLogIterator.hasNext();
	}

}