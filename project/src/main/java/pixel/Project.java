package pixel;

import java.util.Date;

public class Project {
	private String fileName;
	private String filePath;
	private Date lastEdited;
	
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
	
}
