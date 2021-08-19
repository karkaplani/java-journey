package enemies;
/**
 * Last type of enemy which is the final boss. This class' objects are not being generated in the getEnemy class as
 * it is a different type of enemy. Only one object will be generated from this class which is Zeus himself. The toughest
 * one and having a self-healing amount as an extra parameter.
 * @author Abdullah Ilgun
 *
 */
public class Zeus extends Enemy{
	
	private int healAmount;
	
	/**
	 * @param healAmount is constant and 10. It will be used to increase the health value whenever the bossRoundCounter variable
	 * can be divided into 3. That means Zeus will heal himself every third round of the boss fight.  
	 */
	public Zeus(int health, int damage, int armour, int potPossibility, int healAmount) {
		super(health, damage, armour, potPossibility);
		this.setHealAmount(healAmount);
	}
	
	@Override
	public String saySomething() {
		System.out.println("Boss appeared!");
		return null;
	}
	
	public int getHealAmount() {
		return healAmount;
	}

	public void setHealAmount(int healAmount) {
		this.healAmount = healAmount;
	}
	

}
