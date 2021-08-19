package datastructure.queue;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Queue;

public abstract class AbstractQueue< E> implements Queue< E> {

	@Override
	public Iterator< E> iterator() {
		throw new UnsupportedOperationException( "iterator() is a bonus method, you must override it.");
	}

	@Override
	public Object[] toArray() {
		Object[] array = new Object[ size()];
		int i = 0;
		for ( E r : this) {
			array[i++] = r;
		}
		return array;
	}

	@SuppressWarnings( "unchecked")
	@Override
	public < T> T[] toArray( T[] a) {
		Objects.requireNonNull( a);
		if ( a.length < size())
			a = (T[]) new Object[ size()];
		int i = 0;
		for ( E r : this) {
			a[i++] = (T) r;
		}
		return a;
	}

	@Override
	public boolean addAll( Collection< ? extends E> c) {
		Objects.requireNonNull( c);
		for ( E r : c) {
			add( r);
		}
		return true;
	}

	@Override
	public boolean containsAll( Collection< ?> c) {
		for ( Object o : c) {
			if ( !contains( o))
				return false;
		}
		return true;
	}

	@Override
	public boolean removeAll( Collection< ?> c) {
		for ( Object o : c) {
			remove( o);
		}
		return true;
	}

	@Override
	public boolean retainAll( Collection< ?> c) {
		Iterator< E> it = iterator();
		while ( it.hasNext()) {
			if ( !c.contains( it.next()))
				it.remove();
		}
		return true;
	}

	@Override
	public boolean add( E e) {
		return offer( e);
	}

	@Override
	public E remove() {
		return poll();
	}

	@Override
	public E element() {
		return peek();
	}
}

