package com.dvla.excel;

import java.util.ArrayList;
import java.util.List;

import com.dvla.dataobject.VehicleDetails;

import junit.framework.TestCase;

public class ReadExcelTest extends TestCase{

	public ReadExcelTest(String testName) {
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
			outputList = ReadExcel.getInstance().getVehicleDetails("");
			assertEquals(expectedList, outputList);
		
	}
	
	public void testGetVehicleDetailsInvalidSource() {
		List<VehicleDetails> expectedList = null;
		List<VehicleDetails> outputList = new ArrayList<VehicleDetails>();
		
		outputList = ReadExcel.getInstance().getVehicleDetails("C:\\testing");
		assertEquals(expectedList, outputList);
		
	}
	
	public void testGetVehicleDetailsWithoutSourceFileExtension() {
		List<VehicleDetails> expectedList = null;
		List<VehicleDetails> outputList = null;
		
		outputList = ReadExcel.getInstance().getVehicleDetails("C:\\source\\final\\testdata");
		assertEquals(expectedList, outputList);
		
	}

	public void testGetVehicleDetailsWithWrongSourceFileExtension() {
		List<VehicleDetails> expectedList = null;
		List<VehicleDetails> outputList = null;
		
		outputList = ReadExcel.getInstance().getVehicleDetails("C:\\source\\final\\testdata.csv");
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
		
		outputList = ReadExcel.getInstance().getVehicleDetails("C:\\source\\final\\Book1.xlsx");
		dtls = outputList.get(0);
		assertEquals(expectedList.size(), outputList.size());
		assertEquals("Bronze".toLowerCase(), dtls.getVehicleColor().toLowerCase());
		assertEquals("Toyota".toLowerCase(), dtls.getVehicleModel().toLowerCase());
		assertEquals("GY67UBZ".toLowerCase(), dtls.getVehicleNumber().toLowerCase());
		
	}
	
	public void testGetVehicleDetailsWithValidSourceMultipleData() {
		List<VehicleDetails> expectedList = new ArrayList();
		VehicleDetails dtls = new VehicleDetails();
		dtls.setVehicleColor( "Bronze" );
		dtls.setVehicleModel("Toyota");
		dtls.setVehicleNumber("GY67UBZ");
		expectedList.add(dtls);
		List<VehicleDetails> outputList = new ArrayList<VehicleDetails>();
		
		outputList = ReadExcel.getInstance().getVehicleDetails("C:\\source\\final\\Book1.xlsx");
		for ( int i = 0 ; i < outputList.size() ; i++ ) {
			dtls = outputList.get(i);
			System.out.println( dtls.getVehicleNumber() +   ", " + dtls.getVehicleModel() + ", " + dtls.getVehicleColor()   );
		}
		
	}
	
		

}
