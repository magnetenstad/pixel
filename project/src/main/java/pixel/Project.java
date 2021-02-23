package pixel;

import java.util.ArrayList;
import java.util.Date;

import pixel.sprite.Sprite;

public class Project {
	private String fileName;
	private String filePath;
	private Date lastEdited;
	private ArrayList<Sprite> sprites = new ArrayList<Sprite>();
	
	public Project(String fileName, String filePath) {
		this.fileName = fileName;
		this.filePath = filePath;
		this.lastEdited = new Date();
	}

	public String getFileName() {
		return fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public Date getLastEdited() {
		return lastEdited;
	}
	
	public void updateLastEdited() {
		lastEdited = new Date();
	}
	
	public void addSprite(Sprite sprite) {
		sprites.add(sprite);
	}
}
