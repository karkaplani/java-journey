package enemies;
/**
 * Third type of enemy that has high values and hardest to kill. Also has a good possibility to drop a pot.
 * @author Abdullah Ilgun
 */
public class Cyclops extends Enemy{

	public Cyclops(int health, int damage, int armour, int potPossibility) {
		super(health, damage, armour, potPossibility);
	}
	
	@Override
	public String saySomething() {
		return "A cyclops is closing my way!";
	}
}
