package items;

import superclasses.Equipment;
/**
 * First choice of item. The equipment object will be generated from this class if the user inputs 1 in the buyWeapon method.
 * It has damageIncrease increase as an extra value.
 * @author Abdullah Ilgun
 */
public class Excalibur extends Equipment{

	private int damageIncrease;
	/**
	 * @param damageIncrease will increase the user damage and skill damage values by the value in the playerGame class by using the setter and getters here
	 */
	public Excalibur(String name, int damageIncrease) {
		super(name);
		this.setDamageIncrease(damageIncrease);
	}
	
	@Override
	public String saySomething() {
		return "Good choice! Be careful using it.";
	}
	
	@Override
	public int effect(int damage) {
		System.out.println("Your damage is increased");
		damage += damageIncrease;
		return damage;
	}

	public int getDamageIncrease() {
		return damageIncrease;
	}

	public void setDamageIncrease(int damageIncrease) {
		this.damageIncrease = damageIncrease;
	}
}
