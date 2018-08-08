package CustomerListner;

import org.apache.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.Reporter;


public class Retry implements IRetryAnalyzer
{
	public static final Logger log = Logger.getLogger(Retry.class.getName());
	private int min_Count = 0;
	private int max_Count = 2;
	
	
	public boolean retry(ITestResult arg0) 
	{
		if(min_Count < max_Count)
		{
			log("Retrying Test" + arg0.getName() + "With Status" + getResulStatusName(arg0.getStatus()) + 
					"For the" + (min_Count+1) + "Times");
			min_Count++;
			return true;
		}
		
		
		return false;
	}


	public void log(String data) 
	{
		log.info(data);
		Reporter.log(data);
		
	}


	public String getResulStatusName(int status) 
	{
		String resultName = null;
		if(status == 1)
			resultName = "SUCCESS";
		if(status == 2)
			resultName = "FAILURE";
		if(status == 3)
			resultName = "SKIPPED";
		
		
		return resultName;
	}

}
