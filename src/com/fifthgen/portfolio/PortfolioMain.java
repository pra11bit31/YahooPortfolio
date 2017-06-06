package com.fifthgen.portfolio;

import java.io.IOException;

/**
 * @author Prady
 * 
 */
public class PortfolioMain {
	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		PortFolioManager yahoo = new PortFolioManager();
		String filename = args[0];
		String a = yahoo.checkFileDetails(filename);
		if (a.equals("txt")) {
			try {
				boolean c = yahoo.checkFileIntegrity(filename);
				if (c == true) {
					yahoo.getPortfolioDetails(filename);
				} else {
					System.out.println("Empty File");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
