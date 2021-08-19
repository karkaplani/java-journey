package game;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
/**
 * @author Abdullah Ilgun
 * @since 02.01.2021
 * @version 1.0
 * This is a simple fleet battle-like text based game based on OOP and functional programming.
 * See the explanations above each method for better understanding. 
 * Exception handling, debugging, 2D array and array list manipulations done throughout the program.
 */
public class ApoBati implements GameInterface {
	/**
	 * Inner custom exception class is used in playGame and setPlayerShips methods to ensure the user input is not a duplicate value.
	 */
	@SuppressWarnings("serial")
	public static class RepeatedValueException extends Exception {
		public RepeatedValueException(String msg) {
			super(msg);
		}
	}
	
	private char[][] playerBoard = new char[15][15];
	private char[][] computerBoard = new char[15][15];
		
	private ArrayList<Integer> cpuShips = new ArrayList<>(); 
	private ArrayList<Integer> playerShips = new ArrayList<>();
	
	private ArrayList<Integer> playerGuess = new ArrayList<>(); //To prevent the duplicate values.
	private ArrayList<Integer> cpuGuess = new ArrayList<>();
	private ArrayList<Integer> allShips = new ArrayList<>(); //It's first filled with all the numbers from 1 to 64 in setComputerShips method.
	
	private Scanner hey = new Scanner(System.in);
	
	private String playerName;
	 /**
	  * Main method acts like an interface. It runs the necessary methods respectively on a game object. 
	  * Everything is depending on the object and the game is done by doing manipulations on this object by different methods.
	  */
	public static void main(String args[]) throws InterruptedException {
		
		ApoBati obj = new ApoBati();
		obj.startGame();
		obj.makeBoards();
		obj.setComputerShip(); 
		obj.setPlayerShips();
		obj.playGame();
	}
	
