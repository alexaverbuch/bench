package bench;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.impl.util.FileUtils;
import org.neo4j.test.TestGraphDatabaseFactory;

public class GraphDescriptor {

	private final String dbPath;
	private final Map<String, String> dbConfig;
	private GraphDatabaseService db = null;

	public GraphDescriptor(Map<String, String> dbConfig) {
		this.dbPath = null;
		this.dbConfig = dbConfig;
	}

	public GraphDescriptor(String graphPath, Map<String, String> dbConfig) {
		this.dbPath = graphPath;
		this.dbConfig = dbConfig;
	}

	public GraphDatabaseService getDb() {
		return db;
	}

	public void openDb() {
		// Map config = new Properties();
		// config.put("neostore.nodestore.db.mapped_memory", "10M");
		// config.put("string_block_size", "60");
		// config.put("array_block_size", "300");
		if (null == dbPath)
			db = new TestGraphDatabaseFactory().newImpermanentDatabaseBuilder()
					.setConfig(dbConfig).newGraphDatabase();
		else
			db = new GraphDatabaseFactory().newEmbeddedDatabaseBuilder(dbPath)
					.setConfig(dbConfig).newGraphDatabase();
		registerShutdownHook(db);
	}

	public void shutdownDb() {
		db.shutdown();
		db = null;
	}

	public void deleteDb() {
		shutdownDb();
		try {
			FileUtils.deleteRecursively(new File(dbPath));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static void registerShutdownHook(final GraphDatabaseService db) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				db.shutdown();
			}
		});
	}
}
