package bench.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import bench.LogUtils;
import bench.errors.LogWriterException;
import bench.operation.Operation;


public class OperationLogWriter {
	private final String logDelim = LogUtils.LOG_DELIMITER;
	private BufferedWriter bufferedLogWriter = null;

	public OperationLogWriter(File logFile) throws LogWriterException {
		try {
			(new File(logFile.getParent())).mkdirs();
			bufferedLogWriter = new BufferedWriter(new FileWriter(logFile));
			writeHeaders();
		} catch (IOException e) {
			throw new LogWriterException(
					"Error instantiating operation log writer", e.getCause());
		}
	}

	// Write .csv log column headers
	private void writeHeaders() throws LogWriterException {
		try {
			bufferedLogWriter.write("id");
			bufferedLogWriter.write(logDelim);

			bufferedLogWriter.write("name");
			bufferedLogWriter.write(logDelim);

			bufferedLogWriter.write("type");
			bufferedLogWriter.write(logDelim);

			bufferedLogWriter.write("args");
			bufferedLogWriter.write(logDelim);

			bufferedLogWriter.write("time");
			bufferedLogWriter.write(logDelim);

			bufferedLogWriter.write("result");
			bufferedLogWriter.write(logDelim);

			bufferedLogWriter.newLine();
		} catch (IOException e) {
			throw new LogWriterException("Error writing operation log headers",
					e.getCause());
		}
	}

	// Write a .csv log data row
	public void logOperation(Operation op) throws LogWriterException {
		try {
			bufferedLogWriter.write(Integer.toString(op.getId()));
			bufferedLogWriter.write(logDelim);

			bufferedLogWriter.write(op.getName());
			bufferedLogWriter.write(logDelim);

			bufferedLogWriter.write(op.getType());
			bufferedLogWriter.write(logDelim);

			bufferedLogWriter.write(Arrays.toString(op.getArgs()));
			bufferedLogWriter.write(logDelim);

			bufferedLogWriter.write(Long.toString(op.getTime()));
			bufferedLogWriter.write(logDelim);

			bufferedLogWriter.write(op.getResult().toString());
			bufferedLogWriter.write(logDelim);

			bufferedLogWriter.newLine();
		} catch (IOException e) {
			throw new LogWriterException(String.format(
					"Error writing operation %s to log", op.toString()),
					e.getCause());
		}
	}

	public void close() throws LogWriterException {
		try {
			bufferedLogWriter.flush();
			bufferedLogWriter.close();
		} catch (IOException e) {
			throw new LogWriterException("Error closing operation log",
					e.getCause());
		}
	}
}
