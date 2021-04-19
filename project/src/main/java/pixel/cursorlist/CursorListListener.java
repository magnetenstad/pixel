package pixel.cursorlist;

/**
 * Listener of a CursorList.
 * @author tenst
 */
public interface CursorListListener {
	
	/*
	 * Response to a CursorListEvent.
	 */
	public void cursorListChanged(CursorList<?> cursorList, CursorListEvent event, Object element);
	
}
