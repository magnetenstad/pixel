package pixel;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PixelController {
	Project projectCurrent = new Project("New project", "");
	
	@FXML
	Label projectLabel;
	
	@FXML
	void initialize() {
		projectLabel.setText(projectCurrent.getFileName());
	}
}
