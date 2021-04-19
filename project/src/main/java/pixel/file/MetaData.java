package pixel.file;

/**
 * Keys for FileManager metadata JSON file.
 * @author Magne Tenstad
 */
public enum MetaData {
	Recent("recent");
	
	private String path;
	
	MetaData(String path) {
		this.path = path;
	}
	
	@Override
	public String toString() {
		return path;
	}
}
