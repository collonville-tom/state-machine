/*
 * @(#)HashSet.java	1.28 03/01/23
 *
 * Copyright 2003 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package destKit.utils;

import java.util.Vector;

import com.sun.perseus.model.Set;

/**
 * This class implements the <tt>Set</tt> interface, backed by a hash table
 * (actually a <tt>HashMap</tt> instance). It makes no guarantees as to the
 * iteration order of the set; in particular, it does not guarantee that the
 * order will remain constant over time. This class permits the <tt>null</tt>
 * element.
 * <p>
 * 
 * This class offers constant time performance for the basic operations (<tt>add</tt>,
 * <tt>remove</tt>, <tt>contains</tt> and <tt>size</tt>), assuming the
 * hash function disperses the elements properly among the buckets. Iterating
 * over this set requires time proportional to the sum of the <tt>HashSet</tt>
 * instance's size (the number of elements) plus the "capacity" of the backing
 * <tt>HashMap</tt> instance (the number of buckets). Thus, it's very
 * important not to set the initial capacity too high (or the load factor too
 * low) if iteration performance is important.
 * <p>
 * 
 * <b>Note that this implementation is not synchronized.</b> If multiple
 * threads access a set concurrently, and at least one of the threads modifies
 * the set, it <i>must</i> be synchronized externally. This is typically
 * accomplished by synchronizing on some object that naturally encapsulates the
 * set. If no such object exists, the set should be "wrapped" using the
 * <tt>Collections.synchronizedSet</tt> method. This is best done at creation
 * time, to prevent accidental unsynchronized access to the <tt>HashSet</tt>
 * instance:
 * 
 * <pre>
 *     Set s = Collections.synchronizedSet(new HashSet(...));
 * </pre>
 * 
 * <p>
 * 
 * The iterators returned by this class's <tt>iterator</tt> method are
 * <i>fail-fast</i>: if the set is modified at any time after the iterator is
 * created, in any way except through the iterator's own <tt>remove</tt>
 * method, the Iterator throws a <tt>ConcurrentModificationException</tt>.
 * Thus, in the face of concurrent modification, the iterator fails quickly and
 * cleanly, rather than risking arbitrary, non-deterministic behavior at an
 * undetermined time in the future.
 * 
 * <p>
 * Note that the fail-fast behavior of an iterator cannot be guaranteed as it
 * is, generally speaking, impossible to make any hard guarantees in the
 * presence of unsynchronized concurrent modification. Fail-fast iterators throw
 * <tt>ConcurrentModificationException</tt> on a best-effort basis. Therefore,
 * it would be wrong to write a program that depended on this exception for its
 * correctness: <i>the fail-fast behavior of iterators should be used only to
 * detect bugs.</i>
 * <p>
 * 
 * This class is a member of the <a href="{@docRoot}/../guide/collections/index.html">
 * Java Collections Framework</a>.
 * 
 * @author Josh Bloch
 * @version 1.28, 01/23/03
 * @see Collection
 * @see Set
 * @see TreeSet
 * @see Collections#synchronizedSet(Set)
 * @see HashMap
 * @since 1.2
 */

public class HashSet extends Vector implements Collection, Cloneable {

	public boolean add(Object o) {
		super.addElement(o);
		return true;
	}

	public boolean addAll(Collection c) {
		Iterator it = c.iterator();
		for (; it.hasNext();) {
			this.add(it.next());
		}
		return true;
	}

	public void clear() {
		super.removeAllElements();

	}

	public boolean containsAll(Collection c) {
		Iterator it = c.iterator();
		for (; it.hasNext();) {
			if (!this.contains(it.next()))
				return false;
		}
		return true;
	}

	public Iterator iterator() {
		return new HashSetIterator(this);
	}

	public boolean remove(Object o) {
		if (this.contains(o))
			super.removeElement(o);
		return true;
	}

	public boolean removeAll(Collection c) {
		Iterator it = c.iterator();
		for (; it.hasNext();) {
			this.remove(it.next());
		}
		return true;
	}

	public boolean retainAll(Collection c) {
		return false;
	}

	public Object[] toArray() {
		Object[] tab = new Object[super.capacity()];
		for (int i = 0; i < this.size(); i++)
			tab[i] = this.elementAt(i);
		return tab;
	}

	public Object[] toArray(Object[] a) {
		return null;
	}

}
