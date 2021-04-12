package pixel;

import java.util.ArrayList;
import java.util.Iterator;

public class SelectableList<T> implements Iterable<T> {
	protected ArrayList<SelectableListListener> listeners = new ArrayList<>();
	protected ArrayList<T> elements = new ArrayList<>();
	protected int index = -1;
	
	public T get(int index) {
		return elements.get(index);
	}
	public void set(int index, T element) {
		elements.set(index, element);
	}
	public void add(T element) {
		elements.add(element);
		notifyAddedElement(element);
		if (index == -1) {
			select(element);
		}
	}
	public void remove(T element) {
		if (getSelected() == element) {
			setIndex(-1);
		}
		elements.remove(element);
		notifyRemovedElement(element);		
	}
	public int size() {
		return elements.size();
	}
	public void select(T element) {
		setIndex(elements.indexOf(element));
	}
	public int getIndex() {
		return index;
	}
	public T getSelected() {
		return elements.get(index);
	}
	public void setIndex(int index) {
		if (!(0 <= index && index < size())) {
			throw new IllegalArgumentException();
		}
		this.index = index;
		notifySetIndex();
	}
	
	public void addListener(SelectableListListener listener) {
		listeners.add(listener);
	}
	public void removeListener(SelectableListListener listener) {
		listeners.remove(listener);
	}
	
	public void notifyAddedElement(T element) {
		for (SelectableListListener listener : listeners) {
			listener.listAddedElement(this, element);
		}
	}
	
	public void notifyRemovedElement(T element) {
		for (SelectableListListener listener : listeners) {
			listener.listRemovedElement(this, element);
		}
	}
	public void notifySetIndex() {
		for (SelectableListListener listener : listeners) {
			listener.listSetIndex(this, index);
		}
	}
	
	@Override
	public Iterator<T> iterator() {
		return elements.iterator();
	}
}
