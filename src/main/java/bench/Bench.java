package bench;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;
import java.util.Properties;

public class Bench {

	public static Logger logger = Logger.getLogger(Bench.class);
	public static Properties benchProperties = new Properties();

	// DATASETS - GraphML & Databases
	public static final String DATASETS_DIRECTORY = "bench.datasets.directory";

	// LOGS - Operation Logs
	public static final String LOGS_DELIMITER = "bench.logs.delimiter";

	// RESULTS - Logs, Summaries, Plots
	public static final String RESULTS_DIRECTORY = "bench.results.directory";

	// Configuration for Neo4j
	public static final String NEO4J_CONFIG_PATH = "bench.neo4j.config.path";

	static {
		try {
			benchProperties.load(Bench.class
					.getResourceAsStream("bench.properties"));
			System.out.println(benchProperties);
		} catch (IOException e) {
			e.printStackTrace();
		}
		PropertyConfigurator.configure(Bench.class
				.getResource("log4j.properties"));
	}

}
