import java.util.ArrayList;
import java.util.Scanner;

public class TicTacToe {
	Player player1; //The AI, Always the max player
	Player player2; //The user, Always the min player
	String maxSymbol; //The symbol of the AI (the max player in the utility fn)
	State initialState;
	
	public TicTacToe (Player p1, Player p2) {
		this.player1 = p1;
		this.player2 = p2;
		this.initialState = new State();
		this.maxSymbol = p1.getName();
	}
	
	//Note to self: Use this in the tictactoe class and update the initial state accordingly
	public State minimax(State s){
		ArrayList <State> list = getActions(s);
		int max = Integer.MIN_VALUE;
		State newState = s; //The state corresponding to the move the AI should make
		for (State state: list){
			int value = minValue (state);
			if (value > max){
				max = value; 
				newState = state;
			}
		} 
		return newState;
	}

	public int maxValue (State s){
		int v = Integer.MIN_VALUE;
		if (isTerminalTest(s))
			return utility(s);
		ArrayList <State> list = getActions(s);
		for(State state : list) {
			v = Integer.max(v, minValue(state));
		}
		return v;
	}

	public int minValue (State s) {
		int v = Integer.MAX_VALUE;
		if (isTerminalTest(s))
			return utility(s);
		ArrayList <State> list = getActions(s);
		for(State state : list) {
			v = Integer.min(v, maxValue(state));
		}
		return v;
	}

	public int utility (State s) {
		//To handle vertical wins/loses
		for (int i = 0 ; i < 3; i++){
			if (isWin(s.board[i], s.board[i+3], s.board[i+6])){
				if (s.board[i].equalsIgnoreCase(player1.name)){
					return 10;
				}
				else{
					return -10;
				}
			}
		}
		
		//To handle horizontal cases
		for (int i = 0 ; i<7; i+=3){
			if (isWin(s.board[i], s.board[i+1], s.board[i+2])){
				if (s.board[i].equalsIgnoreCase(player1.name))
					return 10;
				else
					return -10;
			}
		}
		
		//To handle diagonals
		if (isWin(s.board[0], s.board[4], s.board[8])){
			if (s.board[0].equalsIgnoreCase(player1.name))
				return 10;
			else
				return -10;
		}
		if (isWin(s.board[2], s.board[4], s.board[6])){
			if (s.board[2].equalsIgnoreCase(player1.name))
				return 10;
			else
				return -10;
		}
		return 0;
	}
		
//		
	//Can be optimized further
	//Do not just look at whether they are equal
	public boolean isTerminalTest(State s){
		if (isWin(s.board[0], s.board[1], s.board[2]))
			return true;
		else if (isWin(s.board[3], s.board[4], s.board[5]))
			return true;
		else if (isWin(s.board[6], s.board[7], s.board[8]))
			return true;
		else if (isWin(s.board[0], s.board[3], s.board[6]))
			return true;
		else if (isWin(s.board[1], s.board[4], s.board[7]))
			return true;
		else if (isWin(s.board[2], s.board[5], s.board[8]))
			return true;
		else if (isWin(s.board[0], s.board[4], s.board[8]))
			return true;
		else if (isWin(s.board[3], s.board[5], s.board[7]))
			return true;
		else if (isBoardFull(s))
			return true;
		else
			return false;

	}

	//Can be optimized further
	public boolean isBoardFull(State s){
		int count = 0;
		for (int i=0; i<9; i++){
			if (s.board[i]==" ")
				count++;
		}
		if (count > 0)
			return false;
		else
			return true;

	}

	public boolean isWin(String t1, String t2, String t3){
		if(t1.equalsIgnoreCase(t2) && t2.equalsIgnoreCase(t3) && !(t1==" "))
			return true;
		else
			return false;
	}
	
	//Method to generate all applicable actions from a state
	public ArrayList<State> getActions (State s) {
		ArrayList <State> list = new ArrayList<State>(); //List of all next states
		String newName; //To initiate the new state to

		if(s.getPlayer().name.equalsIgnoreCase("X"))
			newName = "O"; 
		else
			newName = "X";

		for (int i = 0; i < 9 ; i++){
			if (s.board[i]==" "){
				list.add(new State(new Player(newName),s.getBoard(), i, newName));
			}
		}
		if (!list.isEmpty()){

			return list;
			
		}
		else 
			return null;
	}
	
	public void message (State s){
		if (utility(s) == 10)
			System.err.println("You lose, I win! That's just how life goes.");
		else if (utility (s) == -10)
			System.err.println("Ugh can't believe I lost");
		else
			System.err.println("I guess we tied! You're a strong opponent");
	}
	
	public void printSampleBoard() {
		System.err.println("-------------");
		System.err.print("| 1 | 2 | 3 |\n" );
		System.err.println("-------------");
		System.err.print("| 4 | 5 | 6 |\n" );
		System.err.println("-------------");
		System.err.print("| 7 | 8 | 9 |\n" );
		System.err.print("-------------\n");
	}
	public static void main(String[] args) {
		Scanner scan = new Scanner (System.in);
		TicTacToe game;
		System.err.println("   --------------------------    ");
		System.err.println("   | WELCOME TO TIC TAC TOE |    ");
		System.err.println("   --------------------------    ");
		System.err.println("\nWhich symbol do you want to play?");
		System.err.println("Enter X or O");
		String symbol = scan.next();
		
		//Cases where the user picks an X
		if (symbol.equalsIgnoreCase("X")){
			game = new TicTacToe(new Player ("O"), new Player("X"));
			System.err.println();
			game.printSampleBoard();
			while (!game.isTerminalTest(game.initialState)){
				System.err.println("Enter position to place your symbol [1-9]");
				int index = scan.nextInt(); 
				if (index > 0 && index < 10 && game.initialState.board[index-1]==" ") {
					game.initialState.setBoardAt(index, "X");
					game.initialState.player = new Player ("X"); //Whoever just made a move
					if (!game.isTerminalTest(game.initialState)){
						game.initialState = game.minimax(game.initialState);
						game.initialState.printBoard();
					}
				} else{
					System.err.println("Wrong move");
				}
			} 
			//Display whether they won, lost or tied
			game.message(game.initialState);

		} else if (symbol.equalsIgnoreCase("O")) {
			game = new TicTacToe(new Player("X"), new Player("O"));
			System.err.println("You chose O now I get to go first");
			game.initialState.player = new Player ("O");
			game.initialState = game.minimax(game.initialState);
			game.initialState.printBoard();
			System.err.println("\nNow it's your turn");
			game.printSampleBoard();
			
			while (!game.isTerminalTest(game.initialState)){
				System.err.println("\nEnter position to place your symbol [1-9]");
				int index = scan.nextInt(); 
				if (index > 0 && index < 10 && game.initialState.board[index-1]==" ") {
					game.initialState.setBoardAt(index, "O");
					game.initialState.player = new Player ("O"); //Whoever just made a move
					if (!game.isTerminalTest(game.initialState)){
						game.initialState = game.minimax(game.initialState);
						game.initialState.printBoard();
					}
				} else{
					System.err.println("Wrong move");
				}
			}
			//Display whether they won, lost or tied
			game.message(game.initialState);
		} else {
			System.err.println("Unrecognized symbol");
		}
		
		
		
		
	}

}
