package src.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import src.FileUtility;
import src.PointUtility;
import src.ReadAndCollectresults;
import src.ResultWriter;
import src.model.Point;
import src.model.Result;

class ReadAndCollectResultsImpl implements Runnable, ReadAndCollectresults {
	RandomAccessFile pointsFromFirstHalfOfFile = null;
	RandomAccessFile pointsFromSecondHalfOfFile = null;
	int levels = 0;
	String file = "";

	// minimum chunk to be processed at a time
	int minimumChunk = 100;

	// start of a file to be processed for this thread
	private long start;

	// end of a file to be processed for this thread
	private long stop;

	FileUtility fileUtility = new FileUtilityImpl();

	PointUtility pointUtility = new PointUtilityImpl();

	ResultWriter resultWriter = new ResultWriterImpl();

	// for multi threaded
	ReadAndCollectResultsImpl(String file, int closeLevel, int index) throws IOException, FileNotFoundException {
		this.levels = closeLevel;
		this.file = file;
		pointsFromFirstHalfOfFile = new RandomAccessFile(file, "r");
		long halfFileSize = pointsFromFirstHalfOfFile.length() / 2;
		this.start = (halfFileSize * index / 3);
		this.stop = this.start + (halfFileSize / 3) - 1;
		this.pointsFromFirstHalfOfFile.seek(start);
		Thread.currentThread().setName("" + index);

	}

	// for single threaded
	ReadAndCollectResultsImpl(String file, int closeLevel) throws IOException, FileNotFoundException {
		this.levels = closeLevel;
		this.file = file;
		pointsFromFirstHalfOfFile = new RandomAccessFile(file, "r");
		this.start = 0;
		this.stop = pointsFromFirstHalfOfFile.length() / 2;
	}

	@Override
	public void readAndCollect() throws IOException {
		long bytesProcessed = 0;

		// points from the first of records
		List<String> pointsFromFirstHalf = new ArrayList<String>();

		// points from second half of a file
		List<String> pointsFromSecondHalf = new ArrayList<String>();

		// this structure holds result for each run.
		// stores close points for each point from first half for minChunk
		Map<String, List<Result>> results = new HashMap<String, List<Result>>();

		// read minimumChunk line from the start of a file till the dedicated
		// set of records
		while (pointsFromFirstHalfOfFile != null && pointsFromFirstHalfOfFile.getFilePointer() < this.stop) {

			// clear the list and make it ready for next chunk
			pointsFromFirstHalf.clear();

			// initialize second file pointer
			pointsFromSecondHalfOfFile = null;
			pointsFromSecondHalfOfFile = new RandomAccessFile(file, "r");
			pointsFromSecondHalfOfFile.seek(pointsFromSecondHalfOfFile.length() / 2);
			
			// get minimumChunk of records from first half of a file
			// In case of exception or invalid record, skip that record
			for (int i = 0; i < minimumChunk && pointsFromFirstHalfOfFile.getFilePointer() < this.stop; i++) {
				try {
					String record = pointsFromFirstHalfOfFile.readLine();
					if (fileUtility.validateRecord(record)) {
						pointsFromFirstHalf.add(record);
						bytesProcessed = bytesProcessed + record.getBytes().length;
					}
				} catch (IOException e) {
					e.printStackTrace();
					break;
				}
			}

			while (pointsFromSecondHalfOfFile != null
					&& pointsFromSecondHalfOfFile.getFilePointer() < pointsFromSecondHalfOfFile.length()) {
				pointsFromSecondHalf.clear();

				// read from the second of a file minimumChunk of records
				for (int j = 0; j < minimumChunk
						&& pointsFromSecondHalfOfFile.getFilePointer() < pointsFromSecondHalfOfFile.length(); j++) {
					String str = pointsFromSecondHalfOfFile.readLine();
					if (fileUtility.validateRecord(str)) {
						pointsFromSecondHalf.add(str);
					}
				}

				// For minimumChunk from first of a file, perform close point
				// calculations for records from second half
				// This means e.g. if minimumChunk = 1000, then for each 1000
				// records from first half of a file
				// get 1000 records from second half of a file and calculate in
				// memory close points till level specified.
				// If level is 3 then it means only 3 results will be stored at
				// a time for each point from first half of a file.
				// Repeat the process for next 1000 records from second half of
				// a file till all records are over from second half of a file
				// and compared against the records from next 1000 records from
				// second half of a file
				findClosePoints(pointsFromFirstHalf, pointsFromSecondHalf, results);
			}

			// now you got results for first 100 records from first half of a
			// file, so store them
			flushResults(this.file, results);

			// use this for next 1000 records from first half of a file
			results.clear();

			//progress monitor
			DecimalFormat df = new DecimalFormat();
			df.setMaximumFractionDigits(2);
			System.out.println("\r" + "For thread " + Thread.currentThread().getName() + " Processed "
					+ df.format(bytesProcessed * 100.0 / (this.stop - this.start)) + " % ");
		}
	}

