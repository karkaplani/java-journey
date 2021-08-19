package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

import enemies.Centaur;
import enemies.Cyclops;
import enemies.Enemy;
import enemies.Harpy;
import enemies.Zeus;
import items.Aegis;
import items.Caduceus;
import items.Excalibur;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import player.FighterPlayer;
import player.PlayerType;
import player.TankPlayer;
import superclasses.Equipment;

//generate maze
//Wumpus

public class Gameplay extends Application{

	private PlayerType player;
	private int playerHealth;
	private int playerDamage;
	private int netPlayerDamage;
	private int skillDamage;
	private int skillDamageToUser;
	
	private int cPoint;
	private int playerArmour;
	private int potAmount;
	
	private Equipment equipment;
	private String equipmentName = "Fists"; //The initial value for equipment
	private final int damageIncrease = 30; //Used in the buyWeapon method to upgrade the damage value of the player 
	private final int armourIncrease = 8; //Used in the buyWeapon method to upgrade the armour value of the player
	
	private Enemy enemy; //The generic enemy object pass by reference
	private ArrayList<Enemy> enemies = new ArrayList<>(); //This will be filled in the get enemy object with different enemy objects.
	private int enemyDamage;
	private int netEnemyDamage;
	private int enemyHealth;
	private int enemyArmour;
	private int potPossibility;
	
	private Zeus boss;
	private int bossHeal;
	private final int initialBossDamage = 20; //Initial values are essential for arranging the rage mode values of Zeus
	private final int initialBossHealth = 200;
	
	private final int potRecovery = 10;
	private boolean isPot = false;
	
	private int enemyCounter = 0;
	private int bossRoundCounter; //To determine the healing time of Zeus
	
	private static final int firstRunMaxEnemy = 5;
	private static final int secondRunMaxEnemy = 3;
	private static final int WIDTH = 1350;
	private static final int HEIGHT = 700;
	
	Stage window;
	Label label;
	Label startLabel;
	Button startButton, exitButton;
	VBox root;
	Scene scene;
	
	Button nextButton;
	private int sentenceIndex = 1;
	
	Button fighterButton, tankButton;
	
	Button startFight, runAway;
	
	Button attack, pot, specialAttack;
	
	int test = 0;
	
	String css = this.getClass().getResource("/game/style.css").toExternalForm(); 
	
	public static void main(String[] apos) {
		launch();
	}
	
	public void init() {
		
		label = new Label(); label.setId("label");
		startLabel = new Label(); startLabel.setId("startlabel");
		root = new VBox(); root.setId("root"); 
		
		startButton = new Button("START"); 			  startButton.setId("startbutt");
		exitButton = new Button("EXIT"); 			  exitButton.setId("exitbutt");
		nextButton = new Button("NEXT"); 			  nextButton.setId("nextbutt");
		fighterButton = new Button("FIGHTER"); 		  fighterButton.setId("fighterbutt");
		tankButton = new Button("TANK"); 			  tankButton.setId("tankbutt");
		startFight = new Button("FIGHT"); 			  startFight.setId("startfightbutt");
		runAway = new Button("RUN AWAY"); 			  runAway.setId("runawaybutt");
		attack = new Button("ATTACK"); 				  attack.setId("attackbutt");
		pot = new Button("DRINK POT"); 				  pot.setId("potbutt");
		specialAttack = new Button("SPECIAL ATTACK"); specialAttack.setId("specialattackbutt");
			
		scene = new Scene(root, WIDTH, HEIGHT);
		scene.getStylesheets().add(css);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setTitle("Revenge of Prometheus");
		window.setScene(startScene());
		window.show();
	}
	
	public Scene startScene() {
		startLabel.setText("Revenge of Prometheus");
		root.getChildren().addAll(startLabel, startButton, exitButton);
		
		exitButton.setOnAction(a -> {
			try { System.exit(0);
			} catch (Exception e) { e.printStackTrace(); }
		});
		
		startButton.setOnAction(a -> storyScene());
		
		return scene;
	}
	
	public void storyScene() {
		String sentence = "Long, long time ago..\n" +
							  "a self-righteous retard called Zeus, punished me with chaining to a mountain forever..\n" +
							  "just because I wanted humans to use fire as well as those arrogants upon the Olympos.\n" + 
							  "I was suffering till a legendary hero called Heracles saved me from my chains..\n" + 
							  "NOW IT'S TIME TO TEACH A LESSON TO THE LORD OF THE SKY AND EVERYTHING!\n" + 
							  "But I cannot face with him with that body..\n" +
							  "First, I need an enchancement..";
		
		label.setText(sentence);
		root.getChildren().clear();
		root.getChildren().addAll(label, nextButton);
		
		nextButton.setOnAction( (a) -> playerChoiceScene());
	}
	
