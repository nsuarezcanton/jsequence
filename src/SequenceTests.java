/*
 * Collection of methods to test if Sequence.java is working properly.
 */
public class SequenceTests {

	public static void main(String[] args) {
		Testing.setVerbose(true);
		System.out.println("Starting Tests");

		// Tests start here.

		testConstructor();
		testCapability();
		testAddBefore();
		testAddAfter();
		testAddAll();
		testCurrentElement();
		testIndexPostion();
		testClone();
		testEquals();
		testConcatenate();
		testRemoveCurrent();
		testTrimToSize();
		testSize();
		testPrinting();

		// Tests end here.
		System.out.println("Tests Complete");
	}

	private static void testConstructor() {
		Testing.testSection("Sequence() tests");

		Sequence s1 = new Sequence();
		Testing.assertEquals("Default constructor", "{} (capacity = 10)",
				s1.toString());
		Testing.assertEquals("Default constructor, initial size", 0, s1.size());

		Sequence s2 = new Sequence(20);
		Testing.assertEquals("Non-default constructor", "{} (capacity = 20)",
				s2.toString());
		Testing.assertEquals("Non-default constructor, initial size", 0,
				s2.size());

	}

	private static void testCapability() {
		Testing.testSection("Capacity() tests");

		Sequence s1 = new Sequence();

		Testing.assertEquals("Default constructor", 10, s1.getCapacity());
		s1.ensureCapacity(30);
		Testing.assertEquals("After minCapacity = 30", 30, s1.getCapacity());

		Sequence s2 = new Sequence(20);

		Testing.assertEquals("Non-default constructor", 20, s2.getCapacity());
		s2.ensureCapacity(30);
		Testing.assertEquals("After minCapacity = 30", 30, s2.getCapacity());

		Sequence s3 = new Sequence();
		String[] letters = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
				"K", "L" };
		for (String s : letters)
			s3.addAfter(s);

