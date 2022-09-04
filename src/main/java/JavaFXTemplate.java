/* Abel Abraham
 * UIC CS 342 Spring '21
 * Professor Hallenbeck
 * Project 4: Threaded AI 15 Puzzle
 * 
 * Credits to Lyshastra on DeviantArt (https://www.deviantart.com/lyshastra) for 
 * the beautiful galaxy artwork used on the game tiles as seen within this program.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
//import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class JavaFXTemplate extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("15 Puzzle");
		GamePane board = new GamePane();
		Pane winBoard = new Pane();
		Pane welcomeBoard = new Pane();
		HashMap<Integer, int[]> gameMap = new HashMap<Integer, int[]>();
		
		ArrayList<Node> pathCopy = new ArrayList<Node>();
		
		/* Here are the 10 different puzzles we will be using in the game. */
		int puzzle1[] = {2, 6, 10, 3, 1, 4, 7, 11, 8, 5, 9, 15, 12, 13, 14, 0};
		int puzzle2[] = {15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 0, 1}; 
		int puzzle3[] = {1, 0, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}; 
		int puzzle4[] = {0, 2, 3, 5, 6, 10, 12, 14, 1, 4, 8, 7, 9, 11, 15, 13}; 
		int puzzle5[] = {3, 1, 2, 4, 9, 8, 13, 15, 0, 5, 7, 6, 11, 10, 12, 14};
		int puzzle6[] = {7, 6, 5, 4, 3, 2, 1, 0, 15, 14, 13, 12, 11, 10, 9, 8}; 
		int puzzle7[] = {11, 9, 1, 2, 3, 4, 5, 6, 7, 8, 10, 12, 13, 15, 14, 0}; 
		int puzzle8[] = {10, 9, 8, 7, 6, 5, 4, 2, 3, 1, 0, 15, 14, 13, 12, 11}; 
		int puzzle9[] = {5, 7, 8, 9, 13, 15, 0, 1, 12, 3, 14, 6, 11, 10, 2, 4};
		int puzzle10[] = {9, 6, 10, 11, 1, 0, 4, 2, 3, 5, 8, 15, 12, 13, 14, 7};
		
		/* The puzzles will be placed into a HashMap for container access. */
		gameMap.put(1, puzzle1);
		gameMap.put(2, puzzle2);
		gameMap.put(3, puzzle3);
		gameMap.put(4, puzzle4);
		gameMap.put(5, puzzle5);
		gameMap.put(6, puzzle6);
		gameMap.put(7, puzzle7);
		gameMap.put(8, puzzle8);
		gameMap.put(9, puzzle9);
		gameMap.put(10, puzzle10);
				

		/* This will create a bit of padding for formatting purposes of the grid in-game. */
		board.setPadding(new Insets(100, 20, 20, 20));
		board.setHgap(10);
		board.setVgap(10);
		winBoard.setPadding(new Insets(100, 20, 20, 20));
		welcomeBoard.setPadding(new Insets(100, 20, 20, 20));

		
		/* The following loops create column and row constraints, and set them to dynamically adjust their either thier height or width. */
        for (int i = 0; i < 4; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setHgrow(Priority.ALWAYS);
            board.getColumnConstraints().add(colConst);
        }
        
        for (int i = 0; i < 4; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setVgrow(Priority.ALWAYS);
            board.getRowConstraints().add(rowConst);         
        }
        
        /* The following lines create the horizontal stretch of gameplay buttons at the top of the window. */
        HBox buttonBox = new HBox(35);
        Button newPuzzle = new Button("New Puzzle");
        Button solveH1 = new Button("Solve with AI H1");
        Button solveH2 = new Button("Solve with AI H2");
        Button seeSolution = new Button("See Solution");
        seeSolution.setDisable(true);
        
        /* The following lines of code set and initialize the main game screen. */
        buttonBox.getChildren().addAll(newPuzzle, solveH1, solveH2, seeSolution);
        BorderPane root = new BorderPane();
        root.setTop(buttonBox);
        root.setCenter(board);
        buttonBox.setAlignment(Pos.TOP_CENTER);
		Scene scene = new Scene(root, 550,690);
		
        /* The following lines create the horizontal stretch of gameplay buttons at the top of the window (winning case). */
        HBox buttonBoxWin = new HBox(35);
        Button newPuzzleWin = new Button("New Puzzle");
        Button quitButtonWin = new Button("Quit");
        
        /* The following lines of code set and initialize the winning game screen. */
        buttonBoxWin.getChildren().addAll(newPuzzleWin, quitButtonWin);
        BorderPane winRoot = new BorderPane();
        winRoot.setTop(buttonBoxWin);
		Image img = new Image("/WinScreenBig.jpg");
		ImageView view = new ImageView(img);
		view.fitWidthProperty().bind(winRoot.widthProperty());
		view.fitHeightProperty().bind(winRoot.heightProperty());
		view.setPreserveRatio(true);
		winBoard.getChildren().add(view);
        winRoot.setCenter(winBoard);
        buttonBoxWin.setAlignment(Pos.TOP_CENTER);
		Scene winScene = new Scene(winRoot, 550, 690);
		
        /* The following lines of code set and initialize the welcome game screen. */
        BorderPane welcomeRoot = new BorderPane();
		Image img2 = new Image("/WelcomeScreen.jpg");
		ImageView view2 = new ImageView(img2);
		view2.fitWidthProperty().bind(welcomeRoot.widthProperty());
		view2.fitHeightProperty().bind(welcomeRoot.heightProperty());
		view2.setPreserveRatio(true);
		welcomeBoard.getChildren().add(view2);
        welcomeRoot.setCenter(welcomeBoard);
		Scene welcomeScene = new Scene(welcomeRoot, 550, 690);
		
        /* Here we set the actions for the buttons of the winning game screen. */
		newPuzzleWin.setOnMouseClicked(e->{
			
			primaryStage.setScene(scene);
			board.getChildren().clear();
			solveH1.setDisable(false);
			solveH2.setDisable(false);
			seeSolution.setDisable(true);
			
			int arrayChoice = GamePane.gamePuzzleChoice(); 
			
	        /* These loops create new GameButtons, and then add the buttons to the specific column and row within the root node. */         
	        for(int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
										
					int buttonSetup[] = gameMap.get(arrayChoice);
					GameButton temp = new GameButton(buttonSetup[i+4*j]); 

					/* Here we will define the movement of the GameButtons. */
					temp.setOnMouseClicked(k->{
						
						/* We will only move the button if it's not the empty GameButton. */
						if(temp.buttonID != 0) {
													
							/* If the GameButton above is the empty GameButton. */
							if(GamePane.getRowIndex(temp) != 0 && board.getNode(GamePane.getColumnIndex(temp), GamePane.getRowIndex(temp)-1, board).buttonID == 0) 
							    GamePane.switchButtons(temp, board.getNode(GamePane.getColumnIndex(temp), GamePane.getRowIndex(temp)-1, board));
							
							
							/* If the GameButton to the left is the empty GameButton. */
							else if(GamePane.getColumnIndex(temp) != 0 && board.getNode(GamePane.getColumnIndex(temp)-1, GamePane.getRowIndex(temp), board).buttonID == 0) 
							    GamePane.switchButtons(temp, board.getNode(GamePane.getColumnIndex(temp)-1, GamePane.getRowIndex(temp), board));
							
							
							/* If the GameButton below is the empty GameButton. */
							else if(GamePane.getRowIndex(temp) != 3 && board.getNode(GamePane.getColumnIndex(temp), GamePane.getRowIndex(temp)+1, board).buttonID == 0) 
							    GamePane.switchButtons(temp, board.getNode(GamePane.getColumnIndex(temp), GamePane.getRowIndex(temp)+1, board));
							
							
							/* If the GameButton to the right is the empty GameButton. */
							else if(GamePane.getColumnIndex(temp) != 3 && board.getNode(GamePane.getColumnIndex(temp)+1, GamePane.getRowIndex(temp), board).buttonID == 0) 
							    GamePane.switchButtons(temp, board.getNode(GamePane.getColumnIndex(temp)+1, GamePane.getRowIndex(temp), board));
							
							/* Case of clicking on a GameButton non-adjacent to the empty GameButton. */
							else { }
							
							/* The following statements will determine if a game has been won. */
							int numMatching = 0;
													
							for(int c = 0; c < 4; c++) {
								for(int r = 0; r < 4; r++) {
									if(board.getNode(c, r, board).buttonID == (c+4*r)) 
										numMatching++;
								}
							}
							
							if(numMatching == 16) 
								primaryStage.setScene(winScene);
						}
					});
					
					//This will add the GameButton within the specified column and row of the GamePane.
					board.add(temp, i, j); // Column = i,  Row = j
				}
			}
		});
		
		quitButtonWin.setOnMouseClicked(e->{
			System.exit(0);
		});
        
		
        /* Here we set the actions for the buttons at the top of the screen (main game). */
		newPuzzle.setOnMouseClicked(e->{
			
			board.getChildren().clear();
			solveH1.setDisable(false);
			solveH2.setDisable(false);
			seeSolution.setDisable(true);
			
			int arrayChoice = GamePane.gamePuzzleChoice(); 
			
	        /* These loops create new GameButtons, and then add the buttons to the specific column and row within the root node. */         
	        for(int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
										
					int buttonSetup[] = gameMap.get(arrayChoice);
					GameButton temp = new GameButton(buttonSetup[i+4*j]); 

					/* Here we will define the movement of the GameButtons. */
					temp.setOnMouseClicked(k->{
						
						/* We will only move the button if it's not the empty GameButton. */
						if(temp.buttonID != 0) {
													
							/* If the GameButton above is the empty GameButton. */
							if(GamePane.getRowIndex(temp) != 0 && board.getNode(GamePane.getColumnIndex(temp), GamePane.getRowIndex(temp)-1, board).buttonID == 0) 
							    GamePane.switchButtons(temp, board.getNode(GamePane.getColumnIndex(temp), GamePane.getRowIndex(temp)-1, board));
							
							
							/* If the GameButton to the left is the empty GameButton. */
							else if(GamePane.getColumnIndex(temp) != 0 && board.getNode(GamePane.getColumnIndex(temp)-1, GamePane.getRowIndex(temp), board).buttonID == 0) 
							    GamePane.switchButtons(temp, board.getNode(GamePane.getColumnIndex(temp)-1, GamePane.getRowIndex(temp), board));
							
							
							/* If the GameButton below is the empty GameButton. */
							else if(GamePane.getRowIndex(temp) != 3 && board.getNode(GamePane.getColumnIndex(temp), GamePane.getRowIndex(temp)+1, board).buttonID == 0) 
							    GamePane.switchButtons(temp, board.getNode(GamePane.getColumnIndex(temp), GamePane.getRowIndex(temp)+1, board));
							
							
							/* If the GameButton to the right is the empty GameButton. */
							else if(GamePane.getColumnIndex(temp) != 3 && board.getNode(GamePane.getColumnIndex(temp)+1, GamePane.getRowIndex(temp), board).buttonID == 0) 
							    GamePane.switchButtons(temp, board.getNode(GamePane.getColumnIndex(temp)+1, GamePane.getRowIndex(temp), board));
							
							/* Case of clicking on a GameButton non-adjacent to the empty GameButton. */
							else { }
							
							/* The following statements will determine if a game has been won. */
							int numMatching = 0;
													
							for(int c = 0; c < 4; c++) {
								for(int r = 0; r < 4; r++) {
									if(board.getNode(c, r, board).buttonID == (c+4*r)) 
										numMatching++;
								}
							}
							
							if(numMatching == 16) 
								primaryStage.setScene(winScene);
						}
					});
					
					//This will add the GameButton within the specified column and row of the GamePane.
					board.add(temp, i, j); // Column = i,  Row = j
				}
			}
		});
		
		
		solveH1.setOnMouseClicked(e->{
			
			class MyCall implements Callable<ArrayList<Node>> {
				@Override
				public ArrayList<Node> call() throws Exception {
					// TODO Auto-generated method stub
					return new A_IDS_A_15solver(board, 1).getReturnPath();
				}
			}
			
			ExecutorService ex = Executors.newFixedThreadPool(100);
			Future<ArrayList<Node>> future = ex.submit(new MyCall());
			
			try {
				ArrayList<Node> path = future.get();
				copyNodeList(path, pathCopy);
				seeSolution.setDisable(false);
			}
			catch (Exception f){
				System.out.println(f.getMessage());
				
			}
		});
		
		 
		solveH2.setOnMouseClicked(e->{
			
			class MyCall implements Callable<ArrayList<Node>> {
				@Override
				public ArrayList<Node> call() throws Exception {
					// TODO Auto-generated method stub
					return new A_IDS_A_15solver(board, 2).getReturnPath();
				}
			}
			
			ExecutorService ex = Executors.newFixedThreadPool(100);
			Future<ArrayList<Node>> future = ex.submit(new MyCall());
			
			try {
				ArrayList<Node> path = future.get();
				copyNodeList(path, pathCopy);
				seeSolution.setDisable(false);
			}
			catch (Exception f){
				System.out.println(f.getMessage());
				
			}
		});
		
		
		seeSolution.setOnMouseClicked(e->{
			
			solveH1.setDisable(true);
			solveH2.setDisable(true);
			seeSolution.setDisable(true);
			
			/* Here we will disable the GameButtons until the solution has been solved (enabled again within helper function). */
			for(int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					(board.getNode(i, j, board)).setDisable(true);
				}
			}
			
			/* This is the main body of code that slows the code which switches the GameButtons. */
			
			PauseTransition t1 = new PauseTransition(Duration.seconds(1));
			t1.setOnFinished(f->{
				showSolutionHelper(pathCopy, board, 1, primaryStage, winScene);
			});
			
			PauseTransition t2 = new PauseTransition(Duration.seconds(2));
			t2.setOnFinished(f->{
				showSolutionHelper(pathCopy, board, 2, primaryStage, winScene);
			});
			
			PauseTransition t3 = new PauseTransition(Duration.seconds(3));
			t3.setOnFinished(f->{
				showSolutionHelper(pathCopy, board, 3, primaryStage, winScene);
			});
			
			PauseTransition t4 = new PauseTransition(Duration.seconds(4));
			t4.setOnFinished(f->{
				showSolutionHelper(pathCopy, board, 4, primaryStage, winScene);
			});
			
			PauseTransition t5 = new PauseTransition(Duration.seconds(5));
			t5.setOnFinished(f->{
				showSolutionHelper(pathCopy, board, 5, primaryStage, winScene);
			});
			
			PauseTransition t6 = new PauseTransition(Duration.seconds(6));
			t6.setOnFinished(f->{
				showSolutionHelper(pathCopy, board, 6, primaryStage, winScene);
			});
			
			PauseTransition t7 = new PauseTransition(Duration.seconds(7));
			t7.setOnFinished(f->{
				showSolutionHelper(pathCopy, board, 7, primaryStage, winScene);
			});
			
			PauseTransition t8 = new PauseTransition(Duration.seconds(8));
			t8.setOnFinished(f->{
				showSolutionHelper(pathCopy, board, 8, primaryStage, winScene);
			});
			
			PauseTransition t9 = new PauseTransition(Duration.seconds(9));
			t9.setOnFinished(f->{
				showSolutionHelper(pathCopy, board, 9, primaryStage, winScene);
			});
			
			PauseTransition t10 = new PauseTransition(Duration.seconds(10));
			t10.setOnFinished(f->{
				showSolutionHelper(pathCopy, board, 10, primaryStage, winScene);
			});
			
			
			if(pathCopy.size() > 1) 
				t1.play();
			
			if(pathCopy.size() > 2) 
				t2.play();

			if(pathCopy.size() > 3) 
				t3.play();

			if(pathCopy.size() > 4) 
				t4.play();

			if(pathCopy.size() > 5) 
				t5.play();

			if(pathCopy.size() > 6) 
				t6.play();

			if(pathCopy.size() > 7) 
				t7.play();

			if(pathCopy.size() > 8) 
				t8.play();

			if(pathCopy.size() > 9) 
				t9.play();

			if(pathCopy.size() > 10) 
				t10.play();
		});
		
		
        /* These loops create new GameButtons, and then add the buttons to the specific column and row within the root node. */         
        for(int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				
				GameButton temp = new GameButton(puzzle1[i+4*j]);
				
				/* Here we will define the movement of the GameButtons. */
				temp.setOnMouseClicked(e->{
					
					/* We will only move the button if it's not the empty GameButton. */
					if(temp.buttonID != 0) {
												
						/* If the GameButton above is the empty GameButton. */
						if(GamePane.getRowIndex(temp) != 0 && board.getNode(GamePane.getColumnIndex(temp), GamePane.getRowIndex(temp)-1, board).buttonID == 0) 
						    GamePane.switchButtons(temp, board.getNode(GamePane.getColumnIndex(temp), GamePane.getRowIndex(temp)-1, board));
						
						
						/* If the GameButton to the left is the empty GameButton. */
						else if(GamePane.getColumnIndex(temp) != 0 && board.getNode(GamePane.getColumnIndex(temp)-1, GamePane.getRowIndex(temp), board).buttonID == 0) 
						    GamePane.switchButtons(temp, board.getNode(GamePane.getColumnIndex(temp)-1, GamePane.getRowIndex(temp), board));
						
						
						/* If the GameButton below is the empty GameButton. */
						else if(GamePane.getRowIndex(temp) != 3 && board.getNode(GamePane.getColumnIndex(temp), GamePane.getRowIndex(temp)+1, board).buttonID == 0) 
						    GamePane.switchButtons(temp, board.getNode(GamePane.getColumnIndex(temp), GamePane.getRowIndex(temp)+1, board));
						
						
						/* If the GameButton to the right is the empty GameButton. */
						else if(GamePane.getColumnIndex(temp) != 3 && board.getNode(GamePane.getColumnIndex(temp)+1, GamePane.getRowIndex(temp), board).buttonID == 0) 
						    GamePane.switchButtons(temp, board.getNode(GamePane.getColumnIndex(temp)+1, GamePane.getRowIndex(temp), board));
						
						/* Case of clicking on a GameButton non-adjacent to the empty GameButton. */
						else { }
						
						/* The following statements will determine if a game has been won. */
						int numMatching = 0;
												
						for(int c = 0; c < 4; c++) {
							for(int r = 0; r < 4; r++) {
								if(board.getNode(c, r, board).buttonID == (c+4*r)) 
									numMatching++;
							}
						}
						
						if(numMatching == 16) 
							primaryStage.setScene(winScene);
					}
				});
				
				//This will add the GameButton within the specified column and row of the GamePane.
				board.add(temp, i, j); // Column = i,  Row = j
			}
		}
        

		/* This sets a minimum width and height for the window (primaryStage) */
		primaryStage.setMinWidth(500);
		primaryStage.setMinHeight(675);
		primaryStage.setMaxWidth(1100);
		primaryStage.setMaxHeight(1320);
		
		
		primaryStage.setScene(welcomeScene);
		primaryStage.show();
		
		PauseTransition delay = new PauseTransition(Duration.seconds(2));
		delay.setOnFinished(e->{
			primaryStage.setScene(scene);
			delay.stop();
			});
		delay.play();				

	}
	
	
	/* This method allows us to copy the solutionpath retrieved from H1 or H2 methods into a local variable for use with showSolution. */
	public void copyNodeList(ArrayList<Node> from, ArrayList<Node> to) {
		to.clear();
		for(int i = 0; i < from.size(); i++)
			to.add(from.get(i));
	}
	
	/* This is a helper function which aids in showing the solution from one of the two heuristics. */
	public void showSolutionHelper(ArrayList<Node> pathCopy, GamePane board, int num, Stage primaryStage, Scene winScene) {
		
		int col1 = -1;
		int row1 = -1;
		int col2 = -1;
		int row2 = -1;
		
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				
				if((pathCopy.get(num).getKey()[i+4*j] != board.getPuzzle()[i+4*j]) && (col1 == -1) && (row1 == -1)) {
					col1 = i;
					row1 = j;
				}
				
				else if((pathCopy.get(num).getKey()[i+4*j] != board.getPuzzle()[i+4*j]) && (col1 != -1) && (row1 != -1)) {
					col2 = i;
					row2 = j;
				}
			}
		}
		
		GamePane.switchButtons(board.getNode(col1, row1, board), board.getNode(col2, row2, board));
		
		/* This will enable all the buttons after they have all been shifted. */
		if(pathCopy.size() == num || num == 10) {
				for(int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					if((board.getNode(i, j, board)).buttonID != 0)
						(board.getNode(i, j, board)).setDisable(false);
				}
			}
		}
		
		/* The following statements will determine if a game has been won. */
		int numMatching = 0;
								
		for(int c = 0; c < 4; c++) {
			for(int r = 0; r < 4; r++) {
				if(board.getNode(c, r, board).buttonID == (c+4*r)) 
					numMatching++;
			}
		}
		
		if(numMatching == 16)
			primaryStage.setScene(winScene);
		
	}

	


}

