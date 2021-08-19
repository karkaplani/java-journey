package enemies;
import superclasses.Creature;
/**
 * Another generic subclass inheriting from creature. Enemies will only have pot possibility as a different thing. 
 * @author Abdullah Ilgun
 * @since 03.01.2020
 */
public abstract class Enemy extends Creature{
	
	private int potPossibility;
	/**
	 * @param potPossibility is the determiner for whether the player's pot amount will be increases or not
	 */
	public Enemy(int health, int damage, int armour, int potPossibility) {
		super(health, damage, armour);
		this.setPotPossibility(potPossibility);
	}
	/**
	 * When an new enemy is generated, a text will be appeared on the screen. It will be determined polymorphically 
	 * by the type of the enemy 
	 */
	@Override
	public abstract String saySomething();
	
	public void setPotPossibility(int potPossibility) {
		this.potPossibility = potPossibility;
	}
	
	public int getPotPossibility() {
		return potPossibility;
	}
	
}
