package com.fifthgen.portfolio;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
import java.math.BigDecimal;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

/**
 * @author Prady
 * 
 */
public class PortFolioManager {
	Map<String, Stock> stocks;
	Map<String, String> myCachedSymbols = new TreeMap<String, String>();
	Map<Double, String> myCachedTreeMap = new TreeMap<Double, String>();
	Map<Double, String> myCachedOutput = new TreeMap<Double, String>();
	BufferedReader inputStream = null;
	String content = "";
	Double key;
	String value;

	/* Method which parses the File */
	public String checkFileDetails(String FileName) throws IOException {
		String extcheck = FileName.substring(FileName.lastIndexOf(".") + 1);
		if (extcheck.equals("txt")) {
			return extcheck;
		}
		return null;
	}

	public boolean checkFileIntegrity(String FileName) {
		try {
			File textFile = new File(FileName);
			if (textFile.isFile() && textFile.getName().endsWith(".txt")) {
				inputStream = new BufferedReader(new FileReader(textFile));
				String line;
				if ((line = inputStream.readLine()) != null) {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean getPortfolioDetails(String FileName) throws Exception {
		File textFile = new File(FileName);
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
				if (a == true) {
					boolean finres = fetchDataOutput(FileName);
					if (finres == true) {
						return true;
					} else {
						return false;
					}
				}
			} finally {
				if (inputStream != null) {
					inputStream.close();
				}
			}

		}
		return false;

	}

	/*
	 * * to get the stock symbols and total numbers from each stock from the
	 * value passed from the file and loads it into map
	 */
	public boolean getStockSymbol(String pattern) throws Exception {
		boolean a = patternValidator(pattern);
		if (a == true) {
			String b[] = getSymbolsValue(pattern);
			myCachedSymbols.put(b[1], b[0]);
			return true;
		} else {
			throw new Exception();
		}
	}

	public String[] getSymbolsValue(String pattern) throws Exception {
		String b[] = pattern.split("-");
		return b;
	}

	public double getTotalPriceForStock(String pattern) throws Exception {
		pattern = pattern.replaceAll("\\s", "");
		String b[] = getSymbolsValue(pattern);
		String symbol = b[0];
		String val = b[1];
		if (val.equals("0") || val.contains("-") || val.equals(null)) {
			System.out.println("Invalid Amount");
			throw new Exception();
		} else {
			double value = Double.parseDouble(val);
			Stock intel = stocks.get(symbol);
			if (intel == null) {
				System.out.println("Invalid Symbol:" + symbol);
			}
			BigDecimal a3 = intel.getQuote().getPrice();
			double res1 = a3.doubleValue();
			if (res1 != 0.0) {
				double result = res1 * value;
				return result;
			} else {
				System.out.println("Invalid Amount");
				throw new Exception();
			}
		}
	}

	/* to check the pattern validation */
	public boolean patternValidator(String pattern) {
		pattern = pattern.replaceAll("\\s", "");
		Pattern p = Pattern.compile("[a-zA-Z]+[-]+[0-9]+");
		Matcher m = p.matcher(pattern);
		boolean res = m.matches();
		if (res == true) {
			return true;
		}
		return false;
	}

	/* to get the details of symbols and price from yahoo finance API */
	public boolean getDetailsFromYahooAPI(Map<String, String> myCachedSymbols)
			throws Exception {
		try {
			String[] result = myCachedSymbols.values().toArray(new String[0]);
			stocks = YahooFinance.get(result);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * to fetch the total value of each portfolio and arrange it in descending
	 * order
	 */
	public boolean fetchDataOutput(String FileName) throws Exception {
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
				myCachedTreeMap.put(finaltotal, line);
			}
			NavigableMap<Double, String> nset = ((TreeMap<Double, String>) myCachedTreeMap)
					.descendingMap();
			Set keys = nset.keySet();
			Iterator itr = keys.iterator();
			System.out.println("The OutPut is:");
			PrintWriter out = new PrintWriter(System.out);

			while (itr.hasNext()) {
				key = (Double) itr.next();
				value = (String) nset.get(key);
				out.println(value);
				out.flush();
			}
		} catch (Exception e) {
			return false;
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
		return true;
	}
}