package com.dvla.csv;

import java.util.ArrayList;
import java.util.List;

import com.dvla.dataobject.VehicleDetails;

import junit.framework.TestCase;

public class ReadCSVTest extends TestCase{

	public ReadCSVTest(String testName) {
		super(testName);
	}

	protected void setUp() throws Exception {
		super.setUp();	 
		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testGetVehicleDetailsWithSourceEmpty() {
		List<VehicleDetails> expectedList = null;
		List<VehicleDetails> outputList = new ArrayList<VehicleDetails>();
			outputList = ReadCSV.getInstance().getVehicleDetails("");
			assertEquals(expectedList, outputList);
		
	}
	
	public void testGetVehicleDetailsInvalidSource() {
		List<VehicleDetails> expectedList = null;
		List<VehicleDetails> outputList = new ArrayList<VehicleDetails>();
		
		outputList = ReadCSV.getInstance().getVehicleDetails("C:\\testing");
		assertEquals(expectedList, outputList);
		
	}
	
	public void testGetVehicleDetailsWithoutSourceFileExtension() {
		List<VehicleDetails> expectedList = null;
		List<VehicleDetails> outputList = null;
		
		outputList = ReadCSV.getInstance().getVehicleDetails("C:\\source\\final\\testdata");
		assertEquals(expectedList, outputList);
		
	}

	public void testGetVehicleDetailsWithWrongSourceFileExtension() {
		List<VehicleDetails> expectedList = null;
		List<VehicleDetails> outputList = null;
		
		outputList = ReadCSV.getInstance().getVehicleDetails("C:\\source\\final\\Book1.xlsx");
		assertEquals(expectedList, outputList);
		
	}
	
	public void testGetVehicleDetailsWithValidSource() {
		List<VehicleDetails> expectedList = new ArrayList();
		VehicleDetails dtls = new VehicleDetails();
		dtls.setVehicleColor( "Bronze" );
		dtls.setVehicleModel("Toyota");
		dtls.setVehicleNumber("GY67UBZ");
		expectedList.add(dtls);
		List<VehicleDetails> outputList = new ArrayList<VehicleDetails>();
		
		outputList = ReadCSV.getInstance().getVehicleDetails("C:\\source\\final\\testdata.csv");
		dtls = outputList.get(0);
		assertEquals(expectedList.size(), outputList.size());
		assertEquals("Bronze".toUpperCase(), dtls.getVehicleColor().toUpperCase());
		assertEquals("Toyota".toUpperCase(), dtls.getVehicleModel().toUpperCase());
		assertEquals("GY67UBZ".toUpperCase(), dtls.getVehicleNumber().toUpperCase());
		
	}
	
		

}
