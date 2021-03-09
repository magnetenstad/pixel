package pixel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

public class PixelApp extends Application {
	private static FXMLLoader loader = new FXMLLoader();
	private static Parent root;
	
	@Override
	public void start(final Stage primaryStage) throws Exception {
		primaryStage.setTitle("Pixel");
		loader.setLocation(getClass().getResource("PixelGUI.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMaximized(true);
	}

	public static void main(final String[] args) {
		Application.launch(args);
	}
	public static PixelController getController() {
		return (PixelController) loader.getController();
	}
	public static Parent getRoot() {
		return root;
	}
	public static Scene getScene() {
		return getRoot().getScene();
	}
	public static Window getWindow() {
		return getScene().getWindow();
	}
}
