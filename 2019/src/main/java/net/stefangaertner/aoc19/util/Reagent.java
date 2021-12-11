package net.stefangaertner.aoc19.util;

public class Reagent {

	public Reagent (String in) {
		String str = in.trim();
		String[] parts = str.split(" ");

		this.num = Integer.parseInt(parts[0]);
		this.name = parts[1];

	}

	public Reagent (String name, int num) {
		this.name = name;
		this.num = num;
	}

	public int num;
	public String name;

	public boolean isFuel() {
		return this.name.equals("FUEL");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + num;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reagent other = (Reagent) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (num != other.num)
			return false;
		return true;
	}

	public boolean isOre() {
		return this.name.equals("ORE");
	}

	@Override
	public String toString() {
		return "Reagent [num=" + num + ", name=" + name + "]";
	}

}
