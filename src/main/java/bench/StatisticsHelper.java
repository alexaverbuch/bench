package bench;

import java.util.Random;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.tooling.GlobalGraphOperations;

import bench.sampling.NodeSampler;

public class StatisticsHelper {

	private static Random rand = new Random(42);
	private static long time = -1l;

	// TODO test this function
	public static Long[] getSampleNodeIds(GraphDatabaseService db,
			NodeSampler evaluator, int sampleSize) {

		Long[] sampleIds = new Long[sampleSize];
		Double[] sampleVals = new Double[sampleSize];

		double totalVal = evaluator.evaluateTotal(db);

		for (int i = 0; i < sampleVals.length; i++) {
			sampleVals[i] = rand.nextDouble() * totalVal;
			sampleIds[i] = null;
		}

		boolean finished = true;

		for (Node n : GlobalGraphOperations.at(db).getAllNodes()) {
			double currentVal = evaluator.evaluate(n);
			finished = true;
			for (int i = 0; i < sampleVals.length; i++) {
				if (sampleIds[i] == null) {
					sampleVals[i] -= currentVal;
					if (sampleVals[i] <= 0)
						sampleIds[i] = n.getId();
					else
						finished = false;
				}
			}
			if (finished == true)
				break;
		}

		return sampleIds;
	}

	public static void startWatch() {
		time = System.currentTimeMillis();
	}

	public static long stopWatch() {
		return System.currentTimeMillis() - time;
	}

}
