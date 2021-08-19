package player;
import superclasses.Creature;
/**
 * This is also a generic subclass of creature class. Players are created using this class polymorphically. Every player will have 
 * skill damage, cowardice point, and pot amount
 * @author Abdullah Ilgun
 * @since 03.01.2020
 *
 */
public abstract class PlayerType extends Creature{

	private int skillDamage;
	private int cPoint;
	private int potAmount;
	/**
	 * @param skillDamage special attack for the user costs player to lose some of his health, but can break enemy armour as well as more damage
	 * @param cPoint cowardice point to determine whether the user will have a chance to get an item(In the buyEquipment method)
	 * @param potAmount determines if the user will be able to heal himself(increase his health) during the mobRun and bossFight sessions
	 */
	public PlayerType(int health, int damage, int armour, int skillDamage, int cPoint, int potAmount) {
		super(health, damage, armour);
		this.setSkillDamage(skillDamage);
		this.setcPoint(cPoint);
		this.setPotAmount(potAmount);
	}
	
	/**
	 * This method will give a brief info about the player values according to the type chosen for the better understanding
	 */
	public abstract String saySomething();

	public int getSkillDamage() {
		return skillDamage;
	}

	public void setSkillDamage(int skillDamage) {
		this.skillDamage = skillDamage;
	}

	public int getcPoint() {
		return cPoint;
	}

	public void setcPoint(int cPoint) {
		this.cPoint = cPoint;
	}

	public int getPotAmount() {
		return potAmount;
	}

	public void setPotAmount(int potAmount) {
		this.potAmount = potAmount;
	}
	
}
