package game;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import enemies.Centaur;
import enemies.Cyclops;
import enemies.Enemy;
import enemies.Harpy;
import enemies.Zeus;
import items.Aegis;
import items.Caduceus;
import items.Excalibur;
import player.FighterPlayer;
import player.PlayerType;
import player.TankPlayer;
import superclasses.Equipment;
/**
 * This is an old school text based adventure game called Revenge of Prometheus. The program is mainly based on object-oriented programming
 * concepts; abstraction, inheritance, encapsulation, and object manipulations. Iteration algorithms and a little math is used throughout
 * the program. The story is an alternative scenario for the Greek Mythology character Prometheus. 
 * The entire program took 3 days to design and write by myself. 
 * See the method explanations for better understanding as well as the other class descriptions.
 * throws InterruptedException statements are for adding a sleep time between the sentences being displayed on the screen.
 * @author Abdullah Ilgun
 * @version 1.0
 * @since 04.01.2020
 */
public class RevengeOfPrometheus {
	
	private Scanner hey = new Scanner(System.in);
	/*
	 * Quick explanation for the player and enemy variables: 
	 * They are like a bridge between objects and the program. They are gotten from the objects themselves by the
	 * getter methods, and go into the objects again by the setter methods. So, the manipulations are done to the 
	 * object parameters eventually rather than the bridge values here. They are also useful for having a better 
	 * controlling over the program. 
	 */
	//Player variables
	private PlayerType player;
	private int playerHealth;
	
	private int playerDamage;
	private int netPlayerDamage;
	private int skillDamage;
	private int skillDamageToUser;
	
	private int cPoint;
	private int playerArmour;
	private int potAmount;
	
	//Item variables
	private Equipment equipment;
	private String equipmentName = "Fists"; //The initial value for equipment
	private final int damageIncrease = 30; //Used in the buyWeapon method to upgrade the damage value of the player 
	private final int armourIncrease = 8; //Used in the buyWeapon method to upgrade the armour value of the player
	
	//Enemy Variables
	private Enemy enemy; //The generic enemy object pass by reference
	private ArrayList<Enemy> enemies = new ArrayList<>(); //This will be filled in the get enemy object with different enemy objects.
	private int enemyDamage;
	private int netEnemyDamage;
	private int enemyHealth;
	private int enemyArmour;
	private int potPossibility;
	
	//Boss variables
	private Zeus boss;
	private int bossHeal;
	private final int initialBossDamage = 20; //Initial values are essential for arranging the rage mode values of Zeus
	private final int initialBossHealth = 200;
	
	//Pot variables
	private final int potRecovery = 10;
	private boolean isPot = false;
	
	//Control variables
	private int enemyCounter;
	private int bossRoundCounter; //To determine the healing time of Zeus
	private static final int firstRunMaxEnemy = 5;
	private static final int secondRunMaxEnemy = 3;
	private int choice;
	private String answer;
	
