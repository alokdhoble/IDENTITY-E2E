package com.filehandling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class CheckFilePropertiesTest extends TestCase {

	private List testList = null;
	public CheckFilePropertiesTest(String testName) {
		super(testName);
	}

	protected void setUp() throws Exception {
		super.setUp();	 
		testList = new ArrayList();
		FileProperty prop = new FileProperty();
		prop.setFileDirectory("C:\\source\\Temp");
		prop.setFileName("Book2.csv");
		prop.setFileExtention("csv");
		prop.setFileMimeType("application/vnd.ms-excel");
		prop.setFileSize("0.01 Kb");
		testList.add(prop);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	
	public void testcheckFilePropertyWithSourceEmpty() {
		List<FileProperty> expectedList = null;
		List<FileProperty> outputList = new ArrayList<FileProperty>();
		try {
			outputList = CheckFileProperties.getInstance().checkFileProperty("");
			assertEquals(expectedList, outputList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void testcheckFilePropertyWithInvalidSource() {
		List<FileProperty> expectedList = null;
		List<FileProperty> outputList = new ArrayList<FileProperty>();
		try {
			outputList = CheckFileProperties.getInstance().checkFileProperty("C:\\testing");
			assertEquals(expectedList, outputList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void testcheckFilePropertyWithValidSource() {
		
		List<FileProperty> outputList = new ArrayList<FileProperty>();
		try {
			outputList = CheckFileProperties.getInstance().checkFileProperty("C:\\source\\Temp");
			assertEquals(testList.size(), outputList.size());
			FileProperty prop = outputList.get(0);
			assertEquals( "C:\\source\\Temp" , prop.getFileDirectory());
			assertEquals( "csv" , prop.getFileExtention());
			assertEquals( "application/vnd.ms-excel" , prop.getFileMimeType());
			assertEquals( "Book2.csv" , prop.getFileName());
			assertEquals( "0.01 Kb" , prop.getFileSize());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
