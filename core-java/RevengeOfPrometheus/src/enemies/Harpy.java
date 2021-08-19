package enemies;
/**
 * Second type of enemy having the greatest damage, but easiest to kill.
 * @author Abdullah Ilgun
 */
public class Harpy extends Enemy{

	public Harpy(int health, int damage, int armour, int potPossibility) {
		super(health, damage, armour, potPossibility);
	}
	
	@Override
	public String saySomething() {
		return "A harpy is coming from the sky!";
	}
}
