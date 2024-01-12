package mainPackage;


import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UpdateBaseRent 
{
	public static boolean getBaseRentAmount() throws Exception
	{
		try
		{
		RunnerClass.driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
        RunnerClass.wait = new WebDriverWait(RunnerClass.driver, Duration.ofSeconds(5));
        RunnerClass.driver.findElement(Locators.summaryEditButton).click();
        Thread.sleep(2000);
        String BaseRentFieldValueFromPW = RunnerClass.driver.findElement(Locators.BaseRentFieldinPW).getAttribute("value").replace("$", "");
        RunnerClass.baseRentFromPW = BaseRentFieldValueFromPW;
        System.out.println("Base rent in PW field - " + RunnerClass.baseRentFromPW);
        Thread.sleep(2000);
		RunnerClass.js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.newAutoCharge)).build().perform();
		
 		List<WebElement> autoChargeCodes = RunnerClass.driver.findElements(Locators.autoCharge_List);
		List<WebElement> autoChargeAmounts = RunnerClass.driver.findElements(Locators.autoCharge_Amount);
		List<WebElement> autoChargeStartDates = RunnerClass.driver.findElements(Locators.autoCharge_StartDate);
		List<WebElement> autoChargeEndDates = RunnerClass.driver.findElements(Locators.autoCharge_EndDate);
		
		try
		{
			if(autoChargeCodes.size()==1&&autoChargeCodes.get(0).getText().equals("No Charges"))
			{
				System.out.println("No Auto Charges");
				RunnerClass.failedReason = "No Auto Charges";
				return false;
			}
				
		}
		catch(Exception e)
		{
			
		}
		
		String dateCalculated=null;
		boolean baseRentAvailable = false;
		if(RunnerClass.dateDifference.equals("")){
			RunnerClass.failedReason = "No Move In date";
			return false;
		}
		int days = Integer.parseInt(RunnerClass.dateDifference);
		if(days<=62)
			dateCalculated = CommonMethods.getCalculatedDate(RunnerClass.moveInDate);
		else 
			dateCalculated =CommonMethods.getCurrentDate();  
		System.out.println(dateCalculated.toString());
		double rentCalculated =0.00d;
		for(int i=0;i<autoChargeCodes.size();i++)
		{
			String autoChargeCode = autoChargeCodes.get(i).getText().split("-")[0].trim();
			if(AppConfig.getMonthlyRentChargeCode(RunnerClass.company).contains(autoChargeCode))
			{
				String autoChargeStartDate = autoChargeStartDates.get(i).getText();
				String autoChargeEndDate = autoChargeEndDates.get(i).getText();
				String autoChargeAmount = autoChargeAmounts.get(i).getText();
				if(CommonMethods.compareDates(autoChargeStartDate,dateCalculated)==true&&((autoChargeEndDate.trim().equals(""))||CommonMethods.compareDates(dateCalculated, autoChargeEndDate))&&!autoChargeAmount.contains("-$"))
				{
					String baseRent =  autoChargeAmounts.get(i).getText();
					double d =Double.parseDouble(baseRent.substring(1, baseRent.length()).replace(",", ""));
					rentCalculated = d + rentCalculated;
					
					baseRentAvailable = true;
					//break;
				}
			}
		}
		if(baseRentAvailable==true)
		{
			RunnerClass.baseRentAmount =String.valueOf(rentCalculated);
			if(RunnerClass.baseRentAmount.endsWith(".0"))
			{
				RunnerClass.baseRentAmount= RunnerClass.baseRentAmount+"0";
				System.out.println(RunnerClass.baseRentAmount+" is the Base Rent");
				//updateBaseRent();
			}
			
		}
		if(baseRentAvailable == false) 
		{
			System.out.println("Base Rent is not available");
			RunnerClass.failedReason = "Base Rent Not Available";
			//RunnerClass.baseRentAmount = "Not Found";
			return false;
		}
		
		return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			RunnerClass.failedReason = "Could not find Base Rent";
			return false;
		}
	}
	
	public static boolean updateBaseRent()
	{
		try
		{
			RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.baseRent)).build().perform();
			if(RunnerClass.driver.findElement(Locators.baseRent).getAttribute("value").replace("$", "").replace(",", "").split(Pattern.quote(".")).length != 0){
				if( RunnerClass.driver.findElement(Locators.baseRent).getAttribute("value").replace("$", "").replace(",", "").split(Pattern.quote("."))[0].equals(RunnerClass.baseRentAmount.split(Pattern.quote("."))[0]))
				{
					RunnerClass.failedReason = "Base Rent Already Exists";
					return true;
				}
				else
				{
					RunnerClass.driver.findElement(Locators.baseRent).sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
				    RunnerClass.driver.findElement(Locators.baseRent).sendKeys(RunnerClass.baseRentAmount);  
				    String UpdatedBaseRentFieldValue = RunnerClass.driver.findElement(Locators.baseRent).getAttribute("value").replace("$", "");
			        //RunnerClass.baseRentFromPW = UpdatedBaseRentFieldValue;
				    System.out.println("Updated Base rent in PW field - " + UpdatedBaseRentFieldValue);
				    	 if(AppConfig.saveButtonOnAndOff==false)
				 		{
				 			 RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.cancelLease)).build().perform();
				 			 RunnerClass.driver.findElement(Locators.cancelLease).click();
				 			 return true;
				 		}
				 	    else 
				 	    {
				 			 RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.saveLease)).build().perform();
				 			 RunnerClass.driver.findElement(Locators.saveLease).click();
				 			 PropertyWare.evictionPopUp();
				 			 Thread.sleep(2000);
				 			 try
				 			 {
				 				RunnerClass.driver.switchTo().alert().accept();
				 				RunnerClass.failedReason = RunnerClass.failedReason + "";
				 			 }
				 			 catch(Exception e) {}
				 			 
				 			 try
				 			 {
				 			 if(RunnerClass.driver.findElement(Locators.saveLease).isDisplayed())
				 			 {
				 				 RunnerClass.failedReason = RunnerClass.failedReason + "Base Rent could not be saved";
				 				 return false;
				 			 }
				 			 }
				 			 catch(Exception e) {}
				 			 return true;
				 	    }
				    	
				}
				
			}
	
			else {
				
				RunnerClass.driver.findElement(Locators.baseRent).sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
			    RunnerClass.driver.findElement(Locators.baseRent).sendKeys(RunnerClass.baseRentAmount);
			    String UpdatedBaseRentFieldValue = RunnerClass.driver.findElement(Locators.baseRent).getAttribute("value").replace("$", "");
		        //RunnerClass.baseRentFromPW = UpdatedBaseRentFieldValue;
		       System.out.println("Updated Base rent in PW field - " + UpdatedBaseRentFieldValue);
			    	 if(AppConfig.saveButtonOnAndOff==false)
			 		{
			 			 RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.cancelLease)).build().perform();
			 			 RunnerClass.driver.findElement(Locators.cancelLease).click();
			 			 return true;
			 		}
			 	    else 
			 	    {
			 			RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.saveLease)).build().perform();
			 			 RunnerClass.driver.findElement(Locators.saveLease).click();
			 			 PropertyWare.evictionPopUp();
			 			 Thread.sleep(2000);
			 			 try
			 			 {
			 				 RunnerClass.driver.switchTo().alert().accept();
			 				 RunnerClass.failedReason = RunnerClass.failedReason + "";
			 			 }
			 			 catch(Exception e) {}
			 			 try
			 			 {
			 			 if(RunnerClass.driver.findElement(Locators.saveLease).isDisplayed())
			 			 {
			 				 RunnerClass.failedReason = RunnerClass.failedReason + "Base Rent could not be saved";
			 				 return false;
			 			 }
			 			 }
			 			 catch(Exception e) {}
			 			 return true;
			}
 			
			}	
		}
		catch(Exception e)
		{
			RunnerClass.failedReason = RunnerClass.failedReason + "Base Rent could not be saved";
			return false;
		}
	}

}
