package player;
/**
 * Second concrete class of the player class. The player will have more armour and health, but less damage and skill damage if this is chosen.
 * @author Abdullah Ilgun
 */
public class TankPlayer extends PlayerType {

	public TankPlayer(int health, int damage, int armour, int skillDamage, int cPoint, int potAmount) {
		super(health, damage, armour, skillDamage, cPoint, potAmount);
	}
	
	@Override
	public String saySomething() {
		return "You are a tank now!\n"
				+ "Health      : 120\n"
				+ "Damage      : 9\n"
				+ "Armour      : 6\n"
				+ "Skill Damage: 12 + armour breaking (Will damage you 3)";
	}
	
}