	public static void main(String[] args) throws InterruptedException {
		
		RevengeOfPrometheus obj = new RevengeOfPrometheus();
		obj.gamePlay();
	}
	/**
	 * Almost all the story parts and the methods essential for playing the games is called here.
	 * The game plan is => set the player, first mob run, item buying, second mob run, meeting with sphynx, and the final boss.
	 */
	public void gamePlay() throws InterruptedException {
		System.out.println("Long, long time ago.."); TimeUnit.SECONDS.sleep(1);
		System.out.println("a self-righteous retard called Zeus, punished me with chaining to a mountain forever.."); TimeUnit.SECONDS.sleep(2);
		System.out.println("just because I wanted humans to use fire as well as those arrogants upon the Olympos."); TimeUnit.SECONDS.sleep(2);
		System.out.println("I was suffering till a legendary hero called Heracles saved me from my chains.. "); TimeUnit.SECONDS.sleep(2);
		System.out.println("NOW IT'S TIME TO TEACH A LESSON TO THE GOD OF THE SKY!"); TimeUnit.SECONDS.sleep(2);
		System.out.println("But I cannot face with him with that body.."); TimeUnit.SECONDS.sleep(2);
		System.out.println("First, I need an enchancement..\n"); TimeUnit.SECONDS.sleep(2);
		System.out.println("Fighters can damage well, but can die easier"); TimeUnit.SECONDS.sleep(1);
		System.out.println("Tanks don't die easily, but are not good at fighting"); TimeUnit.SECONDS.sleep(1);
		setPlayer();
		
		System.out.println("I heard that there are different types of ugly creatures upon the way to Olympos.."); TimeUnit.SECONDS.sleep(2);
		System.out.println("I may have to face with them before Zeus!"); TimeUnit.SECONDS.sleep(2);
		System.out.println("Harpies come from the sky and they are easy to kill, but can give a great damage");
		System.out.println("Centaurs are harder to kill, but have less damage");
		System.out.println("Cyclopses are the toughest ones. I may consider running away from them"); TimeUnit.SECONDS.sleep(6);
		System.out.println("\nHere one is coming!\n"); TimeUnit.SECONDS.sleep(2);
		mobRun(firstRunMaxEnemy);
		
		TimeUnit.SECONDS.sleep(3);
		System.out.println("\nHuh! I am exhausted of fighting.. oh there is a house that I can crash tonight"); TimeUnit.SECONDS.sleep(2);
		System.out.println("Hello, I am Prometheus and.."); TimeUnit.SECONDS.sleep(2);
		System.out.println("Housekeeper: I know"); TimeUnit.SECONDS.sleep(2);
		System.out.println("how.. you look familiar"); TimeUnit.SECONDS.sleep(2);
		System.out.println("is that you, the mysterious enchanter?"); TimeUnit.SECONDS.sleep(2);
		System.out.println("Housekeeper: I am more than an enchanter"); TimeUnit.SECONDS.sleep(2);
		System.out.println("You are Hermes!"); TimeUnit.SECONDS.sleep(2);
		System.out.println("Housekeeper: Well I am Hermes Trismegistus. You can stay with me tonight."); TimeUnit.SECONDS.sleep(3);
		System.out.println("\nNext morning\n"); TimeUnit.SECONDS.sleep(2);
		System.out.println("Thanks for everything"); TimeUnit.SECONDS.sleep(2);
		System.out.println("Hermes: Before I forget, I want to give you something that may help you to take my father down"); TimeUnit.SECONDS.sleep(4);
		System.out.println("Hermes: You cannot do it merely by your hands!"); TimeUnit.SECONDS.sleep(2);
		System.out.println("Hermes: Before that, do you have any CP?\n"); TimeUnit.SECONDS.sleep(2);
		System.out.println("..."); TimeUnit.SECONDS.sleep(1);
		buyWeapon();
		
		mobRun(secondRunMaxEnemy);
		meetSphynx();	
		TimeUnit.SECONDS.sleep(1);
		System.out.println("\n..."); TimeUnit.SECONDS.sleep(1);
		System.out.println("\nHey aren't they pots? What are they up to here?"); TimeUnit.SECONDS.sleep(2);
		System.out.println("Anyway, I'll take 'em"); TimeUnit.SECONDS.sleep(2);
		potAmount += 5;
		System.out.println("\nWoah, Zeus?!");TimeUnit.SECONDS.sleep(2);
		System.out.println("Aren't you supposed be laying down on the Olympos?"); TimeUnit.SECONDS.sleep(2);
		System.out.println("Zeus: I am the mightiest god! I know everything"); TimeUnit.SECONDS.sleep(2);
		System.out.println("Zeus: So, you were coming to take your revenge, eh??"); TimeUnit.SECONDS.sleep(2);
		System.out.println("Zeus: Don't overestimate yourself!"); TimeUnit.SECONDS.sleep(2);
		System.out.println("We will see who's overestimating himself now! Take your guard!"); TimeUnit.SECONDS.sleep(2);
		System.out.println("Zeus: Come on, boy!"); TimeUnit.SECONDS.sleep(2);
		bossFight();
	}
	/**
	 * The user is prompted to choose a player class here. The object for the player is created here according to the choice made. 
	 */
	public void setPlayer() {
		String choice;
		while(true) {
			System.out.println("\nEnchanter: Do you want to be a fighter, or a tank?");
			System.out.print("Your choice: "); choice = hey.nextLine();
			
			if(choice.equalsIgnoreCase("fighter")) {
				player = new FighterPlayer(100, 15, 5, 20, 0, 0); //health, damage, armour, skill damage, cowardice point, pot amount
				player.saySomething();
				break;
			}
			else if(choice.equalsIgnoreCase("tank")) {
				player = new TankPlayer(120, 9, 6, 12, 0, 0); //health, damage, armour,skill damage, cowardice point, pot amount
				player.saySomething();
				break;
			}
		}
	}
	/**
	 * Generates and returns a random number between the first and the second parameter entered. 
	 * This method is used in the getEnemy method to generate random values for each enemy's stats.
	 */
	public int randomNumber(int min, int max) {
		int randomNumber = ThreadLocalRandom.current().nextInt(min, max + 1);
		return randomNumber;
	}
	/**
	 * Three types of enemies are created polymorphically here. Then they are added to the enemies list and shuffled. Finally the first
	 * element(enemy) of the list is returned to the caller in order to give a fully randomized random enemy. 
	 */
	public Enemy getEnemy() {
		Enemy enemy1 = new Centaur(randomNumber(40,45), randomNumber(8,10), randomNumber(0,3), randomNumber(10,20)); //health, damage, armour, pot possibility
		Enemy enemy2 = new Harpy(randomNumber(25,35), randomNumber(10,12), randomNumber(0,3), randomNumber(10,20));
		Enemy enemy3 = new Cyclops(randomNumber(55,65), randomNumber(8,10), randomNumber(3,5), randomNumber(12,20));
		enemies.add(enemy1); 
		enemies.add(enemy2);
		enemies.add(enemy3);
		Collections.shuffle(enemies);
		return enemies.get(0);
	}
	/**
	 * Used in the mobRun and bossFight methods to set the values to maintain the game. 
	 */
	public void setStats() {
		//Enemy statistics
		enemy = getEnemy(); //Random enemy
		enemyDamage = enemy.getDamage();
		enemyArmour = enemy.getArmour();
		potPossibility = enemy.getPotPossibility();
		enemyHealth = enemy.getHealth();
		
		if(enemyHealth <= 0) { //Recursive method is used to prevent the enemy health to be negative
			setStats();
		}
		
		if(potPossibility > 15) { //Determines whether the enemy will drop a pot
			isPot = true;
		}
		
		if(enemyDamage - playerArmour < 0) { //To prevent the case that if the enemy damage is less that player armour, the player health will be increased
			netEnemyDamage = 0;
		}
		else {
			netEnemyDamage = enemyDamage - playerArmour;
		}
		
		//Player statistics
		playerHealth = player.getHealth();
		playerDamage = player.getDamage();
		playerArmour = player.getArmour();
		netPlayerDamage = playerDamage - enemyArmour;
		potAmount = player.getPotAmount();
		skillDamage = player.getSkillDamage(); //No need for net damage since the skill can break the armour
		skillDamageToUser = player.getSkillDamage()/4;
		enemy.saySomething(); //Finally warns the user for enemy 
	}
	
