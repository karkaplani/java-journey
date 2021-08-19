package superclasses;
/**
 * Generic class for creating item subclasses. Every item has a name, some text displayed when gotten, and an effect
 * on the user values. The item classes will inherit those things from this class, and the item objects will be 
 * created from this class polymorphically.
 * @author Abdullah Ilgun
 * @since 04.01.2020
 */
public abstract class Equipment {
	
	private String name;
	/**
	 * This is used at the beginning of creating a weapon object.
	 * @param name
	 */
	public Equipment(String name) {
		this.setName(name);
	}
	/**
	 * Every item will have something to display on the screen when gotten.
	 */
	public abstract String saySomething();
	/**
	 * @param valueToUpgrade will be upgraded according to the item type
	 * @return the effected value will be returned to the program
	 */
	public abstract int effect(int valueToUpgrade);
	/**
	 * @return name of the item, and it will be displayed as equipment while the player facing with enemies 
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name used at the beginning of the process of creating an equipment object
	 */
	public void setName(String name) {
		this.name = name;
	}
}
