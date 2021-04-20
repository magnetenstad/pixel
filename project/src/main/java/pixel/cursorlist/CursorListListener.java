package pixel.cursorlist;

/**
 * Listener of a CursorList.
 * @author Magne Tenstad
 */
public interface CursorListListener {
	
	/*
	 * Response to a CursorListEvent.
	 */
	public void cursorListChanged(CursorList<?> cursorList, CursorListEvent event, Object element);
	
}
