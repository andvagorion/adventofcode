package net.stefangaertner.aoc18.pojo;

import java.util.HashSet;
import java.util.Set;

public class Army {
	
	public int id;

	public ArmyType type;

	public int units;
	public int hpEach;

	public Set<AttackType> weaknesses = new HashSet<>();
	public Set<AttackType> immunities = new HashSet<>();

	public int damage;
	public AttackType strength;
	public int initiative;

	@Override
	public String toString() {
		String s = "[" + type + "] " + units + " units each with " + hpEach + " hit points ";
		if (!immunities.isEmpty() || !weaknesses.isEmpty()) {
			s += "(immune to " + immunities + ", weak to " + weaknesses + ") ";
		}
		s += "with an attack that does " + damage + " " + strength + " damage ";
		s += "at initiative " + initiative;
		return s;
	}

	public int getEffectiveDamage() {
		return this.units * this.damage;
	}

	public int getInitiative() {
		return this.initiative;
	}
	
	public void setArmyType(ArmyType type) {
		this.type = type;
		this.id = getNextId(type);
	}

	public int damageTo(Army other) {
		int damage = this.getEffectiveDamage();
		if (other.immunities.contains(this.strength)) {
			return 0;
		}
		if (other.weaknesses.contains(this.strength)) {
			damage *= 2;
		}
		return damage;
	}

	public void attack(Army other) {
		int damage = this.damageTo(other);
		while (damage >= other.hpEach) {
			other.units--;
			damage -= other.hpEach;
		}
	}

	static int groupIdAttack = 0;
	static int groupIdDefend = 0;

	static int getNextId(ArmyType type) {
		switch (type) {
		case attacker:
			groupIdAttack++;
			return groupIdAttack;
		case defender:
			groupIdDefend++;
			return groupIdDefend;
		default:
			return -1;
		}
	}
}
