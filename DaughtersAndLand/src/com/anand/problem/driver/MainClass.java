package com.anand.problem.driver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

public class MainClass {

	private static Map<Integer, MainClass.Range> selectedMap = new LinkedHashMap<Integer, MainClass.Range>();
	private static int lengthOfLand = 0;
	private static BloomFilter<Integer> filter = null;

	private static long time = 0;

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		String[] firstLine = in.readLine().split("\\s");
		lengthOfLand = Integer.parseInt(firstLine[0]);
		int noOfDaughters = Integer.parseInt(firstLine[1]);

		filter = BloomFilter.create(Funnels.integerFunnel(), lengthOfLand, 0.01);

		MainClass mainClass = new MainClass();

		mainClass.read(in, noOfDaughters);

		long startTime = System.currentTimeMillis();
		mainClass.compute(noOfDaughters);
		long computeTime = System.currentTimeMillis() - startTime;
		System.out.println("ComputeTime: " + computeTime + "(ms)");
		System.out.println("Read Overhead: " + time + "(ms)");
		System.out.println("Total Time: " + (computeTime + time) + "(ms)");
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
				newStart = getNewStart(r.getStart());
				newEnd = getNewEnd(r.getEnd());
			}

			System.out.println(r.setRangeAndValues(newStart, newEnd));
			filter.put(newStart);
			filter.put(newEnd);
		}
	}

	private int getValidIndex(int index) {
		if (index < 0)
			return 0;
		if (index >= lengthOfLand)
			return index - 1;
		return index;
	}

	private int getNewEnd(int end) {
		for (int test = end + 1; test < lengthOfLand; test++) {
			if (!filter.mightContain(test)) {
				return test;
			}
		}
		return end;
	}

	private int getNewStart(int start) {
		for (int test = start - 1; test >= 0; test--) {
			if (!filter.mightContain(test)) {
				return test;
			}
		}
		return start;
	}

	private void read(BufferedReader in, int noOfDaughters) throws IOException {
		for (int i = 0; i < noOfDaughters; i++) {
			String[] choice = in.readLine().split("\\s");

			long start = System.currentTimeMillis();
			Range range = new Range(Integer.parseInt(choice[0]), Integer.parseInt(choice[1]));
			selectedMap.put(i, range);

			for (int x = range.getStart(); x <= range.getEnd(); x++)
				filter.put(x);
			time += (System.currentTimeMillis() - start);
		}
	}

	private class Range {
		int start;
		int end;

		public Range(int start, int end) {
			this.start = start;
			this.end = end;
		}

		public int setRangeAndValues(int newStart, int newEnd) {
			int value = start;

			if (this.start != this.end)
				value = ((this.end - this.start + 1) * (this.start + this.end)) / 2;

			if (this.end != newEnd)
				value += newEnd;

			if (this.start != newStart)
				value += newStart;

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
