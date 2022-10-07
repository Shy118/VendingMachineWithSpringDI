package com.vendingmachine.ui;

import java.util.Scanner;

public class UserIOConsoleImpl implements UserIO {
	Scanner InputReader = new Scanner(System.in);
	
	@Override
	public void print(String message) {
		System.out.print(message);
	}
	
	@Override
	public void println(String message) {
		System.out.println(message);
	}

	@Override
	public String readString(String prompt) {
		System.out.println(prompt);
		return InputReader.nextLine();
	}

	@Override
	public int readInt(String prompt) {
		System.out.println(prompt);
		return Integer.parseInt(InputReader.nextLine());
	}

	@Override
	public int readInt(String prompt, int min, int max) {
		System.out.println(prompt + min + " to " + max);
		return Integer.parseInt(InputReader.nextLine());
	}

	@Override
	public double readDouble(String prompt) {
		System.out.println(prompt);
		return Double.parseDouble(InputReader.nextLine());
	}

	@Override
	public double readDouble(String prompt, double min, double max) {
		System.out.println(prompt + min + " to " + max);
		return Double.parseDouble(InputReader.nextLine());
	}

	@Override
	public float readFloat(String prompt) {
		System.out.println(prompt);
		return Float.parseFloat(InputReader.nextLine());
	}

	@Override
	public float readFloat(String prompt, float min, float max) {
		System.out.println(prompt + min + " to " + max);
		return Float.parseFloat(InputReader.nextLine());
	}

	@Override
	public long readLong(String prompt) {
		System.out.println(prompt);
		return Long.parseLong(InputReader.nextLine());
	}

	@Override
	public long readLong(String prompt, long min, long max) {
		System.out.println(prompt + min + " to " + max);
		return Long.parseLong(InputReader.nextLine());
	}

} //class
