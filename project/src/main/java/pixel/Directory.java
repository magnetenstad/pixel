package pixel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javafx.scene.control.TreeView;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import pixel.ext.SimpleFileTreeItem;
import pixel.sprite.Sprite;

public class Directory {
	private File rootFile;
	private TreeView<File> fileView = new TreeView<File>();
	private Pane fileViewPane = PixelApp.getController().getFileViewPane();
	
	public void askForDirectory() {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		File selectedDirectory = directoryChooser.showDialog(fileViewPane.getScene().getWindow());
		if (selectedDirectory != null) {
			setRootFile(selectedDirectory);
		}
		saveSprite(null);
	}
	
	public File getRootFile() {
		return rootFile;
	}
	public void setRootFile(File rootFile) {
		this.rootFile = rootFile;
		fileView = new TreeView<File>(new SimpleFileTreeItem(rootFile));
		fileViewPane.getChildren().clear();
		fileViewPane.getChildren().add(fileView);
	}
	
	public void saveSprite(Sprite sprite) {
		try {
			PrintWriter file = new PrintWriter(rootFile.getAbsolutePath() + "/test.txt");
			file.println("Hello world:)))))");
			file.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Sprite loadSprite() {
		Sprite sprite = new Sprite(32, 32);
		return sprite;
	}
}
