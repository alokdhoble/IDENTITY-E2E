package com.dvla.integration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

import com.dvla.dataobject.VehicleDetails;
import com.dvla.factory.DVLAFactory;
import com.dvla.inter.DvlaVehicle;
import com.dvla.selenium.ConnectDVLASite;
import com.filehandling.CheckFileProperties;
import com.filehandling.FileProperty;

public class VerifyVehicleWithDvla {
	
	private String sourceDir  = null; 
	private String driverLocation = null;
	public VerifyVehicleWithDvla() throws IOException {
		Properties prop = new Properties();
		InputStream input = null;
		input = new FileInputStream("input.properties");

		// load a properties file
		prop.load(input);
		String propFileLocation = prop.getProperty("propFileLocation");

		Properties props = new Properties();
		props.load(new FileInputStream(propFileLocation));
		PropertyConfigurator.configure(props);

		sourceDir = prop.getProperty("source.directory");
		driverLocation = prop.getProperty("chromeDriverLocation");

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		

		CheckFileProperties prop;
		
		List<FileProperty> vehicleFileList = null;
		try {
			prop = CheckFileProperties.getInstance();
			vehicleFileList = prop.checkFileProperty( prop.getSourceDir() );
			VerifyVehicleWithDvla verification = new VerifyVehicleWithDvla();
			List<VehicleDetails> finalList = verification.getDetailsFromEachFile(vehicleFileList);
			verification.verifyWithDVLAWebsite(finalList);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println( "Program completed..." );
	}
	
	public List<VehicleDetails> getDetailsFromEachFile( List<FileProperty> vehicleFileList ) {
		FileProperty fileProp = null;
		DvlaVehicle vehicle = null;
		DVLAFactory factory = new DVLAFactory();
		List<VehicleDetails> finalList = new ArrayList<VehicleDetails>();
		List<VehicleDetails> tempList = null; 
		for ( int i = 0; i< vehicleFileList.size() ; i++) {
			fileProp = vehicleFileList.get(i);
			vehicle = factory.getVehicleDetailsFromFile(fileProp.getFileExtention());
			tempList = vehicle.getVehicleDetails( fileProp.getFileDirectory() + "\\" + fileProp.getFileName()  );
			if ( tempList != null && tempList.size() > 0 ) {
				finalList.addAll(tempList);
			}
		}
		return finalList;
	}
	
	public void verifyWithDVLAWebsite(List<VehicleDetails> finalList ) {
		
		VehicleDetails details = null;
		VehicleDetails outDtls = null;
		ConnectDVLASite site = new ConnectDVLASite();
		for ( int i = 0; i< finalList.size() ; i++) {
			details = finalList.get(i);
			try {
				outDtls = site.connectSite(details.getVehicleNumber(),  driverLocation );
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if ( outDtls == null ) {
				System.err.println( details.getVehicleNumber() + " is not available on DVLA site... " );
			} else {
				if ( ! details.getVehicleNumber().toUpperCase().equals( outDtls.getVehicleNumber().toUpperCase() ) || 
						!details.getVehicleModel().toUpperCase().equals( outDtls.getVehicleModel().toUpperCase() ) ||
						!details.getVehicleColor().toUpperCase().equals( outDtls.getVehicleColor().toUpperCase() )) {
					System.err.println( details.getVehicleNumber() + " details are not mached with DVLA... " );
				} else {
					System.out.println( details.getVehicleNumber() + " details matched...");
				}
			}
		}
	}
}
