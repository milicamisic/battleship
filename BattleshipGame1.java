package battleshipGame1;

import java.util.Scanner;

public class BattleshipGame1 {

	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Welcome to my bettleship game!");
		mainMenu(scan);
		
		scan.close();

	}
	public static void mainMenu(Scanner scan) {
		String userDecision;
		String again;
		
		while(true) {
			System.out.println();
			System.out.println("Options: ");
			System.out.println("To start the game enter \"1\".");
			System.out.println("To exit the game enter \"2\".");
			System.out.print("Your choice: ");
			userDecision = scan.next();
			
			if(userDecision.equals("1")) {
				while(true) {
					play(scan);
					System.out.println();
					System.out.print("Play again? (yes or no) ");
					again = scan.next();
					if (again.equals("yes")) {
						continue;
					} else {
						break;
					}
					
				}
			} else if(userDecision.equals("2")) {
				System.out.println();
				System.out.println("Thanks for playing. Goodbye!");
				break;
			} else {
				System.out.println("Invalid input!");
			}
			
			}
	
		

	}
	
	public static void play(Scanner scan) {
		String coordinates;
		String [] coords;
		int height;
		int width;
		while(true) {
			System.out.print("Pick board dimensions(in format height,width): ");
			coordinates = scan.next();
			coords = coordinates.split(",");
			try {
				height = Integer.parseInt(coords[0]);
				width = Integer.parseInt(coords[1]);
				break;
			} catch (Exception e) {
				System.out.println("Exception: " + e);
				System.out.println("Dimensions aren't in the valid format!");
			}
		}
		
		char[][] openBoardPlayer1 = makeBoard(scan, height, width);
		char[][] openBoardPlayer2 = makeBoard(scan, height, width);
		char[][] hiddenBoardPlayer1 = makeBoard(scan, height, width);
		char[][] hiddenBoardPlayer2 = makeBoard(scan, height, width);
		
		System.out.println();
		System.out.println("Player1:");
		System.out.println();
		
		for(int i = 4; i > 0; i--) {
			makeShip(hiddenBoardPlayer1, i, scan);
		}
		
		System.out.println();
		System.out.println("Player2:");
		System.out.println();
		
		for(int i = 4; i > 0; i--) {
			makeShip(hiddenBoardPlayer2, i, scan);
		}
		
		System.out.println("All the ships are in place. Let the games begin!");
		
		boolean itsDone = false;
		String who = "Player1";
		
		do {
			if(who.equals("Player1")) {
				System.out.println();
				System.out.println("Player1's turn:");
				System.out.println();
				shipGuess(hiddenBoardPlayer1, openBoardPlayer2, hiddenBoardPlayer2, scan);
				who = "Player2";
				
			} else {
				System.out.println();
				System.out.println("Player2's turn:");
				System.out.println();
				shipGuess(hiddenBoardPlayer2, openBoardPlayer1, hiddenBoardPlayer1, scan);
				who = "Player1";
			}
			
			itsDone = endCheck(hiddenBoardPlayer1, hiddenBoardPlayer2);
			
		} while(itsDone == false);
		
		if(who.equals("Player1")) {
			who = "Player2";
		} else {
			who = "Player1";
		}
		
		System.out.println("The winner is: " + who); 

		
	}
	
	public static char [][] makeBoard(Scanner scan, int height, int width) {
		char [][] board = new char [height][width];
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				board[i][j] = '0';
			}
		}
		return board;
		
	}
	
	public static void printBoard(char [][] board) {
		
		System.out.print("\t      ");
		
		for(int i = 0; i < board.length; i++) {
			System.out.print((i + 1) + " ");
		}
		for(int i = 0; i < board.length; i++) {
			System.out.print("\n\t%5d ".formatted(i+1));
			for(int j = 0; j < board[i].length; j++) {
				System.out.print(board[i][j] + " ");
			}
		}
		System.out.println();
	}
	
	public static void makeShip(char[][] board, int shipLen, Scanner scan) {
		
		System.out.println("Current state of your board: ");
		System.out.println();
		printBoard(board);
		String coordinates;
		String [] coords;
		String orientation;
		
		int y;
		int x;
		char a;
		
		boolean placeTaken;
		 
		while(true) {
			placeTaken = false;
			
			System.out.println("Enter the " + shipLen + " tile ship.");
			
			System.out.print("Enter the first tile(in format y,x): ");
			coordinates = scan.next();
			coords = coordinates.split(",");
			try {
				y = Integer.parseInt(coords[0]) - 1;
				x = Integer.parseInt(coords[1]) - 1;
				a = board[y][x];
			} catch(Exception e) {
				System.out.println("Exception: " + e);
				System.out.println("Coordinates aren't in the right format or that tile doesn't exist! Try again!");
				System.out.println();
				continue;
			}
			
			System.out.print("Enter the ship orientation(\"h\" for horizontal or \"v\" for vertical): ");
			orientation = scan.next();
			if(!orientation.equals("h") && !orientation.equals("v")) {
				System.out.println("Invalid orientation! Try again!");
				System.out.println();
				continue;
			}
			
			if(orientation.equals("v")) {
				if(y + shipLen - 1 >= board.length) {
					System.out.println("The ship can't fit! Try again!");
					System.out.println();
					continue;
				} else {
					for(int i = 0; i < shipLen; i++) {
						if (board[y + i][x] == '1') {
							placeTaken = true;
						} 
					}
					if(placeTaken == true) {
						System.out.println("Already a ship there! Try again!");
						System.out.println();
						continue;
					} else {
						System.out.println("The ship was placed!");
						System.out.println();
						for(int i = 0; i < shipLen; i++) {
							board[y + i][x] = '1';
						}
					}
				}
			}
			
			if(orientation.equals("h")) {
				if(x + shipLen - 1 >= board[0].length) {
					System.out.println("The ship can't fit! Try again!");
					System.out.println();
					continue;
				} else {
					for(int j = 0; j < shipLen; j++) {
						if (board[y][x + j] == '1') {
							placeTaken = true;
						}
					}
					if(placeTaken == true) {
						System.out.println("Already a ship there! Try again!");
						System.out.println();
						continue;
					} else {
						System.out.println("The ship was placed!");
						System.out.println();
						for(int j = 0; j < shipLen; j++) {
							board[y][x + j] = '1';
						}
					}
				}
			} 
			break;
		}
	}

	public static void shipGuess(char [][] yourBoard, char [][] enemyBoard, char [][] enemyBoardToCheck, Scanner scan) {
		System.out.println("Your board: ");
		printBoard(yourBoard);
		System.out.println("Enemy board: ");
		printBoard(enemyBoard);
		
		String coordinates;
		String [] coords;
		int x = 0;
		int y = 0;
		char a;
		
		while(true) {
			System.out.print("Enter a guess tile (in from x,y): ");
			coordinates = scan.next();
			coords = coordinates.split(",");
			try {
				y = Integer.parseInt(coords[0]) - 1;
				x = Integer.parseInt(coords[1]) - 1;
				a = enemyBoard[y][x];
				
			} catch(Exception e) {
				System.out.println("Exception: " + e);
				System.out.println("Coordinates aren't in the right format or that tile doesn't exist! Try again!");
				System.out.println();
				continue;
			}
			
			if(enemyBoardToCheck[y][x] == '1') {
				enemyBoardToCheck[y][x] = 's';
				enemyBoard[y][x] = 's';
				System.out.println("Nailed it!");
				System.out.println("Your board: ");
				printBoard(yourBoard);
				System.out.println("Enemy board: ");
				printBoard(enemyBoard);
				continue;
			} else if(enemyBoardToCheck[y][x] == '0') {
				enemyBoardToCheck[y][x] = 'x';
				enemyBoard[y][x] = 'x';
				System.out.println("Missed it!");
				break;
			} else {
				System.out.println("Missed it!");
				break;
			}
		}

		
	}
	
	public static boolean endCheck(char [][] board1, char [][] board2) {
		
		boolean found1Board1 = false;
		boolean found1Board2 = false;
		
		for(int i = 0; i < board1.length; i++) {
			for(int j = 0; j < board1[0].length; j++) {
				if(board1[i][j] == '1') {
					found1Board1 = true;
				}
			}
		}
		
		for(int i = 0; i < board2.length; i++) {
			for(int j = 0; j < board2[0].length; j++) {
				if(board1[i][j] == '1') {
					found1Board2 = true;
				}
			}
		}
		if(!found1Board1 || !found1Board2) {
			return true;
		} else {
			return false;
		}
	}
}
