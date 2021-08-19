package superclasses;
/**
 * Generic class for all types of enemies and players. Enemy and PlayerType subclasses inherits from this class 
 * as all of them are creatures eventually with having health, damage and armour values in the game. 
 * @author Abdullah Ilgun
 * @since 03.01.2020
 */
public abstract class Creature {
	
	private int health;
	private int damage;
	private int armour;
	
	/**
	 * 
	 * @param health used in the main game class to determine if either a player or an enemy is dead
	 * @param damage used in the main game class to determine how much health value will be decreased from the opponent
	 * @param armour used in the main game class to determine how much value will be ignored for the opponent's damage value
	 */
	public Creature(int health, int damage, int armour) {
		this.setHealth(health);
		this.setDamage(damage);
		this.setArmour(armour);
	}
	
	/**
	 * @return health value for the use of determining whether the creature is dead or alive
	 */
	public int getHealth() {
		return health;
	}
	
	/**
	 * @return damage value for the use of determining how much health value will be decreased from the opponent
	 */
	public int getDamage() {
		return damage;
	}
	/**
	 * @return armour value for the use of determining how much damage value will be ignored from the opponent
	 */
	public int getArmour() {
		return armour;
	}
	/**
	 * @param health used to set the health value at the creating process of the creature objects as well as constantly used whenever the health value is changed
	 */
	public void setHealth(int health) {
		this.health = health;
	}
	/**
	 * @param damage used to set the damage value at the creating process of the creature objects. 
	 * Also used when Excalibur is gotten to change the damage value of the user
	 */
	public void setDamage(int damage) {
		this.damage = damage;
	}
	/**
	 * @param armour is used to set the armour value at the creating process of the creature objects.
	 * Also used when Aegis is gotten to change the armour value of the user
	 */
	public void setArmour(int armour) {
		this.armour = armour;
	}
	/**
	 * This abstract method will be used polymorphically throughout the subclasses for having some text when a creature is appeared.
	 * @return 
	 */
	public abstract String saySomething();
}