	/**
	 * Story and introduction part of the game. The player is getting familiarize to the game here. 
	 * @throws InterruptedException to give a waiting time for each sentence displayed.
	 */
	public void startGame() throws InterruptedException {
		while(true) { //Stops when user enters a name different than Apo.
			System.out.print("Your Name: ");
			playerName = hey.nextLine();
			if(!(playerName.equalsIgnoreCase("Apo"))) { break; }
			else {
				System.out.println("You're battling against Apo, your name cannot be Apo..");
			}
		}
		System.out.println("Once there was a man called APO.."); TimeUnit.SECONDS.sleep(2);
		System.out.println("he used to be the mightiest commander in our country"); TimeUnit.SECONDS.sleep(3);
		System.out.println("Everything was changed when he found out the secret of these lands.."); TimeUnit.SECONDS.sleep(3);
		System.out.println("he escaped, and started battling against his own country.."); TimeUnit.SECONDS.sleep(3);
		System.out.println("He's now planning to attack on our sea from the air"); TimeUnit.SECONDS.sleep(3);
		System.out.println("Your duty is to locate our ships to the strategic points, then start the air attack "); TimeUnit.SECONDS.sleep(4);
		System.out.println("He and you cannot see each others' territories, so it shall not be an easy battle!"); TimeUnit.SECONDS.sleep(3);
		System.out.println("Follow your instincts to succesfully complete the mission"); TimeUnit.SECONDS.sleep(3);
		System.out.println("Once you take down all of his ships, we shall be able to start the conquest to his lands"); TimeUnit.SECONDS.sleep(3);
		System.out.println("When you are ready, I shall share the coordinate map"); TimeUnit.SECONDS.sleep(5);
		
		while(true) { //Stops when the user enters yes.
			System.out.printf("Are you ready, %s?%n", playerName); String answer = hey.nextLine();
			if(answer.equalsIgnoreCase("Yes")) { break; }
			else {
				System.out.println("Well you should be ready then! I just want to hear yes.");
			}
		}
		System.out.println("           *         ***       ****  ");
		System.out.println("         *   *       *  *     *    *   ");
		System.out.println("        *******      * *     *      * ");
		System.out.println("        *     *      *        *    *     ");
		System.out.println("        *     *      *         ****");
		System.out.println("                                         ");
		System.out.println("Coordinate Map"); //To make it easy for user to locate the ships.
		System.out.println("-----------------------");
		System.out.println(" 1 2  3  4  5  6  7  8");
		System.out.println(" 9 10 11 12 13 14 15 16");
		System.out.println("17 18 19 20 21 22 23 24");
		System.out.println("25 26 27 28 29 30 31 32");
		System.out.println("33 34 35 36 37 38 39 40");
		System.out.println("41 42 43 44 45 46 47 48");
		System.out.println("49 50 51 52 53 54 55 56");
		System.out.println("57 58 59 60 61 62 63 64");
		System.out.println("-----------------------");
	}
	/**
	 * 2D arrays are filled with symbols to make them look like a game board. 
	 */
	public void makeBoards() {
		
		for(int i = 0; i <= 14; i++) {
			for(int j = 0; j <= 14; j++) {
				if (j%2==0 && i%2==0) { playerBoard[j][i] = ' '; }
				else if (j%2==0 && i%2!=0) { playerBoard[j][i] = '|'; }
				else if (j%2!=0 && i%2==0) { playerBoard [j][i] = '-'; }
				else if (j%2!=0 && i%2!=0) { playerBoard[j][i] = '+'; }
			}
		}
		for(int i = 0; i <= 14; i++) {
			for(int j = 0; j <= 14; j++) {
				if (j%2==0 && i%2==0) { computerBoard[j][i] = ' '; }
				else if (j%2==0 && i%2!=0) { computerBoard[j][i] = '|'; }
				else if (j%2!=0 && i%2==0) { computerBoard [j][i] = '-'; }
				else if (j%2!=0 && i%2!=0) { computerBoard[j][i] = '+'; }
			}
		}
	}
	/**
	 *  2D arrays are printed here and used in the playGame method.
	 */
	public void printBoards() {
		
		System.out.println("Apo's Territory\n--------------"); 
		for (char[] row : computerBoard) { 
			for (char c : row) {
				System.out.print(c);
			}
			System.out.println();
	    }
		
		System.out.printf("%n%s's Territory\n--------------\n", playerName);
		
		for (char[] row : playerBoard) { 
			for (char c : row) {
				System.out.print(c);
			}
			System.out.println();
	    }
	}
	/**
	 * Cpu ships are stored virtually in an array list to use in the playGame method. Whenever a user
	 * guess matches with one of the elements in the list, the element is removed. When the list
	 * becomes empty, the user will win the game. 
	 */
	public void setComputerShip() {
		
		for(int i = 1 ; i <= 64; i++) {
			allShips.add(i);
		}
		
		Collections.shuffle(allShips); //From the first array, 8 random numbers are pulled and put into the main cpuShips array.
		for(int i = 0; i <= 7; i++) {
			cpuShips.add(allShips.get(i));
		}
	}
	/**
	 * The user enters all the ship locations, and the 2D array for player board is manipulated 
	 * according to the input values. Exception handling is done here to make sure if the user
	 * enters a number between 1 and 64 and not duplicate with a number already entered. 
	 */
	public void setPlayerShips() {
		int i = 0;
		do {
			try {
				System.out.printf("Enter the ship coordinate(%d): ",i+1); 
				int coordinate = hey.nextInt();
				if(coordinate > 64 || coordinate <= 0) {
					throw new IllegalArgumentException();
				}
				else {
					if(playerShips.contains(coordinate)) {
						throw new RepeatedValueException("All ships should be in different places cap, don't be crazy!");						
					}
					else {
						playerShips.add(coordinate);
						i++;
					}
				}
			} catch (IllegalArgumentException e) {
			      System.err.println("Easy cap, that's not our territory.. please enter a valid coordinate!(1-64)");
			} catch (RepeatedValueException e) {
				  System.err.println(e.getMessage()); 
			} catch (InputMismatchException e) {
				  System.err.println("That's not even a number cap! Jeez.. please enter a number.");
			  }
			
		    hey.nextLine();
		} while(i <= 7);
		
		for(int playerShip : playerShips) {
			changeBoard(playerBoard, playerShip, '@');
		}
	}
	/**
	 * @param board determines which board to manipulate.
	 * @param coord the array is manipulated according to this value.
	 * @param symbol is @ for setPlayerShips, and X for playGame method. 
	 * It's used in playGame and setPlayerShips methods to manipulate the 2D arrays for boards. 
	 */
	public void changeBoard(char[][] board, int coord, char symbol) {
		switch(coord) {
		case 1: 
		    board [0][0] = symbol; 
			break; 
		case 2: 
		    board [0][2] = symbol; 
			break; 
		case 3: 
		    board [0][4] = symbol; 
			break; 
		case 4: 
		    board [0][6] = symbol; 
			break; 
		case 5: 
		    board [0][8] = symbol; 
			break; 
		case 6: 
		    board [0][10] = symbol; 
			break; 
		case 7: 
		    board [0][12] = symbol; 
			break; 
		case 8: 
		    board [0][14] = symbol; 
			break; 
		case 9: 
		    board [2][0] = symbol; 
			break; 
		case 10: 
		    board [2][2] = symbol; 
			break; 
		case 11: 
		    board [2][4] = symbol; 
			break; 
		case 12: 
		    board [2][6] = symbol; 
			break; 
		case 13: 
		    board [2][8] = symbol; 
			break; 
		case 14: 
		    board [2][10] = symbol; 
			break; 
		case 15: 
		    board [2][12] = symbol; 
			break; 
		case 16: 
		    board [2][14] = symbol; 
			break; 
		case 17: 
		    board [4][0] = symbol; 
			break; 
		case 18: 
		    board [4][2] = symbol; 
			break; 
		case 19: 
		    board [4][4] = symbol; 
			break; 
		case 20: 
		    board [4][6] = symbol; 
			break; 
		case 21: 
		    board [4][8] = symbol; 
			break; 
		case 22: 
		    board [4][10] = symbol; 
			break; 
		case 23: 
		    board [4][12] = symbol; 
			break; 
		case 24: 
		    board [4][14] = symbol; 
			break; 
		case 25: 
		    board [6][0] = symbol; 
			break; 
		case 26: 
		    board [6][2] = symbol; 
			break; 
		case 27: 
		    board [6][4] = symbol; 
			break; 
		case 28: 
		    board [6][6] = symbol; 
			break; 
		case 29: 
		    board [6][8] = symbol; 
			break; 
		case 30: 
		    board [6][10] = symbol; 
			break; 
		case 31: 
		    board [6][12] = symbol; 
			break; 
		case 32: 
		    board [6][14] = symbol; 
			break; 
		case 33: 
		    board [8][0] = symbol; 
			break; 
		case 34: 
		    board [8][2] = symbol; 
			break; 
		case 35: 
		    board [8][4] = symbol; 
			break; 
		case 36: 
		    board [8][6] = symbol; 
			break; 
		case 37: 
		    board [8][8] = symbol; 
			break; 
		case 38: 
		    board [8][10] = symbol; 
			break; 
		case 39: 
		    board [8][12] = symbol; 
			break; 
		case 40: 
			board [8][14] = symbol; 
			break;
		case 41: 
			board [10][0] = symbol; 
			break; 
		case 42: 
			board [10][2] = symbol; 
			break; 
		case 43: 
			board [10][4] = symbol; 
			break; 
		case 44: 
			board [10][6] = symbol; 
			break; 
		case 45: 
			board [10][8] = symbol; 
			break; 
		case 46: 
			board [10][10] = symbol; 
			break; 
		case 47: 
			board [10][12] = symbol; 
			break; 
		case 48: 
			board [10][14] = symbol; 
			break; 
		case 49: 
			board [12][0] = symbol; 
			break; 
		case 50: 
			board [12][2] = symbol; 
			break; 
		case 51: 
			board [12][4] = symbol; 
			break; 
		case 52: 
			board [12][6] = symbol; 
			break; 
		case 53: 
			board [12][8] = symbol; 
			break; 
		case 54: 
			board [12][10] = symbol; 
			break; 
		case 55: 
			board [12][12] = symbol; 
			break; 
		case 56: 
			board [12][14] = symbol; 
			break; 
		case 57: 
			board [14][0] = symbol; 
			break; 
		case 58: 
			board [14][2] = symbol; 
			break; 
		case 59: 
			board [14][4] = symbol; 
			break; 
		case 60: 
			board [14][6] = symbol; 
			break; 
		case 61: 
			board [14][8] = symbol; 
			break; 
		case 62: 
		    board [14][10] = symbol; 
			break; 
		case 63: 
		    board [14][12] = symbol; 
			break; 
		case 64: 
		    board [14][14] = symbol; 
			break; 
		}
	}
	/**
	 * The user is finally being prompted to enter coordinates to hit in the enemy board. 
	 * Exception handling done here as well similar to setPlayerShips method. All possible 
	 * combinations were considered for the right guesses and the algorithm is created accordingly.
	 * End of the game is determined by the array list for the ships. 
	 */
	public void playGame() throws InterruptedException {			
		do {
			try {
				printBoards();
				System.out.print("\nWhere are we shooting?: ");
				int guess = hey.nextInt();
				System.out.print("Shooting.. "); TimeUnit.SECONDS.sleep(1);
				System.out.print("Apo's shooting.."); TimeUnit.SECONDS.sleep(1);
				if(guess > 64 || guess <= 0) {
					throw new IllegalArgumentException();
				}
				else {
					if(playerGuess.contains(guess)) {
						throw new RepeatedValueException("\nWe already shot there cap! Enter another one.");
					}
					else {
						playerGuess.add(guess);
					    int computerGuess = allShips.get(0); //This value is random since it was shuffled in setComputerShips method.
					    allShips.remove(0);
					    
					    cpuGuess.add(computerGuess);
					    	
						if(cpuShips.contains(guess)) { //User hits
								
							changeBoard(computerBoard, guess, 'X');
							System.out.printf("\n\n\nHURRAY! We hit a ship!(%d left)\n\n", cpuShips.size()-1);
							cpuShips.removeIf(s -> s.equals(guess));
								
							if(playerShips.contains(computerGuess)) { //User hits, cpu hits
								changeBoard(playerBoard, computerGuess, 'X');
								System.out.println("\n\nFISH! Apo hit as well!\n");
								playerShips.removeIf(s -> s.equals(computerGuess));
							}
							else { //User hits, cpu not
								changeBoard(playerBoard, computerGuess, 'X');
							}
						}
						else { //User not
								
							changeBoard(computerBoard, guess, 'X');
							System.out.println("\n\n\nNot this time cap!\n");
								
							if(playerShips.contains(computerGuess)) { //User not, cpu hits
								changeBoard(playerBoard, computerGuess, 'X');
								System.out.println("\n\nFISH! Apo hit a ship!\n");
								playerShips.removeIf(s -> s.equals(computerGuess));
							}
							else { //User not, cpu not
								changeBoard(playerBoard, computerGuess, 'X');
							}
						}
					}
				}
			} catch (IllegalArgumentException e) {
				System.err.println("\nEasy cap, that's not his territory.. please enter a valid coordinate!(1-64)");
			} catch (RepeatedValueException e) {
				System.err.println(e.getMessage());
			} catch (InputMismatchException e) {
				System.err.println("\nThat's not even a number cap! Jeez.. please enter a number.");
			}
			hey.nextLine();
		} while(!(cpuShips.isEmpty() || playerShips.isEmpty()));	
		hey.close();
		resultGame();
	}
	/**
	 * This method finally determines who wins. The one who's list becomes empty loses the game.
	 * Some text are printed according to each scenarios. 
	 */
	public void resultGame() throws InterruptedException {
		if(playerShips.isEmpty() && !(cpuShips.isEmpty())) {
			System.out.println("."); TimeUnit.SECONDS.sleep(1);
			System.out.println("."); TimeUnit.SECONDS.sleep(1);
			System.out.println("."); TimeUnit.SECONDS.sleep(1);
			System.out.println("REVENGE!"); TimeUnit.SECONDS.sleep(1);
			System.out.println("NOW FEEL APO'S FURY!"); TimeUnit.SECONDS.sleep(1);
			System.out.println("*****    **    *    ***");
			System.out.println("*        * *   *    *  *");
			System.out.println("*****    *  *  *    *   *");
			System.out.println("*        *   * *    *  *");
			System.out.println("*****    *     *    ***");
			System.out.println("God bless us!");
		} 
		else if(cpuShips.isEmpty() && !(playerShips.isEmpty())) {
			System.out.println("."); TimeUnit.SECONDS.sleep(1);
			System.out.println("."); TimeUnit.SECONDS.sleep(1);
			System.out.println("."); TimeUnit.SECONDS.sleep(1);
			System.out.println("GREAT JOB!"); TimeUnit.SECONDS.sleep(1);
			System.out.println("NOW WE'RE READY TO TAKE APO DOWN!"); TimeUnit.SECONDS.sleep(1);
			System.out.println("*****    **    *    ***");
			System.out.println("*        * *   *    *  *");
			System.out.println("*****    *  *  *    *   *");
			System.out.println("*        *   * *    *  *");
			System.out.println("*****    *     *    ***");
			System.out.println("See you on the next game, "+playerName+"!");
		}
		else {
			System.out.println("."); TimeUnit.SECONDS.sleep(1);
			System.out.println("."); TimeUnit.SECONDS.sleep(1);
			System.out.println("."); TimeUnit.SECONDS.sleep(1);
			System.out.println("GREAT JOB! We hit all the ships"); TimeUnit.SECONDS.sleep(1);
			System.out.println("But we don't have any ships either"); TimeUnit.SECONDS.sleep(1);
			System.out.println("*****    **    *    ***");
			System.out.println("*        * *   *    *  *");
			System.out.println("*****    *  *  *    *   *");
			System.out.println("*        *   * *    *  *");
			System.out.println("*****    *     *    ***");
			System.out.println("Let's wait what will happen then..");
		}
	}
}

