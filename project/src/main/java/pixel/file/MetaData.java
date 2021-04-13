package pixel.file;

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
