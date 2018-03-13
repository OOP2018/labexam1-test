package labexam1;

import static org.junit.Assert.*;

import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

/** JUnit tests of LineItem class. */
public class LineItemTest {
	private static final double TOL = 1.0E-4;
	Product p1, p2;
	
	@Before
	public void setUp() throws Exception {
		p1 = new Product(1000,"Product 1", 100.0);
		p2 = new Product(1001,"Product 2", 0.50);
	}


	@Test
	public void testLineItemConstructor() {
		LineItem item1 = new LineItem(p1, 1);
		LineItem item2 = new LineItem(p2, 400000);
		//  verify attributes not static
		assertSame("Is product static?", p1, item1.getProduct());
		assertEquals("Is quantity static?", 1, item1.getQuantity());
	}
	
	@Test
	public void testLineItemConstructorThrowsException() {
		boolean throwsException = false;
		try {
			LineItem item = new LineItem(null, 4);
		} catch (IllegalArgumentException ex1) {
			throwsException = true;
		}
		assertTrue("Should throw IllegalArgumentException if product=null", throwsException);
		throwsException = false;
		try {
			LineItem item = new LineItem(p1, -1);
		} catch (IllegalArgumentException ex2) {
			throwsException = true;
		}
		assertTrue("Should throw IllegalArgumentException if quantity<0", throwsException);
		// Test that constructor does not ALWAYS throw exception.
		// This should not throw exception.
		LineItem item = new LineItem(p1, 0);
	}

	@Test
	public void testGetTotal() {
		LineItem item1 = new LineItem(p1, 1);
		LineItem item2 = new LineItem(p2, 20); // test for static attributes
		assertEquals(p1.getUnitPrice(), item1.getTotal(), TOL);
		item1.setQuantity(3);
		assertEquals(3*p1.getUnitPrice(), item1.getTotal(), TOL);
		item1.setQuantity(0);
		assertEquals(0.0, item1.getTotal(), TOL);
		// changes to first LineItem do not modify the other LineItem
		assertEquals(20*p2.getUnitPrice(), item2.getTotal(), TOL);
	}

	@Test
	public void testGetSetQuantity() {
		LineItem item1 = new LineItem(p1, 7);
		LineItem item2 = new LineItem(p2, 1); // test for static attributes
		assertEquals(7, item1.getQuantity());
		item1.setQuantity(2000);
		assertEquals(2000, item1.getQuantity());
		item1.setQuantity(0);
		assertEquals(0, item1.getQuantity());
		// changes to first LineItem not modify the other LineItem
		assertEquals(1, item2.getQuantity());
	}

	@Test
	public void testGetDescription() {
		LineItem item1 = new LineItem(p1, 7);
		LineItem item2 = new LineItem(p2, 1);
		assertEquals(p1.getDescription(), item1.getDescription());
		assertEquals(p2.getDescription(), item2.getDescription());
	}

	@Test
	public void testCompareTo() {
		LineItem item1 = new LineItem(p1, 200);
		LineItem item2 = new LineItem(p2, 11);
		assertTrue( "item1.compareTo(item2)<0", item1.compareTo(item2)<0 );
		assertTrue( "item2.compareTo(item1)>0", item2.compareTo(item1)>0 );
		assertEquals( "item1.compareTo(item1)=0", 0, item1.compareTo(item1) );
		assertEquals( "item2.compareTo(item2)=0", 0, item2.compareTo(item2) );
	}

	/** Should implement Comparable<LineItem>.
	 *  implements Comparable<?> not good enough.
	 */
	@Test
	public void testImplementsComparable() {
		LineItem item1 = new LineItem(p1, 200);
		assertTrue( "LineItem should implement Comparable", item1 instanceof java.lang.Comparable );
		// This is to test if it is Comparable<LineItem> or Comparable<SomethingElse>
		try {
			Method method = item1.getClass().getDeclaredMethod("compareTo", LineItem.class);
		} catch (NoSuchMethodException e) {
			fail("LineItem does not have compareTo(LineItem)");
		} catch (SecurityException e) {
			// fat chance
		}
	}
}
