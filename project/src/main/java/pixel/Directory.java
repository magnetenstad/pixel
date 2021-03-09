package pixel;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
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
import javafx.stage.Window;
import pixel.ext.SimpleFileTreeItem;
import pixel.sprite.Sprite;
import pixel.sprite.SpriteTab;

public class Directory {
	private static File rootFile;
	private TreeView<File> fileView = new TreeView<File>();
	private Pane fileViewPane = PixelApp.getController().getFileViewPane();

	public void askForDirectory() {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		File selectedDirectory = directoryChooser.showDialog(PixelApp.getWindow());
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
	public void exportSpriteToPng(Sprite sprite) {
		checkNotNull(sprite);
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("PNG Files", "*.png"));
		File fileSelected = fileChooser.showSaveDialog(PixelApp.getWindow());
		if (fileSelected != null) {
				BufferedImage bufferedImage = new BufferedImage(sprite.getWidth(), sprite.getHeight(), BufferedImage.TYPE_INT_ARGB);
				SwingFXUtils.fromFXImage(sprite.exportImage(), bufferedImage);
			try {
				ImageIO.write(bufferedImage, "png", fileSelected);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
	public void openSprite() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Pixel Files", "*.pixel"));
		File fileSelected = fileChooser.showOpenDialog(PixelApp.getWindow());
		if (fileSelected != null) {
			Sprite sprite = loadSpriteFromPath(fileSelected.getAbsolutePath());
			new SpriteTab(sprite);
		}
	}
	public void saveSprite(Sprite sprite) {
		checkNotNull(sprite);
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Pixel Files", "*.pixel"));
		File fileSelected = fileChooser.showSaveDialog(PixelApp.getWindow());
		if (fileSelected != null) {
			saveSpriteToPath(sprite, fileSelected.getAbsolutePath());
			sprite.setPath(fileSelected.getAbsolutePath());
		}
	}
	private void checkNotNull(Object obj) {
		if (obj == null) {
			throw new NullPointerException("Sprite cannot be null!");
		}
	}
}






















