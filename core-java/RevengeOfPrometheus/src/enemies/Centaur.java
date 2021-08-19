package enemies;
/**
 * First type of enemy that has middle values for everything. 
 * @author Abdullah Ilgun
 */
public class Centaur extends Enemy{

	public Centaur(int health, int damage, int armour, int potPossibility) {
		super(health, damage, armour, potPossibility);
	}
	
	@Override
	public String saySomething() {
		return "A centaur is shooting at me!";
	}
}
