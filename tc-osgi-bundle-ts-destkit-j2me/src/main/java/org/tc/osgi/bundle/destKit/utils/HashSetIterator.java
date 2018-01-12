package destKit.utils;

public class HashSetIterator implements Iterator {

	private HashSet subject;
	private int index = 0;

	public HashSetIterator(HashSet hashSet) {
		this.subject = hashSet;
	}

	public boolean hasNext() {
		if (subject.size() != 0 && index < subject.size()) {
			if (subject.elementAt(index) != null)
				return true;
		}
		return false;
	}

	public Object next() {
		return subject.elementAt(index++);
	}

	public void remove() {
		subject.removeElementAt(index);
	}

}
