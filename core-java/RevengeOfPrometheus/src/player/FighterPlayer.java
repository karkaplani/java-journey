package player;
/**
 * First concrete class of player. The player will have more damage and skill damage, less health and armour if this is chosen
 * @author Abdullah Ilgun
 */
public class FighterPlayer extends PlayerType{

	public FighterPlayer(int health, int damage, int armour, int skillDamage, int cPoint, int potAmount) {
		super(health, damage, armour, skillDamage, cPoint, potAmount);
	}
	
	@Override
	public String saySomething() {
		return "You are a fighter now!\n"
				+ "Health      : 100\n"
				+ "Damage      : 15\n"
				+ "Armour      : 5\n"
				+ "Skill Damage: 20 + armour breaking (Will damage you 5)";
	}
}
