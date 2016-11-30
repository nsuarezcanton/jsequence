/**
 * Implementation of ADT of type Sequence. The sequence hold String objects in a
 * specific order on a partially filled array with no empty spaces in between
 * each String. The capacity of the Sequence can be expanded, and String objects
 * can be added and removed.
 * 
 * @author Nick Suarez-Canton Trueba
 * @version 02/05/2015
 */
public class Sequence implements Cloneable {
	// ************************************************************************
	// Invariant of the Sequence class:
	// (1) Instance variable manyItems always represents the number of String
	// objects stored in the sequence. If manyItems == 0, then the sequence is
	// empty.
	// (2) For a non-empty sequence, the elements in the sequence are stored in
	// data[0] through data[manyItems - 1], and each index in the rest of the
	// array will refer to null.
	// (3) If there is a current element, then it lies in data[currentIndex]. If
	// currentIndex == manyItems, then there is no currentElement.
	// ************************************************************************
	private String[] data;
	private int manyItems;
	private int currentIndex;

	/**
	 * Initialize an empty sequence with an initial capacity of 10. Note that
	 * the addAfter and addBefore methods work efficiently (without needing more
	 * memory) until this capacity is reached.
	 * 
	 * @postcondition This sequence is empty and has an initial capacity of 10.
	 * @exception OutOfMemoryError
	 *                Indicates insufficient memory for: new double[10].
	 **/
	public Sequence() {
		final int INITIAL_CAPACITY = 10;
		manyItems = 0;
		currentIndex = manyItems;
		data = new String[INITIAL_CAPACITY];
	}

	/**
	 * Initialize an empty sequence with a specified initial capacity. Note that
	 * the addAfter and addBefore methods work efficiently (without needing more
	 * memory) until this capacity is reached.
	 * 
	 * @param initialCapacity
	 *            the initial capacity of this sequence
	 * @precondition initialCapacity is non-negative.
	 * @postcondition This sequence is empty and has the given initial capacity.
	 * @exception IllegalArgumentException
	 *                Indicates that initialCapacity is negative.
	 **/
	public Sequence(int initialCapacity) {
		if (initialCapacity < 0) {
			throw new IllegalArgumentException("initialCapacity is negative: "
					+ initialCapacity);
		}
		manyItems = 0;
		currentIndex = manyItems;
		data = new String[initialCapacity];
	}

	/**
	 * Adds a string to the sequence in the location before the current element.
	 * If the sequence has no current element, the string is added to the
	 * beginning of the sequence.
	 * 
	 * The added element becomes the current element.
	 * 
	 * If the sequences's capacity has been reached, the sequence will expand to
	 * twice its current capacity plus 1.
	 * 
	 * @param value
	 *            the string to add.
	 * 
	 * @postcondition A new copy of the element has been added to this sequence.
	 *                If there was a current element, then the new element is
	 *                placed before the current element. If there was no current
	 *                element, then the new element is placed at the start of
	 *                the sequence. In all cases, the new element becomes the
	 *                new current element of this sequence.
	 */
	public void addBefore(String value) {
		if (manyItems == data.length) {
			ensureCapacity(manyItems * 2 + 1);
		}
		if (!isCurrent())
			currentIndex = 0;
		for (int i = manyItems; i > currentIndex; i--) {
			data[i] = data[i - 1];
		}
		data[currentIndex] = value;
		manyItems++;
	}

	/**
	 * Adds a string to the sequence in the location after the current element.
	 * If the sequence has no current element, the string is added to the end of
	 * the sequence.
	 * 
	 * The added element becomes the current element.
	 * 
	 * If the sequences's capacity has been reached, the sequence will expand to
	 * twice its current capacity plus 1.
	 * 
	 * @param value
	 *            the string to add.
	 */
	public void addAfter(String value) {
		if (manyItems == data.length) {
			ensureCapacity(manyItems * 2 + 1);
		}

		if (!isCurrent())
			currentIndex = manyItems;

		else
			currentIndex++;

		for (int i = manyItems; i > currentIndex; i--)
			data[i] = data[i - 1];

		data[currentIndex] = value;
		manyItems++;

	}

	/**
	 * Places the contents of another sequence at the end of this sequence.
	 * 
	 * If adding all elements of the other sequence would exceed the capacity of
	 * this sequence, the capacity is changed to make room for all of the
	 * elements to be added.
	 * 
	 * @param addend
	 *            the sequence whose contents should be added.
	 */
	public void addAll(Sequence addend) {
		ensureCapacity(manyItems + addend.size());
		System.arraycopy(addend.data, 0, data, manyItems, addend.size());
		manyItems += addend.manyItems;
	}

	/**
	 * Move forward in the sequence so that the current element is now the next
	 * element in the sequence.
	 * 
	 * If the current element was already the end of the sequence, then
	 * advancing causes there to be no current element.
	 * 
	 * @precondition: should only be called when there is a current element.
	 */
	public void advance() {
		if (currentIndex < manyItems && isCurrent())
			currentIndex++;
		else
			throw new IllegalStateException("There is no current element.");
	}

	/**
	 * Make a copy of this sequence. Subsequence changes to the copy do not
	 * affect the current sequence, and vice versa.
	 * 
	 * @return the copy of this sequence.
	 */
	public Sequence clone() {
		Sequence answer;
		try {
			answer = (Sequence) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(
					"This class does not implement Cloneable.");
		}
		answer.data = data.clone();
		return answer;
	}

