package net.stefangaertner.aoc19;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import net.stefangaertner.aoc19.util.Advent;
import net.stefangaertner.aoc19.util.Parser;

public class Day23 {

	public static void main(String[] strings) throws IOException {
		Advent.print(1, part1());
		Advent.print(2, part2());
	}

	static long part1() {
		String code = Advent.readLine("aoc19/023-data");
		boolean debugPrint = false;

		Map<Integer, Parser> hosts = new HashMap<>();

		for (int i = 0; i < 50; i++) {

			Parser p = Parser.create(code);
			p.input(i);
			p.stopOnInput();
			p.stopOnOutput();

			hosts.put(i, p);

		}

		while (true) {

			for (Map.Entry<Integer, Parser> p : hosts.entrySet()) {

				int hostAddress = p.getKey();
				Parser host = p.getValue();

				host.run();
				if (host.needsInput()) {
					host.input(-1);
				}

				if (host.hasUnhandledOutput() && host.sizeOfUnhandledOutput() == 3) {
					List<String> s = host.getUnhandledOutput();

					if (debugPrint) {
						System.out.println("host " + hostAddress + " has unhandled packet.");
						System.out.println("output is: " + s.stream()
								.collect(Collectors.joining(", ")));
					}

					for (int i = 0; i < s.size(); i += 3) {
						int address = Integer.parseInt(s.get(i));
						long x = Long.parseLong(s.get(i + 1));
						long y = Long.parseLong(s.get(i + 2));

						if (address > hosts.size()) {
							if (debugPrint) {
								System.out.println(address + " => " + x + ", " + y);
							}

							return y;
						}

						if (debugPrint) {
							System.out.println(hostAddress + " sending [" + x + ", " + y + "] to " + address);
						}

						hosts.get(address)
								.input(new long[] { x, y });
					}
				}

			}

		}

	}

	static long part2() {
		String code = Advent.readLine("aoc19/023-data");
		boolean debugPrint = false;

		Map<Integer, Parser> hosts = new HashMap<>();

		for (int i = 0; i < 50; i++) {

			Parser p = Parser.create(code);
			p.input(i);
			p.stopOnInput();
			p.stopOnOutput();

			hosts.put(i, p);

		}

		Set<Long> yValues = new HashSet<>();
		long[] natPacket = null;

		long count = 0;

		while (true) {
			count++;

			int idleCount = 0;

			for (Map.Entry<Integer, Parser> p : hosts.entrySet()) {

				int hostAddress = p.getKey();
				Parser host = p.getValue();

				host.run();
				if (host.needsInput()) {
					host.input(-1);
					idleCount++;
				}

				if (host.hasUnhandledOutput() && host.sizeOfUnhandledOutput() == 3) {
					List<String> s = host.getUnhandledOutput();

					if (debugPrint) {
						System.out.println("host " + hostAddress + " has unhandled packet.");
						System.out.println("output is: " + s.stream()
								.collect(Collectors.joining(", ")));
					}

					for (int i = 0; i < s.size(); i += 3) {
						int address = Integer.parseInt(s.get(i));
						long x = Long.parseLong(s.get(i + 1));
						long y = Long.parseLong(s.get(i + 2));

						if (address > hosts.size()) {
							if (address == 255) {
								if (debugPrint) {
									System.out.println("Sending to NAT => " + x + ", " + y);
								}
								natPacket = new long[] { x, y };
							}
						} else {

							if (debugPrint) {
								System.out.println(hostAddress + " sending [" + x + ", " + y + "] to " + address);
							}

							hosts.get(address)
									.input(new long[] { x, y });

						}
					}
				}

			}

			if (idleCount == hosts.size() && natPacket != null) {

				if (debugPrint) {
					System.out.println("Idle after " + count + " runs.");
					System.out.println("Sending to 0: " + natPacket[0] + ", " + natPacket[1]);
				}

				// send last NAT packet to address 0
				hosts.get(0)
						.input(natPacket);

				long y = natPacket[1];
				if (yValues.contains(y)) {

					return y;

				} else {
					yValues.add(y);
				}
			}

		}

	}

}
