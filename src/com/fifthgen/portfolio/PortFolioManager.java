package com.fifthgen.portfolio;
import static org.junit.Assert.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
import java.math.BigDecimal;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import org.junit.*;
/**
 * @author Prady
 *
 */
public class PortFolioManager {
	Map<String, Stock> stocks;
	Map<String, String> myCachedSymbols = new TreeMap<String, String>();
	Map<Double, String> myCachedTreeMap = new TreeMap<Double, String>();
	BufferedReader inputStream = null;
	String content = "";
	Double key;
	String value;
	//String FileName = "C:\\Users\\Prady\\Desktop\\input\\sample.txt";
	static String FileName;


	/* Method which parses the File */
	@Test
	public void getPortfolioDetails() throws IOException {
		
		File textFile = new File(FileName);
		String extcheck = FileName.substring(FileName.lastIndexOf(".") + 1);
		assertEquals("txt", extcheck);
		if (textFile.isFile() && textFile.getName().endsWith(".txt")) {
			try {
				inputStream = new BufferedReader(new FileReader(textFile));
				String line;
				while ((line = inputStream.readLine()) != null) {
					System.out.println(line);
					List<String> a = Arrays.asList(line.split(","));
					for (int i = 0; i < a.size(); i++) {
						content = a.get(i);
						getStockSymbol(content);
					}
				}
				boolean a = getDetailsFromYahooAPI(myCachedSymbols);
				assertEquals(true, a);
				if (a == true) {
					fetchDataOutput(FileName);
				}
			} finally {
				if (inputStream != null) {
					inputStream.close();
				}
			}
		}

	}

	/*
	 * to get the stock symbols and total numbers from each stock from the value
	 * passed from the file and loads it into map
	 */
	public void getStockSymbol(String pattern) throws IOException {
		pattern = patternValidator(pattern);
		assertFalse(pattern == null);
		String b[] = pattern.split("-");
		String symbol = b[0];
		String val = b[1];
		assertFalse(symbol == null);
		assertFalse(val == "0");
		myCachedSymbols.put(b[0], symbol);
	}

	/*
	 * to get the stock symbols and total numbers from each stock from the value
	 * passed from the file and loads it into map
	 */
	public double getTotalPriceForStock(String pattern) throws IOException {
		pattern = patternValidator(pattern);
		assertFalse(pattern == null);
		String b[] = pattern.split("-");
		String symbol = b[0];
		String val = b[1];
		double value = Double.parseDouble(val);
		assertFalse(value == 0);
		Stock intel = stocks.get(symbol);
		BigDecimal a3 = intel.getQuote().getPrice();
		double res1 = a3.doubleValue();
		assertNotNull(res1);
		assertNotNull(value);
		double result = res1 * value;
		return result;
	}

	/* to check the pattern validation */
	public String patternValidator(String pattern) {
		pattern = pattern.replaceAll("\\s", "");
		Pattern p = Pattern.compile("[a-zA-Z]+[-]+[0-9]+");
		Matcher m = p.matcher(pattern);
		boolean res = m.matches();
		assertEquals(true, res);
		if (res == true) {
			return pattern;
		}
		return null;
	}

	/* to get the details of symbols and price from yahoo finance API */
	public boolean getDetailsFromYahooAPI(Map<String, String> myCachedSymbols) {
		try {
			String[] result = myCachedSymbols.values().toArray(new String[0]);
			assertFalse(result == null);
			stocks = YahooFinance.get(result);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/*
	 * to fetch the total value of each portfolio and arrange it in descending
	 * order
	 */
	public boolean fetchDataOutput(String Filename) throws IOException {
		try {
			
			File textFile = new File(FileName);
			inputStream = new BufferedReader(new FileReader(textFile));
			String line;
			while ((line = inputStream.readLine()) != null) {
				double finaltotal = 0.0;
				List<String> a = Arrays.asList(line.split(","));
				for (int i = 0; i < a.size(); i++) {
					content = a.get(i);
					finaltotal += getTotalPriceForStock(content);
				}
				assertFalse(finaltotal == 0.0);
				myCachedTreeMap.put(finaltotal, line);
			}
			NavigableMap<Double, String> nset = ((TreeMap<Double, String>) myCachedTreeMap)
					.descendingMap();
			Set keys = nset.keySet();
			Iterator itr = keys.iterator();
			while (itr.hasNext()) {
				key = (Double) itr.next();
				value = (String) nset.get(key);
				assertNotNull(key);
				assertNotNull(value);
				System.out.println(value);
			}
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
		return false;
	}
	/*
	 * to set the file name from string args
	 * 
	 */
	public void setFileName(String a) {
		FileName = a;
		System.out.println(a);
	}

}