	public void mobRun(int maxEnemy) throws InterruptedException {
		
		enemyCounter = 0; //This method will be called twice. It can't start the loop as long as setting the value to 0 each time
		
		while(enemyCounter < maxEnemy) {
			
			setStats();
			System.out.println("Your Health          Enemy Health");
			System.out.printf("    %d                    %d%n%n", playerHealth, enemyHealth);
			System.out.println("Your armour : " + playerArmour);
			System.out.println("Enemy armour: " + enemyArmour);
			System.out.println("Pots        : " + potAmount);
			System.out.println("Equipment   : " + equipmentName);
			
			ESCAPED: //If user chooses to run away, starts over from here. Also starts from here when an enemy is beat down
			do {
				try {
					System.out.println("\nWhat will I do? (1 to start fighting, 2 to run away)");
					System.out.print("Choice: "); choice = hey.nextInt();

					if(choice!=1 && choice !=2) {
						throw new IllegalArgumentException();
					}
					else {
						if (choice == 1) { //Battle starts here
							
							while(enemyHealth > 0 && playerHealth > 0) {
								NOPOT: //If user has no pots, starts over from here
								do {
									try {
										System.out.println("\nYour Health          Enemy Health");
										System.out.printf("    %d                    %d%n%n", playerHealth, enemyHealth);
										System.out.println("\n1-)Attack");
										System.out.println("2-)Drink a pot");
										System.out.println("3-)Use special attack");
										System.out.println("\nWhat will I do?");
										System.out.print("Choice: "); choice = hey.nextInt();
										
										if(choice!=1 && choice!=2 && choice !=3 ) {
											throw new IllegalArgumentException();
										}
										else { //User makes an appropriate choice 
											if(choice == 1) {
												TimeUnit.SECONDS.sleep(1);
												System.out.println("\nTake it!"); TimeUnit.SECONDS.sleep(1);
												enemyHealth -= netPlayerDamage;
												playerHealth -= netEnemyDamage;
												
												enemy.setHealth(enemyHealth);
												player.setHealth(playerHealth);
											}
											else if(choice == 2) {
												if(potAmount <= 0) { //If user has no pots
													TimeUnit.SECONDS.sleep(1);
													System.out.println("\nD'oh! I don't have any pots!"); TimeUnit.SECONDS.sleep(1);
													break NOPOT;
												}
												else {
													TimeUnit.SECONDS.sleep(1);
													System.out.println("\n Gluk gluk.. I feel better now!"); TimeUnit.SECONDS.sleep(1);
													playerHealth += potRecovery;
													potAmount--;
												}
											}
											else if(choice == 3) {
												TimeUnit.SECONDS.sleep(1);
												System.out.println("\nHELLAS!"); TimeUnit.SECONDS.sleep(1);
												enemyHealth -= skillDamage;
												playerHealth -= netEnemyDamage;
												playerHealth -= skillDamageToUser;
												
												enemy.setHealth(enemyHealth);
												player.setHealth(playerHealth);
											}	
										}
									}catch(IllegalArgumentException e) { //If the input is different than the options
										System.err.println("\nThat's not a choice.. I might've confused!");
									}catch(InputMismatchException e) { //If the input is different than a number
										System.err.println("\nThat's not a number.. I might've confused!");
									} hey.nextLine();
							
								}while(enemyHealth > 0 && playerHealth > 0);
							}
							if(enemyHealth <= 0) {
								System.out.println("\nNobody can beat me!"); TimeUnit.SECONDS.sleep(2);
								if(isPot) { //It is already determined in the setStats method
									System.out.println("\nLucky me! There's a pot dropped from the enemy"); TimeUnit.SECONDS.sleep(2);
									potAmount++;
									player.setPotAmount(potAmount);
								}
								enemyCounter++;
								break ESCAPED;
							}
							else if(playerHealth <= 0) {
								finalScenario(4);
							}
 						}
						else if (choice == 2) { //Run away, increases the cowardice point
							System.out.println("\nHmm I found gems called CP, I'll take them");
							cPoint += 10; 
							player.setcPoint(cPoint);
							break ESCAPED;
						}
					}
				}catch(IllegalArgumentException e) {
					System.err.println("There are only 2 choices");
				}catch(InputMismatchException e) {
					System.err.println("I must enter a number");
				} hey.nextLine();
				
			}while(choice == 1 || choice == 2);
		}
	}
	/**
	 * This method is to give user an upgrade since it is not possible to fight with the final boss with the current stats. 
	 */
	public void buyWeapon() throws InterruptedException {
		
		if(cPoint > 40) { //If the player ran away more than 4 times
			System.out.println("\n..."); TimeUnit.SECONDS.sleep(2);
			System.out.println("Hermes: Oh you want to take a legendary item?"); TimeUnit.SECONDS.sleep(2);
			System.out.println("Hermes: Well, YOU CAN'T!!"); TimeUnit.SECONDS.sleep(2);
			System.out.println("I'm not giving you anything since you have  " + cPoint + " CP(Cowardness Point)"); TimeUnit.SECONDS.sleep(2);
			System.out.println("Good luck to take him down by your fists!"); TimeUnit.SECONDS.sleep(2);
		}
		else { //If the player has been brave enough
			TimeUnit.SECONDS.sleep(2);
			System.out.println("Good, you don't have much CP(Cowardness Point), so I can give you one of those"); TimeUnit.SECONDS.sleep(2);
			System.out.println("1-)Excalibur");
			System.out.println("   Yes, I stole it from another mythology I am the god of theft, remember?");
			System.out.println("   Anyways, it is King Arthur's legendary sword Excalibur. ");
			System.out.println("   It can damage your enemies drastically. Just be careful when using your");
			System.out.println("   special attack. It can damage you drastically as well..");
			
			System.out.println("2-) Aegis");
			System.out.println("    Yes, I stole it from my own dad! If you want to beat him by his own weapon,");
			System.out.println("    take it! You will get less damage with his shield Aegis.");
			
			System.out.println("3-) Caduceus");
			System.out.println("    No, I didn't steal it from anywhere. I am giving up my own staff for you if you want.");
			System.out.println("    By Caduceus, you can heal yourself infinitively. ");
			do {
				try {
					System.out.println("What do you want to take, Prometheus?");
					System.out.print("Choice: "); choice = hey.nextInt();
					
				    if(choice < 1 || choice > 3 ) {
				       throw new IllegalArgumentException();
				    }
				    else { //If an appropriate choice made
				    	if(choice == 1) {
				    		equipment = new Excalibur("Excalibur", damageIncrease); TimeUnit.SECONDS.sleep(2);
				    		equipment.saySomething();
				    		player.setDamage(equipment.effect(playerDamage));
				    		player.setSkillDamage(equipment.effect(skillDamage));
				    		
				    		playerDamage = player.getDamage();
				    		skillDamage = player.getSkillDamage();
				    		equipmentName = equipment.getName();
				    	}
				    	else if(choice == 2) {
				    		equipment = new Aegis("Aegis", armourIncrease); TimeUnit.SECONDS.sleep(2);
				    		equipment.saySomething();
				    		player.setArmour(equipment.effect(playerArmour));
				    		
				    		playerArmour = player.getArmour();
				    		equipmentName = equipment.getName();
				    	}
				    	else if(choice == 3) {
				    		equipment = new Caduceus("Caduceus"); TimeUnit.SECONDS.sleep(2);
				    		equipment.saySomething();
				    		player.setPotAmount(equipment.effect(potAmount));
				    		
				    		potAmount = player.getPotAmount();
				    		equipmentName = equipment.getName();
				    	}
				    }
				}catch(IllegalArgumentException e) {
					System.err.println("Easy, I only have those!");
				}catch(InputMismatchException e) {
					System.err.println("Please tell me a number!");
				} hey.nextLine();
			} while(choice < 1 || choice > 3 );
		}
		TimeUnit.SECONDS.sleep(2);
		System.out.println("\nHermes: Good luck, Prometheus!\n"); TimeUnit.SECONDS.sleep(2);
		System.out.println("Hermes: May the Force be with you!"); TimeUnit.SECONDS.sleep(2);
		System.out.println("Force? Isn't it from Star Wars?"); TimeUnit.SECONDS.sleep(4);
		System.out.println("Hermes: Dammit, I am a thief! Jeez.. ok leave now!\n"); TimeUnit.SECONDS.sleep(2);
	}
	/**
	 *  This method is also giving user a heal before facing with the final boss as it will be hard 
	 *  to battle right after the mob runs.
	 */
	public void meetSphynx() throws InterruptedException {
		TimeUnit.SECONDS.sleep(2);
		System.out.println("\n...");
		System.out.println("Spyhnx, really? Aren't you from the Egyptian Mythology?"); TimeUnit.SECONDS.sleep(2);
		System.out.println("Sphynx: Ssh! I am the one asking questions here"); TimeUnit.SECONDS.sleep(2);
		System.out.println("Sphynx: Introduce thyself, warrior!"); TimeUnit.SECONDS.sleep(2);
		System.out.println("I am Prometheus, I am on my way to take Zeus down!"); TimeUnit.SECONDS.sleep(3);
		System.out.println("Sphynx: Easy.. before that, you shall answer my question"); TimeUnit.SECONDS.sleep(2);
		System.out.println("What goes on four legs at dawn, two legs at noon, and three legs in the evening?"); TimeUnit.SECONDS.sleep(3);
		System.out.print("Answer: "); answer = hey.nextLine();
		while(!(answer.equalsIgnoreCase("Human"))) {
			System.out.println("Wrong!");
			System.out.print("Answer: "); answer = hey.nextLine();
		} 
		System.out.println("\nSphynx: Thou art a young(for me), yet clever warrior!"); TimeUnit.SECONDS.sleep(2);
		System.out.println("Sphynx: I'm healing thee as a reward!");
		playerHealth += 70;
		player.setHealth(playerHealth);
	}
	/**
	 * The final method which will conclude the game. A new object for the boss is created
	 * Different scenarios are written for the different endings in this method.
	 * The iteration algorithm is similar to the mobRun's, yet there are some differences
	 * that are hard to implement in the same method, so another method is created for
	 * the boss fight to achieve less complexity. 
	 */
	public void bossFight() throws InterruptedException {
	
		boss = new Zeus(200, 15, 6, 0, 10); //health, damage, armour, pot possibility, heal himself
		enemyHealth = boss.getHealth();
		enemyDamage = boss.getDamage();
		bossHeal = boss.getHealAmount();
		
		if(enemyDamage - playerArmour < 0) {
			netEnemyDamage = 0;
		}
		else {
			netEnemyDamage = enemyDamage - playerArmour;
		}
		
		playerHealth = player.getHealth();
		playerDamage = player.getDamage();
		netPlayerDamage = playerDamage - enemyArmour;
		skillDamage = player.getSkillDamage();
		skillDamageToUser = player.getSkillDamage()/10;
		
		while(enemyHealth > 0 && playerHealth > 0) {
			NOPOT: //If user has no pots, starts over from here
			do {
				try {
					bossArrangements();
					System.out.println("\nEquipment: " + equipmentName);
					System.out.println("\nYour Health          Zeus' Health");
					System.out.printf("    %d                   %d%n%n", playerHealth, enemyHealth);
					System.out.println("\n1-)Attack");
					System.out.println("2-)Heal");
					System.out.println("3-)Use special attack");
					System.out.println("\nWhat will I do?");
					System.out.print("Choice: "); choice = hey.nextInt();
					
					if(choice!=1 && choice!=2 && choice !=3 ) {
						throw new IllegalArgumentException();
					}
					else { //User makes an appropriate choice 
						if(choice == 1) {
							TimeUnit.SECONDS.sleep(1);
							System.out.println("TAKE THIS ZEUS!"); TimeUnit.SECONDS.sleep(1);
							enemyHealth -= netPlayerDamage;
							playerHealth -= netEnemyDamage;
							boss.setHealth(enemyHealth);
							player.setHealth(playerHealth);
							bossRoundCounter++;
						}
						else if(choice == 2) {
							if(potAmount <= 0) { //If user has no pots
								TimeUnit.SECONDS.sleep(1);
								System.out.println("\nD'oh! I don't have any pots!"); TimeUnit.SECONDS.sleep(1);
								break NOPOT;
							}
							else {
								TimeUnit.SECONDS.sleep(1);
								System.out.println("\nGluk, gluk.. I feel better now!"); TimeUnit.SECONDS.sleep(1);
								playerHealth += potRecovery;
								potAmount--;
								bossRoundCounter++;
							}
						}
						else if(choice == 3) {
							TimeUnit.SECONDS.sleep(1);
							System.out.println("HELLAS!"); TimeUnit.SECONDS.sleep(1);
							enemyHealth -= skillDamage;
							playerHealth -= netEnemyDamage;
							playerHealth -= skillDamageToUser;
							boss.setHealth(enemyHealth);
							player.setHealth(playerHealth);
							bossRoundCounter++;
						}	
					}
				}catch(IllegalArgumentException e) {
					System.err.println("That's not a choice.. I might've confused!");
				}catch(InputMismatchException e) {
					System.err.println("That's not even a number.. I might've confused!");
				} hey.nextLine();
				
			}while(enemyHealth > 0 && playerHealth > 0);
		}
		if(enemyHealth <= 0) {
			finalScenario(3);
		}
		else if(playerHealth <= 0) {
			TimeUnit.SECONDS.sleep(2);
			System.out.println("\nZeus: You're still a weak and pathetic titan.."); TimeUnit.SECONDS.sleep(2);
			System.out.println("Zeus: Do you want mercy? Yes or no?"); TimeUnit.SECONDS.sleep(1);
			answer = hey.nextLine();
			if(answer.equalsIgnoreCase("yes")) {
				finalScenario(1);
			}
			else {
				finalScenario(2);
			}
		}
	}
	/**
	 * Whenever the boss initial health is less than 20%, he will turn into the rage mode, 
	 * which will increase his damage by 5. Also, once in every 3 rounds, he will heal
	 * and increase his health by 10.
	 */
	public void bossArrangements() {
		
		if(enemyHealth <= initialBossHealth/20) {
			System.out.println("\nZeus: Now you piss me off!");
			System.out.println("Feel my anger!!");
			enemyDamage += 5;
			boss.setDamage(enemyDamage);
		}
		else {
			enemyDamage = initialBossDamage;
		}
		
		if(bossRoundCounter % 3 == 0 && bossRoundCounter != 0) {
			System.out.println("\nZeus healed himself");
			enemyHealth += bossHeal;
		}
	}
	