	public void playerChoiceScene() {
		
		label.setText("Enchanter: Do you want to be a fighter or a tank?\nFighters damage well, but die easier\n" + ""
					+ "Tanks don't die easily, but not good at fighting");
		root.getChildren().clear();  root.getChildren().addAll(label, fighterButton, tankButton);
		
		fighterButton.setOnAction(a -> {
			player = new FighterPlayer(1000, 15, 5, 20, 0, 0);
			test = 10;
			mobIntroScene();
		}); 
		tankButton.setOnAction(a -> {
			player = new TankPlayer(1200, 9, 6, 12, 0, 0);
			mobIntroScene();
		});
	}
	
	public void mobIntroScene() {
		String sentence = "I heard that there are different types of ugly\ncreatures upon the way to Olympos..\n" + 
							  "I may have to face with them before Zeus!\n" +
							  "Harpies come from the sky and they are easy to kill,\nbut can give a great damage\n" +
							  "Centaurs are harder to kill, but have less damage\n" +
							  "Cyclopses are the toughest ones. I may consider\nrunning away from them\n" +
							  "Here one is coming!";
		label.setText(player.saySomething());
		
		root.getChildren().clear();  root.getChildren().addAll(label, nextButton);

		sentenceIndex = 0;
		nextButton.setOnAction(a -> {
			label.setText(sentence);
			nextButton.setOnAction(b -> mobRun(1));
		} );
	}
	
	public void mobRun(int runIndex) {
		String sentence = String.format("%s%n%nYour Health: %d        Enemy Health: %d", setStats(), playerHealth, enemyHealth);
		sentence += String.format("%nYour Armour: %d            Enemy Armour: %d%nPots: %d%nEquipment: %s", playerArmour, enemyArmour, potAmount, equipmentName);
		label.setText(sentence);
		root.getChildren().clear();  root.getChildren().addAll(label, startFight, runAway);
			
		startFight.setOnAction(a -> fightScenario(runIndex) );
		runAway.setOnAction(a -> runAwayScenario(runIndex) );

		if(runIndex == 1 && enemyCounter == firstRunMaxEnemy) hermesScene();
		if(runIndex == 2 && enemyCounter == secondRunMaxEnemy) meetSphynx();  
	}
	
	public void fightScenario(int runIndex) {

		label.setText("Your health: " + playerHealth + "\nEnemy health: " + enemyHealth);
		
		root.getChildren().clear();  root.getChildren().addAll(label, attack, pot, specialAttack);
		
		attack.setOnAction(a -> {
			enemyHealth -= netPlayerDamage;
			playerHealth -= netEnemyDamage;
			
			enemy.setHealth(enemyHealth);
			player.setHealth(playerHealth);
			
			label.setText("Your health: " + playerHealth + "\nEnemy health: " + enemyHealth + "\nTake it!");

			if(enemyHealth <= 0) afterEnemyScenario(runIndex);
			if(playerHealth <= 0) finalScenario(4);
		});
		
		pot.setOnAction(a -> {
			if(potAmount > 0) {
				playerHealth += potRecovery;
				potAmount--;
				label.setText("Your health: " + playerHealth + "\nEnemy health: " + enemyHealth + "\nGluk gluk, it feels good..");
			} 
			
			else label.setText("Your health: " + playerHealth + "\nEnemy health: " + enemyHealth + "\nOh, I don't have any pots..");
			
			if(enemyHealth <= 0) afterEnemyScenario(runIndex);
			if(playerHealth <= 0) finalScenario(4);
		});
		
		specialAttack.setOnAction(a -> {
			enemyHealth -= skillDamage;
			playerHealth -= netEnemyDamage;
			playerHealth -= skillDamageToUser;
			
			enemy.setHealth(enemyHealth);
			player.setHealth(playerHealth);
			
			label.setText("Your health: " + playerHealth + "\nEnemy health: " + enemyHealth + "\nHELLAS!");
			
			if(enemyHealth <= 0) afterEnemyScenario(runIndex);
			if(playerHealth <= 0) finalScenario(4);
		});
	} 
	
