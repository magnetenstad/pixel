package pixel.file;

public enum Key {
	RecentPaths("recentPaths");
	
	private String path;
	
	Key(String path) {
		this.path = path;
	}
	
	@Override
	public String toString() {
		return path;
	}
}
