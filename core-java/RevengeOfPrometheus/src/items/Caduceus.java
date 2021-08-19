package items;

import superclasses.Equipment;
/**
 * Last choice of item. The equipment object will be generated from this class if the user inputs 3 in the buyWeapon method.
 * It has no extra value. Only the effect.
 * @author Abdullah Ilgun
 */
public class Caduceus extends Equipment {

	public Caduceus(String name) {
		super(name);
	}

	@Override
	public String saySomething() {
		return "Wise choice! I will always be with you by giving this.";
	}
	
	/**
	 * It sets the pot amount of user which means healing, to 1000 as it can be count as infinity for this game's standards
	 */
	@Override
	public int effect(int potAmount) {
		potAmount = 1000;
		System.out.println("You have infinity healing now!");
		return potAmount;
	}
}
