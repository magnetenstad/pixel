package pixel;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.json.JSONObject;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import pixel.ext.SimpleFileTreeItem;
import pixel.sprite.Sprite;
import pixel.sprite.SpriteTab;

public class Directory {
	private static File rootFile;
	private TreeView<File> fileView = new TreeView<File>();
	private Pane fileViewPane = PixelApp.getController().getFileViewPane();
	
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
		Directory.rootFile = rootFile;
		fileView = new TreeView<File>(new SimpleFileTreeItem(rootFile));
		fileViewPane.getChildren().clear();
		fileViewPane.getChildren().add(fileView);
	}
	public static void saveSpriteToPath(Sprite sprite, String path) {
		try {
			PrintWriter file = new PrintWriter(path);
			file.print(Sprite.serialise(sprite));
			file.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public Sprite loadSpriteFromPath(String path) {
		try {
			String text = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
			JSONObject json = new JSONObject(text);
			Sprite sprite = Sprite.deserialise(json);
			return sprite;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void exportImageToPng(Image image) {
		File outputFile = new File(rootFile.getAbsolutePath() + "/sprite.png");
		BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
		try {
			ImageIO.write(bImage, "png", outputFile);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	public void openFile() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Pixel Files", "*.pixel"));
		File fileSelected = fileChooser.showOpenDialog(fileViewPane.getScene().getWindow());
		if (fileSelected != null) {
			Sprite sprite = loadSpriteFromPath(fileSelected.getAbsolutePath());
			new SpriteTab(sprite);
		}
	}
	public void saveSprite(Sprite sprite) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Pixel Files", "*.pixel"));
		File fileSelected = fileChooser.showSaveDialog(fileViewPane.getScene().getWindow());
		saveSpriteToPath(sprite, fileSelected.getAbsolutePath());
		sprite.setPath(fileSelected.getAbsolutePath());
	}
}






















