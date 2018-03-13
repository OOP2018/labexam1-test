package labexam1;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SaleWithDiscount {

	private static final double TOL = 1.0E-4;


	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void testLineItemFactory() {
		final double unitPrice = 16.0;
		Product p1 = new Product(1000, "Generic Product", 99.0);
		LineItem item1 = LineItemFactory.createLineItem(p1);
		assertTrue( item1.getClass() == LineItem.class );

		Product p2 = new Product(102L, "Oishi Green Tea", unitPrice);
		LineItem item2 = LineItemFactory.createLineItem(p2);
		assertTrue( item2.getClass() == TwoForOneItem.class );
		assertEquals(0, item2.getQuantity() );
	}

	@Test
	public void testSaleWithTwoForOneItem() {
		final long id = 102;  // Oishi Green Tea
		final double unitPrice = 16.0;
		Store store = Store.getInstance();
		store.loadProductData();
		Sale sale = new Sale();
		Product pd = store.getProduct(id);
		assertEquals("Price of Oishi is Wrong", unitPrice, pd.getUnitPrice(), TOL);
		
		sale.addItem(pd, 1);
//		LineItem item = sale.getLineItems().get(0);
//		assertTrue(item instanceof TwoForOneItem);
		assertEquals( "Sale: buy 1 pay 1", unitPrice, sale.getTotal(), TOL);
		sale.addItem(pd, 1);
		assertEquals( "Sale: buy 2 pay 1", unitPrice, sale.getTotal(), TOL);
		sale.addItem(pd, 4);
		assertEquals( "Sale: buy 6 pay 3", 3*unitPrice, sale.getTotal(), TOL);
	}

}
