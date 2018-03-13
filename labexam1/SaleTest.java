package labexam1;

import static org.junit.Assert.*;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test of the Sale class.
 * @author jim
 */
public class SaleTest {
	private static final double TOL = 1.0E-6;
	Product p1, p2, p3;
	Sale sale;
	
	@Before
	public void setUp() throws Exception {
		//System.out.println("running setup");
		p1 = new Product(1000,"Product 1", 100.0);
		p2 = new Product(1001,"Product 2", 0.50);
		p3 = new Product(1002,"Product 2", 200.0); // same name as p2
		sale = new Sale();
	}

	@Test
	public void testSaleConstructor() {
		List<LineItem> items = sale.getLineItems();
		assertNotNull("didn't create List<LineItem>", items);
		assertEquals(0, items.size());
		Sale sale2 = new Sale();
		sale2.addItem(p1, 3);
		// should not affect original sale
		assertEquals(0, sale.getLineItems().size());
	}

	/** Simple test of sale. No duplicate items, no removals. */
	@Test
	public void testSimpleSale() {
		sale.addItem(p1, 10);
		List<LineItem> items = sale.getLineItems();
		assertEquals(1, items.size());
		assertSame(p1, items.get(0).getProduct());
		assertEquals(10, items.get(0).getQuantity());
		sale.addItem(p2, 20);
		sale.addItem(p3, 30);
		items = sale.getLineItems();
		assertEquals(3, items.size());
		// total
		double total = 10*p1.getUnitPrice() + 20*p2.getUnitPrice() + 30*p3.getUnitPrice();
		double actual = sale.getTotal();
		assertEquals(total, actual, TOL);	
	}
	
	/** Add sale item multiple times. */
	@Test
	public void testAddDuplicateItem() {
		sale.addItem(p1, 1);
		List<LineItem> items = sale.getLineItems();
		assertEquals(1, items.size());
		assertSame(p1, items.get(0).getProduct());
		sale.addItem(p2, 5);
		sale.addItem(p1, 5);
		sale.addItem(p2, 4);
		items = sale.getLineItems();
		assertEquals(2, items.size());
		assertEquals(6, items.get(0).getQuantity());
		assertEquals(9, items.get(1).getQuantity());
	}
	
	/** Remove some quantity of items, but not all. */
	@Test
	public void testReduceItem() {
		long id1 = p1.getProductId();
		long id2 = p2.getProductId();
		sale.addItem(p1, 4);
		sale.addItem(p2, 2);
		sale.addItem(p3, 5);
		sale.removeItem(id1, 2);
		LineItem item = findItem(sale.getLineItems(), p1);
		assertNotNull("Should not remove LineItem for p1", item);
		assertEquals("Should remove 2 of 4 units", 2, item.getQuantity());
		
		// remove 1 unit of p2
		sale.removeItem(id2, 1);
		LineItem item2 = findItem(sale.getLineItems(), p2);
		assertNotNull("Should not remove LineItem for p2", item2);
		assertEquals("Should remove 1 of 2 units", 1, item2.getQuantity());
	}
	
	/** Remove all units of an item. */
	@Test
	public void testRemoveItem() {
		long id1 = p1.getProductId();
		sale.addItem(p1, 2);
		sale.addItem(p2, 1);
		sale.addItem(p3, 5);
		
		sale.removeItem(id1, 2);
		LineItem item = findItem(sale.getLineItems(), p1);
		assertNull("Should remove entire LineItem for p1", item);
		
		// Remove 10 units of p2 (more than quantity in sale)
		// It should remove all the units of p2.
		sale.removeItem(p2.getProductId(), 10);
		// There should not be a LineItem for p2.
		item = findItem(sale.getLineItems(), p2);
		assertNull("Should remove entire LineItem for p2", item);
	}

	@Test
	public void testGetTotal() {
		double price1 = p1.getUnitPrice();
		double price2 = p2.getUnitPrice();
		double price3 = p3.getUnitPrice();
		assertEquals(0.0, sale.getTotal(), 0.0);
		sale.addItem(p1, 1);
		assertEquals( price1, sale.getTotal(), TOL);
		sale.addItem(p2, 2);
		sale.addItem(p2, 8);
		assertEquals( price1 + 10*price2, sale.getTotal(), TOL);
		sale.addItem(p3, 1);
		sale.addItem(p3, 4);
		assertEquals( price1 + 10*price2 + 5*price3, sale.getTotal(), TOL);
		sale.addItem(p1, 1);
		assertEquals( 2*price1 + 10*price2 + 5*price3, sale.getTotal(), TOL);
	}
	
	/** 
	 * Find a product p in a List of LineItems.
	 * @param items list of LineItems
	 * @param p the product to find
	 * @return LineItem for product p, or null if not in items
	 */
	private LineItem findItem(final List<LineItem> items, final Product p) {
		Optional<LineItem> item = items.stream().filter( x -> x.getProduct() == p).findFirst();
		return item.isPresent() ? item.get() : null;
	}
}
