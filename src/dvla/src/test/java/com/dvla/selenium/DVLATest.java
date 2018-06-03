package com.dvla.selenium;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.dvla.csv.ReadCSV;
import com.dvla.dataobject.VehicleDetails;

import junit.framework.TestCase;

public class DVLATest extends TestCase{
	private String driverLocation = null;
	public DVLATest(String testName) {
		super(testName);
	}

	protected void setUp() throws Exception {
		super.setUp();	 
		driverLocation = "C:\\selenium\\drivers\\chromedriver_win32\\chromedriver.exe";
		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testValidateVehicleDetails() {
		List<VehicleDetails> expectedList = new ArrayList();
		
		expectedList = ReadCSV.getInstance().getVehicleDetails("C:\\source\\final\\testdata.csv");
		ConnectDVLASite site = new ConnectDVLASite();
		VehicleDetails dtls =  null;
		VehicleDetails outputDtls =  null;
		for ( int i = 0 ; i< expectedList.size() ; i++ ) {
			dtls = expectedList.get(i);
			try {
				outputDtls = site.connectSite( dtls.getVehicleNumber(), driverLocation );
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				assertEquals(dtls,outputDtls);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				assertEquals(dtls,outputDtls);
			}
			assertEquals( dtls != null , outputDtls != null );
			assertEquals( dtls.getVehicleNumber().toUpperCase() , outputDtls.getVehicleNumber().toUpperCase() );
			assertEquals( dtls.getVehicleModel().toUpperCase() , outputDtls.getVehicleModel().toUpperCase() );
			assertEquals( dtls.getVehicleColor().toUpperCase() , outputDtls.getVehicleColor().toUpperCase() );
		}
		
		
	}
	
}
