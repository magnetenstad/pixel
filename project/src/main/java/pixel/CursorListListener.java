package pixel;

public interface CursorListListener {
	
	public void listAddedElement(CursorList<?> selectableList, Object element);
	
	public void listRemovedElement(CursorList<?> selectableList, Object element);

	public void listSetCursor(CursorList<?> selectableList, int cursor);
	
}
