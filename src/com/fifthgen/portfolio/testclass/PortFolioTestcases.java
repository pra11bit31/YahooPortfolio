package com.fifthgen.portfolio.testclass;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;
import org.junit.*;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import com.fifthgen.portfolio.PortFolioManager;
import static org.junit.Assert.*;

/**
 * @author Prady
 * 
 */
public class PortFolioTestcases {
	PortFolioManager nc = new PortFolioManager();
	Map<String, String> myCachedTestSymbols = new TreeMap<String, String>();
	Map<Double, String> myCachedTestSymbolsTest = new TreeMap<Double, String>();
	Map<Double, String> myCachedTestSymbolsTest1 = new HashMap<Double, String>();
	Map<String, Stock> stocks;

	@BeforeClass
	public static void init() {
		// Do one-time setup for all test cases
	} // init()

	@Before
	public void doSetup() {
		// Do setup before each test case
	} // setUp()

	@After
	public void doTearDown() {
		// Do tear down after each test case
	} // tearDown()

	@AfterClass
	public static void destroy() {
		// Do tear-down after all test cases are finished
	} // destroy()

	/* File Extension Check */
	@Test
	public void checkFileExtension() throws IOException {
		String a = "C:\\Users\\Prady\\Desktop\\input\\sample.txt";
		String c = nc.checkFileDetails(a);
		assertEquals("txt", c);
	}

	@Test
	public void checkFileExtensionCSV() throws IOException {
		String a = "C:\\Users\\Prady\\Desktop\\input\\sample.csv";
		String c = nc.checkFileDetails(a);
		assertEquals(null, c);
	}

	@Test
	public void checkFileExtensionDoc() throws IOException {
		String a = "C:\\Users\\Prady\\Desktop\\input\\sample.doc";
		String c = nc.checkFileDetails(a);
		assertEquals(null, c);
	}

	@Test
	public void checkFileExtensioncheck1() throws IOException {
		String a = "";
		String c = nc.checkFileDetails(a);
		assertEquals(null, c);
	}

	@Test(expected = Exception.class)
	public void checkFileExtensioncheck2() throws IOException {
		String a = null;
		String c = nc.checkFileDetails(a);
		assertEquals(null, c);
	}

	/* Symbol Validation Check */
	@Test
	public void checkSymbols() throws Exception {
		String c = "GOOG-10";
		String b[] = nc.getSymbolsValue(c);
		assertEquals("GOOG", b[0]);
		assertEquals("10", b[1]);
	}

	@Test(expected = NullPointerException.class)
	public void checkNullSymbols() throws Exception {
		String c = null;
		String b[] = nc.getSymbolsValue(c);
	}

	@Test
	public void checkStockSymbols() throws Exception {
		String c = "GOOG-10";
		boolean a = nc.getStockSymbol(c);
		assertEquals(true, a);
	}

	@Test(expected = Exception.class)
	public void checkStockSymbolsNullCheck() throws Exception {
		String c = null;
		boolean a = nc.getStockSymbol(c);
	}

	@Test(expected = Exception.class)
	public void checkStockSymbolscheck1() throws Exception {
		String c = "-10";
		boolean a = nc.getStockSymbol(c);
	}

	@Test(expected = Exception.class)
	public void checkStockSymbolscheck2() throws Exception {
		String c = "GOOG@10";
		boolean a = nc.getStockSymbol(c);
	}

	/* Pattern Validation Check */
	@Test
	public void validatePatternCheck1() throws IOException {
		String c = "GOOG-10";
		boolean b = nc.patternValidator(c);
		assertEquals(true, b);
	}

	@Test
	public void validateWrongCheck1() throws IOException {
		String c = "GOOG,10 ";
		boolean b = nc.patternValidator(c);
		assertEquals(false, b);
	}

	@Test
	public void validateWrongCheck2() throws IOException {
		String c = "GOOG@#$-10 ";
		boolean b = nc.patternValidator(c);
		assertEquals(false, b);
	}

	@Test
	public void validateWrongCheck3() throws IOException {
		String c = "GOOG-%^$10 ";
		boolean b = nc.patternValidator(c);
		assertEquals(false, b);
	}

	@Test
	public void validateWrongCheck4() throws IOException {
		String c = "GOOG%10 ";
		boolean b = nc.patternValidator(c);
		assertEquals(false, b);
	}

	@Test(expected = Exception.class)
	public void validateNullCheck4() throws IOException {
		String c = null;
		boolean b = nc.patternValidator(c);
	}

	/* Fetching CSV from From YahooAPI using Symbols */
	@Test
	public void getDetailsFromYahooAPI() throws Exception {
		myCachedTestSymbols.put("GOOG", "GOOG");
		boolean b = nc.getDetailsFromYahooAPI(myCachedTestSymbols);
		assertEquals(true, b);
	}

	@Test(expected = Exception.class)
	public void getDetailsFromYahooAPINullCheck() throws Exception {
		myCachedTestSymbols.put(null, null);
		boolean b = nc.getDetailsFromYahooAPI(myCachedTestSymbols);
	}

	public void getDetailsFromYahooAPINullCheck1() throws Exception {
		myCachedTestSymbols.put("GOOG", null);
		boolean b = nc.getDetailsFromYahooAPI(myCachedTestSymbols);
		assertEquals(true, b);

	}

	@Test(expected = Exception.class)
	public void getDetailsFromYahooAPINullCheck2() throws Exception {
		myCachedTestSymbols.put(null, "10");
		boolean b = nc.getDetailsFromYahooAPI(myCachedTestSymbols);
	}

