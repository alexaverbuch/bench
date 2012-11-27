package bench.operation;

import java.lang.reflect.Constructor;

import org.neo4j.graphdb.GraphDatabaseService;

import bench.GraphDescriptor;
import bench.StatisticsHelper;
import bench.errors.OperationException;

public abstract class Operation {

	// Implement these

	protected abstract void onInitialize(String[] args)
			throws OperationException;

	protected abstract void onExecute() throws OperationException;

	// Utils

	private int opId = -1;
	private String[] args = null;
	private long time = -1;
	private Object result = null;
	private GraphDescriptor graphDescriptor = null;
	private String name = null;

	public static final Operation create(OperationDescriptor opDesc)
			throws OperationException {
		return create(opDesc.getId(), opDesc.getType(), opDesc.getArgs(),
				opDesc.getName());
	}

	public static final Operation create(int opId, String type, String[] args,
			String name) throws OperationException {
		Constructor<?> operationConstructor = null;
		Operation operation = null;
		try {
			operationConstructor = Class.forName(type).getConstructors()[0];
			operation = (Operation) operationConstructor
					.newInstance(new Object[] {});
		} catch (Exception e) {
			throw new OperationException("Error while instantiating operation",
					e.getCause());
		}
		operation.setId(opId);
		operation.setArgs(args);
		operation.setName(name);
		return operation;
	}

	/*
	 * Setter Methods
	 */

	public final void setId(int opId) {
		this.opId = opId;
	}

	public final void setArgs(String[] args) {
		this.args = args;
	}

	public final void setName(String name) {
		this.name = name;
	}

	protected final void setResult(Object result) {
		this.result = result;
	}

	/*
	 * Getter Methods
	 */

	public final int getId() {
		return opId;
	}

	public final String[] getArgs() {
		return args;
	}

	public final long getTime() {
		return time;
	}

	public final Object getResult() {
		return result;
	}

	protected final GraphDescriptor getGraphDescriptor() {
		return graphDescriptor;
	}

	protected final GraphDatabaseService getDb() {
		return graphDescriptor.getDb();
	}

	public final String getName() {
		return name;
	}

	public final String getType() {
		return getClass().getName();
	}

	@Override
	public String toString() {
		return String.format("Operation[ID:%s][NAME:%s]", getId(), getName());
	}

	/*
	 * Event Methods
	 */

	public final void initialize(GraphDescriptor graphDescriptor)
			throws OperationException {
		this.graphDescriptor = graphDescriptor;
		onInitialize(args);
	}

	public final void execute() throws OperationException {
		StatisticsHelper.startWatch();
		onExecute();
		time = StatisticsHelper.stopWatch();
	}
}