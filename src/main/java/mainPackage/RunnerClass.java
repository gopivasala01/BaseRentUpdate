package mainPackage;

import java.util.ArrayList;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RunnerClass {
	public static String[][] pendingLeases;
	public static ChromeDriver driver;
	public static String downloadFilePath;
	public static Actions actions;
	public static JavascriptExecutor js;
	public static WebDriverWait wait;

	public static String failedReason;

	public static String ID;
	public static String company;
	public static String leaseEntityID;
	public static String dateDifference;
	public static String moveInDate;

	public static ArrayList<String> autoChargeCodes;
	public static ArrayList<String> autoChargeAmounts;
	public static ArrayList<String> autoChargeStartDate;
	public static ArrayList<String> autoChargeDescription;

	public static String[][] completedLeasesList;
	public static String portfolioType = "";
	public static String baseRentAmount = "";
	public static String baseRentFromPW = "";
	public static String previousRecordCompany;
	public static String portfolioName = "";
	public static boolean loggedOut = false;

	public static void main(String args[]) {
		// Get Pending Leases
		DataBase.getLeasesList(AppConfig.pendingLeasesQuery);

		// Initial Browser
		PropertyWare.initiateBrowser();

		// Login to PW
		PropertyWare.signIn();
		// Loop over leases
		for (int i = 0; i < pendingLeases.length; i++) {
			try {
				try {
					String expiredURL = RunnerClass.driver.getCurrentUrl();
					if(expiredURL.contains("https://app.propertyware.com/pw/expired.jsp") || expiredURL.equalsIgnoreCase("https://app.propertyware.com/pw/expired.jsp?cookie") || expiredURL.contains(AppConfig.URL)) {
						loggedOut = true;
						RunnerClass.driver.navigate().to(AppConfig.URL);
						RunnerClass.driver.findElement(Locators.userName).sendKeys(AppConfig.username); 
					    RunnerClass.driver.findElement(Locators.password).sendKeys(AppConfig.password);
					    Thread.sleep(2000);
					    RunnerClass.driver.findElement(Locators.signMeIn).click();
					    Thread.sleep(3000);
					}
				}
				catch(Exception e) {}
				ID = pendingLeases[i][0];
				company = pendingLeases[i][1];
				leaseEntityID = pendingLeases[i][2];
				dateDifference = pendingLeases[i][3];
				moveInDate = pendingLeases[i][4].split(" ")[0].trim();
				autoChargeCodes = new ArrayList();
				autoChargeAmounts = new ArrayList();
				autoChargeStartDate = new ArrayList();
				autoChargeDescription = new ArrayList();
				portfolioType = "";
				baseRentAmount = "";
				baseRentFromPW = "";
				failedReason = "";
				portfolioName = "";
				if(company.equalsIgnoreCase("Chicago PFW")) {
					   company = "Chicago";
				  }
				if(company.equalsIgnoreCase("California PFW")) {
					   company = "California pfw";
				  }
				System.out.println("Lease --" + leaseEntityID + "-- " + (i + 1));
				if (PropertyWare.selectLease() == false) {
					String query = "Update Automation.BaseRentUpdate set Automation_Status='Failed',Automation_Notes='"
							+ failedReason + "',Automation_CompletionDate =getdate() where ID = '" + ID + "'";
					DataBase.updateTable(query);
					previousRecordCompany = company;
					continue;
				}
				loggedOut = false;
				previousRecordCompany = company;
				if (UpdateBaseRent.getBaseRentAmount() == false) {
					String query = "Update Automation.BaseRentUpdate set Automation_Status='Failed',Automation_Notes='"
							+ failedReason + "',Automation_CompletionDate =getdate(),BaseRentFromAutoCharges='"
							+ baseRentAmount + "',BaseRentFromPW = '" + baseRentFromPW + "',PortfolioName ='"+ portfolioName +"' where ID = '" + ID + "'";
					DataBase.updateTable(query);
					continue;
				}

				if (UpdateBaseRent.updateBaseRent() == false) {
					String query = "Update Automation.BaseRentUpdate set Automation_Status='Failed',Automation_Notes='"
							+ failedReason + "',Automation_CompletionDate =getdate(),BaseRentFromAutoCharges='"
							+ baseRentAmount + "',BaseRentFromPW = '" + baseRentFromPW + "',PortfolioName ='"+ portfolioName +"' where ID = '" + ID + "'";
					DataBase.updateTable(query);
					continue;
				}

				// Update table for successful lease
				try {
					System.out.println("Base Rent Updated");
					String query = "Update Automation.BaseRentUpdate set Automation_Status='Completed',Automation_Notes='"+ failedReason + "',Automation_CompletionDate =getdate(),BaseRentFromAutoCharges='"
							+ baseRentAmount + "',BaseRentFromPW = '" + baseRentFromPW + "',PortfolioName =["+ portfolioName +"] where ID = '" + ID + "'";
					DataBase.updateTable(query);
					continue;
				} catch (Exception e) {
				}
			} catch (Exception e) {
				continue;
			}
			// break;
		}
		RunnerClass.driver.quit();
	}

}
