package bench.benchmark.examples;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.neo4j.kernel.impl.util.FileUtils;

import bench.Bench;
import bench.GraphDescriptor;
import bench.LogUtils;
import bench.benchmark.Benchmark;
import bench.operation.OperationFactory;
import bench.operation.factory.impl.examples.IndexGetOperationFactory;
import bench.sampling.UniformNodeSampler;

public class IndexGetBenchmark extends Benchmark {

	// TODO this "run()" method is shit, need a standard way of setting up and
	// running benchmarks
	public static void run() throws Exception {
		String resultsDir = Bench.benchProperties
				.getProperty(Bench.RESULTS_DIRECTORY) + "IndexGet/";

		FileUtils.deleteRecursively(new File(resultsDir));

		Benchmark benchmark = new IndexGetBenchmark(resultsDir
				+ "index_get.csv");

		// Load operation logs with Impermanent graph
		benchmark.loadOperationLogs(new GraphDescriptor(
				new HashMap<String, String>()), resultsDir
				+ "index_get_impermanent.csv");

		// Load operation logs with Embedded
		benchmark.loadOperationLogs(new GraphDescriptor(resultsDir + "neo4j/",
				new HashMap<String, String>()), resultsDir
				+ "index_get_embedded.csv");

		// Create file with summarized results from all databases and operations
		LinkedHashMap<String, String> resultFiles = new LinkedHashMap<String, String>();
		resultFiles
				.put("Impermanent", resultsDir + "index_get_impermanent.csv");
		resultFiles.put("Embedded", resultsDir + "index_get_embedded.csv");
		LogUtils.makeResultsSummary(resultsDir + "load_graphml_summary.csv",
				resultFiles);
	}

	/*
	 * Instance Code
	 */

	// TODO JUST AN EXAMPLE: "someProperty" is bullshit, it doesn't exist
	private final String ID_PROPERTY_KEY = "someProperty";
	private final int GET_OP_COUNT = 1000;
	private final int GET_LOOKUPS_PER_OP = 100;

	public IndexGetBenchmark(String log) {
		super(log);
	}

	@Override
	protected List<OperationFactory> getOperationFactories() {
		ArrayList<OperationFactory> operationFactories = new ArrayList<OperationFactory>();

		operationFactories.add(new IndexGetOperationFactory(GET_OP_COUNT
				* GET_LOOKUPS_PER_OP, new UniformNodeSampler(),
				ID_PROPERTY_KEY, GET_LOOKUPS_PER_OP, "Index Lookups"));

		return operationFactories;
	}

}
