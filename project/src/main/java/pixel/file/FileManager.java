package pixel.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import pixel.sprite.Sprite;

public interface FileManager {
	
	public static void saveString(String path, String string) throws IOException {
		Files.writeString(Paths.get(path), string);
	}
	
	public static String loadString(String path) throws IOException {
		return Files.readString(Paths.get(path));
	}
	
	public void saveSprite(String path, Sprite sprite);
	
	public void saveSprite(Sprite sprite);
	
	public Sprite loadSprite(String path);
	
	public Sprite loadSprite();
	
	public void exportSprite(String path, Sprite sprite);
	
	public void exportSprite(Sprite sprite);
}
