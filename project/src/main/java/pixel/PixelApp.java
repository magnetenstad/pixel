package pixel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PixelApp extends Application {
	private static Scene pixelScene;
	@Override
	public void start(final Stage primaryStage) throws Exception {
		primaryStage.setTitle("Pixel");
		pixelScene = new Scene(FXMLLoader.load(getClass().getResource("PixelGUI.fxml")));
		primaryStage.setScene(pixelScene);
		primaryStage.show();
	}

	public static void main(final String[] args) {
		Application.launch(args);
	}
	
	public static Node lookup(String id) {
		return pixelScene.lookup(id);
	}
}
