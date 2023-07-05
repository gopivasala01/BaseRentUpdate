package mainPackage;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class CommonMethods 
{
	public static String getCalculatedDate(String dateRaw)
	{
		try
		{
			
			// create instance of the SimpleDateFormat that matches the given date  
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
	          
	        //create instance of the Calendar class and set the date to the given date  
	        Calendar cal = Calendar.getInstance();  
	          cal.setTime(sdf.parse(dateRaw));
	          
	        System.out.println(sdf.format(cal.getTime())+" is the date before adding days");  
	          
	        // use add() method to add the days to the given date  
	        cal.add(Calendar.DAY_OF_MONTH, 62);  
	        String dateAfter = sdf.format(cal.getTime());  
	          
	        //date after adding three days to the given date  
	        System.out.println(dateAfter+" is the date after adding 62 days.");
	        SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd/yyyy");
	        Date date = sdf.parse(dateAfter.trim());
		    System.out.println(sdf2.format(date));
		    String d = sdf2.format(date).toString();;
			return sdf2.format(date).toString();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "";
		}
	}
	public static String getCurrentDate()
    {
    	 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");  
		 LocalDateTime now = LocalDateTime.now();  
		// System.out.println(dtf.format(now));
		String d =  dtf.format(now);
		return d;
    }
	
	public static boolean compareDates(String date1, String date2)
	{
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			System.out.println(sdf.parse(date1).before(sdf.parse(date2)));
			if(sdf.parse(date1).before(sdf.parse(date2)))
				return true;
				else return false;
		}
		catch(Exception e)
		{
		return false;
		}
	}

}
