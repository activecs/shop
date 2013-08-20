package ua.kharkiv.epam.dereza.part4;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * 
 * @author Eduard_Dereza
 * 
 * @param <E>
 */
public class ListWrapper<E> implements List<E> {

	List<E> unmodifiable;
	List<E> modifiable;

	public ListWrapper(List<E> unmodifiable, List<E> modifiable) {
		this.unmodifiable = unmodifiable;
		this.modifiable = modifiable;
	}

	@Override
	public boolean add(E e) {
		return modifiable.add(e);
	}

	@Override
	public void add(int index, E element) {
		int indexForModifiableList = unmodifiable.size() - index;
		try {
			modifiable.add(indexForModifiableList, element);
		} catch (IndexOutOfBoundsException e) {
			throw new IllegalArgumentException(
					"You cannot modify unmodifiable part of list, modifiable part starts from "
							+ unmodifiable.size() + " index");
		}
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		return modifiable.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		int indexForModifiableList = unmodifiable.size() - index;
		try {
			return modifiable.addAll(indexForModifiableList, c);
		} catch (IndexOutOfBoundsException e) {
			throw new IllegalArgumentException(
					"You cannot modify unmodifiable part of list, modifiable part starts from "
							+ unmodifiable.size() + " index");
		}

	}
	
	/**
	 * clear modifiable list
	 * 
	 */
	public void clearModifiableList() {
		modifiable.clear();
	}
	
	@Override
	public void clear() {
		if (true)
			throw new UnsupportedOperationException(
					"You can try to use clearModifiableList() instead of it.");
	}

	@Override
	public boolean contains(Object o) {
		return modifiable.contains(o) || unmodifiable.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		List<E> sublist = new ArrayList<E>();
		sublist.addAll(unmodifiable);
		sublist.addAll(modifiable);
		return sublist.containsAll(c);
	}

	@Override
	public E get(int index) {
		if (index < unmodifiable.size()) {
			return unmodifiable.get(index);
		} else {
			return modifiable.get(index - unmodifiable.size());
		}
	}

	@Override
	public int indexOf(Object o) {
		int index = unmodifiable.indexOf(o);
		if (index != -1)
			return index;
		return modifiable.indexOf(o);
	}

	@Override
	public boolean isEmpty() {
		return modifiable.isEmpty() && unmodifiable.isEmpty();
	}

	@Override
	public Iterator<E> iterator() {
		return new IteratorImpl();
	}

	@Override
	public int lastIndexOf(Object o) {
		int lastIndex = modifiable.lastIndexOf(o);
		if (lastIndex != -1)
			return lastIndex;
		return unmodifiable.lastIndexOf(o);
	}

	@SuppressWarnings("unused")
	@Override
	public ListIterator<E> listIterator() {
		if (true)
			throw new UnsupportedOperationException();
		return null;
	}

	@SuppressWarnings("unused")
	@Override
	public ListIterator<E> listIterator(int index) {
		if (true)
			throw new UnsupportedOperationException();
		return null;
	}

	@Override
	public boolean remove(Object o) {
		return modifiable.remove(o);
	}

	@Override
	public E remove(int index) {
		int indexForRemoving = unmodifiable.size() - index;
		try {
			return modifiable.remove(indexForRemoving);
		} catch (IndexOutOfBoundsException e) {
			throw new IllegalArgumentException(
					"You cannot modify unmodifiable part of list, modifiable part starts from "
							+ unmodifiable.size() + " index");
		}
	}
	
	/**
	 * Removes all elements only from modifiable list
	 * 
	 * @param Collection<?> c
	 * @return true if this list changed as a result of the call
	 */
	public boolean removeAllFromModifiableList(Collection<?> c) {
		return modifiable.removeAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		if (true)
			throw new UnsupportedOperationException(
					"You can try to use removeAllFromModifiableList() instead of it.");
		return false;
	}
	
	/**
	 * Retain all elements only from modifiable list
	 * 
	 * @param Collection<?> c
	 * @return true if this list changed as a result of the call
	 */
	public boolean retainAllFromModifiableList(Collection<?> c) {
		return modifiable.retainAll(c);
	}
	
	@Override
	public boolean retainAll(Collection<?> c) {
		if (true)
			throw new UnsupportedOperationException(
					"You can try to use retainAllFromModifiableList() instead of it.");
		return false;
	}

	@Override
	public E set(int index, E element) {
		int indexForSetting = unmodifiable.size() - index;
		try {
			return modifiable.set(indexForSetting, element);
		} catch (IndexOutOfBoundsException e) {
			throw new IllegalArgumentException(
					"You cannot modify unmodifiable part of list, modifiable part starts from "
							+ unmodifiable.size() + " index");
		}
	}

	@Override
	public int size() {
		return modifiable.size() + unmodifiable.size();
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		List<E> sublist = new ArrayList<E>();
		sublist.addAll(unmodifiable);
		sublist.addAll(modifiable);
		return sublist.subList(fromIndex, toIndex);

	}

	@Override
	public Object[] toArray() {
		List<E> sublist = new ArrayList<E>();
		sublist.addAll(unmodifiable);
		sublist.addAll(modifiable);
		return sublist.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		List<E> sublist = new ArrayList<E>();
		sublist.addAll(unmodifiable);
		sublist.addAll(modifiable);
		return sublist.toArray(a);
	}

	/**
	 * Iterator implementation as inner class
	 * 
	 * @author Eduard_Dereza
	 * 
	 */
	public class IteratorImpl implements Iterator<E> {

		private Iterator<E> iterForUnmodifiable;
		private Iterator<E> iterForModifiable;
		private boolean activeListIsUnmodifiable;

		public IteratorImpl() {
			iterForUnmodifiable = unmodifiable.iterator();
			iterForModifiable = modifiable.iterator();
		}

		@Override
		public boolean hasNext() {
			while (iterForUnmodifiable.hasNext()) {
				activeListIsUnmodifiable = true;
				return true;
			}
			while (iterForModifiable.hasNext()) {
				activeListIsUnmodifiable = false;
				return true;
			}
			return false;
		}

		@Override
		public E next() {
			if (activeListIsUnmodifiable)
				return iterForUnmodifiable.next();
			return iterForModifiable.next();
		}

		@Override
		public void remove() {
			if (activeListIsUnmodifiable)
				throw new UnsupportedOperationException(
						"You cannot modify unmodifiable part of list, modifiable part starts from "
								+ unmodifiable.size() + " index");
			iterForModifiable.remove();
		}

	}

}
