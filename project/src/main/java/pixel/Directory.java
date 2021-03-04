package pixel;

import java.io.File;

import javafx.scene.control.TreeView;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import pixel.ext.SimpleFileTreeItem;

public class Directory {
	private File rootFile;
	private TreeView<File> fileView = new TreeView<File>();
	private Pane fileViewPane;
	
	public Directory(Pane fileViewPane) {
		this.fileViewPane = fileViewPane;
	}
	
	public void askForDirectory() {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		File selectedDirectory = directoryChooser.showDialog(fileViewPane.getScene().getWindow());
		if (selectedDirectory != null) {
			setRootFile(selectedDirectory);
		}
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
}