	public void afterEnemyScenario(int runIndex) {
		
		label.setText("NOBODY CAN BEAT ME!");
		root.getChildren().clear();  root.getChildren().addAll(label, nextButton);
		enemyCounter++;
		nextButton.setOnAction(a -> {
			if(isPot) {
				label.setText("Lucky lucky lucky me, there's a pot dropped from the enemy!");
				potAmount++;  player.setPotAmount(potAmount);
				nextButton.setOnAction(b -> mobRun(runIndex));
			}
			else mobRun(runIndex);
		});
		
	}
	
	public void runAwayScenario(int runIndex) {
		label.setText("Hmm I found some CP gems..");
		root.getChildren().clear();  root.getChildren().addAll(label,nextButton);
		nextButton.setOnAction(a -> mobRun(runIndex));
	}

	public void hermesScene() {
		enemyCounter = 0;
		String[] sentences = {"Huh! I am exhausted of fighting.. \noh there is a house that I can crash tonight", 
							  "Hello, I am Prometheus and..", "Housekeeper: I know", "how.. you look familiar",
							  "is that you, the mysterious enchanter?", "Housekeeper: I am more than an enchanter",
							  "You are Hermes!", "Housekeeper: Well I am Hermes Trismegistus.\nYou can stay with me tonight.",
							  "Next morning", "Thanks for everything", 
							  "Hermes: Before I forget, I want to give you something \nthat may help you to take my father down",
							  "Hermes: You cannot do it merely by your hands!", "Hermes: Before that, do you have any CP?", "",
							  
							  "Hermes: Oh you want to take a legendary item?",
							  "Hermes: Well, YOU CAN'T!!",
							  "I'm not giving you anything since you have  " + cPoint + " CP(Cowardness Point)",
							  "Good luck to take him down by your fists!"}; //13
		label.setText(sentences[0]);
		root.getChildren().clear();  root.getChildren().addAll(label, nextButton);

		sentenceIndex = 1;		
		nextButton.setOnAction(a -> {
			label.setText(sentences[sentenceIndex]);  sentenceIndex++;
			if(sentenceIndex == 13 && cPoint <= 4) buyWeapon();
			if(sentenceIndex == 17) mobRun(2);
		});
	}
	
	public void buyWeapon() {
		label.setText("Good, you don't have much CP(Cowardness Point), \nso I can give you one of those");
		Button excaliButt = new Button("EXCALIBUR"); excaliButt.setId("excalibutt");
		Button aegiButt = new Button("AEGIS"); aegiButt.setId("aegibutt");
		Button caucaButt = new Button("CADUCEUS"); caucaButt.setId("caucebutt");
		root.getChildren().clear();  root.getChildren().addAll(label, excaliButt, aegiButt, caucaButt);
		
		excaliButt.setOnAction(a -> {
			equipment = new Excalibur("Excalibur", damageIncrease);
			player.setDamage(equipment.effect(playerDamage));
    		player.setSkillDamage(equipment.effect(skillDamage));
    		
    		playerDamage = player.getDamage();
    		skillDamage = player.getSkillDamage();
    		equipmentName = equipment.getName();
    		
			root.getChildren().clear();  
			label.setText(equipment.saySomething());
			root.getChildren().addAll(label, nextButton);
		});
		
		aegiButt.setOnAction(a -> {
			equipment = new Aegis("Aegis", armourIncrease); 
    		player.setArmour(equipment.effect(playerArmour));
    		
    		playerArmour = player.getArmour();
    		equipmentName = equipment.getName();
    		
    		root.getChildren().clear();  
			label.setText(equipment.saySomething());
			root.getChildren().addAll(label, nextButton);
		});
		
		caucaButt.setOnAction(a -> {
			equipment = new Caduceus("Caduceus"); 
    		equipment.saySomething();
    		player.setPotAmount(equipment.effect(potAmount));
    		
    		potAmount = player.getPotAmount();
    		equipmentName = equipment.getName();
    		
    		root.getChildren().clear();  
			label.setText(equipment.saySomething());
			root.getChildren().addAll(label, nextButton);
		});
		
		String[] sentences = { "Hermes: Good luck, Prometheus!", "Hermes: May the Force be with you!",
							   "Force? Isn't it from Star Wars?", "Hermes: Dammit, I am a thief! Jeez.. ok leave now!", ""};
		sentenceIndex = 0;
		nextButton.setOnAction(a -> {
			label.setText(sentences[sentenceIndex]); sentenceIndex++;
			if(sentenceIndex == 5) mobRun(2);
		});
		
	}