		Testing.assertEquals("Filled sequence:", (10 * 2) + 1, s3.getCapacity());
		s3.ensureCapacity(50);
		Testing.assertEquals("After minCapacity = 50", 50, s3.getCapacity());

	}

	private static void testAddBefore() {

		Testing.testSection("AddBefore() tests");

		Sequence s1 = new Sequence();
		s1.addBefore("Excelsior");

		// Test on empty string.
		Testing.assertEquals("addBefore() on empty sequence",
				"{>Excelsior} (capacity = 10)", s1.toString());

		s1.addBefore("A");
		Testing.assertEquals("addBefore() one element current @ end",
				"{>A, Excelsior} (capacity = 10)", s1.toString());

		Sequence s2 = new Sequence();
		String[] letters = { "B", "C", "D", "F" };
		for (String s : letters)
			s2.addAfter(s);

		// Test on filled string.
		s2.addBefore("E");
		Testing.assertEquals("addBefore() last position",
				"{B, C, D, >E, F} (capacity = 10)", s2.toString());
		s2.start();
		s2.addBefore("A");
		Testing.assertEquals("addBefore() first position",
				"{>A, B, C, D, E, F} (capacity = 10)", s2.toString());

		s2.advance();
		s2.addBefore("Y");
		Testing.assertEquals("addBefore() middle position",
				"{A, >Y, B, C, D, E, F} (capacity = 10)", s2.toString());

		Sequence s3 = new Sequence();
		s3.addAfter("Nicolas");
		s3.addAfter("Pablo");
		s3.addAfter("Suarez-Canton");
		s3.trimToSize();
		s3.addBefore("Trueba");
		Testing.assertEquals("addBefore() beyond capacity",
				"{Nicolas, Pablo, >Trueba, Suarez-Canton} (capacity = 7)",
				s3.toString());

		Sequence s4 = new Sequence(1);
		s4.addAfter("Z");
		s4.addBefore("Y");
		Testing.assertEquals("oneElementSequenceCurrentAtStart",
				"{>Y, Z} (capacity = 3)", s4.toString());

	}

	private static void testAddAfter() {
		// Test on empty string.
		Testing.testSection("AddAfter() tests");
		Sequence s1 = new Sequence();
		s1.addAfter("Excelsior");
		Testing.assertEquals("addAfter() on empty sequence",
				"{>Excelsior} (capacity = 10)", s1.toString());

		s1.addAfter("A");
		Testing.assertEquals("addAfter() one element current @ end",
				"{Excelsior, >A} (capacity = 10)", s1.toString());

		Sequence s2 = new Sequence();
		String[] letters = { "D", "C", "A" };
		for (String s : letters)
			s2.addBefore(s);

		// Test on filled string with no current element.
		s2.advance();
		s2.advance();
		s2.addAfter("E");
		Testing.assertEquals("addAfter() currentElement @end",
				"{A, C, D, >E} (capacity = 10)", s2.toString());
		s2.advance();
		s2.addAfter("F");
		Testing.assertEquals("addAfter() no current element",
				"{A, C, D, E, >F} (capacity = 10)", s2.toString());

		// Test on filled string with currentIndex = 0
		s2.start();
		s2.addAfter("B");
		Testing.assertEquals("addAfter() first position",
				"{A, >B, C, D, E, F} (capacity = 10)", s2.toString());

		s2.addAfter("Y");
		Testing.assertEquals("addAfter() middle position",
				"{A, B, >Y, C, D, E, F} (capacity = 10)", s2.toString());

		Sequence s3 = new Sequence();
		s3.addAfter("Nicolas");
		s3.addAfter("Pablo");
		s3.addAfter("Suarez-Canton");
		s3.trimToSize();
		s3.addAfter("Trueba");
		Testing.assertEquals("addAfter() beyond capacity",
				"{Nicolas, Pablo, Suarez-Canton, >Trueba} (capacity = 7)",
				s3.toString());
	}

	private static void testAddAll() {
		Testing.testSection("AddAll() tests");

		Sequence seq1 = new Sequence();
		Sequence seq2 = new Sequence();
		seq1.addAll(seq2);
		Testing.assertEquals("addAll() when sequences are empty",
				"{} (capacity = 10)", seq1.toString());

		seq1 = new Sequence();
		seq1.addAfter("A");
		seq1.addAfter("B");

		seq2 = new Sequence();
		seq2.addAfter("C");
		seq2.addAfter("D");
		seq2.addAfter("E");
		seq2.addAfter("F");
		seq1.addAll(seq2);
		Testing.assertEquals("addAll() when capacity is not exceeded",
				"{A, >B, C, D, E, F} (capacity = 10)", seq1.toString());

		seq1 = new Sequence(2);
		seq1.addAfter("A");
		seq1.addAfter("B");

		seq2 = new Sequence();
		seq2.addAfter("C");
		seq2.addAfter("D");
		seq2.addAfter("E");
		seq2.addAfter("F");
		seq1.addAll(seq2);
		Testing.assertEquals("addAll() when capacity is exceeded",
				"{A, >B, C, D, E, F} (capacity = 6)", seq1.toString());

		seq1.advance();
		seq1.advance();
		seq1.removeCurrent();
		Testing.assertEquals("modifying sequence doesnt change addend",
				"{C, D, E, >F} (capacity = 10)", seq2.toString());

		Sequence seq3 = new Sequence();
		seq3.addAfter("A");
		seq3.addAfter("B");

		Sequence seq4 = new Sequence();
		seq4.addAfter("C");
		seq4.addAfter("D");
		seq4.addAfter("E");
		seq4.addAfter("F");

		seq3.addAll(seq4);

		seq4.removeCurrent();
		Testing.assertEquals("modifying addend doesnt change sequence",
				"{A, >B, C, D, E, F} (capacity = 10)", seq3.toString());

	}

	private static void testCurrentElement() {
		Testing.testSection("Current element tests");

		Sequence emptySeq = new Sequence();

		Sequence nonEmptySeq = new Sequence();
		String[] myArray1 = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
				"K" };
		for (String s : myArray1)
			nonEmptySeq.addAfter(s);

		Testing.assertEquals("isCurrent() on empty sequence,", false,
				emptySeq.isCurrent());
		Testing.assertEquals("getCurrent() on empty sequence,", null,
				emptySeq.getCurrent());

		Testing.assertEquals("isCurrent() on non-empty sequence,", true,
				nonEmptySeq.isCurrent());
		Testing.assertEquals("getCurrent() on non-empty sequence,", "K",
				nonEmptySeq.getCurrent());

		nonEmptySeq.advance();
		Testing.assertEquals(
				"isCurrent() on non-empty sequence after advancing past last element",
				false, nonEmptySeq.isCurrent());
		Testing.assertEquals(
				"getCurrent() on non-empty sequence after advancing past last element",
				null, nonEmptySeq.getCurrent());

		nonEmptySeq.start();
		Testing.assertEquals(
				"isCurrent() on non-empty sequence after start(),", true,
				nonEmptySeq.isCurrent());
		Testing.assertEquals(
				"getCurrent() on non-empty sequence after start()", "A",
				nonEmptySeq.getCurrent());
	}

	private static void testIndexPostion() {
		Testing.testSection("Index position tests");
		// start() and advance() on non-empty sequence
		Sequence nonEmptySeq = new Sequence();
		String[] myArray1 = { "A", "B", "C", "D" };
		for (String s : myArray1)
			nonEmptySeq.addAfter(s);

		nonEmptySeq.start();
		Testing.assertEquals("start() on non-empty sequence",
				"{>A, B, C, D} (capacity = 10)", nonEmptySeq.toString());

		nonEmptySeq.advance();
		nonEmptySeq.advance();
		nonEmptySeq.advance();
		Testing.assertEquals("advance() on non-empty sequence",
				"{A, B, C, >D} (capacity = 10)", nonEmptySeq.toString());

		// advance() after trimToSize()
		nonEmptySeq.trimToSize();
		nonEmptySeq.advance();
		Testing.assertEquals(
				"advance() when current element is last in sequence",
				"{A, B, C, D} (capacity = 4)", nonEmptySeq.toString());
	}

	// Based on Lab-5
	private static void testClone() {
		Testing.testSection("Testing clone()");

		Sequence seq1 = new Sequence(3);

		Sequence seq2 = seq1.clone();
		Testing.assertEquals("cloning an empty sequence", "{} (capacity = 3)",
				seq2.toString());

		seq1 = new Sequence(3);
		seq1.addAfter("Ni");
		seq1.addAfter("co");
		seq1.addAfter("las.");
		seq2 = seq1.clone();
		Testing.assertEquals("cloning {Ni, co, las.}",
				"{Ni, co, >las.} (capacity = 3)", seq2.toString());
		Testing.assertEquals(
				"cloning {Ni, co, las.} should produce a different object.  Does (seq2 != seq1) return true?",
				true, (seq2 != seq1));

		seq1 = new Sequence(7);
		seq1.addAfter("Nal");
		seq1.addAfter("ge");
		seq1.addAfter("ne,");
		seq2 = seq1.clone();
		seq1.addAfter("rocks!");
		Testing.assertEquals("clone shouldn't change after adding to original",
				"{Nal, ge, >ne,} (capacity = 7)", seq2.toString());
		Testing.assertEquals(
				"original should change after cloning & adding to original",
				"{Nal, ge, ne,, >rocks!} (capacity = 7)", seq1.toString());

		seq1 = new Sequence(5);
		seq1.addAfter("Ni");
		seq1.addAfter("co");
		seq1.addAfter("las");
		seq1.addAfter("Sua");
		seq2 = seq1.clone();
		seq2.addAfter("rez.");
		Testing.assertEquals("original shouldn't change after adding to clone",
				"{Ni, co, las, >Sua} (capacity = 5)", seq1.toString());

		Testing.assertEquals(
				"clone should change after cloning & adding to clone",
				"{Ni, co, las, Sua, >rez.} (capacity = 5)", seq2.toString());
	}

	private static void testEquals() {
		Testing.testSection("Testing equals()");
		Sequence seq1 = new Sequence();
		Sequence seq2 = new Sequence();
		Sequence seq3 = new Sequence();
		Sequence seq4 = new Sequence();

		Testing.assertEquals("empty sequences should be equal", true,
				seq1.equals(seq2));

		seq1.addAfter("A");
		seq2.addAfter("A");
		seq1.addAfter("B");
		seq2.addAfter("B");

		Testing.assertEquals("equal bc/ same objects, order & index", true,
				seq1.equals(seq2));

		seq2.start();
		Testing.assertEquals("not equal bc/ different index", false,
				seq1.equals(seq2));

		seq3.addAfter("B");
		seq3.addAfter("A");
		Testing.assertEquals("not equal bc/ same objects but different order",
				false, seq1.equals(seq2));

		seq4 = new Sequence();
		seq4.addAfter("Ni");
		seq4.addAfter("co");
		seq4.addAfter("las.");

		Testing.assertEquals("not equal bc/ different sequences", false,
				seq1.equals(seq4));

		Sequence seq4clone = seq4.clone();
		Testing.assertEquals("clone equals seq", true, seq4clone.equals(seq4));
		Testing.assertEquals("Sequence equals clone", true,
				seq4.equals(seq4clone));

		seq4clone.ensureCapacity(20);
		Testing.assertEquals("same elements, different capacities", true,
				seq4.equals(seq4clone));

	}

	private static void testConcatenate() {
		Testing.testSection("Test Sequence.concatenate()");
		Sequence seq1 = new Sequence();
		seq1.addAfter("A");
		seq1.addAfter("B");

		Sequence seq2 = new Sequence();
		seq2.addAfter("C");
		seq2.addAfter("D");

		Sequence concatenated = Sequence.concatenation(seq1, seq2);

		Testing.assertEquals("contains both sequences",
				"{A, B, C, D} (capacity = 20)", concatenated.toString());

		Testing.assertEquals("has a current element", false,
				concatenated.isCurrent());
		Testing.assertEquals(
				"capacity of concatenated  = capacity of seq1 + capacity of seq2",
				seq1.getCapacity() + seq2.getCapacity(),
				concatenated.getCapacity());

		Sequence seq3 = new Sequence();
		seq3.addAfter("A");
		seq3.addAfter("B");

		Sequence seq4 = new Sequence();
		seq4.addAfter("C");
		seq4.addAfter("D");

		Sequence concatenated2 = Sequence.concatenation(seq3, seq4);

		concatenated2.start();
		concatenated2.removeCurrent();
		Testing.assertEquals("modifyingConcatenationDoesntChangeFirstSequence",
				"{A, >B} (capacity = 10)", seq3.toString());
		seq3.start();
		seq3.removeCurrent();
		Testing.assertEquals("modifyingFirstSequenceDoesntChangeConcatenation",
				"{>B, C, D} (capacity = 20)", concatenated2.toString());

		Sequence seq5 = new Sequence();
		seq5.addAfter("A");
		seq5.addAfter("B");

		Sequence seq6 = new Sequence();
		seq6.addAfter("C");
		seq6.addAfter("D");

		Sequence concatenated3 = Sequence.concatenation(seq5, seq6);

		concatenated3.start();
		concatenated3.advance();
		concatenated3.advance();
		concatenated3.advance();
		concatenated3.removeCurrent();
		Testing.assertEquals(
				"modifyingConcatenationDoesntChangeSecondSequence",
				"{C, >D} (capacity = 10)", seq6.toString());
		seq6.start();
		seq6.removeCurrent();
		Testing.assertEquals(
				"modifyingSecondSequenceDoesntChangeConcatenation",
				"{A, B, C} (capacity = 20)", concatenated3.toString());

	}

	private static void testTrimToSize() {
		Testing.testSection("Testing trimToSize()");

		Sequence s1 = new Sequence();
		String[] letters = { "A", "B" };
		for (String s : letters)
			s1.addAfter(s);

		s1.trimToSize();
		Testing.assertEquals(
				"sequence has capacity for only the items it contains",
				"{A, >B} (capacity = 2)", s1.toString());
	}

	private static void testRemoveCurrent() {
		Sequence s1 = new Sequence();
		Testing.assertEquals("empty sequence, removeCurrent does nothing",
				"{} (capacity = 10)", s1.toString());

		String[] letters = { "A", "B", "C" };
		for (String s : letters)
			s1.addAfter(s);

		s1.trimToSize();
		s1.start();
		s1.advance();
		s1.removeCurrent();
		Testing.assertEquals("three element sequence remove element in middle",
				"{A, >C} (capacity = 3)", s1.toString());

		s1.removeCurrent();
		Testing.assertEquals("two element sequence current at end",
				"{A} (capacity = 3)", s1.toString());

		s1.addBefore("Z");
		s1.removeCurrent();
		Testing.assertEquals("two element sequence current at start",
				"{>A} (capacity = 3)", s1.toString());

	}

	private static void testSize() {
		Testing.testSection("Testing size()");
		Sequence s1 = new Sequence();

		Testing.assertEquals("size of an empty sequence", 0, s1.size());

		String[] letters = { "A", "B", "C", "D", "E", "F", "G" };
		for (String s : letters)
			s1.addAfter(s);

		Testing.assertEquals("size of a non-empty sequence", 7, s1.size());
	}

	private static void testPrinting() {
		Testing.testSection("Testing toString()");
		Sequence s1 = new Sequence();

		Testing.assertEquals("print-out of an empty sequence",
				"{} (capacity = 10)", s1.toString());

		String[] letters = { "A", "B", "C", "D", "E", "F", "G" };
		for (String s : letters)
			s1.addAfter(s);

		Testing.assertEquals("print-out of a non-empty sequence",
				"{A, B, C, D, E, F, >G} (capacity = 10)", s1.toString());

		s1.advance();
		Testing.assertEquals(
				"print-out of a non-empty sequence with no current element",
				"{A, B, C, D, E, F, G} (capacity = 10)", s1.toString());
	}
}