	/* Fetching Price From YahooAPI Validation */
	@Test
	public void getTotalPriceForStock() throws Exception {
		// myCachedTestSymbols.put("GOOG", "GOOG");
		boolean b = nc.getDetailsFromYahooAPI(myCachedTestSymbols);
		if (b == true) {
			String v = "GOOG-10";
			double c = nc.getTotalPriceForStock(v);
			assertTrue(c != 0.0);
		}
	}

	@Test
	public void getvalueForStock() throws Exception {
		myCachedTestSymbols.put("GOOG", "GOOG");
		boolean b = nc.getDetailsFromYahooAPI(myCachedTestSymbols);
		String v = "GOOG-10";
		double c = nc.getTotalPriceForStock(v);
		assertTrue(c != 0.0);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void getvalueForStockcheck1() throws Exception {
		double res1 = checkPriceInternal();
		myCachedTestSymbols.put("GOOG", "GOOG");
		boolean b = nc.getDetailsFromYahooAPI(myCachedTestSymbols);
		String v = "GOOG-10";
		double c = nc.getTotalPriceForStock(v);
		assertEquals(res1 * 10, c, 0.0);
	}

	@Test(expected = Exception.class)
	public void getTotalPriceForStock1() throws Exception {
		String v = "GOOG-0";
		double c = nc.getTotalPriceForStock(v);
	}

	@Test(expected = Exception.class)
	public void getTotalPriceForStock2() throws Exception {
		String v = "0-10";
		double c = nc.getTotalPriceForStock(v);
	}

	@Test(expected = Exception.class)
	public void getTotalPriceForStockException() throws Exception {
		String c = "JAVA-10";
		double b = nc.getTotalPriceForStock(c);
	}

	@Test(expected = Exception.class)
	public void getTotalPriceForStockExceptionNullCheck() throws Exception {
		String c = null;
		double b = nc.getTotalPriceForStock(c);
	}

	@Test
	public void testDescendingLogic() throws Exception {
		double key;
		String value;
		myCachedTestSymbolsTest.put(1020.0, "GOOG-10,AMZN-1");
		myCachedTestSymbolsTest.put(10202.0, "GOOG-100,AMZN-1");
		NavigableMap<Double, String> nset = ((TreeMap<Double, String>) myCachedTestSymbolsTest)
				.descendingMap();
		Set keys = nset.keySet();
		Iterator itr = keys.iterator();
		System.out.println("The OutPut is:");
		PrintWriter out = new PrintWriter(System.out);
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		while (itr.hasNext()) {
			key = (Double) itr.next();
			value = (String) nset.get(key);
			myCachedTestSymbolsTest1.put(key, value);
		}
		String a = (String) myCachedTestSymbolsTest1.keySet().toArray(
				new Object[myCachedTestSymbolsTest1.size()])[0].toString();
		double b = Double.parseDouble(a);
		assertEquals(10202.0, b, 0.0);
	}
	
	@Test
	public void testDescendingLogiccheck1() throws Exception {
		double key;
		String value;
		myCachedTestSymbolsTest.put(1020.0, "GOOG-10,AMZN-1");
		myCachedTestSymbolsTest.put(10202.0, "GOOG-100,AMZN-1");
		NavigableMap<Double, String> nset = ((TreeMap<Double, String>) myCachedTestSymbolsTest)
				.descendingMap();
		Set keys = nset.keySet();
		Iterator itr = keys.iterator();
		System.out.println("The OutPut is:");
		PrintWriter out = new PrintWriter(System.out);
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		while (itr.hasNext()) {
			key = (Double) itr.next();
			value = (String) nset.get(key);
			myCachedTestSymbolsTest1.put(key, value);
		}
		String a = (String) myCachedTestSymbolsTest1.keySet().toArray(
				new Object[myCachedTestSymbolsTest1.size()])[1].toString();
		double b = Double.parseDouble(a);
		assertEquals(1020.0, b, 0.0);
	}

	/* Test Cases with Sample Files */
	@Test
	public void checkResultWithFile1() throws Exception {
		String c = "J:\\YahooPortfolio-master\\portfolio jar\\sample1.txt";
		boolean b = nc.getPortfolioDetails(c);
		assertEquals(true, b);
	}

	@Test(expected = Exception.class)
	public void checkResultWithFile2() throws Exception {
		String c = "J:\\YahooPortfolio-master\\portfolio jar\\badSample1.txt";
		boolean b = nc.getPortfolioDetails(c);
	}

	@Test
	public void checkResultWithFile3() throws Exception {
		String c = "J:\\YahooPortfolio-master\\portfolio jar\\badSample2.txt";
		boolean b = nc.getPortfolioDetails(c);
		assertEquals(false, b);
	}

	@Test
	public void checkResultWithBlankFile() throws Exception {
		String c = "J:\\YahooPortfolio-master\\portfolio jar\\emptysample.txt";
		boolean b = nc.getPortfolioDetails(c);
		assertEquals(false, b);
	}

	@Test
	public void checkResultWithWrongDirctory() throws Exception {
		String c = "XYZJSSJSJSJ";
		boolean b = nc.checkFileIntegrity(c);
		assertEquals(false, b);
	}

	public double checkPriceInternal() throws Exception {
		myCachedTestSymbols.put("GOOG", "GOOG");
		String[] result = myCachedTestSymbols.values().toArray(new String[0]);
		stocks = YahooFinance.get(result);
		Stock intel = stocks.get("GOOG");
		BigDecimal a3 = intel.getQuote().getPrice();
		double res1 = a3.doubleValue();
		return res1;
	}

}