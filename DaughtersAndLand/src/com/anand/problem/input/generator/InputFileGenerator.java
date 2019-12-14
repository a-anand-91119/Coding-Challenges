package com.anand.problem.input.generator;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputFileGenerator {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("Enter Size Of Plot");
		int length = Integer.parseInt(in.readLine());
		
		System.out.println("Enter The Number Of Daughters");
		int daughters = Integer.parseInt(in.readLine());
		
		FileWriter inputFileGen = new FileWriter("./input_"+length+".txt");
		int start = 102;
		int end = 200;
		
		inputFileGen.append(length+"");
		inputFileGen.append(" ");
		inputFileGen.append(daughters+"\n");
		inputFileGen.append("0 100\n");
		
		for(int i=1;i<daughters-1;i++) {
			inputFileGen.append(String.valueOf(start));
			inputFileGen.append(" ");
			inputFileGen.append(String.valueOf(end));
			inputFileGen.append("\n");
			start += 100;
			end += 100;
		}
		inputFileGen.append(String.valueOf(start));
		inputFileGen.append(" ");
		inputFileGen.append(String.valueOf(end-1));
		inputFileGen.append("\n");
		
		inputFileGen.flush();
		inputFileGen.close();
	}
}

