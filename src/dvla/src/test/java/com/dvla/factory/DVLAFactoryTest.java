package com.dvla.factory;

import com.dvla.csv.ReadCSV;
import com.dvla.excel.ReadExcel;
import com.dvla.inter.DvlaVehicle;

import junit.framework.TestCase;

public class DVLAFactoryTest extends TestCase {
	public DVLAFactoryTest(String testName) {
		super(testName);
	}

	protected void setUp() throws Exception {
		super.setUp();	 
		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testFactoryWithEmptyExtension() {
		DVLAFactory factory = new DVLAFactory();
		DvlaVehicle vehicle = factory.getVehicleDetailsFromFile("");
		assertEquals( null , vehicle);
		vehicle = factory.getVehicleDetailsFromFile( null );
		assertEquals( null , vehicle);
		
	}

	public void testFactoryWithInvalidExtension() {
		DVLAFactory factory = new DVLAFactory();
		DvlaVehicle vehicle = factory.getVehicleDetailsFromFile("abc");
		assertEquals( null , vehicle);
		
	}
	
	public void testFactoryForExcelObject() {
		DVLAFactory factory = new DVLAFactory();
		DvlaVehicle vehicle = factory.getVehicleDetailsFromFile("xls");
		assertEquals( ReadExcel.class , vehicle.getClass() );
		
	}
	
	public void testFactoryForCSVObject() {
		DVLAFactory factory = new DVLAFactory();
		DvlaVehicle vehicle = factory.getVehicleDetailsFromFile("csv");
		assertEquals( ReadCSV.class , vehicle.getClass() );
		
	}
	

	
}
