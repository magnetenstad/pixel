package pixel;

import java.util.ArrayList;
import java.util.Iterator;

public class CursorList<T> implements Iterable<T> {
	protected ArrayList<CursorListListener> listeners = new ArrayList<>();
	protected ArrayList<T> elements = new ArrayList<>();
	protected int cursor = -1;
	
	public T get(int cursor) {
		return elements.get(cursor);
	}
	public void set(int cursor, T element) {
		elements.set(cursor, element);
	}
	public void add(T element) {
		elements.add(element);
		notifyAddedElement(element);
		if (cursor == -1) {
			select(element);
		}
	}
	public void remove(T element) {
		if (getSelected() == element) {
			setCursor(-1);
		}
		elements.remove(element);
		notifyRemovedElement(element);		
	}

	public void removeSelected() {
		T element = getSelected();
		if (element != null) {
			remove(element);
		}
	}
	
	public int size() {
		return elements.size();
	}
	public void select(T element) {
		setCursor(elements.indexOf(element));
	}
	public int getCursor() {
		return cursor;
	}
	public T getSelected() {
		return elements.get(cursor);
	}
	public void setCursor(int cursor) {
		if (!(0 <= cursor && cursor < size())) {
			throw new IllegalArgumentException();
		}
		this.cursor = cursor;
		notifySetIndex();
	}
	
	public void addListener(CursorListListener listener) {
		listeners.add(listener);
	}
	public void removeListener(CursorListListener listener) {
		listeners.remove(listener);
	}
	
	public void notifyAddedElement(T element) {
		for (CursorListListener listener : listeners) {
			listener.listAddedElement(this, element);
		}
	}
	public void notifyRemovedElement(T element) {
		for (CursorListListener listener : listeners) {
			listener.listRemovedElement(this, element);
		}
	}
	public void notifySetIndex() {
		for (CursorListListener listener : listeners) {
			listener.listSetCursor(this, cursor);
		}
	}
	
	@Override
	public Iterator<T> iterator() {
		return elements.iterator();
	}
}
