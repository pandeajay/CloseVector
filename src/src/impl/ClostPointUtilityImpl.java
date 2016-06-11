package src.impl;

import java.io.RandomAccessFile;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import src.ClosePointUtility;
import src.FileUtility;

public class ClostPointUtilityImpl implements ClosePointUtility {

	private static FileUtility fileUtility = new FileUtilityImpl();
	static long minFileSize = 1000000;

	@Override
	public void closePoints(String file, int levels) throws Exception {
		// validate the file
		fileUtility.validateFile(file);

		// start processing files with three threads if file size is more than
		// 1 MB
		if (new RandomAccessFile(file, "r").length() > minFileSize) {
			ExecutorService executor = Executors.newFixedThreadPool(3);
			for (int i = 0; i < 3; i++) {
				Runnable worker = new ReadAndCollectResultsImpl(file, levels, i);
				executor.execute(worker);
			}
			executor.shutdown();

			// max wait
			executor.awaitTermination(24L, TimeUnit.HOURS);

		} else {
			Thread t = new Thread(new ReadAndCollectResultsImpl(file, levels));
			t.start();
			t.join();
		}
		System.out.println("\r" + "Processed 100% records for a file " + file);
	}
}
