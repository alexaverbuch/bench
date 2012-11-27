package bench.operation;

import java.util.Iterator;

import bench.GraphDescriptor;
import bench.errors.OperationException;
import bench.errors.OperationFactoryException;

public abstract class OperationFactory implements Iterable<Operation> {

	// Implement these

	protected abstract void onInitialize() throws OperationFactoryException;

	protected abstract OperationDescriptor nextOperation()
			throws OperationFactoryException;

	protected abstract boolean hasNextOperation()
			throws OperationFactoryException;

	// Utils

	private GraphDescriptor graphDescriptor = null;
	private int opId = 0;

	protected final OperationDescriptor createOperationDescriptor(
			String[] args, Class<? extends Operation> type, String name) {
		return new OperationDescriptor(opId + 1, args, type.getName(), name);
	}

	public final int getCurrentOpId() {
		return opId;
	}

	protected final GraphDescriptor getGraphDescriptor() {
		return graphDescriptor;
	}

	public final void initialize(GraphDescriptor graphDescriptor, int firstOpId)
			throws OperationFactoryException {
		this.graphDescriptor = graphDescriptor;
		this.opId = firstOpId;
		onInitialize();
	}

	@Override
	public final Iterator<Operation> iterator() {
		return new OperationIterator();
	}

	private final class OperationIterator implements Iterator<Operation> {

		@Override
		public boolean hasNext() {
			try {
				return hasNextOperation();
			} catch (OperationFactoryException e) {
				throw new RuntimeException(
						"Error testing for existence of next operation",
						e.getCause());
			}
		}

		@Override
		public Operation next() {
			OperationDescriptor opDesc = null;
			try {
				opDesc = nextOperation();
				opId = opDesc.getId();
				return Operation.create(opDesc);
			} catch (OperationFactoryException e) {
				throw new RuntimeException("Error loading next operation: "
						+ opDesc, e.getCause());
			} catch (OperationException e) {
				throw new RuntimeException("Error instantiating operation: "
						+ opDesc, e.getCause());
			}
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

}
