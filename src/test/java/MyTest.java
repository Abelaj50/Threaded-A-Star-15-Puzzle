import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MyTest {
	
	static GamePane testPane;
	static GameButton zeroButton = new GameButton(0);
	static int testPuzzle1[] = {2, 6, 10, 3, 1, 4, 7, 11, 8, 5, 9, 15, 12, 13, 14, 0};

	@BeforeAll
	static void setupMethod() {
		
        /* These loops create new GameButtons, and then add the buttons to the specific column and row within the root node. */         
        for(int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
									
				GameButton temp = new GameButton(testPuzzle1[i+4*j]); 

				/* Here we will define the movement of the GameButtons. */
				temp.setOnMouseClicked(k->{
					
					/* We will only move the button if it's not the empty GameButton. */
					if(temp.buttonID != 0) {
												
						/* If the GameButton above is the empty GameButton. */
						if(GamePane.getRowIndex(temp) != 0 && testPane.getNode(GamePane.getColumnIndex(temp), GamePane.getRowIndex(temp)-1, testPane).buttonID == 0) 
						    GamePane.switchButtons(temp, testPane.getNode(GamePane.getColumnIndex(temp), GamePane.getRowIndex(temp)-1, testPane));
						
						
						/* If the GameButton to the left is the empty GameButton. */
						else if(GamePane.getColumnIndex(temp) != 0 && testPane.getNode(GamePane.getColumnIndex(temp)-1, GamePane.getRowIndex(temp), testPane).buttonID == 0) 
						    GamePane.switchButtons(temp, testPane.getNode(GamePane.getColumnIndex(temp)-1, GamePane.getRowIndex(temp), testPane));
						
						
						/* If the GameButton below is the empty GameButton. */
						else if(GamePane.getRowIndex(temp) != 3 && testPane.getNode(GamePane.getColumnIndex(temp), GamePane.getRowIndex(temp)+1, testPane).buttonID == 0) 
						    GamePane.switchButtons(temp, testPane.getNode(GamePane.getColumnIndex(temp), GamePane.getRowIndex(temp)+1, testPane));
						
						
						/* If the GameButton to the right is the empty GameButton. */
						else if(GamePane.getColumnIndex(temp) != 3 && testPane.getNode(GamePane.getColumnIndex(temp)+1, GamePane.getRowIndex(temp), testPane).buttonID == 0) 
						    GamePane.switchButtons(temp, testPane.getNode(GamePane.getColumnIndex(temp)+1, GamePane.getRowIndex(temp), testPane));
						
						/* Case of clicking on a GameButton non-adjacent to the empty GameButton. */
						else { }
						
					}
				});
				
				//This will add the GameButton within the specified column and row of the GamePane.
				testPane.add(temp, i, j); // Column = i,  Row = j
			}
		}
		
	}

	@Test
	void checkZeroButtonTest () {
		assertEquals(0, zeroButton.buttonID, "Failed constructor!");
	}
	
	@Test
	void checkGetPuzzle() {
		assertArrayEquals(testPane.getPuzzle(), testPuzzle1, "Arrays not equal!");
	}
	
	@Test
	void checkGetNode() {
		assertEquals(6, testPane.getNode(1, 0, testPane).buttonID, "Node not returning correctly!");
	}
	
	@Test
	void checkZeroButtonDisabled () {
		assertTrue(zeroButton.isDisabled(), "0 button is not disabled on start!");
	}
	
	@Test
	void checkGameNumber () {
		assertTrue(GamePane.gamePuzzleChoice() > 0, "Game Number out of bounds!");
	}
	
	@Test
	void switchButtonsTest () {
		GamePane.switchButtons(testPane.getNode(2, 3, testPane), testPane.getNode(3, 3, testPane));
		assertTrue(testPane.getNode(2, 3, testPane).buttonID == 0, "Switch buttons not working!");
	}
	
	@Test
	void getColumnTest () {
		assertTrue(GamePane.getColumnIndex(testPane.getNode(3, 3, testPane)) == 3, "Column not updating!");
	}
	
	@Test
	void getRowTest () {
		assertTrue(GamePane.getRowIndex(testPane.getNode(3, 3, testPane)) == 3, "Row not updating!");
	}
	
	@Test
	void h1Solver () {
		assertEquals((new A_IDS_A_15solver(testPane, 1)).returnPath.size(), 40, "Heuristic Solver AI 1 not working!");
	}
	
	@Test
	void h2Solver () {
		assertEquals((new A_IDS_A_15solver(testPane, 2)).returnPath.size(), 16, "Heuristic Solver AI 2 not working!");
	}
	


}
