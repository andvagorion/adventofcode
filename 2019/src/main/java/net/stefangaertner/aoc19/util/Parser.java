package net.stefangaertner.aoc19.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Parser {

	private enum MODE {
		POSITION, IMMEDIATE, RELATIVE
	}

	private long[] memory = null;
	private int counter = 0;
	private boolean finished = false;

	private long[] inputs = new long[0];
	private int inputCounter = 0;

	private List<String> output = new ArrayList<>();
	private List<String> unhandledOutput = new ArrayList<>();

	private boolean stopOnOutput = false;
	private boolean halted = false;

	private boolean stopOnInput = false;
	private boolean needsInput = false;

	private boolean debugPrint = false;

	private int relativeOffset = 0;

	private long stepCount = 0;

	private Parser() {
	}

	public Parser(String input) {
		this.memory = Arrays.stream(input.split(",")).mapToLong(Long::parseLong).toArray();
	}

	public Parser run() {

		this.halted = false;

		while (!this.finished && !this.halted) {
			this.step();
		}

		return this;
	}

	private void step() {

		this.stepCount++;

		int counterPrev = this.counter;

		if (this.debugPrint) {
			System.out.println(this.counter);
		}

		String instruction = StringUtils.fillWith(String.valueOf(this.memory[this.counter]), 5, '0');

		String opCode = instruction.substring(3);
		MODE mode1 = this.getMode(instruction, 2);
		MODE mode2 = this.getMode(instruction, 1);
		MODE mode3 = this.getMode(instruction, 0);

		if (this.debugPrint) {
			System.out.println("op code: " + opCode);
		}

		if ("01".equals(opCode)) {
			// add values

			long param1 = this.evalulateParam(this.counter + 1, mode1);
			long param2 = this.evalulateParam(this.counter + 2, mode2);
			long target = this.evalulateTarget(this.counter + 3, mode3);

			this.checkMemory(target);
			this.memory[(int) target] = param1 + param2;

			this.counter += 4;
		}

		if ("02".equals(opCode)) {
			// multiply values

			long param1 = this.evalulateParam(this.counter + 1, mode1);
			long param2 = this.evalulateParam(this.counter + 2, mode2);
			long target = this.evalulateTarget(this.counter + 3, mode3);

			this.checkMemory(target);
			this.memory[(int) target] = param1 * param2;

			this.counter += 4;
		}

		// takes a single integer as input and saves it to the position given by
		// its
		// only parameter
		if ("03".equals(opCode)) {

			if (this.inputCounter > this.inputs.length - 1) {
				if (this.stopOnInput) {
					this.needsInput = true;
					this.halted = true;
					return;
				}

				throw new IllegalStateException(
						"input value #" + (this.inputCounter + 1) + " was requested, but no input value supplied.");
			}
			this.needsInput = false;

			long in = this.inputs[this.inputCounter++];

			long target = this.evalulateTarget(this.counter + 1, mode1);

			this.checkMemory(target);
			this.memory[(int) target] = in;

			this.counter += 2;
		}

		// output
		if ("04".equals(opCode)) {
			long out = this.evalulateParam(this.counter + 1, mode1);

			this.output.add(String.valueOf(out));
			this.unhandledOutput.add(String.valueOf(out));

			if (this.stopOnOutput) {
				this.halted = true;
			}

			if (this.debugPrint) {
				System.out.println("OUT: " + out);
			}

			this.counter += 2;
		}

		// jump-if-true
		if ("05".equals(opCode)) {
			long param1 = this.evalulateParam(this.counter + 1, mode1);
			long param2 = this.evalulateParam(this.counter + 2, mode2);

			if (param1 != 0) {
				this.counter = (int) param2;
			} else {
				this.counter += 3;
			}
		}

		// jump-if-false
		if ("06".equals(opCode)) {
			long param1 = this.evalulateParam(this.counter + 1, mode1);
			long param2 = this.evalulateParam(this.counter + 2, mode2);

			if (param1 == 0) {
				this.counter = (int) param2;
			} else {
				this.counter += 3;
			}
		}

		// less than
		if ("07".equals(opCode)) {
			long param1 = this.evalulateParam(this.counter + 1, mode1);
			long param2 = this.evalulateParam(this.counter + 2, mode2);
			long target = this.evalulateTarget(this.counter + 3, mode3);

			this.checkMemory(target);

			if (param1 < param2) {
				this.memory[(int) target] = 1;
			} else {
				this.memory[(int) target] = 0;
			}

			this.counter += 4;
		}

		// equals
		if ("08".equals(opCode)) {
			long param1 = this.evalulateParam(this.counter + 1, mode1);
			long param2 = this.evalulateParam(this.counter + 2, mode2);
			long target = this.evalulateTarget(this.counter + 3, mode3);

			this.checkMemory(target);

			if (param1 == param2) {
				this.memory[(int) target] = 1;
			} else {
				this.memory[(int) target] = 0;
			}

			this.counter += 4;
		}

		// change relative offset
		if ("09".equals(opCode)) {
			long param1 = this.evalulateParam(this.counter + 1, mode1);
			this.relativeOffset += param1;

			this.counter += 2;
		}

		if ("99".equals(opCode)) {
			this.finished = true;
		}

		if (this.counter == counterPrev) {
			this.finished = true;
		}

		if (this.finished && this.debugPrint) {
			System.out.println("stopping execution");
		}

		if (this.debugPrint) {
			System.out.println(this.getState());
		}
	}

	private void checkMemory(long address) {

		int currentMemory = this.memory.length;

		if (address >= currentMemory) {

			int times = (int) (address / currentMemory);

			this.addMemory(times);
		}

	}

	private void addMemory(int times) {

		long[] newMem = new long[this.memory.length * times];
		for (int i = 0; i < newMem.length; i++) {
			newMem[i] = 0;
		}

		this.memory = ArrayUtils.combine(this.memory, newMem);
	}

	private long evalulateParam(long val, MODE mode) {
		switch (mode) {
		case IMMEDIATE:
			return this.memory[(int) val];
		case POSITION:
			long refVal = this.memory[(int) val];
			this.checkMemory(refVal);
			return this.memory[(int) refVal];
		case RELATIVE:
			long refVal2 = this.memory[(int) val];
			this.checkMemory(refVal2);
			return this.memory[(int) refVal2 + this.relativeOffset];
		default:
			throw new IllegalStateException("Mode is not set correctly.");
		}
	}

	private long evalulateTarget(long val, MODE mode) {
		switch (mode) {
		case POSITION:
			long refVal = this.memory[(int) val];
			return refVal;
		case RELATIVE:
			long refVal2 = this.memory[(int) val] + this.relativeOffset;
			return refVal2;
		case IMMEDIATE:
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
		case '2':
			return MODE.RELATIVE;
		default:
			throw new IllegalStateException("Mode can only be 0 or 1.");
		}
	}

	public String getState() {
		return Arrays.stream(this.memory).mapToObj(i -> "[" + String.valueOf(i) + "]")
				.collect(Collectors.joining(", "));
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

	public Parser stopOnInput() {
		this.stopOnInput = true;
		return this;
	}

	public boolean needsInput() {
		return this.needsInput;
	}

	public boolean isFinished() {
		return this.finished;
	}

	public String getLastOutput() {
		if (this.output.isEmpty()) {
			return "no output yet";
		}
		return this.output.get(this.output.size() - 1);
	}

	public boolean hasUnhandledOutput() {
		return !this.unhandledOutput.isEmpty();
	}

	public int sizeOfUnhandledOutput() {
		return this.unhandledOutput.size();
	}

	public List<String> getUnhandledOutput() {
		List<String> retval = new ArrayList<>(this.unhandledOutput);
		this.unhandledOutput.clear();
		return retval;
	}

	public void setMemoryAddress(int i, long j) {
		this.checkMemory(i);
		this.memory[i] = j;
	}

	public long getStepCount() {
		return this.stepCount;
	}

	public boolean isHalted() {
		return this.halted;
	}

	public Parser copy() {
		Parser p = new Parser();
		p.counter = this.counter;
		p.debugPrint = this.debugPrint;
		p.finished = this.finished;
		p.halted = this.halted;
		p.inputCounter = this.inputCounter;
		p.inputs = new long[this.inputs.length];
		System.arraycopy(this.inputs, 0, p.inputs, 0, this.inputs.length);
		p.memory = new long[this.memory.length];
		System.arraycopy(this.memory, 0, p.memory, 0, this.memory.length);
		p.needsInput = this.needsInput;
		p.output = new ArrayList<>(this.output);
		p.unhandledOutput = new ArrayList<>(this.unhandledOutput);
		p.relativeOffset = this.relativeOffset;
		p.stepCount = this.stepCount;
		p.stopOnInput = this.stopOnInput;
		p.stopOnOutput = this.stopOnOutput;
		return p;
	}

	public Parser input(long... in) {
		this.inputs = ArrayUtils.combine(this.inputs, in);
		this.needsInput = false;
		return this;
	}

	public Parser input(long i) {
		long[] in = new long[1];
		in[0] = i;
		return this.input(in);
	}

	public Parser input(String input) {
		long[] in = Arrays.stream(input.split(",")).mapToLong(Long::parseLong).toArray();
		return this.input(in);
	}

	public Parser asciiInput(char c) {
		return this.input(c);
	}

	public Parser asciiInput(String string) {
		for (int i = 0; i < string.length(); i++) {
			this.asciiInput(string.charAt(i));
		}
		return this.asciiInput('\n');
	}

}
