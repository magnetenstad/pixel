package pixel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PixelApp extends Application {
	private static FXMLLoader loader = new FXMLLoader();
	
	@Override
	public void start(final Stage primaryStage) throws Exception {
		primaryStage.setTitle("Pixel");
        loader.setLocation(getClass().getResource("PixelGUI.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        
        primaryStage.setScene(scene);
        primaryStage.show();
	}

	public static void main(final String[] args) {
		Application.launch(args);
	}
	public static PixelController getController() {
		return (PixelController) loader.getController();
	}
}
