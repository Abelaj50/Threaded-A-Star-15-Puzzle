import java.util.ArrayList;
//import com.sun.marlin.Version;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

public class GameButton extends Button {
	
	/* Fields to keep track of certain button characteristics. */
	public Integer buttonID;	//ID of the button
	
	/* Strings for styling the buttons. */
	final String initialColor = "-fx-background-color: gainsboro";
	final String p1Color = "-fx-background-color:linear-gradient(purple -50%, black 50%, blue 100%);";
	final String p2Color = "-fx-background-color:linear-gradient(yellow -50%, violet 60%, red 100%);";		
	
	GameButton(int indexToSet) {
		
		/* This sets the button's ID. */
		this.buttonID = indexToSet;
		
		/* To disable the '0' button. */
		if(this.buttonID == 0) {
			this.setDisable(true);
		}
		
		
		/* This allows a button's size to reflect a wide range of values, for window resizing purposes. */
		setMinSize(Integer.MIN_VALUE, Integer.MIN_VALUE);
		setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
		
		/* Initializes a button with a color style. */
		setStyle(initialColor);
		
		/* These lines of code create a highlighting/shadowing effect, used for whenever mousing over a button. */
		ColorAdjust colorHighlight = new ColorAdjust();
		colorHighlight.setBrightness(.2); 
		colorHighlight.setSaturation(-.2);
		DropShadow shadow = new DropShadow();
		shadow.setInput(colorHighlight);

		/* These lambda expressions set the aforementioned highlighting/shadowing effect whenever mousing over a button. */
		setOnMouseEntered(e->setEffect(shadow));
		setOnMouseExited(e->setEffect(null));
		
		/* The following lines of code set the image of each button to that of the specified image in src/main/resources. */
		Image img = new Image("/galaxyslices_" + this.buttonID.toString() + ".jpg");
		ImageView view = new ImageView(img);
		view.fitWidthProperty().bind(this.widthProperty());
		view.fitHeightProperty().bind(this.heightProperty());
		view.setPreserveRatio(true);
		this.setGraphic(view);

	}
	
}

