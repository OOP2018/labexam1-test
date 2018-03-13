package labexam1;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TwoForOneTest {
	private static final double TOL = 1.0E-6;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testTwoForOnePrice() {
		double unitPrice = 99.0;
		Product p1 = new Product(1001,"Product 1", 99.0);
		LineItem item = new TwoForOneItem(p1, 1);
		assertEquals("Buy 1", unitPrice, item.getTotal(), TOL);
		
		item.setQuantity(2);
		assertEquals("Buy 2 pay 1", unitPrice, item.getTotal(), TOL);
		item.setQuantity(4);
		assertEquals("Buy 4 pay 2", 2*unitPrice, item.getTotal(), TOL);
		item.setQuantity(3);
		assertEquals("Buy 3 pay 2", 2*unitPrice, item.getTotal(), TOL);
		item.setQuantity(10);
		assertEquals("Buy 10 pay 5", 5*unitPrice, item.getTotal(), TOL);
		item.setQuantity(9);
		assertEquals("Buy 9 pay 5", 5*unitPrice, item.getTotal(), TOL);
	}
	

	@Test
	public void testGetDescription() {
		Product p1 = new Product(1001,"Product 1", 99.0);
		LineItem item = new TwoForOneItem(p1, 5);
		String desc = item.getDescription();
		
		// Common error.  Students don't pay attention to space in ANYTHING!
		if ("Product 1(2-for-1)".equals(desc)) {
			System.out.println("FORGOT SPACE: TwoForOneItem.getDescription() = \""+desc+"\"");
		}
		// Should use this test:
		// assertEquals("Product 1 (2-for-1)", item.getDescription());
		// but 50% of student codes fail!
		
		// More lenient test for exam, tolerate missing space or too much space:
		assertNotNull("getDescription()="+desc, desc);
		assertTrue("getDescription()="+desc, desc.matches("Product 1\\s*\\(2-for-1\\)") );
	}

}
