package bench.log;

import bench.operation.OperationDescriptor;

public class OperationLogEntry extends OperationDescriptor {

	private final long time;
	private final String result;

	public OperationLogEntry(int id, String[] args, String type,
			String name, long time, String result) {
		super(id, args, type, name);

		// TODO time should not be the only metric
		this.time = time;
		this.result = result;
	}

	// TODO time should not be the only metric
	// TODO maybe this should be a collection of metrics
	public long getTime() {
		return time;
	}

	public String getResult() {
		return result;
	}

}
