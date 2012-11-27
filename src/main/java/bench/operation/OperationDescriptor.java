package bench.operation;

import java.util.Arrays;

public class OperationDescriptor {

	private final int id;
	private final String[] args;
	private final String type;
	private final String name;

	public OperationDescriptor(int id, String[] args, String type, String name) {
		this.id = id;
		this.args = args;
		this.type = type;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String[] getArgs() {
		return args;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		// return ("".equals(tag)) ? type.getSimpleName() : type.getSimpleName()
		// + "-" + tag;
		return name;
	}

	@Override
	public String toString() {
		return String.format("OperationDescriptor(%s,%s,%s,%s)", getId(),
				Arrays.toString(getArgs()), getName(), getName());
	}
}
