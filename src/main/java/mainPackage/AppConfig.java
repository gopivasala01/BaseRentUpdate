package mainPackage;

public class AppConfig 
{
	   public static boolean saveButtonOnAndOff= false;
		
	   public static String URL ="https://app.propertyware.com/pw/login.jsp";
	   public static String username ="mds0418@gmail.com";
	   public static String password ="KRm#V39fecMDGg#";
	   
	   public static String excelFileLocation = "E:\\Automation\\Base Rent Update";
	   public static String downloadFilePath = "C:\\SantoshMurthyP\\Initial Rents Update - Branches\\Tennessee";
	   
	   public static String buildingPageURL = "https://app.propertyware.com/pw/leases/lease_detail.do?entityID=";
	   //Mail credentials
	   public static String fromEmail = "bireports@beetlerim.com";
	   public static String fromEmailPassword = "Welcome@123";
	   
	   public static String toEmail = "gopi.v@beetlerim.com";
	   public static String CCEmail = "gopi.v@beetlerim.com";
	   
	   public static String mailSubject = "Base Rent Update for  ";
	   
	   public static String[] LeaseAgreementFileNames = {"REVISED_Lease_","Lease_","Leases_"};
	   
	   public static String[] IAGClientList = {"510.","AVE.","BTH.","CAP.","FOR.","HRG.","HS.","MAN.","MCH.","OFF.","PIN.","RF.","SFR3.","TH.","HH.","Lofty.Ai","TA."};
	   
	   public static String connectionUrl = "jdbc:sqlserver://azrsrv001.database.windows.net;databaseName=HomeRiverDB;user=service_sql02;password=xzqcoK7T;encrypt=true;trustServerCertificate=true;";
	   
	  // public static String leaseFetchQuery  = "Select Company, Building,leaseName from Automation.InitialRentsUpdate where Status ='Pending' and Company ='Georgia'";
	   
	   public static String pendingLeasesQuery = "Select ID, Company, LeaseEntityID,DateDiff(Day,MoveInDate,Getdate()) as datedifference,moveInDate from Automation.BaseRentUpdate where  Automation_notes='Could not find Base rent'";//Company ='Alabama' and convert(date, Automation_Completiondate )<> '2023-07-05'";
	   
	   public static String failedLeasesQuery = "Select Company, LeaseEntityID,DateDiff(Day,MoveInDate,Getdate()) as datedifference,moveInDate from Automation.BaseRentUpdate where  Company='Alabama' and Status ='Failed'";
	   
	   public static String getLeasesWithStatusforCurrentDay = "Select Company, Building,ThirdPartyUnitID, Leaseidnumber, LeaseName,LeaseStatus,leaseExecutionDate, StartDate, EndDate, MonthlyRent, MonthlyRentFromPW, PetRent, PetRentFromPW,Status, Notes from Automation.InitialRentsUpdate and company in ('Tennessee') ";//where Format(convert(datetime, CompletedDate, 101),'dd MM yyyy') = format(getdate(),'dd MM yyyy') ";//and company in ('Florida','North Carolina')";
	   
	   public static String getMonthlyRentChargeCode(String company)
	   {
		   switch(company)
		   {
		   case "Austin":
			   return "40010 - Rent Income";
		   case "California":
			   return "40010 - Rent Income";
		   case "California PFW":
			   return "40010 - Rent Income";
		   case "Chattanooga":
			   return "40010 - Rent Income";
		   case "Chicago PFW":
			   return "40010 - Rent Income";
		   case "Colorado Springs":
			   return "40010 - Rent Income";
		   case "Kansas City":
			   return "40010 - Rent Income";
		   case "Houston":
			   return "40010 - Rent Income";
		   case "Maine":
			   return "40010 - Rent Income";
		   case "Savannah":
			   return "40010 - Rent Income";
		   case "North Carolina":
			   return "40010 - Rent Income";
		   case "Alabama":
			   return "40010,40011,40012, 40013,40014,40015,40016,40017,40018,40019,40020,40021,40022,40023,40024,40025,40026,40027,40028,40029,40051,40061,40200";
		   case "Arkansas":
			   return "40010 - Rent Income";
		   case "Dallas/Fort Worth":
			   return "40010 - Rent Income";
		   case "Indiana":
			   return "40010 - Rent Income";
		   case "Little Rock":
			   return "40010 - Rent Income";
		   case "San Antonio":
			   return "40010 - Rent Income";
		   case "Tulsa":
			   return "40010 - Rent Income";
		   case "Georgia":
			   return "40010 - Rent Income";
		   case "OKC":
			   return "40010 - Rent Income";
		   case "South Carolina":
			   return "40010 - Rent Income";
		   case "Florida":
			   return "40010 - Rent Income";
		   case "Tennessee":
			   return "40010 - Rent Income";
		   case "New Mexico":
			   return "40010 - Rent Income";
		   case "Ohio":
			   return "40010 - Rent Income";
		   case "Pennsylvania":
			   return "40010 - Rent Income";
		   case "Lake Havasu":
			   return "40010 - Rent Income";
		   case "Saint Louis":
			   return "40010 - Rent Income";
		   case "Maryland":
			   return "40010 - Rent Income";
		   case "Virginia":
			   return "40010 - Rent Income";
		   case "Boise":
			   return "40010 - Rent Income";
		   case "Idaho Falls":
			   return "40010 - Rent Income";
			      
		   }
		   return "";
	   }
	   

}
