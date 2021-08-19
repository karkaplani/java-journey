package game;

public interface GameInterface {
	void makeBoards();
	void printBoards();
	void setComputerShip();
	void setPlayerShips();
	void changeBoard(char[][] board, int coord, char symbol);
	void playGame() throws InterruptedException;
	void resultGame() throws InterruptedException;
}