	public void meetSphynx() {
		String[] sentences = { "Spyhnx, really? Aren't you from the Egyptian Mythology?",
							   "Sphynx: Ssh! I am the one asking questions here",
							   "Sphynx: Introduce thyself, warrior!", 
							   "I am Prometheus, I am on my way to take Zeus down!",
							   "Sphynx: Easy.. before that, you shall answer my question",
							   "What goes on four legs at dawn, two legs at noon,\nand three legs in the evening?", "" }; //7
		
		label.setText(sentences[0]);
		root.getChildren().clear();  root.getChildren().addAll(label, nextButton);
		
		sentenceIndex = 0;
		nextButton.setOnAction(a -> {
			label.setText(sentences[sentenceIndex]); sentenceIndex++;
			if(sentenceIndex == 7) sphynxAnswer();
		});
		
	}
	
	public void sphynxAnswer() {
		
		label.setText("What goes on four legs at dawn, two legs at noon, and three legs in the evening?");
		TextField input = new TextField();
		root.getChildren().clear();  root.getChildren().addAll(label, input, nextButton);
		
		nextButton.setOnAction(a -> {
			if(input.getText().equals("Human")) {
				root.getChildren().remove(input);
				label.setText("Thou art a young, yet clever warrior.\nI am healing you as a reward!");
				playerHealth += 70; player.setHealth(playerHealth);
				nextButton.setOnAction(b -> faceZeus());
			}
		});
	}
	
	public void faceZeus() {
		String[] sentences = {"Hey aren't they pots? What are they up to here?", 
							  "Anyway, I'll take 'em\nPots: " + potAmount, "Woah, Zeus?!", 
							  "Aren't you supposed be laying down on the Olympos?",
							  "Zeus: I am the mightiest god! I know everything",
							  "Zeus: So, you were coming to take your revenge, eh??",
							  "Zeus: Don't overestimate yourself!",
							  "We will see who's overestimating himself now! Take your guard!",
							  "Zeus: Come on, boy!", ""};
		
		label.setText(sentences[0]);
		root.getChildren().clear();  root.getChildren().addAll(label, nextButton);
		
		potAmount += 5;
		player.setPotAmount(potAmount);
		
		sentenceIndex = 0;
		nextButton.setOnAction(a -> {
			label.setText(sentences[sentenceIndex]); sentenceIndex++;
			if(sentenceIndex == 9) bossFight();
		});
	}
	
	public void bossFight() {
		
		boss = new Zeus(200, 15, 6, 0, 10); //health, damage, armour, pot possibility, heal himself
		enemyHealth = boss.getHealth();
		enemyDamage = boss.getDamage();
		bossHeal = boss.getHealAmount();
		
		if(enemyDamage - playerArmour < 0) netEnemyDamage = 0; 
		else netEnemyDamage = enemyDamage - playerArmour;
		
		playerHealth = player.getHealth();
		playerDamage = player.getDamage();
		netPlayerDamage = playerDamage - enemyArmour;
		skillDamage = player.getSkillDamage();
		skillDamageToUser = player.getSkillDamage()/10;
		
		label.setText("Your health: " + playerHealth + "\nEnemy health: " + enemyHealth);
		root.getChildren().clear();  root.getChildren().addAll(label, attack, pot, specialAttack);
		
		attack.setOnAction(a -> {
			bossArrangements();
			
			enemyHealth -= netPlayerDamage;
			playerHealth -= netEnemyDamage;
			
			boss.setHealth(enemyHealth);
			player.setHealth(playerHealth);
			bossRoundCounter++;
			
			label.setText("Your health: " + playerHealth + "\nEnemy health: " + enemyHealth + "\nTake this Zeus!");

			if(enemyHealth <= 0) finalScenario(3);
			if(playerHealth <= 0) loseToZeus(); //Go to the game over scene
		});
		
		pot.setOnAction(a -> {
			if(potAmount <= 0) label.setText("Your health: " + playerHealth + "\nEnemy health: " + enemyHealth + "\nThere's no pot!");
			
			else {
				playerHealth += potRecovery;  potAmount--; 
				label.setText("Your health: " + playerHealth + "\nEnemy health: " + enemyHealth + "\nGluk gluk.. I feel better now!");
				bossRoundCounter++;
			}
		});
		
		specialAttack.setOnAction(a -> {
			label.setText("Your health: " + playerHealth + "\nEnemy health: " + enemyHealth + "\nHELLAS!");
			enemyHealth -= skillDamage;
			playerHealth -= netEnemyDamage;
			playerHealth -= skillDamageToUser;
			boss.setHealth(enemyHealth);
			player.setHealth(playerHealth);
			bossRoundCounter++;
		});
		
	}
	
