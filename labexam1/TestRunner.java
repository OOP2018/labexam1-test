package labexam1;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.print.DocFlavor.INPUT_STREAM;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import labexam1.Product;
import labexam1.Receipt;
import labexam1.Sale;
import labexam1.Store;
/**
 * Run JUnit test suites and print results on console.
 * You can run JUnit tests in an IDE without using this class.
 * For running on console, this TestRunner prints messages
 * about failed tests that are easier to read than JUnit output.
 * 
 * Run this class as an ordinary Java application (using main).
 * 
 * @author James Brucker
 *
 */
public class TestRunner {
	static final java.util.Scanner console = new java.util.Scanner(System.in);
	
	/**
	 * Run the JUnit tests and display results.
	 * @param args: "u" unit tests, "p" printReceipt, "m" run Main. No args: do everything.
	 */
	public static void main(String[] args) {
		Arrays.sort(args);
		boolean runAll = (args.length == 0);
		boolean printReceipt = runAll || Arrays.binarySearch(args,"p") >= 0;
		boolean unitTests = runAll || Arrays.binarySearch(args, "u") >= 0;
		boolean runMain = runAll || Arrays.binarySearch(args, "m") >= 0;
		if (unitTests) {
			System.out.println(">>> Run JUnit Tests");
			int failures = runTestSuite( LineItemTest.class );
			failures += runTestSuite( SaleTest.class );
			failures += runTestSuite( TwoForOneTest.class );
			failures += runTestSuite( SaleWithDiscount.class );
			//System.out.println("Failures: "+failures);
		}
		
		if (printReceipt) {
			System.out.println("\n>>> Test Receipt.printReceipt(sale).");
			System.out.println(">>> Items should be sorted by product id, aligned in columns, \n"
					         + ">>> have a readable date, and the total price of sale.");
			pause("Press ENTER to invoke Receipt.printReceipt(sale): ");
			// return code indicates success or failure
			try {
				testPrintReceipt();
			} catch (Exception ex) {
				System.out.println("Test printReceipt threw exception");
				System.out.println(ex.getMessage());
				ex.printStackTrace();
			}
		}
		
		if (runMain) {
			System.out.println("\n>>> Run Main class to make a Sale. InputStream will contain this:");
			final String SAMPLE_INPUT = "110 \n101 \n102 \n112 \n102 4 \n110 9 \ne \n";
			System.out.println("===========\n" + SAMPLE_INPUT + "===========");
			// Compute the correct total
			Store store = Store.getInstance();
			double total =   store.getProduct(101).getUnitPrice()*1
					       + store.getProduct(102).getUnitPrice()*3  // 5 = buy 3 get 2 free
					       + store.getProduct(110).getUnitPrice()*10 // has 10 of product 110
					       + store.getProduct(112).getUnitPrice();
			System.out.printf("Total should be: %,.2f\n\n", total);
			pause("ENTER to run Main: ");
			System.setIn( makeInputStream(SAMPLE_INPUT) );
			labexam1.Main.main(args);
		}
	}
	
	/** InputStream for running student Main (Register). */
	@SuppressWarnings("deprecation")
	public static InputStream makeInputStream(final String inputValues) {

		return new StringBufferInputStream(inputValues);
	}
	
	/** Create a sale with known content, call PrintReceipt. */
	public static void testPrintReceipt() {
		final long TWOFORONE_ID = 102;
		Store store = Store.getInstance();
		addProductsToStore(store);
		Sale sale = new Sale();
		int quantity = 1;
		double total = 0.0;
		double discount = 0.0;
		// add products to sale, not in order by id
		for(PRODUCTS px: PRODUCTS.values()) {
			quantity = (int) px.id % 12;
			if (quantity == 0) continue;
			Product product = px.asProduct();
			sale.addItem(product, quantity);
			total += quantity*product.getUnitPrice();
			if (px.id == TWOFORONE_ID) 
				discount = (quantity - quantity/2)*product.getUnitPrice();
			quantity++;
		}
		System.out.printf("Total without discount: %,10.2f\n",total);
		System.out.printf("Total with 2-for-1 dis: %,10.2f\n\n",total-discount);

		Receipt.printReceipt(sale);
	}
	
	/** Print a prompt and wait for user to press enter. */
	public static void pause(String prompt) {
		System.out.print(prompt);
		console.nextLine();
	}
	
	/**
	 * Run a JUnit test suite and print summary of results.
	 * @param clazz class object for the JUnit test suite.
	 * @return number of failed tests
	 */
	public static int runTestSuite( Class<?> clazz ) {
		// Print class name first in case it crashes
		String name = clazz.getSimpleName();
		System.out.println("Running "+name);
		Result result = org.junit.runner.JUnitCore.runClasses( clazz );
		int count = result.getRunCount();
		int failed = result.getFailureCount();
		int success = count - failed;
		System.out.printf("%-16.16s   Success: %d  Failures: %d\n",
				clazz.getSimpleName(), success, failed);
		
		// this sometimes doesn't seem to return all the failures
		List<Failure> failures = result.getFailures();
		for(Failure f: failures) {
			Description d = f.getDescription();			
			System.out.println( f.getTestHeader() +": "+ f.getMessage() );
			System.out.println( d.getDisplayName() );
		}
		// return the number of failures
		return failed;
	}
	
	/** Add some test products to Store. */
	public static void addProductsToStore(Store store) {
		// remove any old products
		store.getProducts().clear();
		
		for (PRODUCTS p: PRODUCTS.values()) {
			store.addProduct( p.asProduct() );
		}
	}
	
	/** 
	 * Some test products. Oishi Green Tea is same as starter code,
	 * but others may be different.  Randomize the order.
	 */
	enum PRODUCTS {
		/* parameters: item_id, description, price, Category */
	    WATER(101, "Kaset Clear Drinking Water", 9.0),
	    APPLE(110, "Red Medium Apple", 10),
	    OISHI(102, "Oishi Green Tea", 16.0),
	    BANANACAKE(111, "Banana Cake, 250g", 30),
	    PEANUTS(112, "Peanuts, 45g. pack", 10),
	    COKEZERO(104, "Coke Zero", 15),
	    ICECOFFEE(107,"Ice Coffee", 30.0),
	    SOYMILK(105, "Vitasoy Soy Milk", 10.50),
	    COFFEE(106, "Hot Coffee", 25.0),
	    BANANA(113, "Banana", 5);
	    
	    
	    private PRODUCTS(long id, String desc, double price ) {
	        this.id = id;
	        this.description = desc;
	        this.unitPrice = price;
	        // convenience
	        this.product = new Product(id, description, price);
	    }
	    public final long id;
	    public final String description;
	    public final double unitPrice;
	    private final Product product;
	    
	    public Product asProduct() { return product; }
	}
}
