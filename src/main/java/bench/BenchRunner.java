package bench;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import bench.errors.LogWriterException;
import bench.errors.OperationException;
import bench.errors.OperationFactoryException;
import bench.log.OperationLogWriter;
import bench.operation.Operation;
import bench.operation.OperationFactory;
import bench.operation.factory.impl.LogOperationFactory;
import bench.operation.factory.impl.SimpleOperationFactory;
import bench.operation.impl.OperationOpenGraph;
import bench.operation.impl.OperationShutdownGraph;

public class BenchRunner {
	public static Logger logger = Logger.getLogger(BenchRunner.class);
	private OperationLogWriter logWriter = null;
	private GraphDescriptor graphDescriptor = null;
	private List<OperationFactory> operationFactories = null;

	public BenchRunner(GraphDescriptor graphDescriptor, File logFile,
			OperationFactory... operationFactories) throws LogWriterException {
		this.graphDescriptor = graphDescriptor;
		this.operationFactories = Arrays.asList(operationFactories);
		logWriter = LogUtils.getOperationLogWriter(logFile);
	}

	public void startBench() throws LogWriterException, OperationException,
			OperationFactoryException {
		int startingOpId = -1;

		Iterator<Operation> openFactory = new SimpleOperationFactory(
				OperationOpenGraph.class).iterator();

		Iterator<Operation> shutdownFactory = new SimpleOperationFactory(
				OperationShutdownGraph.class).iterator();

		for (OperationFactory operationFactory : operationFactories) {
			if (operationFactory instanceof LogOperationFactory == false) {
				// Flush cache: open/close before/after each factory
				Operation openOperation = openFactory.next();
				openOperation.setId(++startingOpId);
				openOperation.initialize(graphDescriptor);
				openOperation.execute();
				logWriter.logOperation(openOperation);
			}

			operationFactory.initialize(graphDescriptor, startingOpId);

			logger.info(operationFactory.getClass().getSimpleName());

			for (Operation operation : operationFactory) {
				operation.initialize(graphDescriptor);
				logger.info(String.format("\t[%d] - %s...", operation.getId(),
						operation.getName()));
				operation.execute();
				logWriter.logOperation(operation);
			}

			startingOpId = operationFactory.getCurrentOpId();

			if (operationFactory instanceof LogOperationFactory == false) {
				// Flush cache: open/close before/after each factory
				Operation shutdownOperation = shutdownFactory.next();
				shutdownOperation.setId(++startingOpId);
				shutdownOperation.initialize(graphDescriptor);
				shutdownOperation.execute();
				logWriter.logOperation(shutdownOperation);
			}
		}

		graphDescriptor.shutdownDb();

		logWriter.close();
	}

}