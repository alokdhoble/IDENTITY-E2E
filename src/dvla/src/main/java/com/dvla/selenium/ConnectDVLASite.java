package com.dvla.selenium;

import java.net.MalformedURLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dvla.dataobject.VehicleDetails;

public class ConnectDVLASite {

	public static void main(String args[]) throws InterruptedException, MalformedURLException {
		ConnectDVLASite site = new ConnectDVLASite();
		site.connectSite("GY67UBZ",  "C:\\selenium\\drivers\\chromedriver_win32\\chromedriver.exe" );
	}

	public VehicleDetails connectSite(String regNo, String dirverLoation) throws InterruptedException, MalformedURLException {

		VehicleDetails details = null;
		//dirverLoation = "C:\\selenium\\drivers\\chromedriver_win32\\chromedriver.exe";

		System.setProperty("webdriver.chrome.driver", dirverLoation);


		ChromeOptions options = new ChromeOptions();
		options.addArguments("chrome.switches", "--disable-extensions");
		options.addArguments("start-maximized");
		WebDriver driver = new ChromeDriver(options);

		driver.get("https://vehicleenquiry.service.gov.uk/");

		Thread.sleep(10000);

		WebElement submitButton = driver.findElement(By.name("Continue"));
		Actions actions = new Actions(driver);
		actions.moveToElement(submitButton).perform();

		WebElement vrmInputField = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.name("Vrm")));
		vrmInputField.sendKeys( regNo );
		vrmInputField.submit();
		Thread.sleep(5000);

		String page = driver.getPageSource();

		Pattern r = Pattern.compile("<input id=\"Vrm\".*?>");

		// Now create matcher object.
		Matcher m = r.matcher(page);
		String str = null;
		if (m.find()) {
			details = new VehicleDetails();
			str = m.group(0);
			str = str.substring( str.indexOf("value=\"") +7 , str.lastIndexOf("\""));
			//System.out.println("Reg : Found value: " + m.group(0));
			details.setVehicleNumber( str );
			
			
			r = Pattern.compile("<input id=\"Make\".*?>");
			m = r.matcher(page);
			if (m.find()) {
				str = m.group(0);
				str = str.substring( str.indexOf("value=\"") +7 , str.lastIndexOf("\""));
				//System.out.println("Make : Found value: " + m.group(0));
				System.out.println("Make : Found value: " + str);
				details.setVehicleModel( str );
				
			}
			
			r = Pattern.compile("<input id=\"Colour\".*?>");
			m = r.matcher(page);
			if (m.find()) {
				str = m.group(0);
				str = str.substring( str.indexOf("value=\"") +7 , str.lastIndexOf("\""));
				//System.out.println("Macoloke : Found value: " + m.group(0));
				System.out.println("Colour : Found value: " + str);
				details.setVehicleColor( str );				
			}
			
		}
		
		
		driver.quit();
		// Check the title of the page
		return details ;
	}
}
