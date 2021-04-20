package pixel.file;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.imageio.ImageIO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javafx.embed.swing.SwingFXUtils;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import pixel.PixelApp;
import pixel.palette.Palette;
import pixel.sprite.Sprite;
import pixel.sprite.SpriteSerializer;

/**
 * An implementation of FileManager.
 * Saves files to a .PIXEL (JSON-based) format, and exports to .PNG.
 * @author Magne Tenstad
 */
public class PixelFileManager implements FileManager {
	private final static File METADATA = new File("src/main/resources/metadata.json");
	private ArrayList<String> recentPaths = new ArrayList<>();
	
	public PixelFileManager() {
		readFromMetaData();
	}
	
	/**
	 * Prompts the user to save a file.
	 * @return The selected file, or null!
	 */
	public File showSaveDialog(ExtensionFilter filter) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(filter);
		return fileChooser.showSaveDialog(PixelApp.getWindow());
	}
	
	/**
	 * Prompts the user to open a file.
	 * @return The selected file, or null!
	 */
	public File showOpenDialog(ExtensionFilter filter) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(filter);
		return fileChooser.showOpenDialog(PixelApp.getWindow());
	}
	
	@Override
	public void saveSprite(String path, Sprite sprite) {
		checkNotNull(sprite);
		try {
			sprite.setPath(path);
			FileManager.writeString(path, SpriteSerializer.serializeToString(sprite));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void saveSprite(Sprite sprite) {
		File file = showSaveDialog(new ExtensionFilter("Pixel Files", "*.pixel"));
		if (file != null) {
			addToRecentPaths(file.getAbsolutePath());
			saveSprite(file.getAbsolutePath(), sprite);
		}
	}
	
	@Override
	public Sprite loadSprite(String path) {
		try {
			String string = FileManager.readString(path);
			Sprite sprite = SpriteSerializer.deserializeFromString(string);
			sprite.setPath(path);
			addToRecentPaths(path);
			return sprite;
		} catch (IOException e) {
			e.printStackTrace();
			removeFromRecentPaths(path);
		}
		return null;
	}
	
	@Override
	public Sprite loadSprite() {
		File file = showOpenDialog(new ExtensionFilter("Pixel Files", "*.pixel"));
		if (file != null) {
			return loadSprite(file.getAbsolutePath());
		}
		return null;
	}
	
	@Override
	public void exportSprite(String path, Sprite sprite, Palette palette) {
		checkNotNull(sprite);
		BufferedImage bufferedImage = new BufferedImage(sprite.getWidth(), sprite.getHeight(), BufferedImage.TYPE_INT_ARGB);
		SwingFXUtils.fromFXImage(sprite.exportImage(palette), bufferedImage);
		try {
			ImageIO.write(bufferedImage, "png", new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void exportSprite(Sprite sprite, Palette palette) {
		File file = showSaveDialog(new ExtensionFilter("PNG Files", "*.png"));
		if (file != null) {
			exportSprite(file.getAbsolutePath(), sprite, palette);
		}
	}
	
	private void checkNotNull(Object obj) {
		if (obj == null) {
			throw new NullPointerException("This object cannot be null!");
		}
	}
	
	private void initializeMetaData() {
		if (!METADATA.exists()) {
			try {
				JSONObject json = new JSONObject();
				JSONArray recentPathsJSON = new JSONArray();
				json.put(MetaData.Recent.toString(), recentPathsJSON);
				FileManager.writeString(METADATA.getAbsolutePath(), json.toString(2));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void readFromMetaData() {
		if (!METADATA.exists()) {
			initializeMetaData();
		}
		try {
			String string = FileManager.readString(METADATA.getAbsolutePath());
			try {
				JSONObject json = new JSONObject(string);
				
				JSONArray recentPathsJSON = json.getJSONArray(MetaData.Recent.toString());
				
				for (Object path : recentPathsJSON) {
					recentPaths.add((String) path);
				}
			}
			catch (JSONException e) {
				METADATA.delete();
				readFromMetaData();
				return;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void writeToMetaData() {
		if (!METADATA.exists()) {
			initializeMetaData();
		}
		try {
			String string = FileManager.readString(METADATA.getAbsolutePath());
			JSONObject json = new JSONObject(string);
			JSONArray recentPathsJSON = new JSONArray();
			
			for (String path : recentPaths) {
				recentPathsJSON.put(path);
			}
			
			json.put(MetaData.Recent.toString(), recentPathsJSON);
			FileManager.writeString(METADATA.getAbsolutePath(), json.toString(2));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addToRecentPaths(String path) {
		for (String recentPath : recentPaths) {
			if (recentPath.equals(path)) {
				return;
			}
		}
		recentPaths.add(0, path);
		if (recentPaths.size() > 5) {
			recentPaths.remove(recentPaths.size()-1);
		}
		writeToMetaData();
	}
	
	public void removeFromRecentPaths(String path) {
		for (String recentPath : recentPaths) {
			if (recentPath.equals(path)) {
				recentPaths.remove(recentPath);
			}
		}
		writeToMetaData();
	}

	@Override
	public Collection<String> getRecentPaths() {
		return new ArrayList<String>(recentPaths);
	}
}


