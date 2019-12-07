package net.stefangaertner.aoc19.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import net.stefangaertner.util.ArrayUtils;
import net.stefangaertner.util.StringUtils;

public class Parser {

	private enum MODE {
		POSITION, IMMEDIATE
	}

	private int[] code = null;
	private int counter = 0;
	private boolean finished = false;

	private int[] inputs = new int[0];
	private int inputCounter = 0;

	private List<String> output = new ArrayList<>();
	private boolean stopOnOutput = false;
	private boolean halted = false;
	
	private boolean debugPrint = false;

	public Parser(String input) {
		this.code = Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();
	}

	public Parser run() {
		
		this.halted = false;

		while (!finished && !halted) {
			step();
		}

		return this;
	}

	public Parser input(String input) {
		this.inputs = ArrayUtils.combine(inputs, Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray());
		return this;
	}

	private void step() {

		int counterPrev = counter;

		if (debugPrint) {
			System.out.println(this.counter);
		}

		String instruction = StringUtils.fillWith(String.valueOf(code[counter]), 5, '0');

		String opCode = instruction.substring(3);
		MODE mode1 = getMode(instruction, 2);
		MODE mode2 = getMode(instruction, 1);

		if (debugPrint) {
			System.out.println("op code: " + opCode);
		}

		if ("01".equals(opCode)) {
			// add values

			int param1 = evalulateMode(code[counter + 1], mode1);
			int param2 = evalulateMode(code[counter + 2], mode2);
			int target = code[counter + 3];

			code[target] = param1 + param2;

			counter += 4;
		}

		if ("02".equals(opCode)) {
			// multiply values

			int param1 = evalulateMode(code[counter + 1], mode1);
			int param2 = evalulateMode(code[counter + 2], mode2);
			int target = code[counter + 3];

			code[target] = param1 * param2;

			counter += 4;
		}

		// takes a single integer as input and saves it to the position given by its
		// only parameter
		if ("03".equals(opCode)) {
			int in = this.inputs[inputCounter++];

			int param1 = evalulateMode(counter + 1, mode1);
			code[param1] = in;

			counter += 2;
		}

		if ("04".equals(opCode)) {
			int param1 = evalulateMode(counter + 1, mode1);
			int out = code[param1];

			output.add(String.valueOf(out));
			
			if (this.stopOnOutput) {
				this.halted = true;
			}

			if (this.debugPrint) {
				System.out.println("OUT: " + out);
			}

			counter += 2;
		}

		// jump-if-true
		if ("05".equals(opCode)) {
			int param1 = evalulateMode(code[counter + 1], mode1);
			int param2 = evalulateMode(code[counter + 2], mode2);

			if (param1 != 0) {
				counter = param2;
			} else {
				counter += 3;
			}
		}

		// jump-if-false
		if ("06".equals(opCode)) {
			int param1 = evalulateMode(code[counter + 1], mode1);
			int param2 = evalulateMode(code[counter + 2], mode2);

			if (param1 == 0) {
				counter = param2;
			} else {
				counter += 3;
			}
		}

		// less than
		if ("07".equals(opCode)) {
			int param1 = evalulateMode(code[counter + 1], mode1);
			int param2 = evalulateMode(code[counter + 2], mode2);
			int target = code[counter + 3];

			if (param1 < param2) {
				code[target] = 1;
			} else {
				code[target] = 0;
			}

			counter += 4;
		}

		// equals
		if ("08".equals(opCode)) {
			int param1 = evalulateMode(code[counter + 1], mode1);
			int param2 = evalulateMode(code[counter + 2], mode2);
			int target = code[counter + 3];

			if (param1 == param2) {
				code[target] = 1;
			} else {
				code[target] = 0;
			}

			counter += 4;
		}

		if ("99".equals(opCode)) {
			this.finished = true;
		}

		if (counter == counterPrev) {
			this.finished = true;
		}

		if (this.finished && this.debugPrint) {
			System.out.println("stopping execution");
		}

		if (debugPrint) {
			System.out.println(this.getState());
		}
	}

	private int evalulateMode(int val, MODE mode) {
		switch (mode) {
		case IMMEDIATE:
			return val;
		case POSITION:
			return code[val];
		default:
			throw new IllegalStateException("Mode is not set correctly.");
		}
	}

	private MODE getMode(String instruction, int charNum) {
		switch (instruction.charAt(charNum)) {
		case '0':
			return MODE.POSITION;
		case '1':
			return MODE.IMMEDIATE;
		default:
			throw new IllegalStateException("Mode can only be 0 or 1.");
		}
	}

	public String getState() {
		return Arrays.stream(this.code).mapToObj(i -> "[" + String.valueOf(i) + "]").collect(Collectors.joining(", "));
	}

	public static Parser create(String str) {
		return new Parser(str);
	}

	public Parser enableDebug() {
		this.debugPrint = true;
		return this;
	}

	public List<String> getOutput() {
		return this.output;
	}
	
	public Parser stopOnOutput() {
		this.stopOnOutput = true;
		return this;
	}

	public boolean isFinished() {
		return this.finished;
	}

	public String getLastOutput() {
		return this.output.get(this.output.size() - 1);
	}
}
