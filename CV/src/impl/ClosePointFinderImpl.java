package src.impl;

import java.io.RandomAccessFile;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import src.ClosePointFinder;
import src.FileUtility;

public class ClosePointFinderImpl implements ClosePointFinder {

	private final static java.util.logging.Logger logger = Logger.getLogger();

	private static FileUtility fileUtility = new FileUtilityImpl();
	static long minFileSize = 1000000 ;//1MB

	@Override
	public void closePoints(String file, int levels) {
		try {

			logger.info("Processing file : " + file + " for level " + levels);

			// validate the file
			fileUtility.validateFile(file);

			this.logger.info("Validated file : " + file);

			// start processing files with three threads if file size is more
			// than
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
				this.logger.info("Since file size is less than 1 MB , processing with single thread ... ");
				Thread t = new Thread(new ReadAndCollectResultsImpl(file, levels));
				t.start();
				t.join();
			}
			System.out.println("\r" + "Processed 100% records for a file " + file + " . Results are in file "
					+ file.substring(0, file.lastIndexOf('.')).concat("output.txt"));

			this.logger.info(
					"Processed file " + file + " to " + file.substring(0, file.lastIndexOf('.')).concat("output.txt"));
		} catch (Exception ex) {
			logger.severe("Exception occured while processing file " + file + " Exception : " + ex);
			System.out.println("Error occured while processing file " + file + " Error is " + ex.getMessage());
		}
	}
}
