package test.java.listeners;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.testng.ITestContext ;		
import org.testng.ITestListener ;		
import org.testng.ITestResult ;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;



import main.resources.ExtentReporterNG;
import test.java.basetest.BaseTest;	

public class Listeners extends BaseTest implements ITestListener{

	ExtentReports extent = ExtentReporterNG.getReportObject();
	ExtentTest test;
	ThreadLocal<ExtentTest> extentTest = new ThreadLocal();
	
	
	
	
	@Override		
	public void onTestStart(ITestResult result) {					
		
	   test = extent.createTest(result.getMethod().getMethodName());
	   extentTest.set(test);
	}	
	 
	 @Override		
	 public void onTestSuccess(ITestResult result) {					
	    
		 extentTest.get().log(Status.PASS, "Test passed");
	}	
	 
	 @Override		
	 public void onTestFailure(ITestResult result) {
		 
		 extentTest.get().fail(result.getThrowable());
		
		 //WebDriver
		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        // Take Screenshot and add to the test description
		String filePath = null;
		try {
			filePath = getScreenshot(result.getMethod().getMethodName(),driver);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		extentTest.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName()); 
		
		addCommonTestInfo(result);
       
	}		 
	
	@Override		
    public void onFinish(ITestContext result) {	
		
        extent.flush();		   		
    }	
	
	@Override		
    public void onTestSkipped(ITestResult result) {					
        // TODO Auto-generated method stub				
    	extentTest.get().skip(result.getThrowable());

        addCommonTestInfo(result);		
    }
	
	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub	
		
	}    		

    @Override		
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {					
        // TODO Auto-generated method stub				
        		
    }			

    private void addCommonTestInfo(ITestResult result) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startTime = sdf.format(new Date(result.getStartMillis()));
        String endTime = sdf.format(new Date(result.getEndMillis()));
        extentTest.get().info("Start time: " + startTime);
        extentTest.get().info("End time: " + endTime);
        extentTest.get().info("Total execution time: " + (result.getEndMillis() - result.getStartMillis()) + " ms");

        Object[] parameters = result.getParameters();
        if (parameters.length > 0) {
            String params = Arrays.toString(parameters);
            extentTest.get().info("Test data: " + params);
        }
    }
    
   
    
}