	public void finalScenario(int scenarioNumber) throws InterruptedException {
		
		if(scenarioNumber == 1) { //Lose and mercy
			TimeUnit.SECONDS.sleep(2);
			System.out.println("\nZeus: Mercy, eh?"); TimeUnit.SECONDS.sleep(2);
			System.out.println("Zeus: You are even more pathetic now!"); TimeUnit.SECONDS.sleep(2);
			System.out.println("Zeus: I will chain you to that rock again.."); TimeUnit.SECONDS.sleep(2);
			System.out.println("Zeus: but this time, a cyclop will come and crush your balls everyday"); TimeUnit.SECONDS.sleep(2);
			System.out.println("Zeus: Hasta la vista, Prometheus!"); TimeUnit.SECONDS.sleep(2);
			playAgain();
		}
		else if(scenarioNumber == 2) { //Loss and no mercy
			TimeUnit.SECONDS.sleep(2);
			System.out.println("\nZeus: You are stupid, yet a proud person.. "); TimeUnit.SECONDS.sleep(2);
			System.out.println("Zeus: respect.."); TimeUnit.SECONDS.sleep(2);
			System.out.println("Zeus: I will kill you and send you to the Underworld now."); TimeUnit.SECONDS.sleep(2);
			System.out.println("Zeus: You can try to escape from Hades and beat me again"); TimeUnit.SECONDS.sleep(2);
			System.out.println("Zeus: Adios, Prometheus!"); TimeUnit.SECONDS.sleep(2);
			playAgain();
		}
		
		else if(scenarioNumber == 3) {
			TimeUnit.SECONDS.sleep(2);
			System.out.println("\nZeus: What?!"); TimeUnit.SECONDS.sleep(2);
			System.out.println("Zeus: How come you became so strong.."); TimeUnit.SECONDS.sleep(2);
			System.out.println("You are a miserable father.."); TimeUnit.SECONDS.sleep(2);
			System.out.println("You will die now thanks to your sons, Heracles and Hermes "); TimeUnit.SECONDS.sleep(2);
			System.out.println("Sayonara, Zeus!"); TimeUnit.SECONDS.sleep(2);
			playAgain();
		}
		
		else if(scenarioNumber == 4) {
			TimeUnit.SECONDS.sleep(2);
			System.out.println("\nReally.."); TimeUnit.SECONDS.sleep(2);
			System.out.println("I'm getting killed by an ordinary creature.."); TimeUnit.SECONDS.sleep(2);
			playAgain();
		}
	}

	public void playAgain() throws InterruptedException {
		System.out.println(".");
		System.out.println(".");
		System.out.println("*****    **    *    ***");
		System.out.println("*        * *   *    *  *");
		System.out.println("*****    *  *  *    *   *");
		System.out.println("*        *   * *    *  *");
		System.out.println("*****    *     *    ***");
		System.out.println("Press 1 to play again");
		System.out.println("Press any other key to exit");
		answer = hey.nextLine();
		if(answer.equals("1")) {
			main(null);
		}
		else {
			hey.close();
			System.exit(0);
		}
	}
}