	// this finds close points till level
	// this is done in incremental style and hence file size is not a limit.
	// Also each thread performs 30% of procesing of points
	// from first half
	void findClosePoints(List<String> pointsFromFirstHalf, List<String> pointsFromSecondHalf,
			Map<String, List<Result>> results) {

		for (String vector : pointsFromFirstHalf) {
			Point pointFromFirstHalf = pointUtility.getPoint(vector);
			for (String midVector : pointsFromSecondHalf) {
				Point pointFromSecondHalf = pointUtility.getPoint(midVector);

				if (pointFromFirstHalf == null || pointFromSecondHalf == null) {
					// skip it and move to next
					continue;
				}

				// calculate distance
				double dist = pointUtility.getDistance(pointFromFirstHalf, pointFromSecondHalf);

				if (results.containsKey(pointFromFirstHalf.getName())
						&& results.get(pointFromFirstHalf.getName()).size() == this.levels) {

					// this is the case where we need to see least points from
					// each run
					// as level specified by user is reached

					// this list always stores least distance points
					List<Result> tempList = results.get(pointFromFirstHalf.getName());

					// This is small list having few elements (3/4).
					// Hence sorting this is not a big overhead
					// make sure that this list is ordered so that max
					// element can be found out directly and would sace time
					if (dist < tempList.get(this.levels - 1).getDist()) {
						tempList.remove(tempList.get(this.levels - 1));
						Result tempNode = new Result(dist, pointFromSecondHalf.getName());
						tempList.add(tempNode);
						sortList(tempList);
					}

				} else if (results.containsKey(pointFromFirstHalf.getName())
						&& results.get(pointFromFirstHalf.getName()).size() > 0
						&& results.get(pointFromFirstHalf.getName()).size() < this.levels) {
					List<Result> tempList = results.get(pointFromFirstHalf.getName());
					Result result = new Result();
					result.setName(pointFromSecondHalf.getName());
					result.setDist(dist);

					// add result
					tempList.add(result);

					// small list of 3-4 elements. hence no overhead.
					// sort only once
					if (tempList.size() == this.levels) {
						sortList(tempList);
					}
				} else {
					List<Result> tempList = new ArrayList<Result>();
					Result result = new Result();
					result.setName(pointFromSecondHalf.getName());
					result.setDist(dist);
					tempList.add(result);

					// add result
					results.put(pointFromFirstHalf.getName(), tempList);
				}
			}

		}
	}

	public void sortList(List<Result> resultList) {
		Collections.sort(resultList, new ResultCmp());
	}

	// write results to a file
	public void flushResults(String fileName, Map<String, List<Result>> resultList) throws IOException {
		// Writes the content to the file
		Iterator<Entry<String, List<Result>>> it = resultList.entrySet().iterator();

		// format the result
		while (it.hasNext()) {
			Entry<String, List<Result>> entry = it.next();
			String str = "";
			List<Result> list = entry.getValue();
			for (Result result : list) {
				DecimalFormat df = new DecimalFormat();
				df.setMaximumFractionDigits(2);
				str += result.getName() + "=" + df.format(result.getDist()) + "      ";
			}
			resultWriter.flushResults(fileName, "For " + entry.getKey() + " : Close points are \t " +
					  str + "  \n");
		}		
	}

	// comparator for sorting results
	class ResultCmp implements Comparator<Result> {

		@Override
		public int compare(Result e1, Result e2) {
			if (e1.getDist() <= e2.getDist()) {
				return -1;
			} else {
				return 1;
			}
		}
	}

	@Override
	public void run() {
		try {
			this.readAndCollect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
