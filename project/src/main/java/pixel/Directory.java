package pixel;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import pixel.ext.SimpleFileTreeItem;
import pixel.sprite.Sprite;
import pixel.sprite.SpriteLayer;

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
		saveSprite(PixelApp.getController().getSpriteCurrent());
		saveToFile(PixelApp.getController().getSpriteCurrent().getImageView().getImage());
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
	
	public static void saveSprite(Sprite sprite) {
		try {
			PrintWriter file = new PrintWriter(rootFile.getAbsolutePath() + "/sprite.pixel");
			file.print(SpriteLayer.serialize(sprite.getSpriteLayerCurrent()));
			file.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Sprite loadSprite() {
		Sprite sprite = new Sprite(32, 32);
		return sprite;
	}
	
	public static void saveToFile(Image image) {
		File outputFile = new File(rootFile.getAbsolutePath() + "/sprite.png");
		BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
		try {
			ImageIO.write(bImage, "png", outputFile);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}






















