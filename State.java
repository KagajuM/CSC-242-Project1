public class State {
	public String [] board;
	public Player player;
	//public Player player1;
	//public Player player2;
	
	public State (){
		this.player = new Player("X");
		this.board = new String[9];
		for (int i = 0; i < 9; i++){
			this.board[i] = " ";
		}
	}
//	public State(Player player1, Player player2){
//		this.player1 = player1;
//		this.player2 = player2;
//		this.board = new String[9];
//		for (int i = 0; i < 9; i++){
//			this.board[i] = " ";
//		}
//	}
	
	public State (Player player, String [] board) {
		this.player = player;
		for (int i=0; i < 9; i++){
			this.board[i] = board[i];
		}
	}

	public State (Player player, String [] board, int index, String symbol) {
		this.player = player;
		this.board = board.clone();
		this.board[index]=symbol;
	}
	public String[] getBoard() {
		return board;
	}
	
	public String getBoardAt(int index){
		if (index >= 0 && index <9)
			return board[index];
		return null;
	}
	
	public void setBoard(String[] board) {
		for (int i = 0 ; i< 9; i++)
			this.board[i] = board[i];
	}
	
	public void setBoardAt(int index, String symbol){
		//System.out.println(getBoardAt(index-1));
		if (index > 0 && index <=9 && board[index-1].equals(" "))
			this.board[index-1]= symbol;
//		else 
//			System.err.println("Incorrect placement");
	}
	public Player getPlayer() {
		return player;
	}
	
	public String getPlayerName(){
		return this.player.getName();
	}
	
	public void setPlayerName (String name){
		this.player.setName(name);
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public void printBoard() {
		System.err.println("-------------");
		System.err.print("| "+board[0]+ " | " + board[1]+ " | "+ board[2]+ " |\n" );
		System.err.println("-------------");
		System.err.print("| "+board[3]+ " | " + board[4]+ " | "+ board[5]+ " |\n" );
		System.err.println("-------------");
		System.err.print("| "+board[6]+ " | " + board[7]+ " | "+ board[8]+ " |\n" );
		System.err.println("-------------");
	}
	
	
}