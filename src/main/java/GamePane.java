import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

/* This class, GamePane, extends GridPane to allow for additional functionality needed for the program to work. 
 * Credits to F.M. (https://stackoverflow.com/users/7550295/f-m), BHA Bilel (https://stackoverflow.com/users/8761799/bha-bilel),
 *  and fabian (https://stackoverflow.com/users/2991525/fabian) for the intuitive algorithms which have been repurposed.  */

public class GamePane extends GridPane {
	
	static Integer gameNumber = 1;
	
	/* This method allows retrieval of a child node based on it's column and row index within the GamePane. */
	public GameButton getNode(int col, int row, GamePane board) {
		
		GameButton nodeToReturn = null;
		ObservableList<Node> buttonList = board.getChildren();
		
	    for (Node button : buttonList) {
	        if(GamePane.getRowIndex(button) == row && GamePane.getColumnIndex(button) == col) {
	            nodeToReturn = (GameButton) button;
	            break;
	        }
	    }
	    
		return nodeToReturn;
	}
	
	/* This method will handle the physical swapping of two GameButtons. */
	public static void switchButtons(Node clickedButton, Node emptyButton) {
		
	    Integer indexTracker = GamePane.getRowIndex(clickedButton);
	    GamePane.setRowIndex(clickedButton, GridPane.getRowIndex(emptyButton));
	    GamePane.setRowIndex(emptyButton, indexTracker);

	    indexTracker = GamePane.getColumnIndex(clickedButton);
	    GamePane.setColumnIndex(clickedButton, GridPane.getColumnIndex(emptyButton));
	    GamePane.setColumnIndex(emptyButton, indexTracker);
	    return;
	}
	
	/* This method is a helper function which helps update the displayed puzzle upon pressing 'New Game'. */
	public static Integer gamePuzzleChoice() {
		
		if(gameNumber == 10)
			gameNumber = 0;
		
		return ++gameNumber;
	}
	
	/* This method returns the current puzzle order as an integer array. */
	public int[] getPuzzle() {
		
		int returnArray[] = new int[16];
		
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) 
				returnArray[i*4+j] = ((GameButton) getNode(j, i, this)).buttonID;
		}
		
		return returnArray;
	}
	
}
