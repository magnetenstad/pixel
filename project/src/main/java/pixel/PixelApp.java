package pixel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * Main app class.
 * @author Magne Tenstad
 */
public class PixelApp extends Application {
	private static FXMLLoader loader = new FXMLLoader();
	private static Parent root;
	
	/**
	 * Initializes the app's main stage.
	 */
	@Override
	public void start(final Stage stage) throws Exception {
		loader.setLocation(getClass().getResource("PixelGUI.fxml"));
        root = loader.load();
        stage.setTitle("Pixel");
        stage.setScene(new Scene(root));
        stage.show();
        stage.setMaximized(true);
	}
	
	/**
	 * Launches the app.
	 */
	public static void main(final String[] args) {
		Application.launch(args);
	}
	
	/**
	 * Some elements need the app window, e.g. FileChooser.
	 * @return The app window.
	 */
	public static Window getWindow() {
		return root.getScene().getWindow();
	}
}
