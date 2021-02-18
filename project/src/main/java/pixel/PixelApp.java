package pixel;

import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class PixelApp extends Application {
	@Override
	public void start(final Stage primaryStage) throws Exception {
		primaryStage.setTitle("Pixel");
		primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("PixelGUI.fxml"))));
		primaryStage.show();
	}

	public static void main(final String[] args) {
		Application.launch(args);
	}
}
