package pixel.cursorlist;

public interface CursorListListener {
	
	public void cursorListChanged(CursorList<?> cursorList, CursorListEvent event, Object element);
	
}
