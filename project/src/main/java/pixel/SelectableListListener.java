package pixel;

public interface SelectableListListener {
	
	public void listAddedElement(SelectableList<?> selectableList, Object element);
	
	public void listRemovedElement(SelectableList<?> selectableList, Object element);

	public void listSetIndex(SelectableList<?> selectableList, int index);
	
}