	public void loseToZeus() {
		label.setText("Zeus: You're still a weak and pathetic titan..\nDo you want mercy or not?");
		Button mercy = new Button("Mercy"); Button goToHell = new Button("Go to hell");
		root.getChildren().clear();  root.getChildren().addAll(label, mercy, goToHell);
		mercy.setOnAction(a -> finalScenario(1));
		goToHell.setOnAction(a -> finalScenario(2));
		
	}
	
	public void bossArrangements() {
		
		if(enemyHealth <= initialBossHealth/20) {
			label.setText("Your health: " + playerHealth + "\nEnemy health: " + enemyHealth + "\nYou piss me off..\nFeel my anger!!");
			enemyDamage += 5;
			boss.setDamage(enemyDamage);
		}
		else {
			enemyDamage = initialBossDamage;
		}
		
		if(bossRoundCounter % 3 == 0 && bossRoundCounter != 0) {
			enemyHealth += bossHeal;
			label.setText("Your health: " + playerHealth + "\nEnemy health: " + enemyHealth + "\nZeus healed himself!");
		}
	}
	
	public void finalScenario(int scenarioIndex) {
		
		root.getChildren().clear();  root.getChildren().addAll(label, nextButton);
		
		if(scenarioIndex == 1) {
			String[] sentences = {"Zeus: Mercy, eh?", "Zeus: You are even more pathetic now!", 
								  "Zeus: I will chain you to that rock again..", "Zeus: but this time, a cyclop will come and crush your balls everyday",
								  "Zeus: Hasta la vista, Prometheus!", ""};
			sentenceIndex = 0;
			label.setText(sentences[sentenceIndex]);
			
			nextButton.setOnAction(a -> {
				sentenceIndex++; label.setText(sentences[sentenceIndex]); 
				if(sentenceIndex == 5) System.out.println("Game over");
			});
		}
		
		else if(scenarioIndex == 2) {
			String[] sentences = {"Zeus: You are stupid, yet a proud person.. ", "Zeus: respect..", 
								  "Zeus: I will kill you and send you to the Underworld now.", 
								  "Zeus: You can try to escape from Hades and beat me again", "Zeus: Adios, Prometheus!", ""};
			sentenceIndex = 0;
			label.setText(sentences[sentenceIndex]);
			
			nextButton.setOnAction(a -> {
				sentenceIndex++; label.setText(sentences[sentenceIndex]); 
				if(sentenceIndex == 6) System.out.println("Game over");
			});
			
		}
		
		else if(scenarioIndex == 3) {
			String[] sentences = {"Zeus: What?!", "Zeus: How come you became so strong..", "You are a miserable father..",
								  "You will die now all thanks to your sons, Heracles and Hermes ", "Sayonara, Zeus!", ""};
			
			sentenceIndex = 0;
			label.setText(sentences[sentenceIndex]);
			
			nextButton.setOnAction(a -> {
				sentenceIndex++; label.setText(sentences[sentenceIndex]); 
				if(sentenceIndex == 6) System.out.println("Game over");
			});
		}
		
		else {
			label.setText("Really..");
			nextButton.setOnAction(a -> {
				label.setText("I'm getting killed by an ordinary creature..");
				nextButton.setOnAction(b -> System.out.println("Game over"));
			});
		}
	}

	public int randomNumber(int min, int max) {
		int randomNumber = ThreadLocalRandom.current().nextInt(min, max + 1);
		return randomNumber;
	}
	
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
	
	public String setStats() {
		//Enemy statistics
		enemy = getEnemy(); //Random enemy
		enemyDamage = enemy.getDamage();
		enemyArmour = enemy.getArmour();
		potPossibility = enemy.getPotPossibility();
		enemyHealth = enemy.getHealth();
		
		if(enemyHealth <= 0) setStats(); //Recursive method is used to prevent the enemy health to be negative 
		
		if(potPossibility > 15) isPot = true; //Determines whether the enemy will drop a pot
			
		if(enemyDamage - playerArmour < 0) netEnemyDamage = 0; //To prevent the case that if the enemy damage is less that player armour, the player health will be increased
			
		else netEnemyDamage = enemyDamage - playerArmour;
		
		//Player statistics
		playerHealth = player.getHealth();
		playerDamage = player.getDamage();
		playerArmour = player.getArmour();
		netPlayerDamage = playerDamage - enemyArmour;
		potAmount = player.getPotAmount();
		skillDamage = player.getSkillDamage(); //No need for net damage since the skill can break the armour
		skillDamageToUser = player.getSkillDamage()/4;
		
		return enemy.saySomething(); //Finally warns the user for enemy 
	}

}