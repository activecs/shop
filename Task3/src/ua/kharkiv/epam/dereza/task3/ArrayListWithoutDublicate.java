package ua.kharkiv.epam.dereza.task3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

public class ArrayListWithoutDublicate<E> extends ArrayList<E> {
	
	@Override
	public boolean add(E e) {
		if (!contains(e))
			return super.add(e);
		throw new IllegalArgumentException("List already contains current element");
	}

	@Override
	public void add(int index, E element) {
		if (!contains(element)){
			super.add(index, element);
		}
		throw new IllegalArgumentException("List already contains current element");
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		Iterator<? extends E> iter = c.iterator();
		while(iter.hasNext()){
			if(contains(iter.next()))
				throw new IllegalArgumentException("List already contains current element");
		}
		return super.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		Iterator<? extends E> iter = c.iterator();
		while(iter.hasNext()){
			if(contains(iter.next()))
				throw new IllegalArgumentException("List already contains current element");
		}
		return super.addAll(index, c);
	}

	@Override
	public ListIterator<E> listIterator() {
		if(true) throw new UnsupportedOperationException();
		return super.listIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		if(true) throw new UnsupportedOperationException();
		return super.listIterator(index);
	}

	@Override
	public E set(int index, E element) {
		if (!contains(element))
			return super.set(index, element);
		return element;
	}

}
