package pixel.file;

public enum FilePath {
	Recent("recent.txt");
	
	private String path;
	
	FilePath(String path) {
		this.path = path;
	}
	
	@Override
	public String toString() {
		return path;
	}
}
