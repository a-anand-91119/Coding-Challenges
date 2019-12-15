package com.anand.problem.driver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;

public class MainClass {

	private static Map<Integer, MainClass.Range> selectedMap = new LinkedHashMap<Integer, MainClass.Range>();
	private static int lengthOfLand = 0;
	private static boolean[] usedLandArray = null;
	//private static int[] debugArray = null;
	private static long min;
	private static long max;
	private static long prerequisiteTime = 0;

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Enter Input");
		String[] firstLine = in.readLine().split("\\s");
		lengthOfLand = Integer.parseInt(firstLine[0]);
		int noOfDaughters = Integer.parseInt(firstLine[1]);

		usedLandArray = new boolean[lengthOfLand];
		//debugArray = new int[lengthOfLand];
		min = 0;
		max = lengthOfLand - 1;

		MainClass mainClass = new MainClass();

		mainClass.read(in, noOfDaughters);
		
		long startTime = System.currentTimeMillis();
		mainClass.compute(noOfDaughters);
		long computeTime = System.currentTimeMillis() - startTime;

		System.out.println("Compute Time: " + computeTime + "(ms)");
		System.out.println("Total TIme: " + (prerequisiteTime + computeTime ) + "(ms)");
		//in.readLine();
	}

	private void compute(int noOfDaughters) {
		for (int i = 0; i < noOfDaughters; i++) {
			Range r = selectedMap.get(i);
			int newStart;
			int newEnd;

			if (i == 0) {
				newStart = getValidIndex(r.getStart() - 1);
				newEnd = getValidIndex(r.getEnd() + 1);
			} else {
				newStart = getNewStart(r.getStart(), i, r.getEnd());
				newEnd = getNewEnd(r.getEnd(), i, r.getStart());
			}

			System.out.println(r.setRangeAndValues(newStart, newEnd));
			usedLandArray[newStart] = true;
			usedLandArray[newEnd] = true;
			//debugArray[newStart] = newStart;
			//debugArray[newEnd] = newEnd;
			//System.out.println(Arrays.toString(usedLandArray));
			//System.out.println(Arrays.toString(debugArray));
		}
	}

	private int getValidIndex(int index) {
		if (index < 0)
			return 0;
		if (index >= lengthOfLand)
			return index - 1;
		return index;
	}

	private int getNewEnd(int end, int currentIndex, int start) {
		int test = end + 1;
		for (; test < max; test++) {
			if(!usedLandArray[test]) {
				return test;
			}
		}
		if(test >= max) {
			max = start - 1;
		}
		return end;
		
	}

	private int getNewStart(int start, int currentIndex, int end) {
		int test = start - 1;
		for (; test >= min; test--) {
			if(!usedLandArray[test]) {
				return test;
			}
		}
		if(test <= min) {
			min = end + 1;
		}
		return start;
		
	}

	private void read(BufferedReader in, int noOfDaughters) throws IOException {
		for (int i = 0; i < noOfDaughters; i++) {
			String[] choice = in.readLine().split("\\s");

			long startTime = System.currentTimeMillis();
			Range range = new Range(Integer.parseInt(choice[0]), Integer.parseInt(choice[1]));
			selectedMap.put(i, range);

			for (int x = range.start; x <= range.end; x++) {
				usedLandArray[x] = true;
			}
			prerequisiteTime += (System.currentTimeMillis() - startTime);
			/*for(int x = range.start; x <= range.end; x++)
				debugArray[x] = x;*/
		}
	}

	private class Range {
		int start;
		int end;

		public Range(int start, int end) {
			this.start = start;
			this.end = end;
		}

		public long setRangeAndValues(int newStart, int newEnd) {
			long value = start;

			if (this.start != this.end)
				value = (long)(this.end - this.start + 1) * (long)(this.start + this.end) / 2;

			if (this.end != newEnd)
				value += newEnd;

			if (this.start != newStart)
				value += newStart;

			this.start = newStart;
			this.end = newEnd;
			return value;
		}

		public int getStart() {
			return start;
		}

		public int getEnd() {
			return end;
		}

		@Override
		public String toString() {
			return "Range [start=" + start + ", end=" + end + "]";
		}

	}
}
