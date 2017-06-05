package com.fifthgen.portfolio;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * @author Prady
 * 
 */
public class PortfolioMain {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String a = args[0];
		PortFolioManager yahoo = new PortFolioManager();
		yahoo.setFileName(a);
		Result result = JUnitCore.runClasses(PortFolioManager.class);
		for (Failure failure : result.getFailures()) {
			System.out.println("The Test is Failed:" + failure.toString());
		}
		System.out.println("The Test is Success:" + result.wasSuccessful());
	}
}
