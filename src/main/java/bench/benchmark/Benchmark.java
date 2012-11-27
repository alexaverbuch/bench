package bench.benchmark;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import bench.BenchRunner;
import bench.GraphDescriptor;
import bench.errors.LogWriterException;
import bench.errors.OperationException;
import bench.errors.OperationFactoryException;
import bench.operation.OperationFactory;
import bench.operation.factory.impl.LogOperationFactory;

public abstract class Benchmark {

	// Implement these

	protected abstract List<OperationFactory> getOperationFactories();

	// Utils

	private String logPath = null;

	public Benchmark(String logPath) {
		this.logPath = logPath;
	}

	private final void createOperationLogs() throws LogWriterException,
			OperationException, OperationFactoryException {
		GraphDescriptor graphDescriptor = new GraphDescriptor(
				new HashMap<String, String>());
		List<OperationFactory> opFactories = getOperationFactories();
		BenchRunner benchRunner = new BenchRunner(graphDescriptor, new File(
				logPath), opFactories.toArray(new OperationFactory[opFactories
				.size()]));
		benchRunner.startBench();
	}

	public final void loadOperationLogs(GraphDescriptor graphDescriptor,
			String logOut) throws LogWriterException, OperationException,
			OperationFactoryException {
		// TODO maybe remove this and just throw an Exception
		if (new File(logPath).exists() == false)
			createOperationLogs();

		OperationFactory operationFactory = new LogOperationFactory(new File(
				logPath));

		BenchRunner benchRunner = new BenchRunner(graphDescriptor, new File(
				logOut), operationFactory);

		benchRunner.startBench();
	}

}