	/**
	 * Create a new sequence that contains all of the elements of one sequence
	 * followed by all of the elements of another sequence.
	 * 
	 * The new sequence does not have a current element. The new sequence has
	 * capacity equal to the sum of the capacities of the sequences being
	 * concatenated.
	 * 
	 * @param s1
	 *            the sequence whose elements should come first in the
	 *            concatenation
	 * @param s2
	 *            the sequence whose elements should come second
	 */
	public static Sequence concatenation(Sequence s1, Sequence s2) {
		Sequence concatenated = new Sequence(s1.getCapacity()
				+ s2.getCapacity());

		System.arraycopy(s1.data, 0, concatenated.data, 0, s1.manyItems);
		System.arraycopy(s2.data, 0, concatenated.data, s1.manyItems,
				s2.manyItems);

		concatenated.manyItems = s1.manyItems + s2.manyItems;
		concatenated.currentIndex = concatenated.manyItems;
		return concatenated;

	}

	/**
	 * Change the current capacity of this sequence. The sequence's capacity
	 * will be changed to be at least a minimum capacity.
	 * 
	 * @param minCapacity
	 *            the minimum capacity that the sequence should now have.
	 * @postcondition This sequence's capacity has been changed to at least
	 *                minimumCapacity. If the capacity was already at or greater
	 *                than minimumCapacity, then the capacity is left unchanged.
	 */
	public void ensureCapacity(int minCapacity) {

		if (data.length < minCapacity) {
			String[] biggerArray = new String[minCapacity];
			System.arraycopy(data, 0, biggerArray, 0, manyItems);
			data = biggerArray;
		}

	}

	/**
	 * @return the capacity of the sequence.
	 */
	public int getCapacity() {
		return data.length;
	}

	/**
	 * Accessor method to get the current element of this sequence.
	 * 
	 * @precondition isCurrent() returns true;
	 * 
	 * @return the element at the current location in the sequence, or null if
	 *         there is no current element.
	 */
	public String getCurrent() {
		if (isCurrent())
			return data[currentIndex];

		else
			return null;

	}

	/**
	 * Accessor method to determine whether this sequence has a specified
	 * current element that can be retrieved with the getCurrent method.
	 * 
	 * @return true if and only if the sequence has a current element.
	 */
	public boolean isCurrent() {
		return data[currentIndex] != null;
	}

	/**
	 * Remove the current element from this sequence. The following element, if
	 * there was one, becomes the current element. If there was no following
	 * element (current was at the end of the sequence), the sequence now has no
	 * current element.
	 * 
	 * If there is no current element, does nothing.
	 */
	public void removeCurrent() {
		if (isCurrent()) {

			for (int i = currentIndex; i < manyItems - 1; i++) {
				data[i] = data[i + 1];
			}
			manyItems--;
			data[manyItems] = null;
		}

	}

	/**
	 * @return the number of elements stored in the sequence.
	 */
	public int size() {
		return manyItems;
	}

	/**
	 * Sets the current element to the start of the sequence. If the sequence is
	 * empty, the sequence has no current element.
	 */
	public void start() {
		currentIndex = 0;
	}

	/**
	 * Reduce the current capacity to its actual size, so that it has capacity
	 * to store only the elements currently stored.
	 */
	public void trimToSize() {
		String[] trimmed = new String[manyItems];
		System.arraycopy(data, 0, trimmed, 0, manyItems);
		data = trimmed;
	}

	/**
	 * Produce a string representation of this sequence. The current location is
	 * indicated by a >. For example, a sequence with "A" followed by "B", where
	 * "B" is the current element, and the capacity is 5, would print as:
	 * 
	 * {A, >B} (capacity = 5)
	 * 
	 * An empty sequence with a capacity of 10 would print as:
	 * 
	 * {} (capacity = 10)
	 * 
	 * @return a string representation of this sequence.
	 */
	public String toString() {
		String sequence = new String("{");

		for (int i = 0; i < data.length; i++) {
			if (i != currentIndex && data[i] != null) {
				if (i != manyItems - 1)
					sequence = sequence.concat(data[i] + ", ");
				else
					sequence = sequence.concat(data[i]);
			} else if (i == currentIndex && data[i] != null) {
				if (i != manyItems - 1)
					sequence = sequence.concat(">" + data[i] + ", ");
				else
					sequence = sequence.concat(">" + data[i]);
			}
		}
		sequence = sequence.concat("} (capacity = " + getCapacity() + ")");
		return sequence;
	}

	/**
	 * Checks whether another sequence is equal to this one. To be considered
	 * equal, the other sequence must have the same elements, in the same order,
	 * and with the same element marked current. The capacity can differ.
	 * 
	 * @param other
	 *            the other Sequence with which to compare
	 * @return true iff the other sequence is equal to this one.
	 */
	public boolean equals(Sequence other) {
		if (this.manyItems == 0 && other.manyItems == 0)
			return true;
		else if (currentIndex == other.currentIndex) {
			int count = 0;
			for (int index = 0; index < manyItems; index++) {
				if (data[index].equals(other.data[index])) {
					count++;
					if (count == manyItems) {
						return true;
					}
				} else
					return false;
			}
		}
		return false;
	}
}