package items;
import superclasses.Equipment;
/**
 * Second choice of item. The equipment object will be generated from this class if the user inputs 2 in the buyWeapon method.
 * It has armour increase as an extra value.
 * @author Abdullah Ilgun
 */
public class Aegis extends Equipment{

	private int armourIncrease;
	/**
	 * @param armourIncrease will increase the user armour value by the value in the playerGame class by using the setter and getters here
	 */
	public Aegis(String name, int armourIncrease) {
		super(name);
		this.setArmourIncrease(armourIncrease);
	}

	@Override
	public String saySomething() {
		return "Interesting choice.. daddy will love that ((:"; 
	}

	@Override
	public int effect(int armour) {
		System.out.println("Your armour is increased");
		armour =+ armourIncrease;
		return armour;
	}

	public int getArmourIncrease() {
		return armourIncrease;
	}

	public void setArmourIncrease(int armourIncrease) {
		this.armourIncrease = armourIncrease;
	}
	
}
